package com.example.hrms_android_3.hr.models;

public class Employee {
    private String id;
    private String employee_no;
    private  String full_name;
    private String date_of_birth;
    private String date_of_joining;
    private String cnic;
    private String designation;
    private String picture;
    private  String mobile;
    private String status;

    public Employee(String id, String employee_no, String full_name, String date_of_birth, String date_of_joining, String cnic, String designation, String picture, String mobile, String status) {
        this.id = id;
        this.employee_no = employee_no;
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.date_of_joining = date_of_joining;
        this.cnic = cnic;
        this.designation = designation;
        this.picture = picture;
        this.mobile = mobile;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", employee_no='" + employee_no + '\'' +
                ", full_name='" + full_name + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", date_of_joining='" + date_of_joining + '\'' +
                ", cnic='" + cnic + '\'' +
                ", designation='" + designation + '\'' +
                ", picture='" + picture + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String toStringExceptPicture() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", employee_no='" + employee_no + '\'' +
                ", full_name='" + full_name + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", date_of_joining='" + date_of_joining + '\'' +
                ", cnic='" + cnic + '\'' +
                ", designation='" + designation + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDate_of_joining() {
        return date_of_joining;
    }

    public void setDate_of_joining(String date_of_joining) {
        this.date_of_joining = date_of_joining;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
