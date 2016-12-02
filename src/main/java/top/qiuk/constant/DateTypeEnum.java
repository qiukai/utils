package top.qiuk.constant;

/**
 * 日期enum类型
 *
 * @author qk
 */
public enum DateTypeEnum {

	_yMd, yMdHms, yMdHmsS, _yMdHms, Hm, _Hm, Hms, _Hms,yMd;

	public static String patternFromType(DateTypeEnum type) {
		switch (type) {
		case _yMd:
			return "yyyy-MM-dd";
		case yMdHms:
			return "yyyyMMddHHmmss";
		case yMdHmsS:
			return "yyyyMMddHHmmssSSS";
		case _yMdHms:
			return "yyyy-MM-dd HH:mm:ss";
		case Hm:
			return "HHmm";
		case Hms:
			return "HHmmss";
		case _Hm:
			return "HH:mm";
		case _Hms:
			return "HH:mm:ss";
		case yMd:
			return "yyyyMMdd";
		default:
			return "yyyy-MM-dd HH:mm:ss";
		}
	}
}
