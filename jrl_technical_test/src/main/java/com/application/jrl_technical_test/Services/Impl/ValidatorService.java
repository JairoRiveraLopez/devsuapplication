package com.application.jrl_technical_test.Services.Impl;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class ValidatorService {

    public boolean objectHasNullAttributes(Object object) throws IllegalAccessException {
        try{
            Field[] attributes = object.getClass().getDeclaredFields();
            for(Field attribute : attributes){
                attribute.setAccessible(true);
                if(attribute.get(object) == null){
                    return true;
                }
            }
            return false;
        } catch (IllegalAccessException error){
            throw error;
        }

    }
}
