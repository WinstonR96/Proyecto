package co.com.mypet.mypet.views;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.com.mypet.mypet.R;
import co.com.mypet.mypet.Usuario;

public class RegistroUsuario extends AppCompatActivity {

    private EditText txtEmail, txtPass, txtRePass;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;
    private Resources resources;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        resources = this.getResources();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
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
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)) {
                    Toast.makeText(getApplicationContext(), resources.getString(R.string.camposvacios), Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.length() < 6) {
                        Toast.makeText(getApplicationContext(), resources.getString(R.string.longitudinvalida), Toast.LENGTH_SHORT).show();
                    } else {
                        if (pass.equals(repass)) {
                            progressDialog.setMessage(resources.getString(R.string.registamdo));
                            progressDialog.show();
                            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {//Si encuentra el correo
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),resources.getString(R.string.usuarioyaexiste), Toast.LENGTH_SHORT).show();

                                    }
                                    if (task.isSuccessful()) {
                                        limpiar();
                                        Toast.makeText(getApplicationContext(), resources.getString(R.string.registrado), Toast.LENGTH_SHORT).show();
                                        String id = firebaseAuth.getUid();
                                        Usuario u = new Usuario(id);
                                        u.guardar();
                                        //Toast.makeText(getApplicationContext(),"Id nuevo usuario: "+firebaseAuth.getUid(),Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), resources.getString(R.string.errorregistrando), Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), resources.getString(R.string.contraseñanocoinciden), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
    }

    public void limpiar(){
        txtEmail.setText(null);
        txtPass.setText(null);
        txtRePass.setText(null);
    }
}
