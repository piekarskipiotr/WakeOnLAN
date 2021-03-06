package dev.dazai.wol.network;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.util.List;

import dev.dazai.wol.DashboardFragment;
import dev.dazai.wol.data.Device;

public class DeviceReachableHandler extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "ReachableHandler: ";
    private WeakReference<DashboardFragment> weakReference;
    public DeviceReachableHandler(DashboardFragment fragment, List<Device> devices) {
        allDevices = devices;
        size = devices.size() - 1;
        weakReference = new WeakReference<>(fragment);
    }
    List<Device> allDevices;
    int size;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG,"checking...");
        weakReference.get().binding.listOfAllActiveButton.setClickable(false);
        weakReference.get().binding.listOfAllSavedButton.setClickable(false);
        weakReference.get().binding.activeDevicesRecyclerView.setVisibility(View.GONE);
        weakReference.get().binding.devicesRecyclerView.setVisibility(View.GONE);
        weakReference.get().binding.activeDevicesProgressBar.setVisibility(View.VISIBLE);
        weakReference.get().binding.devicesProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        Device device;
        boolean preDeviceReachableStatus = false;
        boolean currentDeviceReachableStatus;
            for(int i = 0; i <= size; i++){
                device = allDevices.get(i);

                if(device.getReachable() != null){
                    preDeviceReachableStatus = device.getReachable();
                }else{
                    try {
                        currentDeviceReachableStatus = InetAddress.getByName(device.getDeviceIpAddress()).isReachable(500);

                        device.setReachable(currentDeviceReachableStatus);
                        weakReference.get().updateDeviceReachableStatus(device);
                        continue;

                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }

                try {
                    currentDeviceReachableStatus = InetAddress.getByName(device.getDeviceIpAddress()).isReachable(500);

                    if(preDeviceReachableStatus != currentDeviceReachableStatus){
                        device.setReachable(currentDeviceReachableStatus);
                        weakReference.get().updateDeviceReachableStatus(device);

                    }

                }catch(IOException e){
                    e.printStackTrace();
                }
            }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        weakReference.get().binding.listOfAllActiveButton.setClickable(true);
        weakReference.get().binding.listOfAllSavedButton.setClickable(true);
        weakReference.get().binding.activeDevicesRecyclerView.setVisibility(View.VISIBLE);
        weakReference.get().binding.devicesRecyclerView.setVisibility(View.VISIBLE);
        weakReference.get().binding.activeDevicesProgressBar.setVisibility(View.GONE);
        weakReference.get().binding.devicesProgressBar.setVisibility(View.GONE);

    }

}
