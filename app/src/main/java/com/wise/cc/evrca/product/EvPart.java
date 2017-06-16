package com.wise.cc.evrca.product;

import com.wise.cc.evrca.cushionData.Material;
import com.wise.cc.evrca.cushionData.MaterialColor;

/**
 * Created by suc on 2017/6/11.
 */

public class EvPart {
    private String indexName;//around,middle,border,borderSide,sewingThread
    private String displayName;
    private Material material;//材质
    private MaterialColor materialColor;//颜色
    private String craft;//工艺

    public EvPart(String indexName){
        this.indexName=indexName;
    }

    public void setIndexName(String name) {
        this.indexName = name;
    }
    public String getIndexName() {
        return indexName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    public Material getMaterial() {
        return material;
    }

    public void setMaterialColor(MaterialColor materialColor) {
        this.materialColor = materialColor;
    }
    public MaterialColor getMaterialColor() {
        return materialColor;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }
    public String getCraft() {
        return craft;
    }

}
