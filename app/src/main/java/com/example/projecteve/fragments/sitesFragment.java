package com.example.projecteve.fragments;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projecteve.R;
import com.example.projecteve.adapters.SitesAdapter;
import com.example.projecteve.models.Course;
import com.example.projecteve.models.Employee;
import com.example.projecteve.models.site;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        //Courses
        Course site_folder_sign_off = new Course("site_folder_sign_off",false);
        Course toolbox_talks = new Course("toolbox_talks",false);
        Course mcr_employee_form = new Course("mcr_employee_form",false);
        Course an_post_garda_vetting = new Course("an_post_garda_vetting",false);

        // Firebase reference to fetch employees
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("employees");

        // Listener to retrieve employees and count site participation
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int site1Count = 0;
                int site2Count = 0;
                int site3Count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Employee employee = snapshot.getValue(Employee.class);
                    if (employee != null) {
                        if (employee.getSite1()) {
                            site1Count++;
                        }
                        if (employee.getSite2()) {
                            site2Count++;
                        }
                        if (employee.getSite3()) {
                            site3Count++;
                        }
                    }
                }

                // Prepare the list with the site data and employee counts
                List<site> sites = new ArrayList<>();
                sites.add(new site("Site 1", "Docklands", site1Count));
                sites.add(new site("Site 2", "Docklands", site2Count));
                sites.add(new site("Site 3", "Docklands", site3Count));

                SitesAdapter adapter = new SitesAdapter(getContext(), sites);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Failed to read employees", databaseError.toException());
            }
        });

        // Set the click listener for the ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked site
                site selectedSite = (site) parent.getItemAtPosition(position);

                // Navigate to the trainings fragment and pass the site name
                NavController navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                bundle.putString("siteName", selectedSite.getName());
                navController.navigate(R.id.action_menu_sites_to_sitesTranings, bundle);
            }
        });

        return view;
    }
}
