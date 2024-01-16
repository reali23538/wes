package info.wes.school.core.file.thumbnail.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import info.wes.school.core.file.thumbnail.ThumbnailGenerator;
import info.wes.school.core.file.thumbnail.ThumbnailUtil;

@Component
public class ThumbnailGeneratorImpl implements ThumbnailGenerator {

	@Override
	public List<File> generate(File originalFile, String uploadDir, String targetDir, String sizeProperties, Float compressQuality) throws Exception {
		if (sizeProperties == null) {
			throw new Exception();
		}
		
		List<File> fileList = new ArrayList<File>();
		try {
			int width = 0;
			int height = 0;
			String[] thumSize = sizeProperties.split(","); // 180X132,40X40,92X70,138X101
			
			for (int i = 0; i < thumSize.length; i++) {
				
				String[] sizeList = thumSize[i].split("X"); // 180X132
				if (sizeList.length != 2) throw new Exception();
				width = Integer.valueOf(sizeList[0]);
				height = Integer.valueOf(sizeList[1]);

				if (originalFile == null || !originalFile.exists()) return null;
				if (StringUtils.isEmpty(uploadDir)) return null;
				if (width < 1) return null;
				if (compressQuality == null) compressQuality = 1.0F;

//				targetDir = targetDir + File.separator;
				File dir = new File(uploadDir + File.separator + targetDir); // C:\\Projects\\WES\\WORK\\uploaded\\thumbnail
				if (!dir.exists()) dir.mkdir();

				Image image = ImageIO.read(originalFile);
				String thumbnailFileName = getFileName(originalFile.getPath()) + "_" + width + "X" + height + "." 
										 + getFileExtention(originalFile.getPath()); // 15525311320988982_180X132.jpg
				String thumbnailFilePath = uploadDir + File.separator + targetDir + File.separator + thumbnailFileName;
				File thumbnailFile = new File(thumbnailFilePath);
				if (thumbnailFile.exists()) { // 이미 있을경우 다시 만들지 않고 바로 리턴
					fileList.add(thumbnailFile);
					continue;
				}

				BufferedImage thumbnailImage = makeThumbImage(image, width, height, getFileExtention(originalFile.getPath()).trim());

				String fileMimeType = new MimetypesFileTypeMap().getContentType(originalFile);
				if (fileMimeType.equals("image/jpeg")) {
					Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
					ImageWriter writer = iter.next();
					ImageWriteParam iwp = writer.getDefaultWriteParam();
					iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//					iwp.setCompressionQuality(0.3F);
					iwp.setCompressionQuality(compressQuality);

					File file = new File(thumbnailFilePath);
					FileImageOutputStream output = new FileImageOutputStream(file);
					writer.setOutput(output);

					IIOImage image2 = new IIOImage(thumbnailImage, null, null);
					writer.write(null, image2, iwp);
					writer.dispose();
					output.close();
				} else {
					ImageIO.write(thumbnailImage, getFileExtention(originalFile.getPath()), thumbnailFile);
				}

				fileList.add(thumbnailFile);
			}
		} catch (Exception e) {
			throw new Exception();
		}

		return fileList;
	}

	/**
	 * 썸네일이미지 리턴
	 * @param image
	 * @param width
	 * @param height
	 * @param extension
	 * @return
	 * @throws Exception
	 */
	private BufferedImage makeThumbImage(Image image, int width, int height, String extension) throws Exception {
		boolean isPng = false;
		if (extension.toUpperCase().equals("PNG")) isPng = true;
		
		ThumbnailUtil util = new ThumbnailUtil((BufferedImage) image, isPng);
		
		Map<String, Object> resize = resize(util, width, height);
		
		if (resize.get("util") != null){
			util = (ThumbnailUtil) resize.get("util");
			util = util.crop((int)resize.get("x"), (int)resize.get("y"), width, height);
		} else {
			return null;
		}
		
		return util.getBufferedImage();
	}
	
	/**
	 * 리사이즈 (사이즈 계산)
	 * @param util
	 * @param width
	 * @param height
	 * @return
	 */
	public Map<String, Object> resize(ThumbnailUtil util, int width, int height){
		BufferedImage buffer = util.getBufferedImage();
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (buffer.getWidth() >= buffer.getHeight()) {
			int resizedWidth = (height * buffer.getWidth()) / buffer.getHeight();
			if (resizedWidth - width < 0) {
				int resizedHeight = (width * buffer.getHeight()) / buffer.getWidth();
				if (resizedHeight - height < 0) {
					util = util.resizeWidth((int)(width * 1.1));
					result.put("util", util);
					result.put("y", (util.getHeight() - height) / 2);
					result.put("x", 0);
				} else {
					util = util.resizeWidth(width);
					result.put("util", util);
					result.put("y", (util.getHeight() - height) / 2);
					result.put("x", 0);
				}
			} else {
				util = util.resizeHeight(height);
				result.put("util", util);
				result.put("x", (util.getWidth() - width) / 2);
				result.put("y", 0);
			}
		} else {
			if (buffer.getWidth() < buffer.getHeight()) {
				int resizedHeight = (width * buffer.getHeight()) / buffer.getWidth();
				if (resizedHeight - height < 0){
					int resizedWidth = (height * buffer.getWidth()) / buffer.getHeight();
					if (resizedWidth - width < 0) {
						util = util.resizeHeight((int)(height * 1.1));
						result.put("util", util);
						result.put("x", (util.getWidth() - width) / 2);
						result.put("y", 0);
					} else {
						util = util.resizeHeight(height);
						result.put("util", util);
						result.put("x", (util.getWidth() - width) / 2);
						result.put("y", 0);
					}
				} else {
					util = util.resizeWidth(width);
					result.put("util", util);
					result.put("y", (util.getHeight() - height) / 2);
					result.put("x", 0);
				}
			}
		}
		
		return result;
	}

	/**
	 * 파일명을 리턴
	 * 
	 * @param filePath
	 * @return
	 */
	private String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath)) return null;
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.lastIndexOf("."));
		return fileName;
	}

	/**
	 * 파일확장자를 리턴
	 * 
	 * @param filePath
	 * @return
	 */
	private String getFileExtention(String filePath) {
		if (StringUtils.isEmpty(filePath)) return null;
		String extention = filePath.substring(filePath.lastIndexOf(".") + 1);
		return extention;
	}

//	@Override
//	public String getSizeProperties(Integer width, Integer height) {
//		if (width != null && height != null) return width + "X" + height;
//		return null;
//	}
	
}