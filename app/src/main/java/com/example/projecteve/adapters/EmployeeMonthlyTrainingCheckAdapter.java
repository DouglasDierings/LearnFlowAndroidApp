package com.example.projecteve.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.projecteve.R;
import com.example.projecteve.models.Course;
import com.example.projecteve.models.Employee;

import java.util.List;

public class EmployeeMonthlyTrainingCheckAdapter extends BaseAdapter {
    private Context context;
    private List<Employee> employees;
    private int siteIndex;
    private int courseIndex;
    private String selectedMonth;

    public EmployeeMonthlyTrainingCheckAdapter(Context context, List<Employee> employees, int siteIndex, int courseIndex, String selectedMonth) {
        this.context = context;
        this.employees = employees;
        this.siteIndex = siteIndex;
        this.courseIndex = courseIndex;
        this.selectedMonth = selectedMonth;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_employee_training_check, parent, false);
        }

        TextView tvEmployeeName = convertView.findViewById(R.id.tv_employee_name);
        TextView tvEmployeeNumber = convertView.findViewById(R.id.tv_employee_number);
        CheckBox cbCourseCompleted = convertView.findViewById(R.id.cb_course_completed);

        Employee employee = employees.get(position);
        tvEmployeeName.setText(employee.getFirstName());
        tvEmployeeNumber.setText(employee.getEmployeeNumber());

        Course course = employee.getSites().get(siteIndex).getCoursesList().get(courseIndex);
        boolean isCompleted = course.isCompletedForMonth(selectedMonth);


        cbCourseCompleted.setChecked(isCompleted);

        cbCourseCompleted.setOnCheckedChangeListener((buttonView, isChecked) ->
            course.setCompletionForMonth(selectedMonth, isChecked)
        );

        return convertView;
    }
}
