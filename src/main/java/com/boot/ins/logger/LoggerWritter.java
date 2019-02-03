package com.boot.ins.logger;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;
import org.springframework.beans.factory.annotation.Autowired;

public interface LoggerWritter {

    public void addMetric(String key,String value);

    public void logtrackTrace();

    public void setCorrelationId(String correlationId);

}
