package com.jnu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTvTitle;
    /**
     * 确定
     */
    private Button mBtnSure;
    /**
     * 取消
     */
    private Button mBtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        initView();
    }

    private void initView() {
        mTvTitle = (EditText) findViewById(R.id.tv_title);
        mBtnSure = (Button) findViewById(R.id.btn_sure);
        mBtnSure.setOnClickListener(this);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        mBtnCancel.setOnClickListener(this);
        String title = getIntent().getStringExtra("title");
        mTvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_sure:
                Intent data = new Intent();
                data.putExtra("title", mTvTitle.getText().toString());
                setResult(101, data);
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
}