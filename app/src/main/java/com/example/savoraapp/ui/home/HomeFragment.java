package com.example.savoraapp.ui.home;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savoraapp.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.savoraapp.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference usuariosReference;
    DatabaseReference citasReference;
    ProgressDialog progressDialog;

    private String FechaSeleccionada = "";
    private Calendar calendario;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        citasReference = FirebaseDatabase.getInstance().getReference("Citas");
        usuariosReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);

        calendario = Calendar.getInstance();

        binding.Calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePickerDialog();
            }
        });

        actualizarFechaEnTextView();

        binding.BotonGuardarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCita();
            }
        });

    }

    private void mostrarDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, monthOfYear);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                actualizarFechaEnTextView();
            }
        };

        new DatePickerDialog(requireContext(), dateSetListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void actualizarFechaEnTextView() {
        String formatoDeFecha = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.getDefault());
        binding.Fecha.setText(sdf.format(calendario.getTime()));
        FechaSeleccionada = sdf.format(calendario.getTime());
    }
    private void guardarCita() {
        progressDialog.setMessage("Guardando cita...");
        progressDialog.show();

        String nombreCliente = binding.ClienteNombre.getText().toString().trim();
        String telefonoCliente = binding.ClienteTelefono.getText().toString().trim();
        String correoCliente = binding.ClienteCorreo.getText().toString().trim();
        String numeroPersonasStr = binding.NumeroPersonas.getText().toString().trim();
        String preferencias = binding.Preferencias.getText().toString().trim();
        String notas = binding.NotasA.getText().toString().trim();

        if (FechaSeleccionada.isEmpty() || FechaSeleccionada.equals(getString(R.string.Fecha))) {
            Toast.makeText(getContext(), "Por favor, seleccione una fecha.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (nombreCliente.isEmpty()) {
            binding.ClienteNombre.setError("El nombre es requerido");
            binding.ClienteNombre.requestFocus();
            progressDialog.dismiss();
            return;
        }
        if (telefonoCliente.isEmpty()) {
            binding.ClienteTelefono.setError("El telefono es requerido");
            binding.ClienteTelefono.requestFocus();
            progressDialog.dismiss();
            return;
        }
        if (correoCliente.isEmpty()) {
            binding.ClienteCorreo.setError("El correo es requerido");
            binding.ClienteCorreo.requestFocus();
            progressDialog.dismiss();
            return;
        }
        if (numeroPersonasStr.isEmpty()) {
            binding.NumeroPersonas.setError("Digite el número de personas");
            binding.NumeroPersonas.requestFocus();
            progressDialog.dismiss();
            return;
        }


        String idCita = citasReference.push().getKey();
        if (idCita == null) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Error al generar ID para la cita.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> datosCita = new HashMap<>();
        datosCita.put("idCita", idCita);
        datosCita.put("fecha", FechaSeleccionada);
        datosCita.put("nombreCliente", nombreCliente);
        datosCita.put("telefonoCliente", telefonoCliente);
        datosCita.put("correoCliente", correoCliente);
        datosCita.put("numeroPersonas", numeroPersonasStr);
        datosCita.put("preferencias", preferencias);
        datosCita.put("notas", notas);

        if (firebaseUser != null) {
            datosCita.put("uidUsuario", firebaseUser.getUid());
            Log.d("HomeFragment", "Guardando cita para UID: " + firebaseUser.getUid());
        } else {
            Toast.makeText(getContext(), "Error: Usuario no autenticado. No se pudo asociar la cita.", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }


        citasReference.child(idCita).setValue(datosCita)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Cita guardada exitosamente.", Toast.LENGTH_SHORT).show();
                        LimpiarCampos();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error al guardar cita: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void LimpiarCampos(){
        binding.ClienteNombre.setText("");
        binding.ClienteTelefono.setText("");
        binding.ClienteCorreo.setText("");
        binding.NumeroPersonas.setText("");
        binding.Preferencias.setText("");
        binding.NotasA.setText("");
        calendario = Calendar.getInstance();
        binding.Fecha.setText(getString(R.string.Fecha));
        FechaSeleccionada = "";
        binding.ClienteNombre.requestFocus();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
