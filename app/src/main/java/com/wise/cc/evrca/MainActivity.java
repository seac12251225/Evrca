package com.wise.cc.evrca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;

import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wise.cc.evrca.cushionData.JsonsRootBean;
import com.wise.cc.evrca.cushionData.MaterialColor;

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

            Data.loadDataFromeServer(isr);

            //RadioButton bu=new RadioButton(this);




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
