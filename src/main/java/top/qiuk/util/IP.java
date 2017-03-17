package top.qiuk.util;

import javax.servlet.http.HttpServletRequest;

public class IP {

    public static String getIP(HttpServletRequest request) throws Exception {

        if (request == null) {
            throw new Exception("getIpAddr method HttpServletRequest Object is null  请求对象是null");
        }

        // 获取ip地址
        String ip_address = null;
        if (null != checkIp(request.getHeader("X-Real-IP"))) {
            ip_address = request.getHeader("X-Real-IP");
        }
        if (null != checkIp(request.getHeader("x-forwarded-for"))) {
            ip_address = request.getHeader("x-forwarded-for");
        }
        if (null != checkIp(request.getHeader("Proxy-Client-IP"))) {
            ip_address = request.getHeader("Proxy-Client-IP");
        }
        if (null != checkIp(request.getHeader("WL-Proxy-Client-IP"))) {
            ip_address = request.getHeader("WL-Proxy-Client-IP");
        } else {
            ip_address = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip_address.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip_address = str;
                break;
            }
        }

        return ip_address;

    }

    private static String checkIp(String ip) {
        if (StringUtil.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return null;
    }
}
