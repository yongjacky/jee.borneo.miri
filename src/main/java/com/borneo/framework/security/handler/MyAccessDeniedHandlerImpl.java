package com.borneo.framework.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.borneo.framework.common.utils.EConstant;
import com.borneo.framework.common.utils.JacksonUtils;
import com.borneo.framework.json.mo.RestResponse;

/**
 * User: seven_shi@qq.com Date: 13-9-17 Time: 下午5:48
 */

public class MyAccessDeniedHandlerImpl implements AccessDeniedHandler {

    public MyAccessDeniedHandlerImpl() {

    }

    public String getAccessDeniedUrl() {
        return accessDeniedUrl;
    }

    public void setAccessDeniedUrl(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }

    public MyAccessDeniedHandlerImpl(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }

    private String accessDeniedUrl;

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException reason) throws ServletException, IOException {
        boolean isAPI = req.getRequestURI().indexOf("rest") != -1;
        if (isAPI) {
            RestResponse restResponse = new RestResponse();
            restResponse.setStatus(EConstant.ErrorCode.E_403.name());
            restResponse.setMessage(reason.getMessage());
            ObjectMapper mapper = JacksonUtils.getMapperInstance(false);
            StringWriter writer = new StringWriter();
            JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
            mapper.writeValue(gen, restResponse);
            gen.close();
            String json = writer.toString();

            String contentType = "application/json";
            resp.setContentType(contentType);
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
            out.close();
            return;
        } else {
            String path = req.getContextPath();
            req.getSession().setAttribute("exception", reason);
            resp.sendRedirect(path + accessDeniedUrl);
        }
    }
}
