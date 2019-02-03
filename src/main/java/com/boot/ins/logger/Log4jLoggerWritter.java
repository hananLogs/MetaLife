package com.boot.ins.logger;

import com.boot.ins.controller.TestController;
import com.google.gson.Gson;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Log4jLoggerWritter implements LoggerWritter {


    @Value("${logging.level.appinsight}")
    String logLevel;


    @Autowired
    MatrixBiLog matrixBiLog;

    private static final Logger logger = LogManager.getLogger(TestController.class);


    @Override
    public void addMetric(String key, String value) {
        matrixBiLog.addToContex(key, value);

    }

    @Override
    public void logtrackTrace() {

        if (MatrixBiLogLevels.valueOf(logLevel).equals(MatrixBiLogLevels.info)) {
            logger.log(Level.INFO, matrixBiLog.toString());
        } else if (MatrixBiLogLevels.valueOf(logLevel).equals(MatrixBiLogLevels.error)) {
            logger.log(Level.ERROR, matrixBiLog.toString());
        } else {
            return;
        }
    }

    @Override
    public void setCorrelationId(String correlationId) {
        matrixBiLog.setCorrelationId(correlationId);

    }


}




