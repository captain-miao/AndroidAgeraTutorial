package com.github.captain_miao.agera.tutorial.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.github.captain_miao.agera.tutorial.BR;

/**
 * @author YanLu
 * @since 16/4/27
 */
public class BaseObservableVehicleInfo extends BaseObservable {

    private boolean isSelected;
    private String  logoUrl;
    private String  brand;
    private String  description;

    public BaseObservableVehicleInfo(boolean isSelected, String logoUrl, String brand, String description) {
        this.isSelected = isSelected;
        this.logoUrl = logoUrl;
        this.brand = brand;
        this.description = description;
    }

    public BaseObservableVehicleInfo(VehicleInfo vehicleInfo) {
        this.isSelected = vehicleInfo.isSelected.get();
        this.logoUrl = vehicleInfo.logoUrl;
        this.brand = vehicleInfo.brand;
        this.description = vehicleInfo.description;
    }

    @Bindable
    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
        notifyPropertyChanged(BR.isSelected);
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
