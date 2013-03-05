package com.rejasupotaro.sson;

public class Token {

    public int type;
    public String value;

    public Token() {
        value = new String();
    }

    public void setType(int type) {
        this.type = type;
    }
}