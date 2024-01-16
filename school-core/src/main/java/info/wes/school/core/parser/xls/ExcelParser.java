package info.wes.school.core.parser.xls;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelParser<T> {

	/**
	 * excelFile(MultipartFile) to class <br/>
	 * 첫행은 타이틀행으로 판단하여 패스하고 2행 부터 데이터를 파싱
	 * @param file
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<T> read(MultipartFile file, Class<T> type) throws Exception;

	/**
	 * excelFile(MultipartFile) to class
	 * @param file
	 * @param type
	 * @param hasTitle : 첫행이 타이틀행인지 여부
	 * @return
	 * @throws Exception
	 */
	public List<T> read(MultipartFile file, Class<T> type, boolean hasTitle) throws Exception;

	/**
	 * excelFile(File) to class <br/>
	 * 첫행은 타이틀행으로 판단하여 패스하고 2행 부터 데이터를 파싱
	 * @param file
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<T> read(File file, Class<T> type) throws Exception;

	/**
	 * excelFile(File) to class <br/>
	 * @param file
	 * @param type
	 * @param hasTitle : 첫행이 타이틀행인지 여부
	 * @return
	 * @throws Exception
	 */
	public List<T> read(File file, Class<T> type, boolean hasTitle) throws Exception;

}
