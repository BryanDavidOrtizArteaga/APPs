package com.example.savoraapp.ui.gallery;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.savoraapp.R;
import com.example.savoraapp.databinding.ItemCitaBinding;

import java.util.ArrayList;
import java.util.List;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.CitaViewHolder> {

    private Context context;
    private List<Cita> citasListInternal;

    public CitasAdapter(Context context, List<Cita> citasListInicial) {
        this.context = context;
        this.citasListInternal = new ArrayList<>();
        if (citasListInicial != null) {
            this.citasListInternal.addAll(citasListInicial);
        }
    }

    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CitasAdapter", "onCreateViewHolder llamado");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCitaBinding binding = ItemCitaBinding.inflate(inflater, parent, false);
        return new CitaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaViewHolder holder, int position) {
        Cita cita = citasListInternal.get(position);
        Log.d("CitasAdapter", "onBindViewHolder llamado para posición: " + position + ", Nombre Cliente: " + cita.getNombreCliente());
        holder.bind(cita, context);
    }

    @Override
    public int getItemCount() {
        int count = citasListInternal != null ? citasListInternal.size() : 0;
        Log.d("CitasAdapter", "getItemCount() [ID:" + System.identityHashCode(this) +"] llamado. Tamaño de citasListInternal: " + count);
        return count;
    }
    public void actualizarCitas(List<Cita> nuevasCitasExternas) {
        Log.d("CitasAdapter", "actualizarCitas - INICIO. Tamaño nuevasCitasExternas: " + (nuevasCitasExternas != null ? nuevasCitasExternas.size() : "null"));
        Log.d("CitasAdapter", "actualizarCitas - Tamaño this.citasListInternal ANTES de clear: " + this.citasListInternal.size());

        this.citasListInternal.clear(); // Limpia la lista interna del adapter
        Log.d("CitasAdapter", "actualizarCitas - Tamaño this.citasListInternal DESPUÉS de clear: " + this.citasListInternal.size());

        if (nuevasCitasExternas != null) {
            this.citasListInternal.addAll(nuevasCitasExternas);
            Log.d("CitasAdapter", "actualizarCitas - Tamaño this.citasListInternal DESPUÉS de addAll: " + this.citasListInternal.size());
        } else {
            Log.d("CitasAdapter", "actualizarCitas - nuevasCitasExternas fue NULL.");
        }

        final int tamañoActualParaLog = this.citasListInternal.size();
        notifyDataSetChanged();

        Log.d("CitasAdapter", "Adapter notificado. Nuevo tamaño de la lista (variable local): " + tamañoActualParaLog);
        Log.d("CitasAdapter", "Adapter notificado. Nuevo tamaño de la lista (llamando a getItemCount()): " + getItemCount());
    }
    static class CitaViewHolder extends RecyclerView.ViewHolder {
        private final ItemCitaBinding binding;


        public CitaViewHolder(@NonNull ItemCitaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Cita cita, Context context) {
            Log.d("CitaViewHolder", "bind llamado para: " + cita.getNombreCliente());

            binding.textViewCitaFecha.setText(context.getString(R.string.cita_fecha_format, cita.getFecha()));
            Log.d("CitaViewHolder", "Fecha seteada: " + cita.getFecha());

            binding.textViewCitaNombreCliente.setText(context.getString(R.string.cita_cliente_format, cita.getNombreCliente()));
            Log.d("CitaViewHolder", "Nombre Cliente seteado: " + cita.getNombreCliente());

            binding.textViewCitaTelefono.setText(context.getString(R.string.cita_telefono_format, cita.getTelefonoCliente()));
            Log.d("CitaViewHolder", "Teléfono seteado: " + cita.getTelefonoCliente());

            binding.textViewCitaCorreo.setText(context.getString(R.string.cita_correo_format, cita.getCorreoCliente()));
            Log.d("CitaViewHolder", "Correo seteado: " + cita.getCorreoCliente());

            binding.textViewCitaNumeroPersonas.setText(context.getString(R.string.cita_personas_format, cita.getNumeroPersonas()));
            Log.d("CitaViewHolder", "Número de Personas seteado: " + cita.getNumeroPersonas());

            if (!TextUtils.isEmpty(cita.getPreferencias())) {
                binding.textViewCitaPreferencias.setText(context.getString(R.string.cita_preferencias_format, cita.getPreferencias()));
                binding.textViewCitaPreferencias.setVisibility(View.VISIBLE);
                Log.d("CitaViewHolder", "Preferencias (" + cita.getPreferencias() + ") visible: true");
            } else {
                binding.textViewCitaPreferencias.setVisibility(View.GONE);
                Log.d("CitaViewHolder", "Preferencias visible: false");
            }

            if (!TextUtils.isEmpty(cita.getNotas())) {
                binding.textViewCitaNotas.setText(context.getString(R.string.cita_notas_format, cita.getNotas()));
                binding.textViewCitaNotas.setVisibility(View.VISIBLE);
                Log.d("CitaViewHolder", "Notas (" + cita.getNotas() + ") visible: true");
            } else {
                binding.textViewCitaNotas.setVisibility(View.GONE);
                Log.d("CitaViewHolder", "Notas visible: false");
            }
        }

    }
}
