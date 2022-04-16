package com.example.hrms_android_3.hr.models;

public class EmployeeDocument {
    private String id;
    private String extension;
    private  String description;
    private String url;

    public EmployeeDocument(String id, String extension, String description, String url) {
        this.id = id;
        this.extension = extension;
        this.description = description;
        this.url = url;
    }

    @Override
    public String toString() {
        return "EmployeeDocument{" +
                "id='" + id + '\'' +
                ", extension='" + extension + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
