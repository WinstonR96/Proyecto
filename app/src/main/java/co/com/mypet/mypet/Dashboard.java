package co.com.mypet.mypet;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Dashboard extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View navHeader;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private ArrayList<Mascota> mascotas;
    private DatabaseReference databaseReference;
    private String db = "Mascotas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.lista);
        mascotas = new ArrayList<>();
        //mascotas.add(new Mascota("1","Princesa","Chanda","Barranquilla","1 mes","para adopcion","https://firebasestorage.googleapis.com/v0/b/mypet-850a1.appspot.com/o/Miniature-Pinscher-On-White-01.jpg?alt=media&token=18898303-24a5-45f4-a7bd-1c405595b509"));
        final MascotaAdapter mascotaAdapter = new MascotaAdapter(mascotas, this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(db).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mascotas.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Mascota p = snapshot.getValue(Mascota.class);
                        mascotas.add(p);
                    }
                }
                mascotaAdapter.notifyDataSetChanged();
                Datos.setMascotas(mascotas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mascotaAdapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


}
