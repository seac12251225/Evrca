package com.wise.cc.evrca;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unity3d.player.UnityPlayer;
import com.wise.cc.evrca.cushionData.JsonsRootBean;
import com.wise.cc.evrca.cushionData.Material;
import com.wise.cc.evrca.cushionData.MaterialColor;
import com.wise.cc.evrca.cushionData.Part;
import com.wise.cc.evrca.cushionData.Products;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 */

public class Data {

    public static UnityPlayer mUnityPlayer;

    static JsonsRootBean EvrcaData;

    public static void loadDataFromeServer(InputStreamReader inputStrem){
        //InputStreamReader isr=new InputStreamReader();

        //InputStreamReader inputStrem;// = new InputStreamReader(getAssets().open("evrcaProduct.json"));

        Log.d("data","--------------------- load data ----------------------------");
        //读数据
        Gson gson=new GsonBuilder().create();
        EvrcaData= gson.fromJson(inputStrem,JsonsRootBean.class);
        //Log.d("data","cuData: "+cuData.toString().toString());

        Log.d("data","cushionData name: "+EvrcaData.getMsg());

        Log.d("data","material size: "+Integer.toString(EvrcaData.getMaterial().size()));

        Log.d("data","product size: "+Integer.toString(EvrcaData.getProducts().size()));

        Log.d("data","materialColor size: "+Integer.toString(EvrcaData.getMaterialColor().size()));

        for (int i=0;i<EvrcaData.getMaterialColor().size();i++)
        {
            MaterialColor col=EvrcaData.getMaterialColor().get(i);
            Log.d("data","color name: "+col.getName());

        }

        //RadioButton bu=new RadioButton(this);

    }

    //由materialIndex 查找材质Material
    public static Material findMaterialWithIndex(String materialIndex){

        if (EvrcaData!=null){
            for (Material material:EvrcaData.getMaterial()){
                if (material.getIndex().equals(materialIndex)){
                    return material;
                }
            }

        }
        return null;
    }

    //由material 返回包含的颜色列表
    public static List<MaterialColor> getColorListWithMaterial(Material material){
        List<MaterialColor> colList=null;
        if (material!=null){
            colList=new ArrayList<MaterialColor>();

            for (String idx:material.getColorList()){
                int colorIndex=Integer.parseInt(idx);
                MaterialColor mCol=EvrcaData.getMaterialColor().get(colorIndex);
                colList.add(mCol);
            }
        }
        return colList;
    }

    //由materialIndex 返回包含的颜色列表
    public static List<MaterialColor> findColorListWith(String materialIndex){

        List<MaterialColor> colList=null;

        if (EvrcaData!=null){

            colList=new ArrayList<MaterialColor>();

            for (Material m:EvrcaData.getMaterial()){
                if (m.getIndex().equals(materialIndex)){
                    //Log.d("data","find material: "+m.getName());
                    for (String idx:m.getColorList()){
                        int colorIndex=Integer.parseInt(idx);
                        MaterialColor mCol=EvrcaData.getMaterialColor().get(colorIndex);
                        colList.add(mCol);
                    }
                }
            }

        }

        return colList;
    }

}
