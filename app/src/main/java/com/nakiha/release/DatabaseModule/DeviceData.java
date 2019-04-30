package com.nakiha.release.DatabaseModule;

import android.support.annotation.Nullable;
import android.view.View;

public class DeviceData {
    private int imageId;
    private String deviceName;
    private String deviceNote;
    private String MAC_ADDRESS;
    private View.OnClickListener leftListener = null;
    private String leftButtonText = null;
    private View.OnClickListener rightListener = null;
    private String rightButtonText = null;

    public DeviceData(int imageId, String deviceName, @Nullable String deviceNote, String MAC_ADDRESS) {
        this.imageId = imageId;
        this.deviceName = deviceName;
        this.deviceNote = deviceNote;
        this.MAC_ADDRESS = MAC_ADDRESS;
    }

    public View.OnClickListener getLeftListener() {
        return leftListener;
    }

    public void setLeftButton(View.OnClickListener leftListener, String buttonText) {
        this.leftListener = leftListener;
        leftButtonText = buttonText;
    }

    public View.OnClickListener getRightListener() {
        return rightListener;
    }

    public void setRightButton(View.OnClickListener rightListener, String buttonText) {
        this.rightListener = rightListener;
        rightButtonText = buttonText;
    }

    public int getImageId() {
        return imageId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceNote() {
        return deviceNote;
    }

    public String getMAC_ADDRESS() {
        return MAC_ADDRESS;
    }

    public String getLeftButtonText() {
        return leftButtonText;
    }

    public String getRightButtonText() {
        return rightButtonText;
    }
}
