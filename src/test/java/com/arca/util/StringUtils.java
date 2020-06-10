package com.arca.util;

import java.util.Random;

public class StringUtils {

    public static String generateString(int length){
        final Random random = new Random();
        final StringBuilder builder = new StringBuilder();
        int count = 0;
        while(count < length){
            int next = random.nextInt(123);
            if((next >= 65 && next <= 90) || (next >= 97 && next <= 122) ){
               builder.append( (char) next );
               count++;
            }
        }
        System.gc();
        return builder.toString();
    }

    public static Integer generateInt(int bound){
        return new Random().nextInt(bound);
    }

}