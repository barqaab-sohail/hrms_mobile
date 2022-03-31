package com.example.hrms_android_3.asset.models;

public class AssetEmployeeModel {
    private String id, full_name, designation;

    public AssetEmployeeModel(String id, String full_name, String designation) {
        this.id = id;
        this.full_name = full_name;
        this.designation = designation;
    }

    @Override
    public String toString() {
        return full_name +", "+designation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
