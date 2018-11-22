package co.com.mypet.mypet.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import co.com.mypet.mypet.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Donaciones extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View navHeader;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String nombreU, emailU, fotoU;
    private SharedPreferences sharedPreferences;
    private StorageReference storageReference;
    private TextView nombre, email;
    private ImageView foto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donaciones);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);



        storageReference = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

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

        alert();
    }

    private void alert() {
        String ok;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.anuncio));
        builder.setMessage(getResources().getString(R.string.mensaje_advertencia));
        ok = getResources().getString(R.string.ok);

        builder.setNegativeButton(ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
