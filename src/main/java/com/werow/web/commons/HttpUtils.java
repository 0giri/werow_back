package com.werow.web.commons;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class HttpUtils {

    /**
     * 외부 Host와의 Connection 생성
     */
    public HttpURLConnection createConnection(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        return con;
    }

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
     * JSON 형식의 응답 결과를 파싱해 JsonNode로 변환
     */
    public JsonNode responseToJsonNode(HttpURLConnection con) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(con.getInputStream());
    }

    public void setRequestBody(HttpURLConnection con, Map<String, Object> paramsMap) throws IOException {
        String queryString = mapToQueryString(paramsMap);

        OutputStream os = con.getOutputStream();
        os.write(queryString.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();
    }

    public JsonNode executeRequest(HttpURLConnection con) throws IOException {
        con.connect();
        JsonNode result = responseToJsonNode(con);
        con.disconnect();
        return result;
    }

    public String getClientIp(HttpServletRequest request) {
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
}
