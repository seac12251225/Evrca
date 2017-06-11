package com.wise.cc.evrca;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wise.cc.evrca.cushionData.JsonsRootBean;
import com.wise.cc.evrca.cushionData.MaterialColor;

import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/6/9.
 */

public class Data {


    public static void loadDataFromeServer(InputStreamReader inputStrem){
        //InputStreamReader isr=new InputStreamReader();

        //InputStreamReader inputStrem;// = new InputStreamReader(getAssets().open("evrcaProduct.json"));

        Log.d("data","--------------------- load data ----------------------------");
        //读数据
        Gson gson=new GsonBuilder().create();
        JsonsRootBean cuData= gson.fromJson(inputStrem,JsonsRootBean.class);
        //Log.d("data","cuData: "+cuData.toString().toString());

        Log.d("data","cushionData name: "+cuData.getMsg());

        Log.d("data","material size: "+Integer.toString(cuData.getMaterial().size()));

        Log.d("data","product size: "+Integer.toString(cuData.getProducts().size()));

        Log.d("data","materialColor size: "+Integer.toString(cuData.getMaterialColor().size()));

        for (int i=0;i<cuData.getMaterialColor().size();i++)
        {
            MaterialColor col=cuData.getMaterialColor().get(i);
            Log.d("data","color name: "+col.getName());

        }

        //RadioButton bu=new RadioButton(this);

    }

}
