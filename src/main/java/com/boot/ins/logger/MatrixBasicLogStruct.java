package com.boot.ins.logger;

import java.util.Map;

public class MatrixBasicLogStruct {


    String contextId;
    String parentContextId;
    String timeStamp;
    String catagory;  // debug,info,trace
    String sevirity;
    String application;
    String framework;
    String eventtimeStamp;
    String message;


    Map<String ,String> additionalProperties;




}
