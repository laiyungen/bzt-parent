package com.atwzw.commonutils.utils;

import com.atwzw.commonutils.RestResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一请求响应类
 */
public class ResponseUtil {

    public static void out(HttpServletResponse response, RestResult restResult) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), restResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
