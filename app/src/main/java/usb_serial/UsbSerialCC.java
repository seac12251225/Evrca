package usb_serial;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.wise.cc.evrca.MainActivity;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import usb_serial.driver.UsbSerialDriver;
import usb_serial.driver.UsbSerialPort;
import usb_serial.driver.UsbSerialProber;

/**
 * Created by suc on 2017/5/30.
 */

public class UsbSerialCC {

    private static final String ACTION_USB_PERMISSION="com.example.suc.androidtest.USB_PERMISSION";

    Activity activity;
    UsbManager manager;
    UsbSerialDriver driver;
    UsbSerialPort port;
    UsbDeviceConnection connection;
    boolean dataReceiving=true;
    int usbIdx=0;
    Thread th;



    public boolean findAndOpenUsb(Activity activity, int usbIndex)
    {

        Log.d("usb","------------------- USB ----------------------------------");

        this.activity=activity;
        usbIdx=usbIndex;

        // Find all available drivers from attached devices.
        manager=(UsbManager)activity.getSystemService(Context.USB_SERVICE);


        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            Log.e("usb","available drivers fail");
            return false;
        }

        //int usbNum=0;

        // Open a connection to the first available driver.
        driver = availableDrivers.get(usbIndex);

        //权限申请
        //接受到授权成功的广播
        class UsbPermissionReceiver extends BroadcastReceiver {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (ACTION_USB_PERMISSION.equals(action)) {
                    synchronized (this) {
                        UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (device.getDeviceName().equals(driver.getDevice().getDeviceName())) {
                            if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                                //授权成功,在这里进行打开设备操作
                                Log.e("usb","授权成功!");
                                connectToUsb();
                            } else {
                                //授权失败,提示
                                Log.e("usb","授权失败");
                            }
                        }
                    }
                }
            }
        }
        if (! manager.hasPermission(driver.getDevice()))
        {
            Log.e("usb","not permission!");
            UsbPermissionReceiver usbPermissionReceiver = new UsbPermissionReceiver();

            IntentFilter filter=new IntentFilter(ACTION_USB_PERMISSION);
            PendingIntent mPermissionIntent=PendingIntent.getBroadcast(activity,0,new Intent(ACTION_USB_PERMISSION),0);

            activity.registerReceiver(usbPermissionReceiver,filter);//登记接收

            manager.requestPermission(driver.getDevice(),mPermissionIntent);


        }else {
            //已有权限
            connectToUsb();
        }

        return true;

    }

    private void connectToUsb(){

        Log.e("usb","usb will connect  ");

        connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            Log.e("usb","usb connect  fail");
        }

        // Read some data! Most have just one port (port 0).
        port = driver.getPorts().get(usbIdx);
        try {

            port.open(connection);
            port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);

        } catch (IOException e) {
            // Deal with error.
            Log.e("usb","usb  connect fail");
            e.printStackTrace();
        }

        Log.d("usb","usb  connect sucess");
        //开始接收数据
        startReceive();

    }




    public void startReceive()
    {
        dataReceiving=true;

         th=new Thread(){

            public void run(){
                Instrumentation inst=new Instrumentation();

                while(dataReceiving)
                {
                    //try {
                    //    th.sleep(500);
                    // }catch (InterruptedException e ){e.printStackTrace();}
                    try {
                        byte buffer[] = new byte[16384];
                        int numBytesRead = port.read(buffer, 1000);

                       // Log.d("usb", "Read " + numBytesRead + " bytes.");

                        //callback.receiveMessageFromUSB("usb message");

                        //Log.d("usb",String.format("Receive %x  %x  %x  %x  %x  %x  %x  %x",buffer[0],buffer[1],buffer[2],buffer[3],buffer[4],buffer[5],buffer[6],buffer[7]));
                       // Log.d("usb",String.format("Receive %x  %x  %x  %x  %x  %x  %x  %x",buffer[8],buffer[9],buffer[10],buffer[11],buffer[12],buffer[13],buffer[14],buffer[15]));


                        if ((numBytesRead==28) & (buffer[0]==0x7f) & ((buffer[3]&0xff)==0x91) & (buffer[4]==0x0))
                        {
                            //0x7f:指令头  0x91:读块数据  0x0:读卡正确
                            int len=0;//求块内容的字符长度(块内容从11开始)
                            while ((len<16)&(buffer[len+11]!=0x0)){
                                len++;
                            }
                            final String mess=new String(buffer,11,len,"UTF-8");//转字符串
                            //Log.d("usb","get rfid card data:"+mess);

                            //主线程回调到main
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.receiveMessageFromUSB(mess);
                                }
                            });


                        }


                    } catch (IOException e) {
                        // Deal with error.
                        e.printStackTrace();

                    }
                }
            }
        };

        th.start();
    }


    public void stopReceive()
    {
        dataReceiving=false;
    }


    public void closeUsb()
    {
        try {
            port.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private MainActivity.UsbCallBack callback;
    public void setCallBack (MainActivity.UsbCallBack callback){
        this.callback=callback;

    }

}
