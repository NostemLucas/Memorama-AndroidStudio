package com.lemt.lemt_juegomemorama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button btnSalir,btnJugar;
    ImageView imgAnimate;
    private AnimationDrawable fondoAnimacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSalir=findViewById(R.id.btnSalir);
        btnJugar=findViewById(R.id.btnJugar);
        imgAnimate = findViewById(R.id.imgAnimate);
        imgAnimate.setBackgroundResource(R.drawable.animacion);
        fondoAnimacion = (AnimationDrawable) imgAnimate.getBackground();
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarJuego();
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void iniciarJuego(){
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }
    @Override
    protected void onStart() {
        super.onStart();
        fondoAnimacion.start();
    }
}
