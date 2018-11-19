package co.com.mypet.mypet;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarPassword extends Activity {

    private EditText etEmailRecuperar;
    private Button btRecuperar;
    private FirebaseAuth firebaseAuth;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);
        etEmailRecuperar = findViewById(R.id.etEmailRecuperar);
        btRecuperar = findViewById(R.id.btRecuperar);
        firebaseAuth = FirebaseAuth.getInstance();
        resources = this.getResources();
        btRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailRecuperar.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.camposvacios),Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                limpiar();
                                Toast.makeText(getApplicationContext(),resources.getString(R.string.revisecorreo),Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),resources.getString(R.string.error),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void limpiar(){
        etEmailRecuperar.setText(null);
    }
}
