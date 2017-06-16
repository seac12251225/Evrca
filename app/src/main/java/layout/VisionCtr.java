package layout;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unity3d.player.UnityPlayer;
import com.wise.cc.evrca.Data;
import com.wise.cc.evrca.R;

public class VisionCtr extends Fragment {

    private LinearLayout u3dLayout;

    View visionView;//this view





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_vision, container, false);
        //visionView=view;

        Log.d("vision","create vision fragment view");
        //
        Data.mUnityPlayer=new UnityPlayer(getActivity());
        u3dLayout = (LinearLayout)view.findViewById(R.id.u3d_layout);
        u3dLayout.addView(Data.mUnityPlayer);
        Data.mUnityPlayer.requestFocus();


        return view;
    }


    @Override
    public void onResume() {
        //Log.d("FragmentC", "onResume");
        super.onResume();
        Data.mUnityPlayer.resume();
        Data.mUnityPlayer.windowFocusChanged(true);//不执行会黑屏
    }

    @Override
    public void onPause()
    {
        Log.d("FragmentC", "onPause");
        Data.mUnityPlayer.pause();

        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("FragmentC", "onStop");
        //Data.mUnityPlayer.stop
    }


    @Override
    public void onDestroyView() {

        Data.mUnityPlayer.windowFocusChanged(false);
        u3dLayout.removeAllViews();

        super.onDestroyView();
        Log.d("FragmentC", "onDestroyView");
    }

    @Override
    public void onDestroy ()
    {
        Log.d("FragmentC", "onDestroy");
        //Data.mUnityPlayer.windowFocusChanged(false);
        //Data.mUnityPlayer.quit();
        super.onDestroy();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        Log.d("FragmentC", "onConfiguationChange");
        super.onConfigurationChanged(newConfig);
        Data.mUnityPlayer.configurationChanged(newConfig);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("FragmentC", "onDetach");
    }



}
