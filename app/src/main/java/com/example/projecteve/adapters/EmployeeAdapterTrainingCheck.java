package com.example.projecteve.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projecteve.R;
import com.example.projecteve.models.Employee;

import java.util.List;

public class EmployeeAdapterTrainingCheck extends ArrayAdapter<Employee> {

    private Context mContext;
    private List<Employee> employeeList;

    public EmployeeAdapterTrainingCheck(Context context, List<Employee> list) {
        super(context, 0, list);
        mContext = context;
        employeeList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_employee_training_check, parent, false);
        }

        Employee currentEmployee = employeeList.get(position);

        TextView name = listItem.findViewById(R.id.tv_employee_name);
        name.setText(currentEmployee.getFirstName() + " " + currentEmployee.getLastName());

        TextView employeeNumber = listItem.findViewById(R.id.tv_employee_number);
        employeeNumber.setText("Employee #" + currentEmployee.getEmployeeNumber());





        return listItem;
    }
}
