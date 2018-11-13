package co.com.mypet.mypet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Login extends Activity {

    private EditText txtemail, txtpass;
    private TextView recuperar, registrar;
    private Button btLogIn;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        recuperar = findViewById(R.id.tvRecuperarPassword);
        registrar = findViewById(R.id.tvRegistrarUser);
        btLogIn = findViewById(R.id.btLogIn);
        txtemail = findViewById(R.id.etUserLogin);
        txtpass = findViewById(R.id.etPasswordLogin);
        firebaseAuth = FirebaseAuth.getInstance();
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
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(), "Rellene campo vacio", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setMessage("Iniciando");
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(Login.this,Dashboard.class);
                                startActivity(i);
                            }else {
                                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }


}
