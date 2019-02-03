package com.boot.ins.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.boot.ins.logger.LoggerDispatcher;
import com.boot.ins.logger.LoggerWritter;
import com.boot.ins.logger.MatrixBiLog;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class LoggerFilter implements Filter {

    @Autowired
    LoggerDispatcher loggerWritter;



    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        try {


            ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(
                    (HttpServletRequest) request);

            if (wrappedRequest.getMethod().equalsIgnoreCase("POST")) {
                String body = IOUtils.toString(wrappedRequest.getReader());

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(body);

                if(json.containsKey("correlationId"))
                {
                    loggerWritter.setCorrelationId(json.get("correlationId").toString());
                }

                for (String key : Collections.list(wrappedRequest.getHeaderNames())){
                    loggerWritter.addMetric(key, wrappedRequest.getHeader(key));

                }
                loggerWritter.addMetric("body", body);
            }

            if (wrappedRequest.getMethod().equalsIgnoreCase("GET")) {
                String body = IOUtils.toString(wrappedRequest.getReader());

                for (String key : Collections.list(wrappedRequest.getHeaderNames())) {
                    System.out.println(key + " " + wrappedRequest.getHeader(key));
                    loggerWritter.addMetric(key, wrappedRequest.getHeader(key));
                }
                loggerWritter.addMetric("queryString", ((HttpServletRequest) request).getRequestURI());
            }

            loggerWritter.addMetric("method", ((HttpServletRequest) request).getMethod());

            loggerWritter.logtrackTrace();

            wrappedRequest.resetInputStream();

            chain.doFilter(wrappedRequest, response);

        }
        catch (Exception e)
        {
            int tt=0;//Do nothing
        }

    }

    public void init(FilterConfig arg0) throws ServletException {
        // Nothing to do
    }

    private static class ResettableStreamHttpServletRequest extends
            HttpServletRequestWrapper {

        private byte[] rawData;
        private HttpServletRequest request;
        private ResettableServletInputStream servletStream;

        public ResettableStreamHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
            this.servletStream = new ResettableServletInputStream();
        }


        public void resetInputStream() {
            servletStream.stream = new ByteArrayInputStream(rawData);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            if (rawData == null) {
                rawData = IOUtils.toByteArray(this.request.getReader());
                servletStream.stream = new ByteArrayInputStream(rawData);
            }
            return servletStream;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            if (rawData == null) {
                rawData = IOUtils.toByteArray(this.request.getReader());
                servletStream.stream = new ByteArrayInputStream(rawData);
            }
            return new BufferedReader(new InputStreamReader(servletStream));
        }


        private class ResettableServletInputStream extends ServletInputStream {

            private InputStream stream;

            @Override
            public int read() throws IOException {

                return stream.read();

            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        }
    }

//    public void setAuditor(Auditor auditor) {
//        this.auditor = auditor;
//    }

}