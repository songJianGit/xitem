package com.xxsword.xitem.admin.utils;

import com.xxsword.xitem.admin.model.EVO;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    /**
     * 将Map转为EVO
     *
     * @param map
     * @return
     */
    public static List<EVO> listQByMap(Map<String, Object> map) {
        List<EVO> list = new ArrayList<>();
        for (String k : map.keySet()) {
            Object oo = map.get(k);
            list.add(EVO.newCell(oo == null ? "" : oo.toString()));
        }
        return list;
    }

    /**
     * 将数据，写入流
     *
     * @param out
     * @param datas
     * @param title
     * @return
     */
    public static SXSSFWorkbook writeExcelcolXlsx(OutputStream out, List<List<EVO>> datas, List<String> title) {
        return writeExcelcolXlsx(out, datas, title.toArray(new String[0]));
    }

    /**
     * 将数据，写入流
     *
     * @param out
     * @param datas
     * @param title
     * @return
     */
    public static SXSSFWorkbook writeExcelcolXlsx(OutputStream out, List<List<EVO>> datas, String[] title) {
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        Sheet sheet = sxssfWorkbook.createSheet("第1页");
        try {
            writeExcelcolXlsx(sxssfWorkbook, sheet, datas, title);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sxssfWorkbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sxssfWorkbook;
    }

    /**
     * 无翻页，有跨列
     *
     * @param datas
     * @param title
     */
    public static void writeExcelcolXlsx(SXSSFWorkbook sxssfWorkbook, Sheet sheet, List<List<EVO>> datas, String[] title) throws IOException {
        Row header = sheet.createRow(0);
        Drawing drawing = sheet.createDrawingPatriarch();
//        XSSFCellStyle xssfCellStyleHeader = getAndSetXSSFCellStyleHeader(sxssfWorkbook);
//        XSSFCellStyle xssfCellStyleOne = getAndSetXSSFCellStyleOne(sxssfWorkbook);
//        XSSFCellStyle xssfCellStyleTwo = getAndSetXSSFCellStyleTwo(sxssfWorkbook);
        if (title != null) {// title 不为空则填充title
            for (int cellnum = 0; cellnum < title.length; cellnum++) {
                Cell cell = header.createCell(cellnum);
//            cell.setCellStyle(xssfCellStyleHeader);
                cell.setCellValue(title[cellnum]);
            }
        }
        for (int rownum = 1; rownum <= datas.size(); rownum++) {
            Row row;
            if (title == null) {// 没有title，则从第一行开始
                row = sheet.createRow(rownum - 1);
            } else {
                row = sheet.createRow(rownum);
            }
            // 循环创建单元格
            List<EVO> questionSumList = datas.get(rownum - 1);
            if (questionSumList == null) {
                continue;
            }
            for (int cellnum = 0; cellnum < questionSumList.size(); cellnum++) {
                Cell cell = row.createCell(cellnum);
                // 根据行数,设置该行内的单元格样式
//                if (rownum % 2 == 1) { // 奇数
//                    cell.setCellStyle(xssfCellStyleOne);
//                } else { // 偶数
//                    cell.setCellStyle(xssfCellStyleTwo);
//                }
                // 根据单元格所属,录入相应内容
                // 将部分数字类型的字符串,转换为Long;以免导出excel后,单元格左上角有三
                //    角形(这是excel检查到该单元格内的内容均为数字,但是单元格类型却不是
                //    数字,给出的提示),转不转看自己需求灵活处理
                EVO questionSum = questionSumList.get(cellnum);
                int cac = questionSum.getColnum() - 1 > 0 ? questionSum.getColnum() - 1 : 0;
                int car = questionSum.getRownum() - 1 > 0 ? questionSum.getRownum() - 1 : 0;
                ClientAnchor anchor = null;
                if (cac > 0 || car > 0) {
                    CellRangeAddress cra = null;
                    if (title == null) {
                        cra = new CellRangeAddress(rownum - 1, rownum - 1 + car, cellnum, cellnum + cac);
                        anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cellnum, rownum - 1, (short) cellnum + cac + 1, rownum - 1 + car + 1);
                    } else {
                        cra = new CellRangeAddress(rownum, rownum + car, cellnum, cellnum + cac);
                        anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cellnum, rownum, (short) cellnum + cac + 1, rownum + car + 1);
                    }
                    //在sheet里增加合并单元格
                    sheet.addMergedRegion(cra);
                } else {
                    anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cellnum, rownum, (short) cellnum + cac + 1, rownum + car + 1);
                }
                if (questionSum.getType().equals(2)) {
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    BufferedImage bufferImg = null;
                    try {
                        bufferImg = ImageIO.read(new File(UpLoadUtil.getProjectPath() + questionSum.getValue()));
                    } catch (Exception e) {
                        bufferImg = null;
                    }
                    if (bufferImg != null && questionSum.getValue().toLowerCase().indexOf(".jpg") != -1) {
                        ImageIO.write(bufferImg, "jpg", byteArrayOut);
                        drawing.createPicture(anchor, sxssfWorkbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else if (bufferImg != null && questionSum.getValue().toLowerCase().indexOf(".png") != -1) {
                        ImageIO.write(bufferImg, "png", byteArrayOut);
                        drawing.createPicture(anchor, sxssfWorkbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));
                    }
                } else {
                    cell.setCellValue(questionSum.getValue() == null ? "" : questionSum.getValue());
                }
            }
        }
    }

    public static Workbook readExcle(File file) {
        try {
            String fileType = FilenameUtils.getExtension(file.getName()).toLowerCase();
            InputStream stream = new FileInputStream(file);
            Workbook wb = null;
            if ("xls".equals(fileType)) {
                wb = new HSSFWorkbook(stream);
            } else if ("xlsx".equals(fileType)) {
                wb = new XSSFWorkbook(stream);
            }
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取数据
     * 去掉全角空格 和 不间断空格 和 前后空格
     *
     * @param cell
     * @return
     */
    public static String getString(Cell cell) {
        if (cell == null) {
            return "";
        }
        return cell.getStringCellValue().trim().replaceAll("\u00A0", "").replaceAll("\u3000", "");
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
}
