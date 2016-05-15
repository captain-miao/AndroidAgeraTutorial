package com.github.captain_miao.agera.tutorial.model;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.github.captain_miao.agera.tutorial.BR;

/**
 * @author YanLu
 * @since 16/4/27
 */
public class ObservableVehicleInfo implements Observable {

    private boolean isSelected;
    private String  logoUrl;
    private String  brand;
    private String  description;

    public ObservableVehicleInfo(boolean isSelected, String logoUrl, String brand, String description) {
        this.isSelected = isSelected;
        this.logoUrl = logoUrl;
        this.brand = brand;
        this.description = description;
    }

    public ObservableVehicleInfo(VehicleInfo vehicleInfo) {
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

    //for data binding Observable
    private transient PropertyChangeRegistry mCallbacks;
    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        if (mCallbacks == null) {
            mCallbacks = new PropertyChangeRegistry();
        }
        mCallbacks.add(onPropertyChangedCallback);
    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(onPropertyChangedCallback);
        }
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public synchronized void notifyChange() {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, 0, null);
        }
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, fieldId, null);
        }
    }
}
