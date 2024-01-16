package info.wes.school.core.file.thumbnail;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class ThumbnailUtil {
	
	private BufferedImage buffer;
	
	private boolean isPng = false;
	
	public ThumbnailUtil(File file) throws IOException {
		buffer = ImageIO.read(file);
	}
	
	public ThumbnailUtil(URL url)throws IOException {
		buffer = ImageIO.read(url);
	}
	
	public ThumbnailUtil(InputStream stream)throws IOException {
		buffer = ImageIO.read(stream);
	}
	
	public ThumbnailUtil(BufferedImage buffer) {
		this.buffer = buffer;
	}
	
	public ThumbnailUtil(BufferedImage buffer, boolean isPng) {
		this.buffer = buffer;
		this.isPng = isPng;
	}
	
	public BufferedImage getBufferedImage() {
		return this.buffer;
	}
	
	public void setIsPng(boolean isPng) {
		this.isPng = isPng;
	}
	
	public int getWidth() {
		return buffer.getWidth();
	}
	
	public int getHeight() {
		return buffer.getHeight();
	}
	
	public ThumbnailUtil resize(int width, int height){
		return resize(width, height, false);
	}
	
	public ThumbnailUtil resize(int width, int height, boolean isPng){
		BufferedImage dest ;
		if (isPng) {
			dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} else {
			dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		}
		Graphics2D g = dest.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(buffer,0, 0, width, height, null);
		g.dispose();
		return new ThumbnailUtil(dest, isPng);
	}
	
	public ThumbnailUtil resizeWidth(int width){
		return resizeWidth(width, isPng);
	}
	
	public ThumbnailUtil resizeWidth(int width, boolean isPng){
		int resizedHeight = (width * buffer.getHeight()) / buffer.getWidth();
		return resize(width, resizedHeight, isPng);
	}
	
	public ThumbnailUtil resizeHeight(int height){
		return resizeHeight(height, isPng);
	}
	
	public ThumbnailUtil resizeHeight(int height, boolean isPng){
		int resizedWidth = (height * buffer.getWidth()) / buffer.getHeight();
		return resize(resizedWidth, height, isPng);
	}
	
	/**
	 * 이미지를 자른다
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public ThumbnailUtil crop(int x, int y, int width, int height){
		return crop(x, y, width, height, isPng);
	}
	
	/**
	 * 이미지를 자른다
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isPng
	 * @return
	 */
	public ThumbnailUtil crop(int x, int y, int width, int height, boolean isPng) {
		BufferedImage dest;
		if (isPng) {
			dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} else {
			dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		}
		
		Graphics2D g = dest.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(buffer, 0, 0, width, height, x, y, x + width, y + height, null);
		g.dispose();
		return new ThumbnailUtil(dest, isPng);
	}
	
//	public boolean isSupportedImage(){
//		return buffer != null ? true : false;
//	}
	
//	public void writeTo(OutputStream stream, String formatName) throws IOException{
//		if(formatName.equals("jpg")){
//			changeHighQuality(stream);
//		}
//		ImageIO.write(buffer,formatName, stream);
//	}
	
//	/**
//	 * Improve quality.
//	 * @param out
//	 */
//	private void changeHighQuality(OutputStream out){
//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(buffer);
//		param.setQuality(1, true);
//		encoder.setJPEGEncodeParam(param);
//		try {
//			encoder.encode(buffer);
//		} catch (ImageFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
