package com.example.map.service.impl;

import com.example.map.service.UtilValidate;
import org.springframework.stereotype.Service;

@Service
public class UtilValidateImpl implements UtilValidate {
    @Override
    public boolean isEmpty(String token) {
            if (token == null|| token.equals("")) {
                return true;
            } else {
                return false;
            }
    }
}
