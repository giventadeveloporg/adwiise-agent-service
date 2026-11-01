package com.adwiiseagent.service.dto;

import java.io.Serializable;

/** Pack plus the size band used to pick a retail billing plan. */
public class RetailSizeResolutionDTO implements Serializable {

    private String retailerTypeCode;
    private String sizeBandCode;
    private long locationCount;
    private boolean overridden;
    private String billingProduct;
    private String billingCode;

    public String getRetailerTypeCode() {
        return retailerTypeCode;
    }

    public void setRetailerTypeCode(String retailerTypeCode) {
        this.retailerTypeCode = retailerTypeCode;
    }

    public String getSizeBandCode() {
        return sizeBandCode;
    }

    public void setSizeBandCode(String sizeBandCode) {
        this.sizeBandCode = sizeBandCode;
    }

    public long getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(long locationCount) {
        this.locationCount = locationCount;
    }

    public boolean isOverridden() {
        return overridden;
    }

    public void setOverridden(boolean overridden) {
        this.overridden = overridden;
    }

    public String getBillingProduct() {
        return billingProduct;
    }

    public void setBillingProduct(String billingProduct) {
        this.billingProduct = billingProduct;
    }

    public String getBillingCode() {
        return billingCode;
    }

    public void setBillingCode(String billingCode) {
        this.billingCode = billingCode;
    }
}
