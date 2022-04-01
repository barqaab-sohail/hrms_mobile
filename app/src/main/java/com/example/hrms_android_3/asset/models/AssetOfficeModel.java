package com.example.hrms_android_3.asset.models;

public class AssetOfficeModel {
    private String id, name;

    public AssetOfficeModel() {
        this.id = id;
        this.name = name;
    }

    public AssetOfficeModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
