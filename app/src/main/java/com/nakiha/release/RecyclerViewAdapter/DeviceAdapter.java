package com.nakiha.release.RecyclerViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nakiha.release.DatabaseModule.DeviceData;
import com.nakiha.release.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {


    private List<DeviceData> mDeviceDataList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView deviceImage;
        TextView deviceName;
        TextView deviceNoteTitle;
        TextView deviceNote;
        TextView deviceMac;

        ImageButton settingButton;
        ImageButton chatButton;

        Button leftButton;
        Button rightButton;

        ViewHolder(View view){
            super(view);
            deviceImage = view.findViewById(R.id.item_device_image);
            deviceName = view.findViewById(R.id.item_device_name);
            deviceNote = view.findViewById(R.id.item_device_note);
            deviceNoteTitle = view.findViewById(R.id.item_device_note_title);
            deviceMac = view.findViewById(R.id.item_device_mac);

            settingButton = view.findViewById(R.id.item_setting_button);
            chatButton = view.findViewById(R.id.item_chat_button);
            leftButton = view.findViewById(R.id.item_button_start);
            rightButton = view.findViewById(R.id.item_button_end);
        }
    }

    public DeviceAdapter(List<DeviceData> deviceDataList) {
        mDeviceDataList = deviceDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_device, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DeviceData deviceData = mDeviceDataList.get(i);
        viewHolder.deviceImage.setImageResource(deviceData.getImageId());
        viewHolder.deviceName.setText(deviceData.getDeviceName());
        //如果note非空, 设置note, 并将其显示
        String note; if ((note = deviceData.getDeviceNote()) !=null) {
            viewHolder.deviceNote.setText(note);
            viewHolder.deviceNoteTitle.setVisibility(View.VISIBLE);
            viewHolder.deviceNote.setVisibility(View.VISIBLE);
        }
        viewHolder.deviceMac.setText(deviceData.getMAC_ADDRESS());

        //检测监听器是否存在, 如存在绑定到对应按钮
        if (deviceData.getLeftListener()==null) viewHolder.leftButton.setVisibility(View.GONE);
        else {
            viewHolder.leftButton.setOnClickListener(deviceData.getLeftListener());
            viewHolder.leftButton.setText(deviceData.getLeftButtonText());
        }
        if (deviceData.getRightListener()==null) viewHolder.rightButton.setVisibility(View.GONE);
        else {
            viewHolder.rightButton.setOnClickListener(deviceData.getRightListener());
            viewHolder.rightButton.setText(deviceData.getRightButtonText());
        }
    }

    @Override
    public int getItemCount() {
        return mDeviceDataList.size();
    }
}
