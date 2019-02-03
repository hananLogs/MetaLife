package com.boot.ins.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoggerDispatcher {

    @Autowired
    LoggerWritter [] loggerWritters;

    public void addMetric(String key,String value)
    {

        for (LoggerWritter logger: loggerWritters)
        {
            logger.addMetric(key,value);
        }
    }



    public void logtrackTrace() {

        for (LoggerWritter logger: loggerWritters)
        {
            logger.logtrackTrace();
        }
    }

    public void setCorrelationId(String correlationId) {
        for (LoggerWritter logger: loggerWritters)
        {
            logger.setCorrelationId(correlationId);
        }
    }

}

