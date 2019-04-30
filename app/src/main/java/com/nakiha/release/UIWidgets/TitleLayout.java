package com.nakiha.release.UIWidgets;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.nakiha.release.AboutAppActivity;
import com.nakiha.release.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TitleLayout extends ConstraintLayout {

    public static final int QUICK_MODE = 0x00;
    public static final int ACCOUNT_MODE = 0X01;

    private Context context;

    private TextView mAboutAppText;
    private TextView mAccountNameText;
    private TextView mModeText;

    private CircleImageView mHeadImage;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.title_account, this);
        initUIWidgets();
    }

    private void initUIWidgets(){
        mAboutAppText = findViewById(R.id.about_app_title);
        mAccountNameText = findViewById(R.id.account_name_title);
        mModeText = findViewById(R.id.mode_title);
        mHeadImage = findViewById(R.id.account_image_title);

        mAboutAppText.setOnClickListener(v -> {
            AboutAppActivity.startActivity(context);
        });
    }

    public void setHeadImage_TEMP(int imageId){
        mHeadImage.setImageResource(imageId);
    }

    public void setAccountNameText(String accountName){
        if (accountName!=null) mAccountNameText.setText(accountName);
    }

    public void setModeText (int MODE_CODE) throws Exception {
        switch (MODE_CODE){
            case QUICK_MODE:
                mModeText.setText("快速模式");
                mAccountNameText.setText("访客");
                break;
            case ACCOUNT_MODE:
                mModeText.setText("档案模式");
                break;
                default:throw new Exception("启动模式错误");
        }
    }


    /*涉及到Bitmap这里暂时不实现, 用代码写死*/
//    public void setHeadImage(CircleImageView headImage) {
//        mHeadImage = headImage;
//    }
}
