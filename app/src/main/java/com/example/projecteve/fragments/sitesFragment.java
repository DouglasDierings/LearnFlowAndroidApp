package com.example.projecteve.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projecteve.R;
import com.example.projecteve.adapters.SitesAdapter;
import com.example.projecteve.models.site;

import java.util.ArrayList;
import java.util.List;

public class sitesFragment extends Fragment {

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sites, container, false);



        // ListView setup
        ListView listView = view.findViewById(R.id.lv_sites);

        // Dados de exemplo
        List<site> sites = new ArrayList<>();
        sites.add(new site("Site 1", "Docklands", 6));
        sites.add(new site("Site 2", "Docklands", 6));
        sites.add(new site("Site 3", "Docklands", 6));

        SitesAdapter adapter = new SitesAdapter(getContext(), sites);
        listView.setAdapter(adapter);

// Definir o click listener para os itens da ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obter o site clicado
                site selectedSite = sites.get(position);

                // Navegar para o fragmento de treinamentos e passar o nome do site
                NavController navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                bundle.putString("siteName", selectedSite.getName());
                navController.navigate(R.id.action_menu_sites_to_sitesTranings, bundle);
            }
        });

        return view;
    }
}
