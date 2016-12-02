package top.qiuk.util;

import top.qiuk.constant.DateTypeEnum;
import top.qiuk.constant.ExceptionTypeEnum;
import top.qiuk.exception.UtilRuntimeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author : qk Function: 日期的帮助类
 */
public class DateUtil {

	public enum DateCalendar {

		YEAR(Calendar.YEAR), MONTH(Calendar.MONTH), DATE(Calendar.DATE), HOUR_OF_DAY(Calendar.HOUR_OF_DAY), MINUTE(
				Calendar.MINUTE);

		private int value;

		private DateCalendar(int i) {
			this.value = i;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

	}

	private static HashMap<DateTypeEnum, SimpleDateFormat> formatters = new HashMap<DateTypeEnum, SimpleDateFormat>();

	/**
	 * 将日期转成指定的格式的字符串
	 *
	 * @param date
	 *            Date类型 目标日期
	 * @param pattern
	 *            DateTypeEnum类型 转换的类型
	 * @return 返回pattern格式的字符串
	 */
	public static String getString(Date date, DateTypeEnum pattern) {
		if (date != null) {
			SimpleDateFormat formatter = formatters.get(pattern);
			if (formatter == null) {
				formatter = new SimpleDateFormat(DateTypeEnum.patternFromType(pattern));
				formatters.put(pattern, formatter);
			}
			return formatter.format(date);
		}
		throw new UtilRuntimeException(ExceptionTypeEnum.DATE_FORMATTER, "日期转字符串错误！");
	}

	/**
	 * 将String类型Date类型Long类型转换成Date类型
	 *
	 * @param obj
	 *            目标对象
	 * @param pattern
	 *            DateTypeEnum类型 转换的类型
	 * @return 返回pattern格式的Date类型对象
	 */
	public static Date getDate(Object obj, DateTypeEnum pattern) {
		if (obj == null) {
			throw new UtilRuntimeException(ExceptionTypeEnum.DATE_FORMATTER, "目标对象为null");
		}
		if (obj instanceof String) {
			try {
				SimpleDateFormat formatter = formatters.get(pattern);
				if (formatter == null) {
					formatter = new SimpleDateFormat(DateTypeEnum.patternFromType(pattern));
					formatters.put(pattern, formatter);
				}
				return formatter.parse((String) obj);
			} catch (ParseException e) {
				throw new UtilRuntimeException(ExceptionTypeEnum.DATE_FORMATTER, obj.toString() + "目标对象无法转换成日期");
			}
		} else if (obj instanceof Date) {
			return (Date) obj;
		} else if (obj instanceof Long) {
			return new Date(((Long) obj).longValue());
		} else if (obj instanceof Integer) {
			return new Date((long) ((Integer) obj).intValue() * 1000);
		}
		throw new UtilRuntimeException(ExceptionTypeEnum.DATE_FORMATTER, obj.toString() + "目标对象类型错误！");
	}

	/**
	 * 日期的指定域加减
	 *
	 * @param date
	 *            时间戳(长整形字符串)
	 * @param dateCalendar
	 *            加减的域,如date表示对天进行操作,month表示对月进行操作,其它表示对年进行操作
	 * @param num
	 *            加减的数值
	 * @return 操作后的时间戳(长整形字符串)
	 */
	public static Date addDate(Date date, DateCalendar dateCalendar, int num) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(date.getTime());
			cal.add(dateCalendar.getValue(), num);
			return new Date(cal.getTimeInMillis());
		}
		return null;
	}
}
