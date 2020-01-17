package com.zupu.zp.myactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.util.Constants;
import com.google.gson.Gson;
import com.zupu.zp.R;
import com.zupu.zp.utliss.MyProgressDialog;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

import static com.zupu.zp.entity.ZegoApplication.dismissProgress;
import static com.zupu.zp.entity.ZegoApplication.showProgress;

public class PdfActivity extends AppCompatActivity {
    String  pdfName;
    MyProgressDialog dialog;
    private PDFView pdfView;
    String pdf_trun_time="5000";
     Handler handler;
    File dest=null;
    ImageView back;
    String mPdfUrl="https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/apk/opt.pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        initView();
        DownloadPdf();
        showProgress(PdfActivity.this, "生成中...");
        pdfView.fromFile(dest).onRender(new OnRenderListener() {
            @Override
            public void onInitiallyRendered(int nbPages) {
                if (pdf_trun_time != null) {
                    if (handler == null) {
                        handler = new Handler();
                    }
                    handler.postDelayed(goNextPageRunnable, Long.parseLong(pdf_trun_time));
                }
            }
        })
                .load();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        pdfView = (PDFView) findViewById(R.id.pdfView);
        back=findViewById(R.id.back);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mPdfUrl=url;
        Log.e("数据", mPdfUrl );


    }
    /**
     * 查看PDF
     */
    private void SeePdf(File dest) {
        try {
            dismissProgress(PdfActivity.this);
            pdfView.setVisibility(View.VISIBLE);
            pdfView.useBestQuality(false);
            Constants.Cache.CACHE_SIZE=40;
            pdfView.fromFile(dest).load();
            dismissProgress(PdfActivity.this);
            Toast.makeText(this, "请到文件管理查找"+pdfName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) { e.printStackTrace(); } }
    /**
     * 开始下载PDF
     */
    private void DownloadPdf() {
     // String  cacheUrl = getCacheDir().getAbsolutePath();//应用缓存路径
        String sdPath = Environment.getExternalStorageDirectory() + "/";
         pdfName = mPdfUrl.substring(mPdfUrl.lastIndexOf("/") + 1);//文件名称
        showProgress(PdfActivity.this, "生成中...");
        Log.e("地址", pdfName+"路径"+sdPath );
         dest = new File(sdPath, pdfName);
        if (dest.exists()) {
            SeePdf(dest);
        } else {
            Request request = new Request.Builder().url(mPdfUrl).build();
            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // 下载失败
                    dismissProgress(PdfActivity.this);
                    Log.e("地址", "下载失败" );
                    Toast.makeText(PdfActivity.this, R.string.donlaodsb, Toast.LENGTH_SHORT).show();
                    dismissProgress(PdfActivity.this);
                    finish();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Sink sink = null;
                    BufferedSink bufferedSink = null;
                    try {
                        if (!dest.exists()) {
                            boolean newFile = dest.createNewFile();
                        }
                        sink = Okio.sink(dest);
                        bufferedSink = Okio.buffer(sink);
                        bufferedSink.writeAll(response.body().source());
                        bufferedSink.close();
                        if (handler == null) {
                            handler = new Handler(Looper.getMainLooper());
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                SeePdf(dest);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (bufferedSink != null) {
                            bufferedSink.close();
                        }

                    }
                }
            });
        }
    }

    private Runnable goNextPageRunnable = new Runnable() {
        @Override
        public void run() {
            if (pdf_trun_time != null) {
                handler.postDelayed(this, Long.parseLong(pdf_trun_time));//设置循环时间，此处是5秒
                GoNextPage();
            }
        }
    };



    private void GoNextPage() {
        int totalPage = pdfView.getPageCount();
        int curPage = pdfView.getCurrentPage();
        int nextPage = 0;
        if (curPage < totalPage - 1) {
            nextPage = curPage + 1;
        } else {
            nextPage = 0;
        }

        pdfView.jumpTo(nextPage, true);
    }


    /***
     * 启动
     */
    public void showProgress(Context context, String message) {
        if (dialog == null) {
            dialog = new MyProgressDialog(context);
        }
        dialog.showMessage(message);
    }

    /***
     * 关闭
     */
    public void dismissProgress(Context context) {
        if (dialog == null) {
            dialog = new MyProgressDialog(context);
        }
        dialog.dismiss();
    }
}
