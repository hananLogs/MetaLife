package com.boot.ins.logger;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MatrixBiLog {

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    private String correlationId="";

    public String getCorrelationId() {
        return correlationId;
    }

    private Date date;


    private Map<String,String> contex = new HashMap<String, String>();

    public Map<String, String> getContex() {
        return contex;
    }


    public MatrixBiLog(String correlationId) {
        this.correlationId = correlationId;
       // DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/DD HH:mm:ss");
        Date date = new Date();
        this.date = date;
    }


    public void addToContex(String key ,Object value) {
        this.contex.put(key,value.toString());
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

