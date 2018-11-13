package co.com.mypet.mypet;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegistroUsuario extends AppCompatActivity {

    private EditText txtEmail, txtPass, txtRePass;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        firebaseAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.etEmailRe);
        txtPass = findViewById(R.id.etContrasena);
        txtRePass = findViewById(R.id.etReContrasena);
        btnRegistrar = findViewById(R.id.btRegistrar);
        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();
                String repass = txtRePass.getText().toString().trim();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)){
                    Toast.makeText(getApplicationContext(), "Rellene campo vacio", Toast.LENGTH_SHORT).show();
                }else {
                    if (pass.equals(repass)) {
                        progressDialog.setMessage("Registrando");
                        progressDialog.show();
                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    if(task.getException() instanceof FirebaseAuthUserCollisionException){//Si encuentra el correo
                                        Toast.makeText(getApplicationContext(),"Usuario ya existe",Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(getApplicationContext(),"Registrado",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"No se pudo registrar", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "Contraseña no coindicen", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
