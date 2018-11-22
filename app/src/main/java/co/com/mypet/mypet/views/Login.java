package co.com.mypet.mypet.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.com.mypet.mypet.R;

public class Login extends Activity {

    private EditText txtemail, txtpass;
    private TextView recuperar, registrar;
    private Button btLogIn;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private CheckBox checksesion;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        obtenerSharedPreferences();
        resources = this.getResources();
        checksesion = findViewById(R.id.checksesion);
        recuperar = findViewById(R.id.tvRecuperarPassword);
        registrar = findViewById(R.id.tvRegistrarUser);
        btLogIn = findViewById(R.id.btLogIn);
        txtemail = findViewById(R.id.etUserLogin);
        txtpass = findViewById(R.id.etPasswordLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, RecuperarPassword.class);
                startActivity(i);

            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, RegistroUsuario.class);
                startActivity(i);

            }
        });

        btLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtemail.getText().toString().trim();
                String pass = txtpass.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), resources.getString(R.string.camposvacios), Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage(resources.getString(R.string.iniciando));
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if(checksesion.isChecked()){
                                    guardarSharedPreferences("Conectado");
                                }else{
                                    guardarSharedPreferences("SinSesion");
                                }

                                progressDialog.dismiss();
                                Intent i = new Intent(Login.this, Dashboard.class);
                                startActivity(i);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), resources.getString(R.string.usuarioinvalido), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }


    public void obtenerSharedPreferences() {
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String estado = sharedPreferences.getString("Estado_Conexion", " ");
        if (estado.equals(("Conectado"))) {
            Intent i = new Intent(this, Dashboard.class);
            startActivity(i);
            finish();
        }else{
            SharedPreferences.Editor editor = getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
        }
    }

    public void guardarSharedPreferences(String estado) {
        SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
        editor.putString("Estado_Conexion", estado);
        editor.apply();

    }


}
