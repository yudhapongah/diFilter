package com.yudha.difilter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yudha.difilter.R;
import com.yudha.difilter.adapter.LessonListAdapter;
import com.yudha.difilter.model.Lesson;
import com.yudha.difilter.model.Materi;

import java.util.Collections;

public class MateriListActivity extends AppCompatActivity {

    private RecyclerView rvLessonList;
    private Materi materi;
    private String materiJenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_list);

        Intent mIntent = getIntent();
        materi = mIntent.getParcelableExtra(MateriActivity.EXTRA_MATERI);
        materiJenis = mIntent.getStringExtra(MateriActivity.EXTRA_MATERI_JENIS);

        final TextView tvLoadingStatus =findViewById(R.id.tv_loading_status);
        final ProgressBar pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
            linearLayoutManager.getOrientation());

        final LessonListAdapter lessonListAdapter = new LessonListAdapter(Collections.<Lesson>emptyList());

        rvLessonList = findViewById(R.id.rv_lesson_list);
        rvLessonList.addItemDecoration(dividerItemDecoration);
        rvLessonList.setAdapter(lessonListAdapter);
        rvLessonList.setLayoutManager(linearLayoutManager);

        if (materi!=null && materiJenis!=null){

            Log.d("TAG", "onCreate: Materi dan jenis materi tidak kosong");

            if (getSupportActionBar()!=null){
                getSupportActionBar().setTitle(materi.judul + " | " + materiJenis.toUpperCase());
            }

            FirebaseFirestore.getInstance().collection("kelompok_materi").document(materi.id).collection(materiJenis).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error!=null){
                        tvLoadingStatus.setText(R.string.lesson_list_error);
                        tvLoadingStatus.setVisibility(View.VISIBLE);

                        pbLoadingIndicator.setVisibility(View.GONE);
                    }

                    if (value != null) {
                        if (value.isEmpty()){
                            rvLessonList.setVisibility(View.GONE);
                            tvLoadingStatus.setText(R.string.lesson_list_empty);
                            tvLoadingStatus.setVisibility(View.VISIBLE);
                        }else {
                            lessonListAdapter.setLessonList(value.toObjects(Lesson.class));
                            rvLessonList.setVisibility(View.VISIBLE);
                            tvLoadingStatus.setVisibility(View.GONE);
                        }
                        pbLoadingIndicator.setVisibility(View.GONE);

                    } else {
                        pbLoadingIndicator.setVisibility(View.VISIBLE);
                        rvLessonList.setVisibility(View.GONE);
                        tvLoadingStatus.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lesson_list_action,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_uji && materi!=null && materiJenis!=null){
            Intent mIntent = new Intent(this, UjiKepahamanActivity.class);
            mIntent.putExtra(MateriActivity.EXTRA_MATERI,materi);
            mIntent.putExtra(MateriActivity.EXTRA_MATERI_JENIS,materiJenis);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
