package com.tothon.layarperak.model;

import java.util.ArrayList;
import java.util.List;

public class DepartmentFactory {

    public static List<Department> getAllDepartment(List<Crew> crews) {
        ArrayList<String> groupName = getAllDepartmentName(crews);
        List<Department> result = new ArrayList<>();
        for (String name: groupName) {
            result.add(makeGroup(name, crews));
        }
        return result;
    }

    private static Department makeGroup(String department, List<Crew> crews) {
        return new Department(department, addCrewInDepartment(department, crews));
    }

    private static List<Crew> addCrewInDepartment(String department, List<Crew> crews) {
        List<Crew> result = new ArrayList<>();
        for (Crew crew: crews) {
            if (crew.getDepartment().equals(department)) {
                result.add(crew);
            }
        }
        return result;
    }

    private static ArrayList<String> getAllDepartmentName(List<Crew> crews) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < crews.size(); i++) {
            String department = crews.get(i).getDepartment();
            if (i != 0) {
                boolean isSimilar = false;
                for (int j = 0; j < result.size(); j++) {
                    if (department.equals(result.get(j))) {
                        isSimilar = true;
                        break;
                    }
                }
                if (!isSimilar) {
                    result.add(department);
                }
            } else {
                result.add(department);
            }
        }
        return result;
    }
}