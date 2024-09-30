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
import com.example.projecteve.models.Site;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SitesListFragment extends Fragment {

    private View view;
    private List<Course> emptyCoursesList = new ArrayList<>();
    private String userId; // Add a variable to store the user ID

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sites, container, false);

        // ListView setup
        ListView listView = view.findViewById(R.id.lv_sites);

        // Get current user ID from Firebase Auth
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        } else {
            Log.e("Firebase", "User not authenticated");
            return view; // Exit if no user is authenticated
        }

        // Firebase reference to fetch employees under the current user's branch
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("employees");

        // Listener to retrieve employees and count Site participation
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Integer> siteCounts = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Employee employee = snapshot.getValue(Employee.class);
                    if (employee != null) {
                        List<Site> sites = employee.getSites(); // Get the list of sites from the employee
                        if (sites != null) {
                            for (Site site : sites) {
                                String siteName = site.getName();
                                siteCounts.put(siteName, siteCounts.getOrDefault(siteName, 0) + 1);
                            }
                        }
                    }
                }

                // Prepare the list with the Site data and employee counts
                List<Site> sitesList = new ArrayList<>();
                int siteCode = 1;
                for (Map.Entry<String, Integer> entry : siteCounts.entrySet()) {
                    sitesList.add(new Site(siteCode, entry.getKey(), "Docklands", entry.getValue(), emptyCoursesList));
                }

                SitesAdapter adapter = new SitesAdapter(getContext(), sitesList);
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
                // Get the clicked Site
                Site selectedSite = (Site) parent.getItemAtPosition(position);

                // Navigate to the trainings fragment and pass the Site name
                NavController navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                bundle.putString("siteName", selectedSite.getName());
                navController.navigate(R.id.action_menu_sites_to_sitesTranings, bundle);
            }
        });

        return view;
    }
}
