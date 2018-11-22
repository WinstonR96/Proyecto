package co.com.mypet.mypet.views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.com.mypet.mypet.R;

public class MainActivity extends AppCompatActivity {

    private final int duracion = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, Login.class);
                MainActivity.this.startActivity(i);
                MainActivity.this.finish();
            }
        },duracion);
    }
}
