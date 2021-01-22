package com.yudha.difilter.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yudha.difilter.R;
import com.yudha.difilter.adapter.LessonListAdapter;
import com.yudha.difilter.adapter.SoalAdapter;
import com.yudha.difilter.model.Lesson;
import com.yudha.difilter.model.Materi;
import com.yudha.difilter.model.Soal;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UjiKepahamanActivity extends AppCompatActivity implements SoalAdapter.OnAnswerChange {

    public static final String EXTRA_NILAI = "EXTRA_NILAI";
    private List<Soal> soalList;
    private Materi materi;
    private String materiJenis;
    private RecyclerView rvSoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uji_kepahaman);

        Intent mIntent = getIntent();
        materi = mIntent.getParcelableExtra(MateriActivity.EXTRA_MATERI);
        materiJenis = mIntent.getStringExtra(MateriActivity.EXTRA_MATERI_JENIS);

        final TextView tvLoadingStatus =findViewById(R.id.tv_loading_status);
        final ProgressBar pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        final Button btnSelesai = findViewById(R.id.btn_selesai);
        rvSoal = findViewById(R.id.rv_soal_list);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
            linearLayoutManager.getOrientation());

        final SoalAdapter lessonListAdapter = new SoalAdapter(Collections.<Soal>emptyList(), this);

        rvSoal.addItemDecoration(dividerItemDecoration);
        rvSoal.setAdapter(lessonListAdapter);
        rvSoal.setLayoutManager(linearLayoutManager);

        if (materi!=null && materiJenis!=null){

            Log.d("TAG", "onCreate: Materi dan jenis materi tidak kosong");

            if (getSupportActionBar()!=null){
                getSupportActionBar().setTitle(materi.judul + " | " + materiJenis.toUpperCase());
            }

            FirebaseFirestore.getInstance().collection("kelompok_materi").document(materi.id).collection("soal_"+materiJenis).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error!=null){
                        tvLoadingStatus.setText(R.string.soal_list_error);
                        tvLoadingStatus.setVisibility(View.VISIBLE);
                        btnSelesai.setVisibility(View.GONE);
                        pbLoadingIndicator.setVisibility(View.GONE);
                    }

                    if (value != null) {
                        if (value.isEmpty()){
                            rvSoal.setVisibility(View.GONE);
                            tvLoadingStatus.setText(R.string.soal_list_empty);
                            tvLoadingStatus.setVisibility(View.VISIBLE);
                            btnSelesai.setVisibility(View.GONE);
                        }else {
                            soalList = value.toObjects(Soal.class);
                            lessonListAdapter.setSoalList(soalList);
                            rvSoal.setVisibility(View.VISIBLE);
                            tvLoadingStatus.setVisibility(View.GONE);
                            btnSelesai.setVisibility(View.VISIBLE);
                        }
                        pbLoadingIndicator.setVisibility(View.GONE);

                    } else {
                        pbLoadingIndicator.setVisibility(View.VISIBLE);
                        rvSoal.setVisibility(View.GONE);
                        btnSelesai.setVisibility(View.GONE);
                        tvLoadingStatus.setVisibility(View.GONE);
                    }
                }
            });
        }

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!soalList.isEmpty()){
                    Iterator<Soal> iterable = soalList.iterator();
                    int totalBenar = 0;
                    while (iterable.hasNext()){
                        if (iterable.next().status){
                            totalBenar++;
                        }
                    }

                    double hasil = (totalBenar / (double) soalList.size()) * 100.0;
                    Intent mIntent = new Intent(view.getContext(),NilaiActivity.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                    mIntent.putExtra(EXTRA_NILAI,hasil);
                    startActivity(mIntent);
                    finish();
                }
            }
        });

    }

    @Override
    public void changeAnswer(Boolean answer,int position) {
        soalList.get(position).status = answer;
    }
}
