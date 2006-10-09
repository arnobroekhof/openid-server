/**
 * Created on 2006-10-5 下午08:42:56
 */
package cn.net.openid.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Shutra
 * 
 */
public class OpenIDNVFormat {
	private static final Log log = LogFactory.getLog(OpenIDNVFormat.class);

	public static HashMap decodeStream(InputStream inputStream)
			throws IOException {
		HashMap<String, String> values = new HashMap<String, String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		int linenum = 0;
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() == 0) {
				continue;
			}
			linenum++;
			int separatorIndex = line.indexOf(":");
			if (separatorIndex <= 0) {
				log.warn("Error parsing line " + linenum
						+ " of reply; could not find name separator in '"
						+ line + "'");
				continue;
			}
			String name = line.substring(0, separatorIndex);
			String value = "";
			if (line.length() > separatorIndex + 1) {
				value = line.substring(separatorIndex + 1).trim();
			}
			values.put(name, value);
		}
		return values;
	}

	public static HashMap decodeString(String input) {
		ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
		try {
			return decodeStream(bais);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void encodeToStream(Map valueMap, OutputStream outputStream)
			throws IOException {
		Set keySet = valueMap.keySet();
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			writer.write(key);
			writer.write(":");
			writer.write((String) valueMap.get(key));
			writer.write("\n");
		}
		writer.flush();
	}

	public static String encodeToString(Map<String, String> valueMap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			encodeToStream(valueMap, baos);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return baos.toString();
	}

}
