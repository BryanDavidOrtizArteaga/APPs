package com.example.savoraapp.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.savoraapp.R;
import com.example.savoraapp.databinding.FragmentGalleryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding _binding;
    private FragmentGalleryBinding getBinding() { return _binding; }


    private CitasAdapter citasAdapter;
    private List<Cita> citasList;
    private DatabaseReference citasReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ValueEventListener citasValueEventListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = getBinding().getRoot();

        setupRecyclerView();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        citasReference = FirebaseDatabase.getInstance().getReference("Citas");


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (firebaseUser != null) {
            cargarCitas();
        } else {
            if (_binding != null) {
                getBinding().progressBarGallery.setVisibility(View.GONE);
                getBinding().textViewNoCitas.setText("Por favor, inicie sesión para ver sus citas.");
                getBinding().textViewNoCitas.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupRecyclerView() {

        citasList = new ArrayList<>();
        citasAdapter = new CitasAdapter(requireContext(), citasList);
        if (_binding != null) {
            getBinding().recyclerViewCitas.setLayoutManager(new LinearLayoutManager(requireContext()));
            getBinding().recyclerViewCitas.setAdapter(citasAdapter);
        }
    }

    private void cargarCitas() {
        if (_binding == null) {
            Log.w("GalleryFragment", "cargarCitas: Binding es null, no se puede continuar.");
            return;
        }

        Log.d("GalleryFragment", "cargarCitas: Iniciando carga de citas.");
        Log.d("GalleryFragment", "cargarCitas: firebaseUser es null? " + (firebaseUser == null));

        getBinding().progressBarGallery.setVisibility(View.VISIBLE);
        getBinding().textViewNoCitas.setVisibility(View.GONE);
        getBinding().recyclerViewCitas.setVisibility(View.GONE);

        if (firebaseUser == null) {
            Log.w("GalleryFragment", "cargarCitas: firebaseUser es null. Mostrando mensaje de error.");
            getBinding().progressBarGallery.setVisibility(View.GONE);
            getBinding().textViewNoCitas.setText("Error: Usuario no disponible. Inicie sesión.");
            getBinding().textViewNoCitas.setVisibility(View.VISIBLE);
            return;
        }

        String currentUserId = firebaseUser.getUid();
        Log.d("GalleryFragment", "cargarCitas: CurrentUserID para consulta: " + currentUserId);

        citasValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (_binding == null) {
                    Log.w("GalleryFragment", "onDataChange: Binding es null, vista destruida.");
                    return;
                }

                Log.d("GalleryFragment", "onDataChange: Listener disparado. UID usado en consulta original: " + currentUserId);
                Log.d("GalleryFragment", "onDataChange: dataSnapshot.exists() = " + dataSnapshot.exists());
                Log.d("GalleryFragment", "onDataChange: dataSnapshot.getChildrenCount() = " + dataSnapshot.getChildrenCount());
                citasList.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d("GalleryFragment", "onDataChange: Procesando snapshot con key: " + snapshot.getKey());
                        Cita cita = snapshot.getValue(Cita.class);
                        if (cita != null) {
                            Log.d("GalleryFragment", "onDataChange: Cita obtenida: " + cita.getNombreCliente() + ", UID en cita: " + cita.getUidUsuario());

                            if (currentUserId.equals(cita.getUidUsuario())) {
                                citasList.add(cita);
                            } else {
                                Log.w("GalleryFragment", "onDataChange: Cita filtrada en cliente. UID de cita (" + cita.getUidUsuario() + ") no coincide con UID de consulta (" + currentUserId + ").");
                            }
                        } else {
                            Log.w("GalleryFragment", "onDataChange: Cita obtenida del snapshot es NULL. Key: " + snapshot.getKey());
                        }
                    }
                    Log.d("GalleryFragment", "onDataChange: Total citas añadidas a la lista (después del bucle): " + citasList.size());

                    if (citasList.isEmpty()) {
                        Log.d("GalleryFragment", "onDataChange: La lista de citas está vacía después de procesar el snapshot. Mostrando 'no citas'.");
                        getBinding().textViewNoCitas.setText(R.string.no_citas_disponibles);
                        getBinding().textViewNoCitas.setVisibility(View.VISIBLE);
                        getBinding().recyclerViewCitas.setVisibility(View.GONE);
                        citasAdapter.actualizarCitas(new ArrayList<>());
                    } else {
                        Log.d("GalleryFragment", "onDataChange: Actualizando adaptador con " + citasList.size() + " citas.");
                        citasAdapter.actualizarCitas(new ArrayList<>(citasList));
                        getBinding().recyclerViewCitas.setVisibility(View.VISIBLE);
                        getBinding().textViewNoCitas.setVisibility(View.GONE);
                    }
                } else {
                    Log.d("GalleryFragment", "onDataChange: No existen datos en el snapshot (dataSnapshot.exists() es false).");
                    getBinding().textViewNoCitas.setText(R.string.no_citas_disponibles);
                    getBinding().textViewNoCitas.setVisibility(View.VISIBLE);
                    getBinding().recyclerViewCitas.setVisibility(View.GONE);
                    citasAdapter.actualizarCitas(new ArrayList<>());
                }
                getBinding().progressBarGallery.setVisibility(View.GONE);
                Log.d("GalleryFragment", "onDataChange: Fin. Visibilidad RecyclerView: " + getBinding().recyclerViewCitas.getVisibility() + ", Visibilidad textViewNoCitas: " + getBinding().textViewNoCitas.getVisibility());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (_binding == null) {
                    Log.w("GalleryFragment", "onCancelled: Binding es null.");
                    return;
                }
                Log.e("GalleryFragment", "onCancelled: Error al cargar citas: " + databaseError.getMessage(), databaseError.toException());
                getBinding().progressBarGallery.setVisibility(View.GONE);
                if (isAdded() && getContext() != null) {
                    Toast.makeText(getContext(), "Error al cargar citas: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    getBinding().textViewNoCitas.setText("Error al cargar datos.");
                    getBinding().textViewNoCitas.setVisibility(View.VISIBLE);
                    getBinding().recyclerViewCitas.setVisibility(View.GONE);
                }
            }
        };

        Log.d("GalleryFragment", "cargarCitas: Adjuntando ValueEventListener a la referencia de Citas con orderByChild('uidUsuario').equalTo('" + currentUserId + "')");
        citasReference.orderByChild("uidUsuario").equalTo(currentUserId)
                .addValueEventListener(citasValueEventListener);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (citasValueEventListener != null && citasReference != null) {
            citasReference.removeEventListener(citasValueEventListener);
        }
        _binding = null;
    }
}
