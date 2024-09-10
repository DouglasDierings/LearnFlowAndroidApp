package com.example.projecteve.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecteve.R;
import com.example.projecteve.models.Employee;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EmployeeAdapter extends BaseAdapter {

    private Context context;
    private List<Employee> employeeList;
    private LayoutInflater inflater;

    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.employee_list_item, parent, false);
        }

        TextView tvEmployeeName = convertView.findViewById(R.id.tv_employee_name);
        TextView tvEmployeeNumber = convertView.findViewById(R.id.tv_employee_number);
        ImageView imgDelete = convertView.findViewById(R.id.img_delete);

        Employee employee = employeeList.get(position);

        tvEmployeeName.setText(employee.getFirstName() + " " + employee.getLastName());
        tvEmployeeNumber.setText(employee.getEmployeeNumber());

        imgDelete.setOnClickListener(v -> {
            // Show a confirmation dialog
            new AlertDialog.Builder(context)
                    .setTitle("Delete Employee")
                    .setMessage("Are you sure you want to delete " + employee.getFirstName() + " " + employee.getLastName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Check if the position is valid before attempting to delete
                        if (position >= 0 && position < employeeList.size()) {
                            Employee employeeToRemove = employeeList.get(position);

                            // Remove employee from the list before Firebase operation
                            employeeList.remove(position);
                            notifyDataSetChanged();

                            // Remove employee from Firebase
                            DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference()
                                    .child("employees")
                                    .child(employeeToRemove.getEmployeeNumber());

                            employeeRef.removeValue().addOnSuccessListener(aVoid -> {
                                Toast.makeText(context, "Employee removed successfully", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                // If the Firebase operation fails, add the employee back to the list
                                employeeList.add(position, employeeToRemove);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Failed to remove employee", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            Toast.makeText(context, "Failed to remove employee: Invalid index", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // Dismiss the dialog and do nothing if "No" is clicked
                        dialog.dismiss();
                    })
                    .show();
        });

        return convertView;
    }

}
