package com.yudha.difilter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.yudha.difilter.R;
import com.yudha.difilter.model.Materi;

public class MateriActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MATERI_JENIS = "EXTRA_MATERI_JENIS";
    public static final String EXTRA_MATERI = "Extra_MATERI";
    private Materi materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);

        Intent mIntent = getIntent();
        materi = mIntent.getParcelableExtra(MainActivity.EXTRA_MATERI);

        TextView tvTitle = findViewById(R.id.tv_materi_item_title);
        TextView tvDescription = findViewById(R.id.tv_materi_description);
        ImageView ivMateri = findViewById(R.id.iv_materi_item_image);

        if (materi!=null){
            tvTitle.setText( materi.judul);
            tvDescription.setText(materi.deskripsi);
            Picasso.get().load(materi.gambar).into(ivMateri);
        }

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        findViewById(R.id.ib_pemula).setOnClickListener(this);
        findViewById(R.id.ib_lanj).setOnClickListener(this);
        findViewById(R.id.ib_pro).setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if((viewId==R.id.ib_pemula)||(viewId==R.id.ib_lanj)||(viewId==R.id.ib_pro)){
            String jenis = "";

            switch (viewId){
                case R.id.ib_pemula:
                    jenis = "pemula";
                    break;
                case R.id.ib_lanj:
                    jenis = "menengah";
                    break;
                case R.id.ib_pro:
                    jenis = "pro";
                    break;
            }

            Intent intent = new Intent(this,MateriListActivity.class);

            intent.putExtra(EXTRA_MATERI,materi);
            intent.putExtra(EXTRA_MATERI_JENIS,jenis);

            startActivity(intent);
        }
    }
}
