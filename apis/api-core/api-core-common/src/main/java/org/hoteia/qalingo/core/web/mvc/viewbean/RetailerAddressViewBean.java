/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.util.CoreUtil;

public class RetailerAddressViewBean extends AbstractAddressViewBean {

	/**
	 * Generated UID
	 */
    protected static final long serialVersionUID = -359258454675030384L;

    protected String phone;
    protected String mobile;
    protected String fax;
    protected String email;
    protected String website;

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
    
	public String getMobile() {
    	return mobile;
    }

	public void setMobile(String mobile) {
    	this.mobile = mobile;
    }
	
    public String getFormatedMobile() {
        return CoreUtil.formatNationalPhone(mobile, countryCode);
    }
    
    public String getMobileToCall() {
        return CoreUtil.encodePhone(mobile, countryCode);
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
    
	public String getEmail() {
    	return email;
    }

	public void setEmail(String email) {
    	this.email = email;
    }
	
	public String getWebsite() {
	    return website;
    }
	
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public String getWebsiteWithoutHttp() {
        if (StringUtils.isNotEmpty(website)
                && website.contains("http")) {
            if(website.endsWith("/")){
                website = website.substring(0, website.length() - 1);
            }
            return website.replace("http://", "");
        }
        return website;
    }

    public String getWebsiteHttpUrl() {
        if (StringUtils.isNotEmpty(website)
                && !website.contains("http")) {
            return "http://" + website;
        }
        return website;
    }
    
}