package layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wise.cc.evrca.Data;
import com.wise.cc.evrca.R;
import com.wise.cc.evrca.cushionData.Material;
import com.wise.cc.evrca.cushionData.MaterialColor;
import com.wise.cc.evrca.cushionData.Part;
import com.wise.cc.evrca.product.EvCushion;
import com.wise.cc.evrca.product.EvPart;

import java.util.ArrayList;
import java.util.List;


public class CartCtr extends Fragment {

    //private String selectProductType="cushion";//"cushion" or "foot"
    //private String selectPart="Around";//"Around","Middle","Border","BorderSide"
    //public String selectMaterialIndex="A";//"皇家至尊系列" 等

    public EvCushion cushion,foot;//座垫，脚垫
    public List<EvPart> selectPartArry;//选择部位数组

    private RadioGroup colorChooseLayout,otherMaterialSelectLayout,craftSelectLayout;
    public RadioButton buAllPart,buMainPart,buAllBorder,buAround,buMiddle,buBorder,buBorderSide,buFootAround,buFootBorderSide,buFootMiddle;
    private RadioButton buMaterial_org,buMaterial_suede,buMaterial_summer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_cart, container, false);


        View.OnClickListener partSelectListener=new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectPart(v);
            }
        };

        //产品
        cushion=new EvCushion("座垫");
        cushion.setAround(new EvPart("around"));
        cushion.getAround().setDisplayName("面边");

        cushion.setMiddle(new EvPart("middle"));
        cushion.getMiddle().setDisplayName("面中");

        cushion.setBorder(new EvPart("border"));
        cushion.getBorder().setDisplayName("滚边");

        cushion.setBorderSide(new EvPart("borderSide"));
        cushion.getBorderSide().setDisplayName("包边");

        cushion.setSewingThread(new EvPart("sewingThread"));
        cushion.getSewingThread().setDisplayName("缝线");

        foot=new EvCushion("脚垫");
        foot.setAround(new EvPart("footAround"));
        foot.getAround().setDisplayName("面边");

        foot.setBorderSide(new EvPart("footBorderSide"));
        foot.getBorderSide().setDisplayName("面边");


        foot.setMiddle(new EvPart("footMiddle"));
        foot.getMiddle().setDisplayName("幻影");

        //默认选中原车款
        selectPartArry=new ArrayList<EvPart>();
        selectPartArry.add(cushion.getAround());
        selectPartArry.add(cushion.getMiddle());
        selectPartArry.add(cushion.getBorder());
        selectPartArry.add(cushion.getBorderSide());



        //档位按钮和监听
        buAllPart=(RadioButton)view.findViewById(R.id.buAllPart);
        buMainPart=(RadioButton)view.findViewById(R.id.buMainPart);
        buAllBorder=(RadioButton)view.findViewById(R.id.buAllBorder);
        buAround=(RadioButton)view.findViewById(R.id.buAround);
        buMiddle=(RadioButton)view.findViewById(R.id.buMiddle);
        buBorder=(RadioButton)view.findViewById(R.id.buBorder);
        buBorderSide=(RadioButton)view.findViewById(R.id.buBorderSide);

        buFootAround=(RadioButton)view.findViewById(R.id.buFootAround);
        buFootBorderSide=(RadioButton)view.findViewById(R.id.buFootBorderSide);
        buFootMiddle=(RadioButton)view.findViewById(R.id.buFootMiddle);

        buAllPart.setOnClickListener(partSelectListener);
        buMainPart.setOnClickListener(partSelectListener);
        buAllBorder.setOnClickListener(partSelectListener);
        buAround.setOnClickListener(partSelectListener);
        buMiddle.setOnClickListener(partSelectListener);
        buBorder.setOnClickListener(partSelectListener);
        buBorderSide.setOnClickListener(partSelectListener);
        buFootAround.setOnClickListener(partSelectListener);
        buFootBorderSide.setOnClickListener(partSelectListener);
        buFootMiddle.setOnClickListener(partSelectListener);



        CompoundButton.OnCheckedChangeListener mSelectListener= new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
                selectOtherMaterial(buttonView,isChecked);
            }
        };
        buMaterial_org=(RadioButton)view.findViewById(R.id.buMaterial_org);
        buMaterial_suede=(RadioButton)view.findViewById(R.id.buMaterial_suede);
        buMaterial_summer=(RadioButton)view.findViewById(R.id.buMaterial_summer);
        buMaterial_org.setOnCheckedChangeListener(mSelectListener);
        buMaterial_suede.setOnCheckedChangeListener(mSelectListener);
        buMaterial_summer.setOnCheckedChangeListener(mSelectListener);



        colorChooseLayout=(RadioGroup) view.findViewById(R.id.layout_ColorChoose);
        otherMaterialSelectLayout=(RadioGroup)view.findViewById(R.id.layout_otherMaterial);
        craftSelectLayout=(RadioGroup)view.findViewById(R.id.layout_craftSelect);




        return view;
    }






    //------------ 界面 -----------------


    //View.OnClickListener colorButtonListener=new View.OnClickListener(){
    //    @Override
    //    public void onClick(View v){

    //    }
    //};
    //选择颜色
    private void selectColor(View v, MaterialColor mColor){

        //Log.d("cart","select color: "+mColor.getName());

        //安装部位
        /*
        String part="around";
        if (buMiddle.isChecked()){
            part="middle";
        }else if (buBorder.isChecked()){
            part="border";
        }else if (buBorderSide.isChecked()){
            part="borderSide";
        }
        */

        for (EvPart part:selectPartArry){
            //to unity3d
            String partIndex=part.getIndexName();
            String colStr=","+Float.toString(mColor.getR())+","+Float.toString(mColor.getG())+","+Float.toString(mColor.getB())+",1";
            Data.mUnityPlayer.UnitySendMessage("MainCamera","changeObjectColor",partIndex+colStr);
        }



    }

    //添加颜色选择按钮
    private void addColorButtonInLayout(){


        Material showMaterial=selectPartArry.get(0).getMaterial();


        List<MaterialColor> colorList= Data.getColorListWithMaterial(showMaterial);
        colorChooseLayout.removeAllViews();
        for (final MaterialColor mCol:colorList){

            int rr= (int) (255*mCol.getR());


            RadioButton bu=new RadioButton(this.getContext());
            //bu.setButtonDrawable(getResources().getDrawable(R.drawable.color_button_style,null));
            bu.setBackground(getResources().getDrawable(R.drawable.color_button_style,null));
            bu.setButtonDrawable(null);
            bu.setText("");
            //bu.setButtonTintList(ColorStateList.valueOf(Color.argb(255,(int) (255*mCol.getR()),(int) (255*mCol.getG()),(int) (255*mCol.getB()))));

            bu.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,(int) (255*mCol.getR()),(int) (255*mCol.getG()),(int) (255*mCol.getB()))));
            //button listener
            bu.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    selectColor(v,mCol);
                }
            });

            colorChooseLayout.addView(bu);

        }

    }

    //从读卡器接收颜色索引名
    public void receiveMessageFromUSB(String message)
    {
        //message MAXX   XX:索引名
        //截取索引名
        String index=message.substring(2);

        Material material=selectPartArry.get(0).getMaterial();
        List<MaterialColor> colorList= Data.getColorListWithMaterial(material);
        MaterialColor selColor=null;
        for (MaterialColor mCol:colorList) {
            if (mCol.getIndex().equals(index)){
                selColor=mCol;
                Log.d("cart","find color:"+selColor.getName());
                selectColor(null,selColor);
                break;

            }
        }
        if (selColor==null){
            //显示消息
            Toast.makeText(getContext(),"产品型号不匹配!",Toast.LENGTH_SHORT).show();
        }
         //Log.d("cart","color index: "+index);
    }

    //选择部位
    public void  selectPart(View v){

        selectPartArry.clear();
        //Material selectMaterial=cushion.getAround().getMaterial();
        int tag=Integer.parseInt((String)v.getTag());
        if (tag==0){
            otherMaterialSelectLayout.setVisibility(View.GONE);
            craftSelectLayout.setVisibility(View.GONE);
        }else {
            otherMaterialSelectLayout.setVisibility(View.VISIBLE);
            craftSelectLayout.setVisibility(View.VISIBLE);
        }



        switch (v.getId()){
            case R.id.buAllPart:
                //全车
                //selectMaterial=cushion.getAround().getMaterial();
                selectPartArry.add(cushion.getAround());
                selectPartArry.add(cushion.getMiddle());
                selectPartArry.add(cushion.getBorder());
                selectPartArry.add(cushion.getBorderSide());
                break;
            case R.id.buMainPart:
                //全部的面
                selectPartArry.add(cushion.getAround());
                selectPartArry.add(cushion.getMiddle());
                break;
            case R.id.buAround:
                //面边
                selectPartArry.add(cushion.getAround());
                break;
            case R.id.buMiddle:
                //面中
                selectPartArry.add(cushion.getMiddle());
                break;
            case R.id.buAllBorder:
                //全部的边
                selectPartArry.add(cushion.getBorder());
                selectPartArry.add(cushion.getBorderSide());
                break;
            case R.id.buBorder:
                //滚边
                selectPartArry.add(cushion.getBorder());
                break;
            case R.id.buBorderSide:
                //包边
                selectPartArry.add(cushion.getBorderSide());
                break;

            case R.id.buFootAround:
                //脚垫
                selectPartArry.add(foot.getAround());
                break;

            case R.id.buFootBorderSide:
                //脚垫
                selectPartArry.add(foot.getBorderSide());
                break;

            case R.id.buFootMiddle:
                //脚垫
                selectPartArry.add(foot.getMiddle());
                break;
        }


        Log.d("cart","select part:");


        //加入颜色选择按钮
        addColorButtonInLayout();


    }

    //在 MainActivity 选择材质
    public void selectMaterialIndex(String  materialIndex){
        //selectMaterialIndex=materialIndex;
        //刷新数据
        //selectOtherMaterial(null,false);

        Material material=Data.findMaterialWithIndex(materialIndex);

        if (material!=null){
            cushion.getAround().setMaterial(material);
            foot.getAround().setMaterial(material);


            //面中为空或三个系列之一时付值
            Material middleMaterial=cushion.getMiddle().getMaterial();
            if (middleMaterial==null){
                cushion.getMiddle().setMaterial(material);
            }else if (middleMaterial.getIndex().equals("A") | middleMaterial.getIndex().equals("B") | middleMaterial.getIndex().equals("F")){
                cushion.getMiddle().setMaterial(material);
            }
        }

        //显示颜色按钮
        addColorButtonInLayout();


    }

    //选择其它材质
    private void selectOtherMaterial(CompoundButton buttonView,boolean isChecked){

        Log.d("cart","select other material");

        Material material=cushion.getAround().getMaterial();

        if (buMaterial_suede.isChecked()){
            //麂皮绒
            material=Data.findMaterialWithIndex("C");

        }else if (buMaterial_summer.isChecked()){
            //夏目得
            material=Data.findMaterialWithIndex("K");

        }

        cushion.getMiddle().setMaterial(material);

        //显示颜色按钮
        addColorButtonInLayout();

        //addColorButtonInLayout(mIndex);


    }

    //选择工艺
    private void selectCarft(View v){

    }




}
