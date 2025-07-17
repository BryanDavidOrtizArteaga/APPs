package com.example.savoraapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.savoraapp.R;
import com.example.savoraapp.adapter.MenuAdapter;
import com.example.savoraapp.model.Plato;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private RecyclerView recyclerViewMenu;
    private MenuAdapter menuAdapter;
    private List<Plato> listaDePlatos;
    private ProgressBar progressBarMenu;
    private TextView textViewNoMenu;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        recyclerViewMenu = root.findViewById(R.id.recyclerViewMenu);
        progressBarMenu = root.findViewById(R.id.progressBarMenu);
        textViewNoMenu = root.findViewById(R.id.textViewNoMenu);

        setupRecyclerView();
        cargarDatosDelMenu();

        return root;
    }

    private void setupRecyclerView() {
        listaDePlatos = new ArrayList<>();
        menuAdapter = new MenuAdapter(requireContext(), listaDePlatos);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewMenu.setAdapter(menuAdapter);
    }

    private void cargarDatosDelMenu() {
        progressBarMenu.setVisibility(View.VISIBLE);
        textViewNoMenu.setVisibility(View.GONE);
        recyclerViewMenu.setVisibility(View.GONE);

        List<Plato> platosHardcodeados = new ArrayList<>();

        platosHardcodeados.add(new Plato(
                "Empanadas Colombianas",
                "(3 Unidades))",
                Arrays.asList("Masa de maíz crocante rellena de papa sazonada y carne de res desmechada. Se sirven con ají casero."),
                7500,
                R.drawable.empanadas,
                "Entradas"
        ));

        platosHardcodeados.add(new Plato(
                "Patacones con Hogao y Suero Costeño",
                "(2 unidades)",
                Arrays.asList("Rodajas de plátano verde fritas y aplastadas, acompañadas de hogao (sofrito de tomate y cebolla) y suero costeño (crema agria típica de la costa)."),
                8000,
                R.drawable.patacones_ahogao,
                "Entradas"
        ));

        platosHardcodeados.add(new Plato(
                "Chicharrón con Arepa",
                "(2 Unidades)",
                Arrays.asList("Trozos de tocino de cerdo frito hasta quedar crujiente, servido con una arepa de maíz blanca."),
                8500,
                R.drawable.chicharron_arepa,
                "Entradas"
        ));

        platosHardcodeados.add(new Plato(
                "Bandeja Paisa",
                "(Plato principal individual)",
                Arrays.asList("El plato insignia. Incluye arroz blanco, frijoles rojos, carne molida, chicharrón, chorizo, huevo frito, tajadas de plátano maduro, arepa y aguacate."),
                35000,
                R.drawable.bandeja_paisa,
                "Platos Fuertes"
        ));

        platosHardcodeados.add(new Plato(
                "Ajiaco Santafereño",
                "(Sopa principal)",
                Arrays.asList("Sopa espesa a base de tres tipos de papa (criolla, pastusa y sabanera), pechuga de pollo desmechada, mazorca. Se sirve con alcaparras, crema de leche y aguacate."),
                18000,
                R.drawable.ajiaco_s,
                "Platos Fuertes"
        ));

        platosHardcodeados.add(new Plato(
                "Sancocho de Gallina",
                "(Sopa en plato hondo)",
                Arrays.asList("Sopa abundante con trozos de gallina, papa, yuca, plátano verde, mazorca y cilantro. Acompañado de arroz y aguacate."),
                8500,
                R.drawable.sancocho_gallina,
                "Platos Fuertes"
        ));

        platosHardcodeados.add(new Plato(
                "Mojarra Frita con Patacones ",
                "(Plato de mar)",
                Arrays.asList("Pescado mojarra entero frito hasta quedar dorado y crocante, servido con patacones y arroz preparado con leche de coco."),
                33000,
                R.drawable.mojarra,
                "Platos Fuertes"
        ));

        platosHardcodeados.add(new Plato(
                "Carne a la Llanera (Mamona)",
                "(Porciones generosas en plato extendido)",
                Arrays.asList("Cortes de ternera asados lentamente a la brasa con especias, lo que le da un sabor ahumado y jugoso. Se sirve con papa criolla y yuca."),
                40000,
                R.drawable.carne_llanera,
                "Platos Fuertes"
        ));

        platosHardcodeados.add(new Plato(
                "Frijoles con Garra y Arroz",
                "(Plato extendido u hondo si se prefiere más caldoso)",
                Arrays.asList("Frijoles rojos cocidos con garra (trozos de carne de cerdo), servidos con arroz blanco, aguacate y chicharrón."),
                25000,
                R.drawable.chicharron_arepa,
                "Platos Fuertes"
        ));

        if (platosHardcodeados.isEmpty()) {
            textViewNoMenu.setVisibility(View.VISIBLE);
            recyclerViewMenu.setVisibility(View.GONE);
        } else {
            menuAdapter.actualizarPlatos(platosHardcodeados);
            textViewNoMenu.setVisibility(View.GONE);
            recyclerViewMenu.setVisibility(View.VISIBLE);
        }
        progressBarMenu.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
