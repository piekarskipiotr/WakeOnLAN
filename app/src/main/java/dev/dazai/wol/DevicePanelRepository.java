package dev.dazai.wol;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

public class DevicePanelRepository {
    private DataDao dataDao;
    private LiveData<Device> deviceById;


    public DevicePanelRepository(Application application){
        DeviceDatabase deviceDatabase = DeviceDatabase.getInstance(application);
        dataDao = deviceDatabase.dataDao();
        
    }

    public LiveData<Device> getDeviceById(int id){
        deviceById = dataDao.getById(id);
        return deviceById;
    }

    public void insert(Device device){
        new InsertDeviceAsyncTask(dataDao).execute(device);

    }

    public void update(Device device){
        new UpdateDeviceAsyncTask(dataDao).execute(device);

    }

    public void delete(Device device){
        new DeleteDeviceAsyncTask(dataDao).execute(device);

    }

    private static class InsertDeviceAsyncTask extends AsyncTask<Device, Void, Void> {
        private DataDao dataDao;
        private InsertDeviceAsyncTask(DataDao dataDao){
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Device... devices) {
            dataDao.insert(devices[0]);
            return null;
        }

    }

    private static class UpdateDeviceAsyncTask extends AsyncTask<Device, Void, Void> {
        private DataDao dataDao;
        private UpdateDeviceAsyncTask(DataDao dataDao){
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Device... devices) {
            dataDao.update(devices[0]);
            return null;
        }

    }

    private static class DeleteDeviceAsyncTask extends AsyncTask<Device, Void, Void> {
        private DataDao dataDao;
        private DeleteDeviceAsyncTask(DataDao dataDao){
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Device... devices) {
            dataDao.delete(devices[0]);
            return null;
        }
    }

}
