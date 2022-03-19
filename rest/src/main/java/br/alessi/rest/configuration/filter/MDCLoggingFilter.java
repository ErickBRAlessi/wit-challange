package br.alessi.rest.configuration.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        UUID requestID = UUID.randomUUID();
        MDC.put("method", req.getMethod());
        MDC.put("request", requestID.toString());
        MDC.put("uri", req.getRequestURI());

        ((HttpServletResponse) response).addHeader("request-id", requestID.toString());
        chain.doFilter(request, response);

    }

}