package com.xxsword.xitem.admin.utils;

import com.xxsword.xitem.admin.config.SystemConfig;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.vo.UpFileVO;
import com.xxsword.xitem.admin.model.UpState;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * 上传工具类
 *
 * @author songJian
 * @version 2018-3-29
 */
@Slf4j
public class UpLoadUtil {

    public static final String PATH_INFO = "/fileinfo";// 所有文件路径都会加的前缀，用作nginx转发可以减轻java压力

    private static final String PATH_DEF = "/def";// 默认文件夹

    private static final String PATH_TEMP = "/temp";// 临时文件夹

    public static String upload(MultipartFile file) {
        return upload(file, PATH_DEF);
    }

    public static String upload(MultipartFile file, String path) {
        path = doPath(path);// 格式检查
        path = path + getTIMEPath();
        return uploadLocal(file, path);
    }

    public static List<UpFileVO> upload(MultipartFile[] files, String path) {
        List<UpFileVO> list = new ArrayList<>();
        for (MultipartFile item : files) {
            UpFileVO m = new UpFileVO();
            m.setUrl(upload(item, path));
            m.setName(item.getOriginalFilename());
            list.add(m);
        }
        return list;
    }

    /**
     * 获取项目路径
     *
     * @return
     */
    public static String getProjectPath() {
        String p = getPath();
        return p.substring(0, p.length() - PATH_INFO.length());
    }

    /**
     * 校验目录路径的格式（要前带杠杠，后不带）
     *
     * @param path
     * @return
     */
    public static String doPath(String path) {
        path = replaceSeparator(path);
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 将文件从临时文件夹里面移动出来（本方法专门用作临时文件夹的文件移动，不要用做其它）
     */
    public static String tempToFileInfoPath(String url) {
        String[] p0 = FilenameUtils.getFullPathNoEndSeparator(url).split(PATH_TEMP);
        String newFileName = Utils.getuuid() + "." + FilenameUtils.getExtension(url);
        String newPath = PATH_DEF + getTIMEPath() + "/";
        try {
            FileUtils.moveFile(new File(url), new File(p0[0] + newPath + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PATH_INFO + newPath + newFileName;
    }

    /**
     * 获取时间分隔字符
     */
    public static String getTIMEPath() {
        return "/" + DateUtil.now(DateUtil.sdfA4);
    }

    /**
     * @param file
     * @param path
     * @return
     */
    private static String uploadLocal(MultipartFile file, String path) {
        if (file != null && !file.isEmpty()) {
            try {
                String fileSuffix = "." + FilenameUtils.getExtension(file.getOriginalFilename());
                String id = Utils.getuuid();
                String rootPath = getPath();
                String filePath = path + "/" + id + fileSuffix;
                Utils.hasFolder(rootPath + path);
                file.transferTo(new File(rootPath, filePath));
                return PATH_INFO + filePath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 不指定资源目录时，默认路径的获取
     *
     * @return
     */
    private static String getDPath() {
        try {
            String url = ResourceUtils.getURL("").getPath();
            url = replaceSeparator(url);
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);// 去掉最后一个杠，保持：前带杠杠，后不带的风格
            }
            String pathStr = url + PATH_INFO;
            Utils.hasFolder(pathStr);
            log.debug("uploadPath:" + pathStr);
            return replaceSeparator(pathStr);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定的资源路径
     *
     * @return
     */
    private static String getPath() {
        int type = Integer.parseInt(SystemConfig.getProperty("filepath.type"));
        if (type == 1) {
            return getDPath();
        } else if (type == 2) {
            String path = SystemConfig.getProperty("filepath.path") + PATH_INFO;
            Utils.hasFolder(path);
            log.debug("uploadPath:" + path);
            return replaceSeparator(path);
        }
        return getDPath();
    }

    // 这个比较特殊，屁股后面有杠杠
    private static String tempPathById(String uuid) {
        return UpLoadUtil.getPath() + PATH_TEMP + "/" + uuid + "/";
    }

    /**
     * 分片文件上传,文件默认存储在temp/uuid文件夹中
     * 2020-06-29只允许上传压缩文件（Constant.COMPRESS_EX）
     */
    public static UpState sliceFileUpload(HttpServletRequest request) {
        UpState upState = new UpState();
        MultipartHttpServletRequest mQuest = (MultipartHttpServletRequest) request;
        MultipartFile file = mQuest.getFile("file");// 获得上传的文件
        String fName = file.getOriginalFilename().toLowerCase();
        if (!Constant.COMPRESS_EX.toLowerCase().contains(FilenameUtils.getExtension(fName).toLowerCase())) {// 只允许上传指定文件
            upState.setInfo(3);
            log.error("上传的文件格式，不是允许的格式:{}", FilenameUtils.getExtension(fName));
            return upState;
        }
        String uuid = request.getParameter("uuid");// 上传站点标识，用于区分各站点，以便判断文件合并信息
        if (uuid.contains(".") || uuid.contains("/")) {// 防止遍历攻击
            upState.setInfo(3);
            return upState;
        }
        String pathuuid = tempPathById(uuid);
        String chunkStr = request.getParameter("chunk");
        String chunksStr = request.getParameter("chunks");
        if (chunkStr == null) {
            chunkStr = "0";
        }
        if (chunksStr == null) {
            chunksStr = "1";
        }
        int chunk = Integer.parseInt(chunkStr);// 第几片
        int chunks = Integer.parseInt(chunksStr);// 一共分了几片
        String fileId = request.getParameter("id").toLowerCase();// 文件id（文件分片上传的时候，文件id是一样的，同一个站点不会有相同文件id，但是不同站点的id可能会相同）
        log.info("uuid:{},chunk:{},chunks:{}", uuid, chunk, chunks);
        String suffix = "";
        if (!file.isEmpty()) {
            String oldfilename = file.getOriginalFilename();
            suffix = FilenameUtils.getExtension(oldfilename);
            try {
                Utils.hasFolder(pathuuid);
                File uploadFile = new File(pathuuid + chunk + fileId + "." + suffix);
                FileCopyUtils.copy(file.getBytes(), uploadFile);
                int fileSize = 0;
                for (int i = 0; i < chunks; i++) {
                    File chunkFile = new File(pathuuid + i + fileId + "." + suffix);
                    if (chunkFile.exists()) {
                        fileSize++;
                    }
                }
                log.info("uuid:{},chunks:{},fileSize:{}", uuid, chunks, fileSize);
                if (chunks == fileSize) {// 文件全部到齐
                    log.info("xxx=uuid:{},chunks:{},fileSize:{},oldfilename:{}", uuid, chunks, fileSize, oldfilename);
                }
            } catch (Exception e) {
                log.error("分片文件接收出错");
                e.printStackTrace();
                clearErrorFile(chunks, pathuuid, fileId, suffix);
                upState.setInfo(3);
                return upState;
            }
        } else {
            log.error("分片文件为空,uuid:{},fileid:{}", uuid, fileId);
            upState.setInfo(4);
            return upState;
        }
        upState.setInfo(0);
        upState.setChunks(chunks);
        upState.setSuffix(suffix);
        upState.setUuid(uuid);
        upState.setFileid(fileId);
        return upState;// 每一次成功的分片上传，都返回详细信息
    }

    /**
     * 文件合并
     *
     * @param chunks
     * @param uuid
     * @param fileId
     * @param suffix
     * @return
     */
    public static UpState merge(Integer chunks, String uuid, String fileId, String suffix) {
        fileId = fileId.toLowerCase();
        String pathuuid = tempPathById(uuid);
        UpState upState = new UpState();
        String fFileName = fileId + "." + suffix;
        try {
            log.info("开始合并分片文件,uuid:{},fileid:{},fFileName:{}", uuid, fileId, fFileName);
            File destFile = new File(pathuuid + fFileName);
            OutputStream out = Files.newOutputStream(destFile.toPath());
            BufferedOutputStream bos = new BufferedOutputStream(out);
            for (int i = 0; i < chunks; i++) {// 按顺序，从第一个开始进行拼接
                File srcFile = new File(pathuuid + i + fileId + "." + suffix);
                if (!srcFile.exists()) {
                    log.error("分片丢失，chunk is null,i:{},fFileName:{}", i, fFileName);
                    clearErrorFile(chunks, pathuuid, fileId, suffix);
                    upState.setInfo(4);
                    return upState;
                }
                InputStream in = Files.newInputStream(srcFile.toPath());
                BufferedInputStream bis = new BufferedInputStream(in);
                byte[] bytes = new byte[1024 * 1024];// (1024 * 1024)byte=1MB
                int len = -1;
                while ((len = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                }
                bis.close();
                in.close();
                srcFile.delete();
            }
            bos.close();
            out.close();
            log.info("分片文件合并完成，fFileName：{}", fFileName);
            upState.setInfo(1);
            upState.setFileid(fileId);
            return upState;
        } catch (Exception e) {
            log.error("分片文件合并出错，fFileName：{}", fFileName);
            clearErrorFile(chunks, pathuuid, fileId, suffix);
            e.printStackTrace();
            upState.setInfo(2);
            return upState;
        }
    }

    /**
     * 清除异常分片,不然会影响下次上传
     *
     * @param chunks
     * @param pathuuid
     * @param fileid
     * @param suffix
     */
    private static void clearErrorFile(Integer chunks, String pathuuid, String fileid, String suffix) {
        for (int i = 0; i < chunks; i++) {
            File chunkFile = new File(pathuuid + i + fileid + "." + suffix);
            if (chunkFile.exists()) {
                chunkFile.delete();
            }
        }
    }

    /**
     * 将所有反斜杠换成正斜杠
     *
     * @param path
     * @return
     */
    private static String replaceSeparator(String path) {
        return path.replaceAll("\\\\", "/");
    }

//    public static void main(String[] args) {
////        System.out.println("Project directory: " + getProjectPath());
//        System.out.println("Project directory: " + doPath("/a/b/d/"));
//        System.out.println("Project directory: " + doPath("/a/b/d"));
//        System.out.println("Project directory: " + doPath("a/b/d/"));
//        System.out.println("Project directory: " + doPath("a/b/d"));
//
//        System.out.println("Project directory: " + doPath("\\a\\b\\d\\"));
//        System.out.println("Project directory: " + doPath("\\a\\b\\d"));
//        System.out.println("Project directory: " + doPath("a\\b\\d\\"));
//        System.out.println("Project directory: " + doPath("a\\b\\d"));
//    }
}
