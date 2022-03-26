package com.example.hrms_android_3.model.charts;

public class AgeChart {
    String label;
    int value;

    public AgeChart(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AgeChart{" +
                "label='" + label + '\'' +
                ", value=" + value +
                '}';
    }
}
