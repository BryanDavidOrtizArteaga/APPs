package com.example.savoraapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.savoraapp.R;
import com.example.savoraapp.model.Plato;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.PlatoViewHolder> {

    private Context context;
    private List<Plato> platoListInternal;

    public MenuAdapter(Context context, List<Plato> platoListInicial) {
        this.context = context;
        this.platoListInternal = new ArrayList<>();
        if (platoListInicial != null) {
            this.platoListInternal.addAll(platoListInicial);
        }
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plato, parent, false);
        return new PlatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {
        Plato plato = platoListInternal.get(position);
        holder.bind(plato);
    }

    @Override
    public int getItemCount() {
        return platoListInternal != null ? platoListInternal.size() : 0;
    }

    public void actualizarPlatos(List<Plato> nuevosPlatos) {
        this.platoListInternal.clear();
        if (nuevosPlatos != null) {
            this.platoListInternal.addAll(nuevosPlatos);
        }
        notifyDataSetChanged();
    }

    static class PlatoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPlato;
        TextView textViewNombrePlato;
        TextView textViewIngredientesPlato;
        TextView textViewPrecioPlato;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPlato = itemView.findViewById(R.id.imageViewPlato);
            textViewNombrePlato = itemView.findViewById(R.id.textViewNombrePlato);
            textViewIngredientesPlato = itemView.findViewById(R.id.textViewIngredientesPlato);
            textViewPrecioPlato = itemView.findViewById(R.id.textViewPrecioPlato);
        }

        public void bind(Plato plato) {
            textViewNombrePlato.setText(plato.getNombre());

            String ingredientesStr = "";
            if (plato.getIngredientes() != null && !plato.getIngredientes().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < plato.getIngredientes().size(); i++) {
                    sb.append(plato.getIngredientes().get(i));
                    if (i < plato.getIngredientes().size() - 1) {
                        sb.append(", ");
                    }
                }
                ingredientesStr = sb.toString();
            } else if (plato.getDescripcion() != null && !plato.getDescripcion().isEmpty()){
                ingredientesStr = plato.getDescripcion();
            }
            textViewIngredientesPlato.setText(ingredientesStr);

            textViewPrecioPlato.setText(String.format(Locale.getDefault(), "$%.2f", plato.getPrecio()));

            if (plato.getImagenResId() != 0) {
                imageViewPlato.setImageResource(plato.getImagenResId());
            } else {

                imageViewPlato.setImageResource(R.drawable.ic_default_dish);
            }
        }
    }
}