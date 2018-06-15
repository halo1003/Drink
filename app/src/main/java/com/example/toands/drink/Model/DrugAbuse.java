package com.example.toands.drink.Model;

public class DrugAbuse {
    int constant_blister;
    int current_drug;
    int the_number_blister;
    String warning;

    public DrugAbuse() {
    }

    public DrugAbuse(int constant_blister, int current_drug, int the_number_blister, String warning) {
        this.constant_blister = constant_blister;
        this.current_drug = current_drug;
        this.the_number_blister = the_number_blister;
        this.warning = warning;
    }

    public int getConstant_blister() {
        return constant_blister;
    }

    public void setConstant_blister(int constant_blister) {
        this.constant_blister = constant_blister;
    }

    public int getCurrent_drug() {
        return current_drug;
    }

    public void setCurrent_drug(int current_drug) {
        this.current_drug = current_drug;
    }

    public int getThe_number_blister() {
        return the_number_blister;
    }

    public void setThe_number_blister(int the_number_blister) {
        this.the_number_blister = the_number_blister;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}
