package dev.dazai.wol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import dev.dazai.wol.databinding.ActiveDeviceListItemBinding;

public class ActiveDevicesListAdapter extends RecyclerView.Adapter<ActiveDevicesListAdapter.MyViewHolder>  {
    private Context mContext;
    protected List<Device> mActiveDevicesList;
    private onDeviceClick mOnDeviceListener;
    private int size = 0;

    public ActiveDevicesListAdapter(Context context, onDeviceClick onDeviceListener){
        mContext = context;
        mOnDeviceListener = onDeviceListener;

    }

    @NonNull
    @Override
    public ActiveDevicesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActiveDeviceListItemBinding itemBinding = ActiveDeviceListItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new MyViewHolder(itemBinding, mOnDeviceListener);


    }

    @Override
    public void onBindViewHolder(@NonNull ActiveDevicesListAdapter.MyViewHolder holder, int position) {
        Device device = mActiveDevicesList.get(position);
        holder.name.setText(device.getDeviceName());
        holder.icon.setText(device.getDeviceIcon());

    }

    @Override
    public int getItemCount() {
        return size;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        onDeviceClick onDeviceListener;
        TextView name, icon;
        CardView itemContainer;
        public MyViewHolder(@NonNull ActiveDeviceListItemBinding itemView, onDeviceClick onDeviceListener) {
            super(itemView.getRoot());
            name = itemView.name;
            icon = itemView.icon;
            itemContainer = itemView.itemContainer;
            this.onDeviceListener = onDeviceListener;

            itemContainer.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onDeviceListener.onDeviceCardClick(mActiveDevicesList.get(getAdapterPosition()));
        }
    }

    public void setActiveDevices(List<Device> devices){
        mActiveDevicesList = devices;
        size = mActiveDevicesList.size();
        notifyDataSetChanged();

    }

    public interface onDeviceClick {
        void onDeviceCardClick(Device device);

    }


}
