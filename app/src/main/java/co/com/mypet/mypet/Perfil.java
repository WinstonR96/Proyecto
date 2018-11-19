package co.com.mypet.mypet;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View navHeader;
    private TextView nombre, email;
    private ImageView foto;
    private FirebaseUser user;
    private Resources resources;
    private FirebaseAuth auth;
    private StorageReference storageReference;
    private Uri uri;
    private String nombreU, emailU, fotoU,snombreU,sapellidoU,apellidoU;
    SharedPreferences sharedPreferences;
    private CircleImageView foto_profile_U;
    private EditText txtSegundoNombrePerfil,txtEmailPerfil,txtDisplayName,txtApellidoPerfil,txtSegundoApellidoPerfil;
    private Button actualizarperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        resources = this.getResources();
        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        txtDisplayName = findViewById(R.id.txtDisplayName);
        txtEmailPerfil = findViewById(R.id.txtEmailPerfil);
        txtSegundoNombrePerfil = findViewById(R.id.txtSegundoNombrePerfil);
        txtApellidoPerfil = findViewById(R.id.txtApellidoPerfil);
        txtSegundoApellidoPerfil = findViewById(R.id.txtSegundoApellidoPerfil);
        actualizarperfil = findViewById(R.id.actualizarperfil);
        foto_profile_U = findViewById(R.id.foto_profile_U);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        nombreU = sharedPreferences.getString("nombre","");
        emailU = sharedPreferences.getString("email","");
        fotoU = sharedPreferences.getString("foto","");
        snombreU = sharedPreferences.getString("segundonombre","");
        apellidoU = sharedPreferences.getString("apellido","");
        sapellidoU = sharedPreferences.getString("segundoapellido","");

        navHeader = navigationView.getHeaderView(0);
        nombre = navHeader.findViewById(R.id.tvusernamer);
        email = navHeader.findViewById(R.id.tvemailde);
        foto = navHeader.findViewById(R.id.circle_image);

        nombre.setText(nombreU);
        email.setText(emailU);
        if(!fotoU.isEmpty()){
            Glide.with(getApplicationContext()).load(fotoU).into(foto);
            Glide.with(getApplicationContext()).load(fotoU).into(foto_profile_U);
        }


        txtDisplayName.setText(nombreU);
        txtEmailPerfil.setText(emailU);
        txtApellidoPerfil.setText(apellidoU);
        txtSegundoApellidoPerfil.setText(sapellidoU);
        txtSegundoNombrePerfil.setText(snombreU);


        navigationView.setNavigationItemSelectedListener(this);

        foto_profile_U.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionar_foto();
            }
        });

        actualizarperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foto, id, nombre, primerApellido, segundoApellido,segundoNombre;
                foto = auth.getUid()+".jpg";
                id = auth.getUid();
                nombre = txtDisplayName.getText().toString();
                segundoNombre = txtSegundoNombrePerfil.getText().toString();
                primerApellido = txtApellidoPerfil.getText().toString();
                segundoApellido = txtSegundoApellidoPerfil.getText().toString();
                if(nombre.isEmpty() || segundoNombre.isEmpty() || primerApellido.isEmpty() || segundoApellido.isEmpty()){
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.camposvacios),Toast.LENGTH_SHORT).show();
                }else{
                    Usuario u = new Usuario(foto, id, nombre, primerApellido, segundoApellido,segundoNombre);
                    u.editar();
                    subirFoto(foto);
                    Toast.makeText(getApplicationContext(), resources.getString(R.string.actualizado),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void subirFoto(String foto) {
        StorageReference child = storageReference.child(foto);
        UploadTask uploadTask = child.putFile(uri);


    }

    public void seleccionar_foto(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,
                getResources().getString(R.string.seleccionarFoto)),1);
    }
    protected void onActivityResult(int requesCode,int resultCode,Intent data){
        super.onActivityResult(requesCode,resultCode,data);

        if(requesCode==1){
            uri = data.getData();

            if(uri != null){
                foto_profile_U.setImageURI(uri);
                subirFoto(auth.getUid()+".jpg");

            }
        }
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
                break;
            case R.id.profile:
                Perfil();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }


    public void Perfil(){
        Intent i = new Intent(this,Perfil.class);
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
