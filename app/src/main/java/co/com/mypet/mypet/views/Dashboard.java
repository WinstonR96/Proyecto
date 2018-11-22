package co.com.mypet.mypet.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import co.com.mypet.mypet.Datos;
import co.com.mypet.mypet.modelos.Mascota;
import co.com.mypet.mypet.adapter.MascotaAdapter;
import co.com.mypet.mypet.R;
import co.com.mypet.mypet.modelos.Usuario;


public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View navHeader;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private ArrayList<Mascota> mascotas;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String db = "Mascotas";
    private String dbUser = "Usuarios";
    private ProgressDialog progressDialog;
    private Resources resources;
    private TextView nombre, email;
    private ImageView foto;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private Usuario u;
    private String fotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.lista);
        resources = this.getResources();
        mascotas = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);
        //mascotas.add(new Mascota("1","Princesa","Chanda","Barranquilla","1 mes","para adopcion","https://firebasestorage.googleapis.com/v0/b/mypet-850a1.appspot.com/o/Miniature-Pinscher-On-White-01.jpg?alt=media&token=18898303-24a5-45f4-a7bd-1c405595b509"));
        final MascotaAdapter mascotaAdapter = new MascotaAdapter(mascotas, this);
        storageReference = FirebaseStorage.getInstance().getReference();
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

        navHeader = navigationView.getHeaderView(0);
        nombre = navHeader.findViewById(R.id.tvusernamer);
        email = navHeader.findViewById(R.id.tvemailde);
        foto = navHeader.findViewById(R.id.circle_image);

        /*nombre.setText(user.getDisplayName());
        email.setText(user.getEmail());
        Glide.with(getApplicationContext()).load(user.getPhotoUrl()).into(foto)*/;

        navigationView.setNavigationItemSelectedListener(this);
        /*progressDialog.setMessage(resources.getString(R.string.cargando));
        progressDialog.show();*/
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
                progressDialog.dismiss();
                Datos.setMascotas(mascotas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference.child(dbUser).child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    u = dataSnapshot.getValue(Usuario.class);
                    String uid = dataSnapshot.getKey();
                    nombre.setText(u.getNombre());
                    email.setText(user.getEmail());

                    storageReference.child(u.getFoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fotoUrl = uri.toString();
                            Glide.with(getApplicationContext()).load(uri).into(foto);
                        }
                    });

                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logoutRequest();
                break;
            case R.id.donacion:
                break;
            case R.id.help:
                ayuda();
                break;
            case R.id.profile:
                perfil();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void perfil() {
        guardarDatosUsuario();
        Intent i = new Intent(this,Perfil.class);
        startActivity(i);
    }

    public void ayuda(){
        Intent i = new Intent(this,Ayuda.class);
        startActivity(i);
    }

    public void guardarDatosUsuario(){
        SharedPreferences.Editor editor = getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
        editor.putString("nombre",u.getNombre());
        editor.putString("email", user.getEmail());
        editor.putString("foto", fotoUrl);
        editor.putString("segundonombre",u.getSegundoNombre());
        editor.putString("segundoapellido",u.getSegundoApellido());
        editor.putString("apellido",u.getPrimerApellido());
        editor.apply();

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
        }) ;

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void logout() {
        auth.signOut();
        SharedPreferences.Editor editor = getSharedPreferences("userInfo",Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }
}
