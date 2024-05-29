package com.xxsword.xitem.admin.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public class CaptchaUtils {
    private static final String CHARACTERS = "ABCDEFGHJKMNPRSTUVWXYZ12345678";
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 40;
    private static final int CODE_LENGTH = 4;
    private static final Random RANDOM = new Random();


    /**
     * 生成验证码并输出到OutputStream.
     *
     * @param width        图像宽度
     * @param height       图像高度
     * @param outputStream 输出流
     * @return 验证码文本内容
     */
    public static String generateCaptcha(int width, int height, OutputStream outputStream) throws IOException {
        // 设置默认尺寸
        if (width <= 0) width = DEFAULT_WIDTH;
        if (height <= 0) height = DEFAULT_HEIGHT;

        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 设置字体
        Font font = new Font("DejaVu Sans", Font.PLAIN, height * 3 / 4);
        g.setFont(font);
        // 生成验证码文本
        StringBuilder captchaText = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            char randomChar = CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length()));
            captchaText.append(randomChar);

            // 计算字符位置
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.setToRotation(Math.toRadians(RANDOM.nextBoolean() ? -5 : 5), Utils.div(i * width, CODE_LENGTH, 2), Utils.div(height, 2, 2));
            g.setTransform(affineTransform);

            // 绘制字符
            g.setColor(new Color(RANDOM.nextInt(100), RANDOM.nextInt(100), RANDOM.nextInt(100)));
            g.drawString(String.valueOf(randomChar), i * width / CODE_LENGTH, height / 2 + height / 4);
        }

        // 控制噪点的颜色和透明度
        int alpha = 255; // Alpha值，255表示完全不透明，0表示完全透明==》【好像没有用】
//        int red = RANDOM.nextInt(256); // 随机红色分量
//        int green = RANDOM.nextInt(256); // 随机绿色分量
//        int blue = RANDOM.nextInt(256); // 随机蓝色分量
        int red = 240, green = 240, blue = 240;
        // 结合Alpha通道得到最终的RGB值
        int rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;

        // 添加噪点，控制噪点大小
        float yawpRate = 0.002f; // 噪声率
        int area = (int) (yawpRate * width * height);
        int noiseSize = 7; // 新增：定义噪点的大小（例如2x2像素的方块）
        for (int i = 0; i < area; i++) {
            int x = RANDOM.nextInt(width - noiseSize + 1); // 确保噪声块不会超出边界
            int y = RANDOM.nextInt(height - noiseSize + 1);

            // 画一个noiseSize x noiseSize的矩形噪点
            for (int dx = 0; dx < noiseSize; dx++) {
                for (int dy = 0; dy < noiseSize; dy++) {
                    image.setRGB(x + dx, y + dy, rgb);
                }
            }
        }

        // 输出到OutputStream
        ImageIO.write(image, "JPEG", outputStream);
        outputStream.flush();
        outputStream.close();

        return captchaText.toString();
    }

}