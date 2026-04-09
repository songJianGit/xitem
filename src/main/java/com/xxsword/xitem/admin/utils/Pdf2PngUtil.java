package com.xxsword.xitem.admin.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.Loader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pdf2PngUtil {
    /**
     * dpi越大越清晰，但是转换速度越慢
     */
    private static final Integer DPI = 100;

    /**
     * @param pdfPath    pdf文件路径
     * @param targetPath 输出文件夹路径
     * @return
     */
    public static List<String> pdfToPng(String pdfPath, String targetPath) {
        List<String> result = new ArrayList<>();
        Utils.hasFolder(targetPath);
        try (PDDocument document = Loader.loadPDF(new File(pdfPath))) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int i = 0; i < document.getNumberOfPages(); ++i) {
                BufferedImage bufferedImage = renderer.renderImageWithDPI(i, DPI);
                String imgSrc = "/img" + i + ".png";
                ImageIO.write(bufferedImage, "png", new File(targetPath + imgSrc));
                result.add(imgSrc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
