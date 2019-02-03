package com.boot.ins.logger;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class ApplicationInsightLoggerWritter implements LoggerWritter {

    @Value("${logging.level.appinsight}")
    String logLevel;

    @Autowired
    TelemetryClient telemetryClient;

    @Autowired
    MatrixBiLog matrixBiLog;

    public void addMetric(String key, String value) {
        matrixBiLog.getContex().put(key, value);
    }


    public void logtrackTrace() {

        try {

            if (MatrixBiLogLevels.valueOf(logLevel).equals(MatrixBiLogLevels.info)) {
                telemetryClient.trackTrace(matrixBiLog.getCorrelationId(), SeverityLevel.Information, matrixBiLog.getContex());
            } else if (MatrixBiLogLevels.valueOf(logLevel).equals(MatrixBiLogLevels.error)) {
                telemetryClient.trackTrace(matrixBiLog.getCorrelationId(), SeverityLevel.Error, matrixBiLog.getContex());
            } else {
                return;
            }
        } catch (Exception e) {
            int y = 0;
        }

    }

    @Override
    public void setCorrelationId(String correlationId) {

        if(matrixBiLog!=null)
        {
            matrixBiLog.setCorrelationId(correlationId);
        }

    }

    public void logtrackTrace( MatrixBiLog matrixBiLog) {

        try {

            if (MatrixBiLogLevels.valueOf(logLevel).equals(MatrixBiLogLevels.info)) {
                telemetryClient.trackTrace(matrixBiLog.getCorrelationId(), SeverityLevel.Information, matrixBiLog.getContex());
            } else if (MatrixBiLogLevels.valueOf(logLevel).equals(MatrixBiLogLevels.error)) {
                telemetryClient.trackTrace(matrixBiLog.getCorrelationId(), SeverityLevel.Error, matrixBiLog.getContex());
            } else {
                return;
            }
        } catch (Exception e) {
            int y = 0;
        }

    }
}
