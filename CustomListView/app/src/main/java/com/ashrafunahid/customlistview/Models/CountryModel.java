package com.ashrafunahid.customlistview.Models;

public class CountryModel {
    String countryName, countryCupAchived;
    int countryFlag;

    public CountryModel(String countryName, String countryCupAchived, int countryFlag) {
        this.countryName = countryName;
        this.countryCupAchived = countryCupAchived;
        this.countryFlag = countryFlag;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCupAchived() {
        return countryCupAchived;
    }

    public void setCountryCupAchived(String countryCupAchived) {
        this.countryCupAchived = countryCupAchived;
    }

    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }
}
