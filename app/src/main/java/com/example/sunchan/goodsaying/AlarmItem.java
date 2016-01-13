package com.example.sunchan.goodsaying;

public class AlarmItem {
    private int iFlag;
    private int iHour;
    private int iMin;

    public AlarmItem(){}

    public AlarmItem(int iFlag, int iHour, int iMin) {
        super();
        this.iFlag = iFlag;
        this.iHour = iHour;
        this.iMin = iMin;
    }

    public int getFlag() {
        return iFlag;
    }

    public void setFlag(int iFlag) {
        this.iFlag = iFlag;
    }

    public int getHour() {
        return iHour;
    }

    public void setHour(int iHour) {
        this.iHour = iHour;
    }

    public int getMin() {
        return iMin;
    }

    public void setMin(int iMin) {
        this.iMin = iMin;
    }
}