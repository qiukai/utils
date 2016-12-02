package top.qiuk.exception;

import top.qiuk.constant.ExceptionTypeEnum;

/**
 * 帮助类运行时异常
 *
 * @author Administrator
 *
 */
public class UtilRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;

	private String message;

	/**
	 * @param exceptionTypeEnum
	 *            错误类型
	 */
	public UtilRuntimeException(ExceptionTypeEnum exceptionTypeEnum) {
		this(exceptionTypeEnum.getKey(),exceptionTypeEnum.getValue());
	}

	/**
	 *
	 * @param exceptionTypeEnum 错误类型
	 * @param errorDescription 错误描述
	 */
	public UtilRuntimeException(ExceptionTypeEnum exceptionTypeEnum, String errorDescription) {
		this(exceptionTypeEnum.getKey(),exceptionTypeEnum.getValue() + ":" + errorDescription);
	}

	public UtilRuntimeException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "GlobalRuntimeException [code=" + code + ", message=" + message + "]";
	}
}
