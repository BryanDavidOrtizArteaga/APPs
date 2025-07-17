package com.example.savoraapp;

import android.content.Intent; // Asegúrate de importar Intent
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem; // Asegúrate de importar MenuItem
import android.widget.TextView;
import android.widget.Toast; // Para mensajes opcionales

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.savoraapp.databinding.ActivityPuenteBinding;

public class Puente extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPuenteBinding binding;

    // Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference userDatabaseReference;

    // Views del Header
    private TextView textViewNombrePrincipal;
    private TextView textViewCorreoPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPuenteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPuente.toolbar);
        // ... (código del FAB)

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        View headerView = navigationView.getHeaderView(0);
        textViewNombrePrincipal = headerView.findViewById(R.id.NombresPrincipal);
        textViewCorreoPrincipal = headerView.findViewById(R.id.CorreoPrincipal);

        firebaseAuth = FirebaseAuth.getInstance(); // Inicializa firebaseAuth aquí
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            cargarDatosUsuarioHeader();
        } else {
            // Manejar caso donde el usuario no está logueado al iniciar PuenteActivity
            // Podrías redirigir a Login o mostrar estado de invitado
            textViewNombrePrincipal.setText("Invitado");
            textViewCorreoPrincipal.setText("correo@ejemplo.com");
        }


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_puente);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.CerrarSesion) {
                    firebaseAuth.signOut();
                    Toast.makeText(Puente.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Puente.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                }

                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                if (handled) {
                    drawer.closeDrawers();
                }
                return handled;
            }
        });
    }

    private void cargarDatosUsuarioHeader() {
        // ... (tu código existente para cargar datos del header)
        if (firebaseUser != null) {
            String userEmail = firebaseUser.getEmail();
            String userId = firebaseUser.getUid();

            if (userEmail != null) {
                textViewCorreoPrincipal.setText(userEmail);
            }

            userDatabaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(userId);

            userDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        String nombre = dataSnapshot.child("nombre").getValue(String.class);
                        String correo = dataSnapshot.child("correo").getValue(String.class);

                        if (binding != null && textViewNombrePrincipal != null && textViewCorreoPrincipal != null) {
                            textViewNombrePrincipal.setText(nombre);
                            textViewCorreoPrincipal.setText(correo);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    if (binding != null && textViewNombrePrincipal != null ) {
                        textViewNombrePrincipal.setText(getString(R.string.NombresPrincipal));
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.puente, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_puente);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
