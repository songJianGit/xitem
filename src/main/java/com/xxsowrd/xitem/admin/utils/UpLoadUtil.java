package com.xxsowrd.xitem.admin.utils;

import com.xxsowrd.xitem.admin.config.SystemConfig;
import com.xxsowrd.xitem.admin.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 上传工具类
 *
 * @author songJian
 * @version 2018-3-29
 */
@Slf4j
public class UpLoadUtil {

    private static final String PATH_INFO = "/fileinfo";// 所有文件路径都会加的前缀，用作nginx转发可以减轻java压力

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

    public static List<Map> upload(MultipartFile[] files, String path) {
        List<Map> list = new ArrayList<>();
        for (MultipartFile item : files) {
            Map m = new HashMap();// TODO 换成对象
            m.put("path", upload(item, path));
            m.put("name", item.getOriginalFilename());
            if (m.get("path") == null) {
                m.put("path", "");
            }
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
     *
     * @param url
     * @return
     */
    public static String tempToFileInfoPath(String url) {
        return tempToFileInfoPath(url, UpLoadUtil.getTIMEPath());
    }

    private static String tempToFileInfoPath(String url, String timePath) {
        timePath = doPath(timePath);
        String[] u = FilenameUtils.getFullPathNoEndSeparator(url).split(PATH_TEMP);
        String urlPrefix = u[0] + PATH_DEF + timePath + u[1];
        try {
            FileUtils.moveToDirectory(new File(url), new File(urlPrefix), true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        urlPrefix = urlPrefix.replaceAll(UpLoadUtil.getPath(), "") + "/" + FilenameUtils.getName(url);
        return PATH_INFO + urlPrefix;
    }

    /**
     * 获取时间分隔字符
     */
    private static String getTIMEPath() {
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
            String pathStr = ResourceUtils.getURL("").getPath() + PATH_INFO;
            Utils.hasFolder(pathStr);
            log.debug("uploadPath:" + pathStr);
            return pathStr.replaceAll("\\\\", "/");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取资源路径
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
            return path.replaceAll("\\\\", "/");
        }
        return getDPath();
    }

    private static String pathuuid(String uuid) {
        return UpLoadUtil.getPath() + PATH_TEMP + "/" + uuid + "/";
    }

    /**
     * 分片文件上传,文件默认存储在temp/uuid文件夹中
     * 2020-06-29只允许上传压缩文件（Constant.COMPRESS_EX）
     *
     * @param request
     * @return info 0-正常返回 1-合并成功 2-合并失败 3-未知错误 4-分片丢失
     */
    public static Map<String, Object> sliceFileupload(HttpServletRequest request) {
        Map<String, Object> map = new ConcurrentHashMap<>();// TODO 换成对象
        MultipartHttpServletRequest mQuest = (MultipartHttpServletRequest) request;
        MultipartFile file = mQuest.getFile("file");// 获得上传的文件
        String fname = file.getOriginalFilename().toLowerCase();
        if (!Constant.COMPRESS_EX.toLowerCase().contains(FilenameUtils.getExtension(fname).toLowerCase())) {// 只允许上传指定文件
            map.put("info", 3);
            log.error("上传的文件格式，不是允许的格式:{}", FilenameUtils.getExtension(fname));
            return map;
        }
        String uuid = request.getParameter("uuid");// 上传站点标识，用于区分各站点，以便判断文件合并信息
        if (uuid.contains(".") || uuid.contains("/")) {// 防止遍历攻击
            map.put("info", 3);
            return map;
        }
        String pathuuid = pathuuid(uuid);
        String chunkStr = request.getParameter("chunk");
        String chunksStr = request.getParameter("chunks");
        if (chunkStr == null) {
            chunkStr = "0";
        }
        if (chunksStr == null) {
            chunksStr = "1";
        }
        Integer chunk = Integer.valueOf(chunkStr);// 第几片
        int chunks = Integer.parseInt(chunksStr);// 一共分了几片
        String fileid = request.getParameter("id").toLowerCase();// 文件id（文件分片上传的时候，文件id是一样的，同一个站点不会有相同文件id，但是不同站点的id可能会相同）
        log.info("uuid:{},chunk:{},chunks:{}", uuid, chunk, chunks);
        String suffix = "";
        if (!file.isEmpty()) {
            String oldfilename = file.getOriginalFilename();
            suffix = FilenameUtils.getExtension(oldfilename);
            try {
                Utils.hasFolder(pathuuid);
                File uploadFile = new File(pathuuid + chunk + fileid + "." + suffix);
                FileCopyUtils.copy(file.getBytes(), uploadFile);
                Set<File> files = new HashSet<File>();
                for (int i = 0; i < chunks; i++) {
                    File chunkFile = new File(pathuuid + i + fileid + "." + suffix);
                    if (chunkFile.exists()) {
                        files.add(chunkFile);
                    }
                }
                log.info("uuid:{},chunks:{},files.size:{}", uuid, chunks, files.size());
                if (chunks == files.size()) {// 文件全部到齐
                    log.info("xxx=uuid:{},chunks:{},files.size:{},oldfilename:{}", uuid, chunks, files.size(), oldfilename);
                }
            } catch (Exception e) {
                log.error("分片文件接收出错");
                e.printStackTrace();
                clearErrorFile(chunks, pathuuid, fileid, suffix);
                map.put("info", 3);
                return map;
            }
        } else {
            log.error("分片文件为空,uuid:{},fileid:{}", uuid, fileid);
            map.put("info", 4);
            return map;
        }
        map.put("info", 0);
        // 每一次成功的分片上传，都返回详细信息
        map.put("chunks", chunks);
        map.put("suffix", suffix);
        map.put("uuid", uuid);
        map.put("fileid", fileid);
        return map;
    }

    // 文件合并
    public static Map<String, Object> merge(Integer chunks, String uuid, String fileid, String suffix) {
        fileid = fileid.toLowerCase();
        String pathuuid = pathuuid(uuid);
        Map<String, Object> map = new ConcurrentHashMap<>();
        String fFileName = fileid + "." + suffix;
        try {
            log.info("开始合并分片文件,uuid:{},fileid:{},fFileName:{}", uuid, fileid, fFileName);
            File destFile = new File(pathuuid + fFileName);
            OutputStream out = Files.newOutputStream(destFile.toPath());
            BufferedOutputStream bos = new BufferedOutputStream(out);
            for (int i = 0; i < chunks; i++) {// 按顺序，从第一个开始进行拼接
                File srcFile = new File(pathuuid + i + fileid + "." + suffix);
                if (!srcFile.exists()) {
                    log.error("分片丢失，chunk is null,fFileName:{}", fFileName);
                    clearErrorFile(chunks, pathuuid, fileid, suffix);
                    map.put("info", 4);
                    return map;
                }
                InputStream in = Files.newInputStream(srcFile.toPath());
                BufferedInputStream bis = new BufferedInputStream(in);
                byte[] bytes = new byte[1024 * 1024];
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
            map.put("info", 1);
            map.put("fileid", fileid);
            return map;
        } catch (Exception e) {
            log.error("分片文件合并出错，fFileName：{}", fFileName);
            clearErrorFile(chunks, pathuuid, fileid, suffix);
            e.printStackTrace();
            map.put("info", 2);
            return map;
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
     * 编码中文字符，使其输出到浏览器时，中文不乱码
     *
     * @param fileNames
     * @param request
     * @return
     */
    public static String encodeFileName(String fileNames, HttpServletRequest request) {
        String codedFilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && agent.contains("MSIE") || null != agent && agent.contains("Trident") ||
                    null != agent && agent.contains("Edge")) {
                // ie浏览器及Edge浏览器
                codedFilename = URLEncoder.encode(fileNames, "UTF-8");
            } else if (null != agent && agent.contains("Mozilla")) {
                // 火狐,Chrome等浏览器
                codedFilename = new String(fileNames.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedFilename;
    }

//    public static void main(String[] args) {
//        System.out.println("Project directory: " + getProjectPath());
//    }
}
