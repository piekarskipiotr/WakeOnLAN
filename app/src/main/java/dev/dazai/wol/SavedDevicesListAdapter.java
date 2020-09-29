package dev.dazai.wol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.dazai.wol.databinding.DeviceListItemBinding;

public class SavedDevicesListAdapter extends RecyclerView.Adapter<SavedDevicesListAdapter.MyViewHolder>  {
    private Context mContext;
    protected ArrayList<Device> mDevicesList;
    private OnMyDeviceListener mOnDeviceListener;

    public SavedDevicesListAdapter(Context context, ArrayList<Device> devicesList, OnMyDeviceListener onDeviceListener){
        mContext = context;
        mDevicesList = devicesList;
        mOnDeviceListener = onDeviceListener;

    }

    @NonNull
    @Override
    public SavedDevicesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeviceListItemBinding itemBinding = DeviceListItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new MyViewHolder(itemBinding, mOnDeviceListener);


    }

    @Override
    public void onBindViewHolder(@NonNull SavedDevicesListAdapter.MyViewHolder holder, int position) {
        Device device = mDevicesList.get(position);
        holder.name.setText(device.getDeviceName());
        holder.icon.setText(device.getDeviceIcon());

    }

    @Override
    public int getItemCount() {
        return mDevicesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnMyDeviceListener onDeviceListener;
        TextView name, icon;
        public MyViewHolder(@NonNull DeviceListItemBinding itemView, OnMyDeviceListener onDeviceListener) {
            super(itemView.getRoot());
            name = itemView.name;
            icon = itemView.icon;
            this.onDeviceListener = onDeviceListener;
        }

        @Override
        public void onClick(View v) {
            onDeviceListener.onDeviceClick(getAdapterPosition());
        }
    }

    public interface OnMyDeviceListener{
        void onDeviceClick(int position);

    }


}
