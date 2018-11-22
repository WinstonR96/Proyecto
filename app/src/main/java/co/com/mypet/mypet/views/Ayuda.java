package co.com.mypet.mypet.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import co.com.mypet.mypet.modelos.AyudaOb;
import co.com.mypet.mypet.R;
import co.com.mypet.mypet.adapter.AyudaAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class Ayuda extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    private String nombreU, emailU, fotoU;
    private SharedPreferences sharedPreferences;
    private CircleImageView foto_profile_U;
    private StorageReference storageReference;
    private ArrayList<AyudaOb> ayuda;
    private ListView listAyuda;
    private Resources resources;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View navHeader;
    private DatabaseReference databaseReference;
    private String dbAyudas = "Ayudas";
    private TextView nombre, email;
    private ImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        listAyuda = findViewById(R.id.listAyuda);

        resources = this.getResources();

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ayuda = new ArrayList<AyudaOb>();
        final AyudaAdapter adapter = new AyudaAdapter(this,ayuda);

        listAyuda.setAdapter(adapter);



        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(this);



        databaseReference.child(dbAyudas).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ayuda.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    AyudaOb ayudaOb = snapshot.getValue(AyudaOb.class);
                    ayuda.add(ayudaOb);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listAyuda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        nombreU = sharedPreferences.getString("nombre", "");
        emailU = sharedPreferences.getString("email", "");
        fotoU = sharedPreferences.getString("foto", "");


        navHeader = navigationView.getHeaderView(0);
        nombre = navHeader.findViewById(R.id.tvusernamer);
        email = navHeader.findViewById(R.id.tvemailde);
        foto = navHeader.findViewById(R.id.circle_image);

        nombre.setText(nombreU);
        email.setText(emailU);
        storageReference.child(fotoU).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(foto);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logoutRequest();
                break;
            case R.id.donacion:
                donaciones();
                break;
            case R.id.help:
                break;
            case R.id.profile:
                perfil();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void perfil() {
        Intent i = new Intent(this, Perfil.class);
        startActivity(i);
    }

    public void ayuda() {
        Intent i = new Intent(this, Ayuda.class);
        startActivity(i);
    }

    public void donaciones() {
        Intent i = new Intent(this, Donaciones.class);
        startActivity(i);
    }


    private void logoutRequest() {
        String positivo, negativo;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.cerrarsesion));
        builder.setMessage(getResources().getString(R.string.mensaje_cerrar));
        positivo = getResources().getString(R.string.si);
        negativo = getResources().getString(R.string.no);

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
            }
        });

        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void logout() {
        auth.signOut();
        SharedPreferences.Editor editor = getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
