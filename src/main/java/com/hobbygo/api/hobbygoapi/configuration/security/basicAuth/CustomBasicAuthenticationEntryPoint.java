package com.hobbygo.api.hobbygoapi.configuration.security.basicAuth;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hobbygo.api.hobbygoapi.restapi.exception.UserNonAuthorizatedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        //Authentication failed, send error response.

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        response.addHeader("Content-Type","application/json;charset=UTF-8");

        //PrintWriter writer = response.getWriter();
        //writer.println("HTTP Status 401 : " + authException.getMessage());

        UserNonAuthorizatedException userNonAuthorizatedException = new UserNonAuthorizatedException(
                HttpStatus.UNAUTHORIZED.value(),"Acceso Denegado"
        );

        userNonAuthorizatedException.addLink("rel","self");
        userNonAuthorizatedException.addLink("href",request.getRequestURL().toString());

        OutputStream out = response.getOutputStream();
        com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, userNonAuthorizatedException);
        out.flush();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("MY_TEST_REALM");
        super.afterPropertiesSet();
    }
}
