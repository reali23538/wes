package info.wes.school.core.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailMatcher {

	private Map<String, String> solution = new HashMap<String, String>();

	/**
	 * 바인딩할 데이터를 셋팅
	 * @param pattern
	 * @param replacement
	 */
	public void addPattern(String pattern, String replacement) {
		solution.put(pattern, replacement);
	}
	
	/**
	 * 바인딩할 데이터를 셋팅
	 * @param pattern_replacement
	 */
	public void addPattern(Map<String, String> pattern_replacement) {
		solution.putAll(pattern_replacement);
	}
	
	/**
	 * 템플릿 파일 to String
	 * @param htmlPath
	 * @return
	 * @throws IOException
	 */
	public String getMailBody(String htmlPath) throws IOException {
		File f = new File(htmlPath);
		if(!f.exists() || !f.isFile()) return null;

		StringBuffer sb = new StringBuffer();
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);

		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}

		return sb.toString();
	}
	
	/**
	 * 템플릿에 값을 바인딩 (ex: #NAME# -> ahn) 
	 * @param chunk
	 * @return
	 */
	public String getBoundBody(String chunk) {
		for (Iterator<String> it = solution.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			Pattern p = Pattern.compile(key);
			Matcher m = p.matcher(chunk);

			if (m.find()) {
				String replacement = (String) solution.get(key);
				chunk = m.replaceAll(replacement);
			}
		}
		return chunk;
	}
	
}
