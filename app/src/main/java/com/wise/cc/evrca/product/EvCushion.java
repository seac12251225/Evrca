package com.wise.cc.evrca.product;

/**
 * Created by suc on 2017/6/11.
 */

public class EvCushion {
    private String indexName;
    private String brand;
    private EvPart around;
    private EvPart middle;
    private EvPart border;
    private EvPart borderSide;
    private EvPart sewingThread; //缝纫线

    public EvCushion(String indexName){
        this.indexName=indexName;
        brand="EVRCA";

        //this.around=new EvPart("面边");
        //this.middle=new EvPart("面中");
        //this.border=new EvPart("滚边");
        //this.borderSide=new EvPart("包边");
        //this.sewingThread=new EvPart("缝边");

    }


    public void setName(String name) {
        this.indexName = indexName;
    }
    public String getName() {
        return indexName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getBrand() {
        return brand;
    }

    public void setAround(EvPart around) {
        this.around = around;
    }
    public EvPart getAround() {
        return around;
    }

    public void setMiddle(EvPart middle) {
        this.middle = middle;
    }
    public EvPart getMiddle() {
        return middle;
    }

    public void setBorder(EvPart border) {
        this.border = border;
    }
    public EvPart getBorder() {
        return border;
    }

    public void setBorderSide(EvPart borderSide) {
        this.borderSide = borderSide;
    }
    public EvPart getBorderSide() {
        return borderSide;
    }

    public void setSewingThread(EvPart sewingThread) {
        this.sewingThread = sewingThread;
    }
    public EvPart getSewingThread() {
        return sewingThread;
    }

}
