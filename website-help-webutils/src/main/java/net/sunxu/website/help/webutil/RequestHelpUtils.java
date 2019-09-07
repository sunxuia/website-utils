package net.sunxu.website.help.webutil;

import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
public class RequestHelpUtils {

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        final String unknown = "unknown";
        if (Strings.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (Strings.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (Strings.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (Strings.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (Strings.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
