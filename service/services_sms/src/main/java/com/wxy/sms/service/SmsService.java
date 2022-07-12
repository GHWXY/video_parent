package com.wxy.sms.service;

import java.util.Map;

public interface SmsService {
    boolean send(String PhoneNumbers, String templateCode, Map<String,Object> param);
}

