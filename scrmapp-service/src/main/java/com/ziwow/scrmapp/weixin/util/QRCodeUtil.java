/**   
* @Title: QRCodeUtil.java
* @Package com.ziwow.marketing.weixin.util
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-8-4 下午7:40:18
* @version V1.0   
*/
package com.ziwow.scrmapp.weixin.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.aspectj.util.FileUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @ClassName: QRCodeUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-8-4 下午7:40:18
 * 
 */
public class QRCodeUtil {

	 private static final String CHARSET = "utf-8";  
	    private static final String FORMAT_NAME = "JPG";  
	    // 二维码尺寸  
	    private static final int QRCODE_SIZE = 280;  
	    // LOGO宽度  
	    private static final int WIDTH = 96;  
	    // LOGO高度  
	    private static final int HEIGHT = 96;  
	  
	    private static BufferedImage createImage(String content, String imgPath,  
	            boolean needCompress) throws Exception {  
	        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
	        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
	        hints.put(EncodeHintType.MARGIN, 1);  
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,  
	                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);  
	        int width = bitMatrix.getWidth();  
	        int height = bitMatrix.getHeight();  
	        BufferedImage image = new BufferedImage(width, height,  
	                BufferedImage.TYPE_INT_RGB);  
	        for (int x = 0; x < width; x++) {  
	            for (int y = 0; y < height; y++) {  
	                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000  
	                        : 0xFFFFFFFF);  
	            }  
	        }  
	        if (imgPath == null || "".equals(imgPath)) {  
	            return image;  
	        }  
	        // 插入图片  
	        QRCodeUtil.insertImage(image, imgPath, needCompress);  
	        return image;  
	    }  
	  
	    /** 
	     * 插入LOGO 
	     *  
	     * @param source 
	     *            二维码图片 
	     * @param imgPath 
	     *            LOGO图片地址 
	     * @param needCompress 
	     *            是否压缩 
	     * @throws Exception 
	     */  
	    private static void insertImage(BufferedImage source, String imgPath,  
	            boolean needCompress) throws Exception {  
	        File file = new File(imgPath);  
	        if (!file.exists()) {  
	            return;  
	        }
	      //头像
			byte [] imgHeadData=getImageFromNetByUrl(imgPath);
			ImageIcon imgHeadIcon = new ImageIcon(imgHeadData);
			// 得到Image对象。
			Image src = imgHeadIcon.getImage();
	        int width = src.getWidth(null);  
	        int height = src.getHeight(null);  
	        if (needCompress) { // 压缩LOGO  
	            if (width > WIDTH) {  
	                width = WIDTH;  
	            }  
	            if (height > HEIGHT) {  
	                height = HEIGHT;  
	            }  
	            Image image = src.getScaledInstance(width, height,  
	                    Image.SCALE_SMOOTH);  
	            BufferedImage tag = new BufferedImage(width, height,  
	                    BufferedImage.TYPE_INT_RGB);  
	            Graphics g = tag.getGraphics();  
	            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
	            g.dispose();  
	            src = image;  
	        }  
	        // 插入LOGO  
	        Graphics2D graph = source.createGraphics();  
	        int x = (QRCODE_SIZE - width) / 2;  
	        int y = (QRCODE_SIZE - height) / 2;  
	        graph.drawImage(src, x, y, width, height, null);  
	        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);  
	        graph.setStroke(new BasicStroke(3f));  
	        graph.draw(shape);  
	        graph.dispose();  
	    }  
	  
	    /** 
	     * 从输入流中获取数据 
	     * @param inStream 输入流 
	     * @return 
	     * @throws Exception 
	     */  
		private  static byte[] getImageFromNetByUrl(String strUrl) {
			try {  
				strUrl=strUrl.replace("https", "http");
	            URL url = new URL(strUrl);  
	            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	            conn.setRequestMethod("GET");  
	            conn.setConnectTimeout(5 * 1000);  
	            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
	            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
	            return btImg;  
	        } catch (Exception e) {  
	        }  
	        return null;  
		}
		/** 
	     * 从输入流中获取数据 
	     * @param inStream 输入流 
	     * @return 
	     * @throws Exception 
	     */  
	    private  static byte[] readInputStream(InputStream inStream) throws Exception{  
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] buffer = new byte[1024];  
	        int len = 0;  
	        while( (len=inStream.read(buffer)) != -1 ){  
	            outStream.write(buffer, 0, len);  
	        }  
	        inStream.close();  
	        return outStream.toByteArray();  
	    }
	    /** 
	     * 生成二维码(内嵌LOGO) 
	     *  
	     * @param content 
	     *            内容 
	     * @param imgPath 
	     *            LOGO地址 
	     * @param destPath 
	     *            存储地址 
	     * @param needCompress 
	     *            是否压缩LOGO 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String imgPath, String destPath,  
	            boolean needCompress) throws Exception {  
	        BufferedImage image = QRCodeUtil.createImage(content, imgPath,  
	                needCompress);  
	        //File.mkdirs(destPath);  
	        ImageIO.write(image, FORMAT_NAME, new File(destPath));  
	    }  
	  
	    /** 
	     * 生成二维码(内嵌LOGO) 
	     *  
	     * @param content 
	     *            内容 
	     * @param imgPath 
	     *            LOGO地址 
	     * @param destPath 
	     *            存储地址 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String imgPath, String destPath)  
	            throws Exception {  
	        QRCodeUtil.encode(content, imgPath, destPath, false);  
	    }  
	  
	    /** 
	     * 生成二维码 
	     *  
	     * @param content 
	     *            内容 
	     * @param destPath 
	     *            存储地址 
	     * @param needCompress 
	     *            是否压缩LOGO 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String destPath,  
	            boolean needCompress) throws Exception {  
	        QRCodeUtil.encode(content, null, destPath, needCompress);  
	    }  
	  
	    /** 
	     * 生成二维码 
	     *  
	     * @param content 
	     *            内容 
	     * @param destPath 
	     *            存储地址 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String destPath) throws Exception {  
	        QRCodeUtil.encode(content, null, destPath, false);  
	    }  
	  
	    /** 
	     * 生成二维码(内嵌LOGO) 
	     *  
	     * @param content 
	     *            内容 
	     * @param imgPath 
	     *            LOGO地址 
	     * @param output 
	     *            输出流 
	     * @param needCompress 
	     *            是否压缩LOGO 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String imgPath,  
	            OutputStream output, boolean needCompress) throws Exception {  
	        BufferedImage image = QRCodeUtil.createImage(content, imgPath,  
	                needCompress);  
	        ImageIO.write(image, FORMAT_NAME, output);  
	    }  
	  
	    /** 
	     * 生成二维码 
	     *  
	     * @param content 
	     *            内容 
	     * @param output 
	     *            输出流 
	     * @throws Exception 
	     */  
	    public static void encode(String content, OutputStream output)  
	            throws Exception {  
	        QRCodeUtil.encode(content, null, output, false);  
	    }  
	  
	    /** 
	     * 解析二维码 
	     *  
	     * @param file 
	     *            二维码图片 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String decode(File file) throws Exception {  
	        BufferedImage image;  
	        image = ImageIO.read(file);  
	        if (image == null) {  
	            return null;  
	        }  
	        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(  
	                image);  
	        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
	        Result result;  
	        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();  
	        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);  
	        result = new MultiFormatReader().decode(bitmap, hints);  
	        String resultStr = result.getText();  
	        return resultStr;  
	    }  
	  
	    /** 
	     * 解析二维码 
	     *  
	     * @param path 
	     *            二维码图片地址 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String decode(String path) throws Exception {  
	        return QRCodeUtil.decode(new File(path));  
	    }  
	  
	    public static void main(String[] args) throws Exception {  
	        String text = "http://abc.aiwashe.com/marketing/lt/jXHQ57NJ";  
	        QRCodeUtil.encode(text, "", 
	        		"d:/Michael_QRCode.png", true);  
	    }  
}
