package com.example.hrms_android_3.model.asset;

public class AssetClassModel {
    private String id, name;

    public AssetClassModel() {
        this.id = id;
        this.name = name;
    }

    public AssetClassModel(String id, String name) {
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
