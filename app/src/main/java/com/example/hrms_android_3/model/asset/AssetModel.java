package com.example.hrms_android_3.model.asset;

public class AssetModel {
    private String description, asset_code, asset_image, allocation, location, ownsership;

    public AssetModel(String description, String asset_code, String asset_image, String allocation, String location, String ownsership) {
        this.description = description;
        this.asset_code = asset_code;
        this.asset_image = asset_image;
        this.allocation = allocation;
        this.location = location;
        this.ownsership = ownsership;
    }

    @Override
    public String toString() {
        return "AssetModel{" +
                "description='" + description + '\'' +
                ", asset_code='" + asset_code + '\'' +
                ", asset_image='" + asset_image + '\'' +
                ", allocation='" + allocation + '\'' +
                ", location='" + location + '\'' +
                ", ownsership='" + ownsership + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAsset_code() {
        return asset_code;
    }

    public void setAsset_code(String asset_code) {
        this.asset_code = asset_code;
    }

    public String getAsset_image() {
        return asset_image;
    }

    public void setAsset_image(String asset_image) {
        this.asset_image = asset_image;
    }

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnsership() {
        return ownsership;
    }

    public void setOwnsership(String ownsership) {
        this.ownsership = ownsership;
    }
}
