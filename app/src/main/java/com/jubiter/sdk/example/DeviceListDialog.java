package com.jubiter.sdk.example;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * @author fengshuo
 * @date 2019/9/30
 * @time 11:43
 */
public class DeviceListDialog extends Dialog implements View.OnClickListener,
        DialogInterface.OnShowListener, DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

    private static final String TAG = "DeviceListDialog";

    private TextView tvTitle;
    private ProgressBar progressBar;
    private ListView mListView;
    private Button btnClose;
    private Button btnRefresh;
    private DeviceCallback mDeviceCallback;


    public DeviceListDialog(@NonNull Context context, DeviceCallback callback) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDeviceCallback = callback;
        initUI(context);
    }

    private void initUI(Context context) {
        ViewGroup.LayoutParams lp1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams lp2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        lp5.setMargins(10, 10, 10, 10);
        LinearLayout.LayoutParams lp6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);


        // ========================= 顶部布局 =========================

        LinearLayout titleLayout = new LinearLayout(context);
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLayout.setLayoutParams(lp1);

        tvTitle = new TextView(context);
        tvTitle.setText("选择设备");
        tvTitle.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        tvTitle.setTextSize(22);

        progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(lp2);
        progressBar.setVisibility(View.GONE);

        titleLayout.addView(tvTitle);
        titleLayout.addView(progressBar);


        // ========================= 底部布局 =========================

        LinearLayout buttonsLayout = new LinearLayout(context);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(lp1);
        btnClose = new Button(context);
        btnRefresh = new Button(context);


        btnClose.setLayoutParams(lp5);
        btnRefresh.setLayoutParams(lp5);

        btnClose.setText("关闭");
        btnClose.setTextColor(Color.WHITE);
        btnClose.setBackgroundResource(R.drawable.bg_btn);

        btnRefresh.setText("刷新");
        btnRefresh.setTextColor(Color.WHITE);
        btnRefresh.setBackgroundResource(R.drawable.bg_btn);

        btnClose.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);

        buttonsLayout.addView(btnClose);
        buttonsLayout.addView(btnRefresh);

        // ===================== 中部的 ListView 布局 =================

        mListView = new ListView(context);
        mListView.setLayoutParams(lp6);
        mListView.setHeaderDividersEnabled(true);
        mListView.setFooterDividersEnabled(true);

        // ===================== 根布局 ==============================

        LinearLayout rootLayout = new LinearLayout(context);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundResource(R.drawable.setbar_bg);
        rootLayout.addView(titleLayout);
        rootLayout.addView(mListView);
        rootLayout.addView(buttonsLayout);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(rootLayout);

        setOnShowListener(this);
        setOnCancelListener(this);
        setOnDismissListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnClose) {
            cancel();
        } else if (v == btnRefresh) {
            mDeviceCallback.onRefresh();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Log.d(TAG, ">>> onCancel");
        mDeviceCallback.onCancel();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        Log.d(TAG, ">>> onShow");
        progressBar.setVisibility(View.VISIBLE);
        mDeviceCallback.onShow();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.d(TAG, ">>> onDismiss");
    }

    public void setAdapter(BaseAdapter adapter) {
        mListView.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListView.setOnItemClickListener(listener);
    }


    static interface DeviceCallback {

        void onShow();

        void onRefresh();

        void onCancel();

    }

}
