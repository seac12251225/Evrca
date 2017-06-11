package com.wise.cc.evrca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;

import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wise.cc.evrca.cushion.JsonsRootBean;
import com.wise.cc.evrca.cushion.MaterialColor;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //读json文件
        try{
            InputStreamReader isr = new InputStreamReader(getAssets().open("evrcaProduct.json"));

            Log.d("data","--------------------- load data ----------------------------");
            //读数据
            Gson gson=new GsonBuilder().create();
            JsonsRootBean cuData= gson.fromJson(isr,JsonsRootBean.class);
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

            RadioButton bu=new RadioButton(this);




        }catch (Exception e)
        {
            e.printStackTrace();
        }



        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
