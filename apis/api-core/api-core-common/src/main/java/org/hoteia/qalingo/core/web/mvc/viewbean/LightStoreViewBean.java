package org.hoteia.qalingo.core.web.mvc.viewbean;

import org.hoteia.qalingo.core.util.CoreUtil;

public class LightStoreViewBean {

    protected String code;
    protected String name;
    protected String i18nName;
    protected String i18nDescription;
    protected String i18nShortDescription;
    protected String longitude;
    protected String latitude;
    protected String addressOnLine;
    protected String countryCode;
    protected String phone;
    protected String fax;
    protected String website;
    protected String detailsUrl;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddressOnLine() {
        return addressOnLine;
    }

    public void setAddressOnLine(String addressOnLine) {
        this.addressOnLine = addressOnLine;
    }

    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getI18nName() {
        return i18nName;
    }

    public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
    }

    public String getI18nDescription() {
        return i18nDescription;
    }

    public void setI18nDescription(String i18nDescription) {
        this.i18nDescription = i18nDescription;
    }

    public String getI18nShortDescription() {
        return i18nShortDescription;
    }

    public void setI18nShortDescription(String i18nShortDescription) {
        this.i18nShortDescription = i18nShortDescription;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFormatedPhone() {
        return CoreUtil.formatNationalPhone(phone, countryCode);
    }
    
    public String getPhoneToCall() {
        return CoreUtil.encodePhone(phone, countryCode);
    }
    
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getFormatedFax() {
        return CoreUtil.formatNationalPhone(fax, countryCode);
    }
    
    public String getFaxToCall() {
        return CoreUtil.encodePhone(fax, countryCode);
    }
    
    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
    
}