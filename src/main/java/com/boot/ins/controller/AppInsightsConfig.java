package com.boot.ins.controller;



import com.boot.ins.logger.MatrixBiLog;
import com.microsoft.applicationinsights.TelemetryClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.web.internal.WebRequestTrackingFilter;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;


@Configuration
public class AppInsightsConfig {

    @Bean
    public TelemetryClient telemetryClient()
    {
        return new TelemetryClient();
    }

    TelemetryClient telemetry = new TelemetryClient();

    @Bean
    @RequestScope
    public MatrixBiLog matrixBiLog() {
        return new MatrixBiLog( UUID.randomUUID().toString());
    }

    @Bean
    public void telemetryConfig() {
        String telemetryKey = System.getenv("APPLICATION_INSIGHTS_IKEY");
        //telemetryKey="e9ac7ef6-13d6-413c-a0f1-92e019d9149f";
        if (telemetryKey != null) {
            TelemetryConfiguration.getActive().setInstrumentationKey(telemetryKey);
        }
    }



    /**
     * Programmatically registers a FilterRegistrationBean to register WebRequestTrackingFilter
     * @param webRequestTrackingFilter
     * @return Bean of type {@link FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean webRequestTrackingFilterRegistrationBean(WebRequestTrackingFilter webRequestTrackingFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(webRequestTrackingFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        return registration;
    }


    /**
     * Creates bean of type WebRequestTrackingFilter for request tracking
     * @param applicationName Name of the application to bind filter to
     * @return {@link Bean} of type {@link WebRequestTrackingFilter}
     */
    @Bean
    @ConditionalOnMissingBean
    public WebRequestTrackingFilter webRequestTrackingFilter(@Value("${spring.application.name:application}") String applicationName) {
        return new WebRequestTrackingFilter(applicationName);
    }


}