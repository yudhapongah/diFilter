package com.yudha.difilter.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yudha.difilter.R;

public class NilaiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);

        Intent intent = getIntent();
        double nilai = intent.getDoubleExtra(UjiKepahamanActivity.EXTRA_NILAI,0.0);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        TextView tvNilai = findViewById(R.id.tv_nilai);
        tvNilai.setText(String.format("%d/100", Math.round(nilai)));

    }
}
