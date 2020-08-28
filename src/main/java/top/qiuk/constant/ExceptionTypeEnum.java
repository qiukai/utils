package top.qiuk.constant;

import top.qiuk.exception.UtilRuntimeException;

public enum ExceptionTypeEnum {

	/**
	 * 字符串为空
	 */
	STRING_IS_NULL("字符串为空", 1),

	/**
	 * 日期错误
	 */
	DATE("日期错误", 2),

	/**
	 * 文件不存在
	 */
	FILE_NAME("文件不存在", 3),

	/**
	 * 日期格式错误
	 */
	DATE_FORMATTER("日期格式错误", 4),

	/**
	 * 文件名为空
	 */
	FILE_NAME_NULL("文件名为空", 5),

	/**
	 * key不存在
	 */
	KEY_IS_NULL("key不存在", 6),

	/**
	 * 连接池已经没有连接
	 */
	CONNECTION_POOL_IS_NULL("连接池已经没有连接", 7),

	/**
	 * 连接池已经没有连接
	 */
	REDIS("redis 异常", 7),

	/**
	 * 异常类型枚举异常
	 */
	EXCEPTION_TYPE_ENUM("异常类型枚举异常", 0);


	private String message;

	private int code;

	ExceptionTypeEnum(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public String getValue() {
		return message;
	}

	public int getKey() {
		return code;
	}

	public static String getValue(int key) {
		for (ExceptionTypeEnum ete : ExceptionTypeEnum.values()) {
			if (key == ete.getKey()) {
				return ete.getValue();
			}
		}
		throw new UtilRuntimeException(ExceptionTypeEnum.EXCEPTION_TYPE_ENUM);
	}

	public static int getKey(String value) {
		for (ExceptionTypeEnum ete : ExceptionTypeEnum.values()) {
			if (ete.getValue().contains(value)) {
				return ete.getKey();
			}
		}
		throw new UtilRuntimeException(ExceptionTypeEnum.EXCEPTION_TYPE_ENUM);
	}

	public String toString() {
		return this.code + ":" + this.message;
	}
}