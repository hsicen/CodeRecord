package com.poetic.testandroid;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

/**
 * 作者： poeticAndroid   2017/6/7 8:58
 * 邮箱： codinghuang@163.com
 * 作用：
 * 描述： 7.0手机拍照适配
 * 需要使用FileProvider来获取文件的uri地址,并在AndroidManifest文件中添加provider标签(与activity同级)
 * <provider
 * android:name="android.support.v4.content.FileProvider"
 * android:authorities="com.poetic.take_photo"
 * android:exported="false"
 * android:grantUriPermissions="true">
 * <meta-data
 * android:name="android.support.FILE_PROVIDER_PATHS"
 * android:resource="@xml/file_paths" />
 * </provider>
 * 并创建一个filepath的xml资源文件(在res资源文件目录下新建xml文件夹,并新建一个名为file_paths的xml文件)
 * <?xml version="1.0" encoding="utf-8"?>
 * <paths xmlns:android="http://schemas.android.com/apk/res/android">
 * <external-path name="my_images" path=""/>
 * </paths>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TAKE_PHOTO = 0X01;
    private static final int LANG_AUTO = 0x02;
    private static final int LANG_ZH = 0x03;
    private static final int LANG_HK = 0x04;
    private static final int LANG_EN = 0x05;

    private ImageView mivImg;
    private Uri imgUri;
    private Button mbtnAuto;
    private Button mbtnZh;
    private Button mbtnHk;
    private Button mbtnEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mbtnPhoto = ((Button) findViewById(R.id.btn_photo));
        mbtnAuto = ((Button) findViewById(R.id.btn_auto));
        mbtnZh = ((Button) findViewById(R.id.btn_zh));
        mbtnHk = ((Button) findViewById(R.id.btn_hk));
        mbtnEn = ((Button) findViewById(R.id.btn_en));
        mivImg = ((ImageView) findViewById(R.id.iv_img));

        mbtnPhoto.setOnClickListener(this);
        mbtnAuto.setOnClickListener(this);
        mbtnZh.setOnClickListener(this);
        mbtnHk.setOnClickListener(this);
        mbtnEn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_photo:
                takePhoto();
                break;
            case R.id.btn_auto:
                setLanguage(LANG_AUTO);
                break;
            case R.id.btn_zh:
                setLanguage(LANG_ZH);
                break;
            case R.id.btn_hk:
                setLanguage(LANG_HK);
                break;
            case R.id.btn_en:
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 点击拍照
     */
    private void takePhoto() {
        File outputImage = new File(getExternalCacheDir(), "output_img.jpg");
        try {
            if (outputImage.exists())
                outputImage.delete();
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24)  //Android7.0及以上使用FileProvider来获取文件的Uri地址
            imgUri = FileProvider.getUriForFile(MainActivity.this, "com.poetic.take_photo", outputImage);
        else //7.0以下直接获得文件的uri地址
            imgUri = Uri.fromFile(outputImage);

        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 根据type类型设置字体
     * @param type
     */
    @TargetApi(17)
    private void setLanguage(int type){

        Resources res = getResources();
        DisplayMetrics  displayMetrics = res.getDisplayMetrics();
        Configuration configuration = res.getConfiguration();

        switch (type) {
            case LANG_AUTO:  //跟随系统
                configuration.setLocale(Locale.getDefault());
                break;
            case LANG_ZH: //简体中文
                configuration.setLocale(Locale.SIMPLIFIED_CHINESE);
                break;
            case LANG_HK: //繁体
                configuration.setLocale(Locale.JAPAN);
                break;
            default:
                configuration.setLocale(Locale.getDefault());
                break;
        }
        res.updateConfiguration(configuration,displayMetrics);
        startActivity(new Intent(MainActivity.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imgUri));
                        mivImg.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
