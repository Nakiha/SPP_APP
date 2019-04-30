package com.nakiha.release;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nakiha.release.ControlModule.BaseActivity;

import java.net.URI;

public class AboutAppActivity extends BaseActivity {

    TextView aboutAuthor;
    Button wentToGithub;

    public static void startActivity(Context from){
        from.startActivity(new Intent(from,AboutAppActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        initUIWidget();
        //initTypeFace();
    }

    private void initUIWidget(){
        aboutAuthor = findViewById(R.id.author);
        wentToGithub = findViewById(R.id.github_button);

        wentToGithub.setOnClickListener((v -> {Toast.makeText(this,"仓库暂未建立",Toast.LENGTH_SHORT).show();}));

        aboutAuthor.setText(getSpan(Uri.parse("https://github.com/Nakiha"), getResources().getString(R.string.author_github), 32 , 10));
        aboutAuthor.setMovementMethod(LinkMovementMethod.getInstance());
    }



    private SpannableString getSpan(Uri webSiteURI, String originString , int start, int length){
        SpannableString span = new SpannableString(originString);
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(AboutAppActivity.this, "跳转至github", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(webSiteURI);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                // super.updateDrawState(ds);
                //去掉下划线
                ds.setUnderlineText(true);
                //去掉点击背景色
                // ds.bgColor = getColor(R.color.colorWhite);
                // 距离文本基线的距离
                // ds.baselineShift = 20;
            }
        },start,originString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(getColor(R.color.colorAccent)),start, originString.length(),

                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    /*private void initTypeFace(){
        TextView appEnglishName = findViewById(R.id.app_name_english);
        appEnglishName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/msyhbd.ttc"));
    }*/
}
