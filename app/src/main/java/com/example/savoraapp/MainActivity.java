package com.example.savoraapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText CorreoEJ, EjC;
    Button IN;
    TextView RegistroBTN;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    String correo, contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ingresar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        CorreoEJ = findViewById(R.id.CorreoEJ);
        EjC = findViewById(R.id.EjC);

        IN = findViewById(R.id.IN);
        RegistroBTN = findViewById(R.id.RegistroBTN);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(MainActivity.this);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Ingresando");
        progressDialog.setCancelable(false);

        IN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ValidarDatos();
            }
        });

        RegistroBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registro.class));
                }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void ValidarDatos() {
        correo = CorreoEJ.getText().toString().trim();
        contra = EjC.getText().toString().trim();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Ingrese correo valido", Toast.LENGTH_SHORT).show();
        }
        else if (contra.isEmpty()){
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
        }
        else {
            LoginUsuario();
        }    
    }

    private void LoginUsuario() {
        progressDialog.setMessage("Ingresando");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(correo, contra)
                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        startActivity(new Intent(MainActivity.this, Puente.class));
                        Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error al ingresar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}