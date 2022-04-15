package com.example.hrms_android_3.hr.models;

public class EmployeeDocument {
    private String id;
    private String docType;
    private  String description;

    public EmployeeDocument(String id, String docType, String description) {
        this.id = id;
        this.docType = docType;
        this.description = description;
    }

    @Override
    public String toString() {
        return "EmployeeDocuments{" +
                "id='" + id + '\'' +
                ", docType='" + docType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
