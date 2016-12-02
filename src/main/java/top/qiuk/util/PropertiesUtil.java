package top.qiuk.util;

import top.qiuk.constant.ExceptionTypeEnum;
import top.qiuk.exception.UtilRuntimeException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static String getPropertyValue(String configFile, String key) {
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(
					PropertiesUtil.class.getClassLoader().getResource("").getPath() + configFile + ".properties");
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UtilRuntimeException(ExceptionTypeEnum.FILE_NAME, "文件名：" + configFile);
		}
		try {
			return new String(properties.getProperty(key).getBytes(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UtilRuntimeException(ExceptionTypeEnum.KEY_IS_NULL, "key名：" + key);
		}
	}

}