package com.example.projecteve.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecteve.R;
import com.example.projecteve.models.Course;
import com.example.projecteve.models.Employee;
import com.example.projecteve.models.Site;

import java.util.List;

public class EmployeeAdapterMonthlyCourseCheck extends ArrayAdapter<Employee> {

    private Context mContext;
    private List<Employee> employeeList;
    private int siteIndex;
    private int courseIndex;
    private String selectedMonth;

    public EmployeeAdapterMonthlyCourseCheck(Context context, List<Employee> list, int siteIndex, int courseIndex,String selectedMonth) {
        super(context, 0, list);
        mContext = context;
        employeeList = list;
        this.siteIndex = siteIndex;
        this.courseIndex = courseIndex;
        this.selectedMonth = selectedMonth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_employee_training_check, parent, false);
        }

        Employee currentEmployee = employeeList.get(position);

        TextView name = listItem.findViewById(R.id.tv_employee_name);
        TextView employeeNumber = listItem.findViewById(R.id.tv_employee_number);
        CheckBox courseCompleted = listItem.findViewById(R.id.cb_course_completed);

        name.setText(currentEmployee.getFirstName() + " " + currentEmployee.getLastName());
        employeeNumber.setText("Employee #" + currentEmployee.getEmployeeNumber());

        if (currentEmployee.getSites() != null && siteIndex < currentEmployee.getSites().size()) {
            Site site = currentEmployee.getSites().get(siteIndex);

            if (site.getCoursesList() != null && courseIndex < site.getCoursesList().size()) {
                Course course = site.getCoursesList().get(courseIndex);

                // Set initial checkbox state
                courseCompleted.setChecked(course.getIsCompleted() != null && course.getIsCompleted());

                // Handle checkbox change
                courseCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    course.setIsCompleted(isChecked);//OLHAR AQUI USAR SETMONTHCOMPLETITION
                });
            } else {
                // Hide checkbox if course does not exist
                courseCompleted.setVisibility(View.GONE);
                Toast.makeText(mContext, "Course not found for employee: " + currentEmployee.getEmployeeNumber(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Hide checkbox if site does not exist
            courseCompleted.setVisibility(View.GONE);
            Toast.makeText(mContext, "Site not found for employee: " + currentEmployee.getEmployeeNumber(), Toast.LENGTH_SHORT).show();
        }

        return listItem;
    }

    }


