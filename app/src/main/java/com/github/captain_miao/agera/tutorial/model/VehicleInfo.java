package com.github.captain_miao.agera.tutorial.model;

import android.databinding.ObservableBoolean;

/**
 * @author YanLu
 * @since 16/4/27
 */
public class VehicleInfo extends BaseModel {

    public ObservableBoolean isSelected;
    public String  logoUrl;
    public String  brand;
    public String  description;

    public VehicleInfo(ObservableBoolean isSelected, String logoUrl, String brand, String description) {
        this.isSelected = isSelected;
        this.logoUrl = logoUrl;
        this.brand = brand;
        this.description = description;
    }

}
