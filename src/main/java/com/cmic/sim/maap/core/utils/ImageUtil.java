package com.cmic.sim.maap.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * @author haiqiang
 * @date 2019/3/14 22:05
 */
public class ImageUtil {

    private static final int width = 500;

    private static final int height = 160;

    private static final int fontHeight = 50;

    private static final int fontSize = 30;

    private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 风格编码生成文字图片
     *
     * @param drawStr
     * @return
     */
    public static BufferedImage drawTranslucentStringPic(String drawStr, int sealId) {
        BufferedImage buffImg = null;
        String drawDate = DateUtil.formatDate(new Date(), "yyyy年MM月dd日 HH:mm:ss");
        String ttfPath = "fonts/" + ttfName(sealId);
        try {
            ClassLoader classLoader = PdfUtil.class.getClassLoader();
            URL url = classLoader.getResource(ttfPath);
            String ttf = url.getFile();
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(ttf));
            font = font.deriveFont(Font.PLAIN, fontSize);

            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = buffImg.createGraphics();
            //设置透明  start
            buffImg = gd.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            gd = buffImg.createGraphics();
            //设置透明  end
            gd.setFont(font); //设置字体
            gd.setColor(Color.black); //设置颜色
            gd.drawRect(0, 0, 0, 0); //画边框
            gd.drawString(drawStr, width / 2 - fontSize * drawStr.length() / 2, height / 2); //输出姓名（中文横向居中）
            gd.drawString(drawDate, width / 2 - fontSize * 11 / 2, height / 2 +fontSize+((fontHeight - fontSize) / 2)); //输出日期（中文横向居中）
            return buffImg;
        } catch (Exception e) {
            log.error("合成印章失败：" + e);
            return null;
        }
    }

    /**
     * 生成印章
     *
     * @param name
     * @return
     */
    public static File creatImage(String name,int sealId, String filePrefix) {
        BufferedImage imgMap = drawTranslucentStringPic(name,sealId);
        File imgFile = new File(filePrefix+"_image.PNG");
        try {
            ImageIO.write(imgMap, "PNG", imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgFile;
    }

    /**
     * 加载相应的ttf文件
     *
     * @param sealId
     * @return
     */
    public static String ttfName(int sealId) {
        String ttfName = null;
        switch (sealId) {
            case 1:
                ttfName = "fzhcjw.ttf";
                break;
            case 2:
                ttfName = "fzhzgbjw.ttf";
                break;
            case 3:
                ttfName = "fzlthjw.ttf";
                break;
            case 4:
                ttfName = "fztjlsjw.ttf";
                break;
            case 5:
                ttfName = "hya9gjm.ttf";
                break;
        }
        return ttfName;
    }

    public static void main(String[] args){
        File file =creatImage("郭娟",5,"test");
    }
}
