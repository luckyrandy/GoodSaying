package com.example.sunchan.goodsaying;

public class Item {
    private int iId;
    private String sText;

    public Item(){}

    public Item(String sText) {
        super();
        this.sText = sText;
    }

    public int getId() {
        return iId;
    }

    public void setId(int iId) {
        this.iId = iId;
    }

    public String getText() {
        return sText;
    }

    public void setText(String sText) {
        this.sText = sText;
    }
}