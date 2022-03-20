package br.alessi.rest.configuration.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCLoggingFilter implements Filter {

    @Value("${mdc.request.method}")
    private String httpMethod;
    @Value("${mdc.request.key}")
    private String requestKey;
    @Value("${mdc.request.uri}")
    private String uriKey;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        UUID requestID = UUID.randomUUID();
        MDC.put(httpMethod, req.getMethod());
        MDC.put(requestKey, requestID.toString());
        MDC.put(uriKey, req.getRequestURI());

        ((HttpServletResponse) response).addHeader("Request-Id", requestID.toString());
        chain.doFilter(request, response);

    }

}