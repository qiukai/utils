package top.qiuk.util;

import java.util.List;

public class ListUtil {

	/**
	 * 判断list是否为空
	 * @param list
	 * @return
	 */
	public static boolean isNull(List<?> list){
		if (null == list || 0 == list.size()) {
			return true;
		}
		return false;
	}

}
