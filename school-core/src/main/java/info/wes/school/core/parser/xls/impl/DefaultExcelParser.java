package info.wes.school.core.parser.xls.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import info.wes.school.core.parser.annotation.Parse;
import info.wes.school.core.parser.xls.ExcelParser;

@Component
public class DefaultExcelParser<T> implements ExcelParser<T> {

	@Override
	public List<T> read(MultipartFile multipartFile, Class<T> type) throws Exception {
		return this.read(multipartFile, type, true);
	}

	@Override
	public List<T> read(MultipartFile multipartFile, Class<T> type, boolean hasTitle) throws Exception {
		String tempDir = FileUtils.getTempDirectoryPath(); // C:\\Users\\awj04\\AppData\\Local\\Temp\\
		String name = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
		File file = new File(tempDir + File.separator + name);
		
		multipartFile.transferTo(file);
		return this.read(file, type, hasTitle);
	}

	@Override
	public List<T> read(File file, Class<T> type) throws Exception {
		return read(file, type, true);
	}

	@Override
	public List<T> read(File file, Class<T> type, boolean hasTitle) throws Exception {
		List<T> results = null;
		Workbook workbook = null;
		FileInputStream fileIn = null;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			fileIn = new FileInputStream(file);
			workbook = new HSSFWorkbook(fileIn);
			Sheet sheet = null;
			Cell cell = null;

			if (workbook != null) { // 엑셀파일

				sheet = workbook.getSheetAt(0);
				if (sheet != null) { // 시트

					int rowStartIndex = (hasTitle) ? 1 : 0; // title행이 있을경우, 두번째행(1)부터 읽음
					int rowEndIndex   = sheet.getPhysicalNumberOfRows();
					Row row = sheet.getRow(0);
					int columnStartIndex = 0;
					int columnEndIndex = row.getLastCellNum() - 1;

					if (rowEndIndex > 0) {
						if (results == null) results = new ArrayList<T>();
					}

					// rows
					for (int i = rowStartIndex; i <= rowEndIndex; i++) {
						if (sheet.getRow(i) == null) {
							break;
						}
						
						// row
						T t = type.newInstance();
						for (int j = columnStartIndex; j <= columnEndIndex; j++) {

							// cells
							Field[] fields = t.getClass().getDeclaredFields();
							for (int x = 0; x < fields.length; x++) {
								if (!fields[x].isAnnotationPresent(Parse.class)) continue;
								if (fields[x].getAnnotation(Parse.class).index() != j) continue;
								
								boolean wasAccessible = fields[x].isAccessible();
								fields[x].setAccessible(true);

								cell = sheet.getRow(i).getCell(j);
								if (cell == null) continue;
								
								// 필드타입에 맞게 셀을 읽어옴
								Class<?> fieldType = fields[x].getType();
								if (fieldType == Long.class) {
									fields[x].set(t, (long) cell.getNumericCellValue());
								} else if(fieldType == String.class) {
									switch (cell.getCellType()) {
										case HSSFCell.CELL_TYPE_STRING:
											fields[x].set(t, cell.getStringCellValue());
											break;
										case HSSFCell.CELL_TYPE_NUMERIC:
											fields[x].set(t, (Long.toString((long) cell.getNumericCellValue())));
											break;
									}
								} else if(fieldType == Date.class) {
									fields[x].set(t, sdf.parse(cell.getStringCellValue()));
								} else if(fieldType == Boolean.class) {
									fields[x].set(t, Boolean.valueOf(cell.getBooleanCellValue()));
								} else if(fieldType == Integer.class) {
									fields[x].set(t, Integer.valueOf(cell.getStringCellValue()));
								} else {
									fields[x].set(t, String.valueOf(cell.getStringCellValue()));
								}
								fields[x].setAccessible(wasAccessible);
							}
						}
						results.add(t);
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (ParseException e) {
			throw e;
		} catch (InstantiationException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fileIn != null) {
					fileIn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}

}
