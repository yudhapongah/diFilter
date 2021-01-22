package com.yudha.difilter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yudha.difilter.R;
import com.yudha.difilter.adapter.MateriListAdapter;
import com.yudha.difilter.misc.RvListDivider;
import com.yudha.difilter.model.Materi;
import com.yudha.difilter.support.ItemClickSupport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_MATERI = "EXTRA_MATERI";

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private List<Materi> materiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        final RecyclerView rvMateriList = findViewById(R.id.rv_materi_list);
        final TextView tvLoadingStatus =findViewById(R.id.tv_loading_status);
        final ProgressBar pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);

        final MateriListAdapter materiListAdapter = new MateriListAdapter(Collections.<Materi>emptyList());

        rvMateriList.addItemDecoration(new RvListDivider(30));
        rvMateriList.setAdapter(materiListAdapter);
        rvMateriList.setLayoutManager(linearLayoutManager);

        ItemClickSupport.addTo(rvMateriList).setmOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                openDetail(materiList.get(position));
            }
        });

        firebaseFirestore.collection("kelompok_materi").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    tvLoadingStatus.setText(R.string.materi_load_error);
                    tvLoadingStatus.setVisibility(View.VISIBLE);

                    pbLoadingIndicator.setVisibility(View.GONE);
                }

                if (value != null) {
                    if (value.isEmpty()){
                        rvMateriList.setVisibility(View.GONE);
                        tvLoadingStatus.setText(R.string.materi_list_empty);
                        tvLoadingStatus.setVisibility(View.VISIBLE);
                    }else {
                        materiList = value.toObjects(Materi.class);
                        materiListAdapter.setList(materiList);
                        rvMateriList.setVisibility(View.VISIBLE);
                        tvLoadingStatus.setVisibility(View.GONE);
                    }
                    pbLoadingIndicator.setVisibility(View.GONE);

                } else {
                    pbLoadingIndicator.setVisibility(View.VISIBLE);
                    rvMateriList.setVisibility(View.GONE);
                    tvLoadingStatus.setVisibility(View.GONE);
                }

            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.drawer_open,R.string.drawer_close);

        NavigationView navigationView = findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    protected void onPause() {
        drawerLayout.removeDrawerListener(drawerToggle);
        super.onPause();
    }

    private void openDetail(Materi materi){
        Intent intent = new Intent(this,MateriActivity.class);
        intent.putExtra(EXTRA_MATERI,materi);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nv_tentang_item){
            startActivity(new Intent(this,AboutActivity.class));
        }else if(item.getItemId()==R.id.nv_tanya_item){
            try {
                String uri = String.format("https://api.whatsapp.com/send?phone=%s&text=%s","6281231233937", URLEncoder.encode("Saya ingin bertanya kepada kreator","UTF-8"));
                Intent mIntent = new Intent(Intent.ACTION_VIEW);
                mIntent.setData(Uri.parse(uri));
                startActivity(mIntent);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
