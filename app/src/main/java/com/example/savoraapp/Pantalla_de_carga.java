package com.example.savoraapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pantalla_de_carga extends AppCompatActivity {

    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_de_carga);

        progressBar = findViewById(R.id.progressBar);

        final int tiempoTotal = 3000;
        final int intervalo = 30;
        progressBar.setMax(100);

        countDownTimer = new CountDownTimer(tiempoTotal, intervalo) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progresoActual = (int) (((tiempoTotal - millisUntilFinished) / (float) tiempoTotal) * 100);
                progressBar.setProgress(progresoActual);
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(100);
                startActivity(new Intent(Pantalla_de_carga.this, MainActivity.class));
                finish();
            }
        };

        countDownTimer.start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}