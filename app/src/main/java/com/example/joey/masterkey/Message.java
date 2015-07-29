package com.example.joey.masterkey;

import java.io.Serializable;

/**
 * Created by joey on 7/29/15.
 */
public class Message{

    public enum Type{
        INFO, CONN, OPEN, ERROR, CONFIRM
    }

    private Type type;
    private String token;
    private String extraInfo;


    public Message(Type type, String token, String extraInfo) {
        this.type = type;
        this.token = token;
        this.extraInfo = extraInfo;
    }

    public Message(String message){
        String[] values = message.split(",");
        this.type = Type.valueOf(values[0]);
        this.token = values[1];
        this.extraInfo = values[2];
    }

    public Message() {

    }

    @Override
    public String toString() {
        return type +
                "," + token +
                "," + extraInfo;
    }

    public Type getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public String getExtraInfo() {
        return extraInfo;
    }


}