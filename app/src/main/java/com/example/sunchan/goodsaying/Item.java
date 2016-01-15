package com.example.sunchan.goodsaying;

public class Item {
    private int iId;
    private String sText;
    private int iCount;

    public Item(){}

    public Item(String sText, int iCount) {
        super();
        this.sText = sText;
        this.iCount = iCount;
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

    public int getCount() {
        return iCount;
    }

    public void setCount(int iCount) {
        this.iCount = iCount;
    }
}