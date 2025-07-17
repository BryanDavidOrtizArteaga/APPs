package com.example.savoraapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.StringJoiner;

public class Registro extends AppCompatActivity {

    EditText Ej, EjC, CorreoEJ, EjCC;
    Button REG;
    TextView IngresoBTN;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    String nombre = "", correo = "", contra = "", concontra = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registrar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Ej = findViewById(R.id.Ej);
        CorreoEJ = findViewById(R.id.CorreoEJ);
        EjC = findViewById(R.id.EjC);
        EjCC = findViewById(R.id.EjCC);
        REG = findViewById(R.id.REG);
        IngresoBTN = findViewById(R.id.IngresoBTN);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setMessage("Registrando...");
        progressDialog.setCancelable(false);

        REG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarDatos();
                               }
        });

        IngresoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registro.this, MainActivity.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }

    private void ValidarDatos(){
        nombre = Ej.getText().toString().trim();
        correo = CorreoEJ.getText().toString().trim();
        contra = EjC.getText().toString().trim();
        concontra = EjCC.getText().toString().trim();
        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Ingrese su nombre", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(contra)){
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(concontra)){
            Toast.makeText(this, "Confirme su contraseña", Toast.LENGTH_SHORT).show();
        }
        else if (!contra.equals(concontra)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        else{
            CrearCuenta();
        }
    }

    private void CrearCuenta() {

        progressDialog.setMessage("Creando Cuenta");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(correo, contra)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        GuardarInformacion();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void GuardarInformacion() {
        progressDialog.setMessage("Guardando Informacion");
        progressDialog.show();

        String uid = firebaseAuth.getUid();

        HashMap<String, String> Datos = new HashMap<>();

        Datos.put("uid", uid);
        Datos.put("nombre", nombre);
        Datos.put("correo", correo);
        Datos.put("contra", contra);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid)
                .setValue(Datos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registro.this, Puente.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}