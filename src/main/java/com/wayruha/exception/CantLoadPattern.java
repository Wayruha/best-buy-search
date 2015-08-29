package com.wayruha.exception;

import java.io.IOException;
import java.util.ArrayList;

public class CantLoadPattern extends IOException {
    String myErrorMessage;
    public CantLoadPattern(ArrayList<String> patternArr){
        StringBuilder builder=new StringBuilder();
        for (String patternName:patternArr){
            builder.append(" Не вдалося загрузити шаблон ");
            builder.append(patternName);
            builder.append(".");
        }
        myErrorMessage=builder.toString();

    }
    public CantLoadPattern(String message){
        super(message);
        myErrorMessage=message;
    }

    @Override
    public String getMessage() {
        return myErrorMessage;
    }
}
