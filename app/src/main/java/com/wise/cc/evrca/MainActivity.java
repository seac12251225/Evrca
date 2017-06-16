package com.wise.cc.evrca;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unity3d.player.UnityPlayer;
import com.wise.cc.evrca.cushionData.JsonsRootBean;
import com.wise.cc.evrca.cushionData.Material;
import com.wise.cc.evrca.cushionData.MaterialColor;

import layout.CartCtr;
import usb_serial.UsbSerialCC;

public class MainActivity extends AppCompatActivity {


    CartCtr cartCtr;

    UsbSerialCC usbcc;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    //USB 回调
    public interface UsbCallBack{
        void receiveMessageFromUSB(String message);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //读json文件
        try{
            InputStreamReader isr = new InputStreamReader(getAssets().open("evrcaProduct.json"));
            Data.loadDataFromeServer(isr);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        cartCtr=(CartCtr)getSupportFragmentManager().findFragmentById(R.id.frag_cart);
        //默认选择材质 皇家至尊系列
        cartCtr.selectMaterialIndex("A");
        //滚边和包边只能用雅致系列加柠檬黄颜色("Z")
        Material borderMaterial=Data.findMaterialWithIndex("Z");
        cartCtr.cushion.getBorder().setMaterial(borderMaterial);
        cartCtr.cushion.getBorderSide().setMaterial(borderMaterial);

        //脚垫
        Material footMiddleMaterial=Data.findMaterialWithIndex("E");
        cartCtr.foot.getAround().setMaterial(borderMaterial);
        cartCtr.foot.getBorderSide().setMaterial(borderMaterial);
        cartCtr.foot.getMiddle().setMaterial(footMiddleMaterial);


        //选择材质
        View.OnClickListener materialListener=new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //选择材质
                selectMaterialSerial(v);
            }
        };
        Button buM1=(Button)findViewById(R.id.buMaterialS1);
        Button buM2=(Button)findViewById(R.id.buMaterialS2);
        Button buM3=(Button)findViewById(R.id.buMaterialS3);
        buM1.setOnClickListener(materialListener);
        buM2.setOnClickListener(materialListener);
        buM3.setOnClickListener(materialListener);


        //选择款式
        View.OnClickListener partStyleListener=new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //选择款式
                selectPartStyle(v);
            }
        };

        Button buStyleOrg=(Button)findViewById(R.id.buStyleOrg);
        Button buStyleBussine=(Button)findViewById(R.id.buStyleBussine);
        Button buStylePer=(Button)findViewById(R.id.buStylePer);
        Button buStyleFoot=(Button)findViewById(R.id.buStyleFoot);
        Button buStyleFootAddition=(Button)findViewById(R.id.buStyleFootAddition);

        buStyleOrg.setOnClickListener(partStyleListener);
        buStyleBussine.setOnClickListener(partStyleListener);
        buStylePer.setOnClickListener(partStyleListener);
        buStyleFoot.setOnClickListener(partStyleListener);
        buStyleFootAddition.setOnClickListener(partStyleListener);





        //---------  初始化unity3d  ------------------------------
        Log.d("main","------------------------------------------");
        Log.d("main","new unityPlayer");
        getWindow().setFormat(PixelFormat.RGBX_8888);
        //Data.mUnityPlayer=new UnityPlayer(this);


        //---------  初始化USB ------------------------------
        usbcc=new UsbSerialCC();
        //回调
        usbcc.setCallBack(new UsbCallBack() {
            @Override
            public void receiveMessageFromUSB(String message) {
                Log.d("main","receive usb message: "+message);
                cartCtr.receiveMessageFromUSB(message);
            }
        });

        usbcc.findAndOpenUsb(this,0);//打开并接收数据





        Log.d("main","-------------------- main on create  ----------------------------------");


        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }




    // Quit Unity
    @Override protected void onDestroy ()
    {
        Data.mUnityPlayer.quit();
        super.onDestroy();
    }




    //-------------- 界面  -------------------
    //选择材质系列
    private void selectMaterialSerial(View v){
        String index="A";
        switch (v.getId()){
            case R.id.buMaterialS1:
                index="A";
                break;
            case R.id.buMaterialS2:
                index="B";
                break;
            case R.id.buMaterialS3:
                index="F";
                break;
        }

        //传递到购物车cart
        cartCtr.selectMaterialIndex(index);



    }

    //选择位置组合,原车款，商务款，个性款，脚垫等
    private void selectPartStyle(View v){
        cartCtr.buAllPart.setVisibility(View.GONE);
        cartCtr.buMainPart.setVisibility(View.GONE);
        cartCtr.buAround.setVisibility(View.GONE);
        cartCtr.buMiddle.setVisibility(View.GONE);
        cartCtr.buAllBorder.setVisibility(View.GONE);
        cartCtr.buBorderSide.setVisibility(View.GONE);
        cartCtr.buBorder.setVisibility(View.GONE);
        cartCtr.buFootAround.setVisibility(View.GONE);
        cartCtr.buFootBorderSide.setVisibility(View.GONE);
        cartCtr.buFootMiddle.setVisibility(View.GONE);


        switch (v.getId()){
            case R.id.buStyleOrg:
                cartCtr.buAllPart.setVisibility(View.VISIBLE);
                cartCtr.buMiddle.setVisibility(View.VISIBLE);
                cartCtr.selectPart(cartCtr.buAllPart);
                cartCtr.buAllPart.setChecked(true);
                break;
            case R.id.buStyleBussine:
                cartCtr.buMainPart.setVisibility(View.VISIBLE);
                cartCtr.buAllBorder.setVisibility(View.VISIBLE);
                cartCtr.buMiddle.setVisibility(View.VISIBLE);

                cartCtr.selectPart(cartCtr.buMainPart);
                cartCtr.buMainPart.setChecked(true);
                break;
            case R.id.buStylePer:
                cartCtr.buAround.setVisibility(View.VISIBLE);
                cartCtr.buMiddle.setVisibility(View.VISIBLE);
                cartCtr.buBorderSide.setVisibility(View.VISIBLE);
                cartCtr.buBorder.setVisibility(View.VISIBLE);

                cartCtr.selectPart(cartCtr.buAround);
                cartCtr.buAround.setChecked(true);
                break;
            case R.id.buStyleFoot:
                cartCtr.buFootAround.setVisibility(View.VISIBLE);
                cartCtr.buFootBorderSide.setVisibility(View.VISIBLE);

                cartCtr.selectPart(cartCtr.buFootAround);
                cartCtr.buFootAround.setChecked(true);
                break;
            case R.id.buStyleFootAddition:
                cartCtr.buFootAround.setVisibility(View.VISIBLE);
                cartCtr.buFootBorderSide.setVisibility(View.VISIBLE);
                cartCtr.buFootMiddle.setVisibility(View.VISIBLE);

                cartCtr.selectPart(cartCtr.buFootAround);
                cartCtr.buFootAround.setChecked(true);
                break;
        }

    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
