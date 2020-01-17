package com.zupu.zp.myactivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


//import com.lcw.library.imagepicker.ImagePicker;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.utliss.FileSizeUtil;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.UrlCount;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/*import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;*/

public class PicActivity extends BaseActivity {
    private static final int REQUEST_IMAGE3 = 5;
    private ArrayList<String> strings =new ArrayList<>();
    private static final String[] authBaseArr = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int authBaseRequestCode = 1;
    private ImageView imageView;
    Button sp,ps;
    private PopupWindow window;
    @Override
    public int initlayout() {
        return R.layout.activity_pic;
    }

    @Override
    public void initview() {

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initdata() {
        sp=findViewById(R.id.sp);
        ps=findViewById(R.id.ps);
        initNavi();//权限方法
        imageView = (ImageView)findViewById(R.id.testimage);

        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmorePic();
             /*   Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 66);*/
            }
        });
        ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                Uri fileUri = null;
                try {
                    fileUri = Uri.fromFile(createMediaFile()); // create a file to save the video
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);//限制录制时间10秒
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
                // start the Video Capture Intent
                startActivityForResult(intent, 106);


            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiImageSelector.create(PicActivity.this)
                        .showCamera(true) // 是否显示相机. 默认为显示
                        .count(3) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .single() // 单选模式
                        .multi() // 多选模式, 默认模式;
                        .origin(strings) // 默认已选择图片. 只有在选择模式为多选时有效
                        .start(PicActivity.this, REQUEST_IMAGE3);
            }
        });
    }

    @Override
    public void initlisenter() {

    }

    @Override
    public void persenter() {

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case 5:
                    if (requestCode == REQUEST_IMAGE3) {
                        if (resultCode == RESULT_OK) {
//                            List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                            strings = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                            Log.e("TAG", "图:"+strings);
                            Httputlis1 instance = Httputlis1.getInstance();
                            for (int i = 0; i <strings.size() ; i++) {
                                instance.uploadPic(UrlCount.URL_UpPic, new File(strings.get(i)), new Httputlis1.Mycallbacks() {
                                    @Override
                                    public void sucess(String json) {
                                        Log.e("TAG", "多"+json );
                                    }
                                });
                            }

                            Bitmap bitmap = BitmapFactory.decodeFile(strings.get(0));
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                    break;
            }
        }
        if (requestCode == 66 && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
         String   VIDEOPATH = cursor.getString(columnIndex);
            cursor.close();
            Log.e("TAG", "视频路径:"+VIDEOPATH);
            FileSizeUtil fileSizeUtil = new FileSizeUtil();
            double fileOrFilesSize = fileSizeUtil.getFileOrFilesSize(VIDEOPATH, 3);
            if(fileOrFilesSize>5.0){
                Toast.makeText(this, R.string.vidono5, Toast.LENGTH_SHORT).show();
            }else {
                persenterimpl.puthttp(UrlCount.URL_UpPic,new File(VIDEOPATH), Picbean.class);
                Toast.makeText(this, R.string.viedoupz, Toast.LENGTH_SHORT).show();

            }


        }
        if (requestCode == 106) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                Uri data1 = data.getData();
                //拿到视频保存地址
                String s = data1.toString();
                String[] split = s.split(":");
                Log.e("TAG", "拍摄视频路径" + split[1].substring(2));

                persenterimpl.puthttp(UrlCount.URL_UpPic,new File(split[1].substring(2)), Picbean.class);
                Toast.makeText(this, R.string.viedoupz, Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            }
        }


    }


    private boolean hasBasePhoneAuth() {
        PackageManager pm = getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initNavi() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }
    }


    private File createMediaFile() throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "CameraDemo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VID_" + timeStamp;
        String suffix = ".mp4";
        File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
        return mediaFile;
    }


    @Override
    public void sucecess(Object o) {
        if (o instanceof Picbean){
            Picbean picbean= (Picbean)o;
            int code = picbean.getCode();
            if (code==0){
                //js方法
                String mediaUrl = picbean.getMediaUrl();
                Toast.makeText(this, R.string.viedoucg, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "sucecess: 视频成功"+mediaUrl );
            }
        }
    }


    public void getmorePic(){
        View view = LayoutInflater.from(this).inflate(R.layout.pop, null);
        window = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window.setContentView(view);
        window.setOutsideTouchable(true);
        TextView xj = view.findViewById(R.id.xj);
        TextView xc = view.findViewById(R.id.xc);
        TextView qx = view.findViewById(R.id.qx);
        xj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 66);
                window.dismiss();
            }
        });
        xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                Uri fileUri = null;
                try {
                    fileUri = Uri.fromFile(createMediaFile()); // create a file to save the video
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);//限制录制时间10秒
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
                // start the Video Capture Intent
                startActivityForResult(intent, 106);

                window.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        View inflate = LayoutInflater.from(this).inflate(R.layout.prolaout, null);
        window.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
    }

}
