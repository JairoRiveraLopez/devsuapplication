package com.application.jrl_technical_test.Utils;

import java.util.Calendar;
import java.util.Date;

public class FormatUtil {

    public static final String upperCase = "UPPERCASE";

    public static final String lowerCase = "LOWERCASE";

    public static String fullName(String name, String lastName1, String lastName2, String format){
        if(format.equalsIgnoreCase(upperCase)){
            return (name.toUpperCase() + " " + lastName1.toUpperCase() + " " + lastName2.toUpperCase());
        }else if(format.equalsIgnoreCase(lowerCase)) {
            return (name.toLowerCase() + " " + lastName1.toLowerCase() + " " + lastName2.toLowerCase());
        }else {
            return (name + " " + lastName1 + " " + lastName2);
        }
    }

    public static Date addSubstractDaysDate(Date date, int days){
        Calendar baseDate = Calendar.getInstance();
        baseDate.setTime(date);
        baseDate.add(Calendar.DAY_OF_WEEK, days);
        return baseDate.getTime();
    }

    public static Date getCleanTodayDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date result = calendar.getTime();
        return result;
    }

}
