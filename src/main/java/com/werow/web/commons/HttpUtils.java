package com.werow.web.commons;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class HttpUtils {

    /**
     * 파라미터가 담긴 Map을 쿼리스트링 형식의 String으로 변환
     */
    public String mapToQueryString(Map<String, Object> paramsMap) {
        StringBuffer sb = new StringBuffer();
        for (String key : paramsMap.keySet()) {
            sb.append(key).append("=").append((String) paramsMap.get(key)).append("&");
        }
        sb.deleteCharAt(sb.lastIndexOf("&"));
        return sb.toString();
    }

    /**
     * 클라이언트의 IP 주소 반환
     */
    public String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 서버 호스트명 반환
     */
    public String getServerHostName(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String requestURI = request.getRequestURI();
        return requestURL.substring(0, requestURL.indexOf(requestURI));
    }

}
