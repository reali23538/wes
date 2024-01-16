package info.wes.school.core.file;

import java.io.File;

import org.junit.Test;

import info.wes.school.core.file.zip.ZipUtils;
import info.wes.school.core.test.TestSupport;

public class ZipTest extends TestSupport {
	
	@Test
	public void zipTest() throws Exception {
		ZipUtils.zip("C:\\Projects\\WES\\WORK\\uploaded\\test\\201903", "C:\\Users\\awj04\\Desktop\\aaa.zip");
	}
	
	@Test
	public void unzipTest() throws Exception {
		ZipUtils.unzip(new File("C:\\Users\\awj04\\Desktop\\aaa.zip"), new File("C:\\Users\\awj04\\Desktop\\result"), false);
	}

}
