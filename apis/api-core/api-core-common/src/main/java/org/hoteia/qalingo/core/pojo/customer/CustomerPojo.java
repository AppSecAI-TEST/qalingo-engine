/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hoteia.qalingo.core.domain.enumtype.CustomerNetworkOrigin;
import org.hoteia.qalingo.core.domain.enumtype.CustomerPlatformOrigin;
import org.hoteia.qalingo.core.pojo.AbstractPojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPojo extends AbstractPojo {

    private Long id;
    private int version;
    private String login;
    private String title;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String defaultLocale;
    private String birthday;
    private boolean active;
    private boolean guest;
    private Date dateCreate;
    private Date dateUpdate;
    private CustomerPlatformOrigin platformOrigin;
    private CustomerNetworkOrigin networkOrigin;
    private CustomerOrderAuditPojo customerOrderAudit;

    private List<CustomerAddressPojo> addresses = new ArrayList<CustomerAddressPojo>();
    private List<CustomerConnectionLogPojo> connectionLogs = new ArrayList<CustomerConnectionLogPojo>();
    private List<CustomerMarketAreaPojo> customerMarketAreas = new ArrayList<CustomerMarketAreaPojo>();
    private List<CustomerAttributePojo> customerAttributes = new ArrayList<CustomerAttributePojo>();
    private List<CustomerTokenPojo> tokens = new ArrayList<CustomerTokenPojo>();
    private List<CustomerGroupPojo> customerGroups = new ArrayList<CustomerGroupPojo>();

    public CustomerPojo() {}

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(final String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getBirthday() {
        return birthday;
    }
    
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(final Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(final Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public CustomerPlatformOrigin getPlatformOrigin() {
        return platformOrigin;
    }

    public void setPlatformOrigin(final CustomerPlatformOrigin platformOrigin) {
        this.platformOrigin = platformOrigin;
    }

    public CustomerNetworkOrigin getNetworkOrigin() {
        return networkOrigin;
    }

    public void setNetworkOrigin(final CustomerNetworkOrigin networkOrigin) {
        this.networkOrigin = networkOrigin;
    }

    public CustomerOrderAuditPojo getCustomerOrderAudit() {
        return customerOrderAudit;
    }

    public void setCustomerOrderAudit(CustomerOrderAuditPojo customerOrderAudit) {
        this.customerOrderAudit = customerOrderAudit;
    }

    public List<CustomerAddressPojo> getAddresses() {
        return addresses;
    }

    public void setAddresses(final List<CustomerAddressPojo> addresses) {
        this.addresses = addresses;
    }

    public List<CustomerConnectionLogPojo> getConnectionLogs() {
        return connectionLogs;
    }

    public void setConnectionLogs(final List<CustomerConnectionLogPojo> connectionLogs) {
        this.connectionLogs = connectionLogs;
    }

    public List<CustomerMarketAreaPojo> getCustomerMarketAreas() {
        return customerMarketAreas;
    }

    public void setCustomerMarketAreas(final List<CustomerMarketAreaPojo> customerMarketAreas) {
        this.customerMarketAreas = customerMarketAreas;
    }

    public List<CustomerAttributePojo> getCustomerAttributes() {
        return customerAttributes;
    }

    public void setCustomerAttributes(final List<CustomerAttributePojo> customerAttributes) {
        this.customerAttributes = customerAttributes;
    }

    public List<CustomerGroupPojo> getCustomerGroups() {
        return customerGroups;
    }

    public void setCustomerGroups(final List<CustomerGroupPojo> customerGroups) {
        this.customerGroups = customerGroups;
    }

    public List<CustomerTokenPojo> getTokens() {
        return tokens;
    }
    
    public void setTokens(List<CustomerTokenPojo> tokens) {
        this.tokens = tokens;
    }
    
}