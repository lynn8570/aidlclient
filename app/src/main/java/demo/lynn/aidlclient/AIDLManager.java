package demo.lynn.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import demo.lynn.aidldemo.IMyAidlInterface;
import demo.lynn.aidldemo.IMyAidlInterfaceCallback;

/**
 * Created by lynn on 2018/1/19.
 * 负责与aidl服务的绑定，访问，和回调的传递
 */

public class AIDLManager {


    private IMyAidlInterface mAIDLservice = null;

    private static AIDLManager mInstance=null;

    public void bindService(Context context) {
        Intent intent = new Intent("demo.lynn.aidldemo.AIDLService");
        intent.setPackage("demo.lynn.aidldemo");
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    public void unbindService(Context context) {
        context.unbindService(mConnection);
    }


    public static AIDLManager getInstance(Context context){
        if(mInstance==null){
            mInstance=new AIDLManager();
        }
        mInstance.bindService(context);
        return mInstance;
    }
    private IMyAidlInterfaceCallback myAidlInterfaceCallback= new IMyAidlInterfaceCallback.Stub(){//can not be new !!!new IMyAidlInterfaceCallback() {
        @Override
        public void onSomeState(int id, int state) throws RemoteException {
            //TODO：
            Log.i("AIDLManager","client call back onSomeState"+id+" state="+state);

        }

        @Override
        public void onResult(int id, int result) throws RemoteException {
             //TODO：
        }

        @Override
        public void onError(String msg) throws RemoteException {
            //TODO：

        }


    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("AIDLManager","onServiceConnected");
            mAIDLservice = IMyAidlInterface.Stub.asInterface(iBinder);//建立访问
            try {
                if(mAIDLservice!=null){
                    Log.i("linlian","mAIDLservice="+mAIDLservice);
                    Log.i("linlian","myAidlInterfaceCallback="+myAidlInterfaceCallback);
                    mAIDLservice.registerListener(myAidlInterfaceCallback);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("AIDLManager","onServiceConnected");
            try {
                if(mAIDLservice!=null){

                    mAIDLservice.unregisterListener(myAidlInterfaceCallback);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }finally {

                mAIDLservice = null;
            }
        }
    };


    public String getAlVersion(){
        if(mAIDLservice!=null){
            try {
                return mAIDLservice.getAlVersion();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return "null";
    }

}
