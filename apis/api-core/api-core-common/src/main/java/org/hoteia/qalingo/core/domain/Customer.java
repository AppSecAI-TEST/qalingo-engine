/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;

@Entity
@Table(name="TECO_CUSTOMER")
public class Customer extends AbstractEntity<Customer> {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6596549095870442990L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false) // , columnDefinition = "int(11) default 1"
    private int version;

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "LOGIN", unique = true)
    private String login;

    @Column(name = "AVATAR_IMG")
    private String avatarImg;

    @Column(name = "PERMALINK")
    private String permalink;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DEFAULT_LOCALE")
    private String defaultLocale;

    @Column(name = "IS_ACTIVE", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean active = false;

    @Column(name = "VALIDATED", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean validated = false;

    @Column(name = "IS_ANONYMOUS", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean anonymous = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CustomerCredential.class)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerCredential> credentials = new HashSet<CustomerCredential>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CustomerAddress.class)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();

    @Column(name = "DEFAULT_SHIPPING_ADDRESS")
    private Long defaultShippingAddressId;

    @Column(name = "DEFAULT_BILLING_ADDRESS")
    private Long defaultBillingAddressId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CustomerConnectionLog.class)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerConnectionLog> connectionLogs = new HashSet<CustomerConnectionLog>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CustomerMarketArea.class)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerMarketArea> customerMarketAreas = new HashSet<CustomerMarketArea>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CustomerAttribute.class)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerAttribute> attributes = new HashSet<CustomerAttribute>(); 
	
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE }, targetEntity = org.hoteia.qalingo.core.domain.CustomerGroup.class)
    @JoinTable(name = "TECO_CUSTOMER_GROUP_REL", joinColumns = @JoinColumn(name = "CUSTOMER_ID"), inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    private Set<CustomerGroup> groups = new HashSet<CustomerGroup>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CustomerToken.class)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerToken> tokens = new HashSet<CustomerToken>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CustomerPayment.class)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerPayment> payments = new HashSet<CustomerPayment>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CustomerOrderAudit.class)
    @JoinColumn(name = "CUSTOMER_ORDER_AUDIT")
    private CustomerOrderAudit customerOrderAudit;

    @Column(name = "PLATFORM_ORIGN")
    private String platformOrigin;

    @Column(name = "NETWORK_ORIGN")
    private String networkOrigin;

    @Column(name = "MARKET_AREA_ORIGN")
    private String marketAreaOrigin;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;
	
	public Customer() {
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
	    this.code = code;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getAvatarImg() {
	    return avatarImg;
    }
	
	public void setAvatarImg(String avatarImg) {
	    this.avatarImg = avatarImg;
    }
	
	public String getPermalink() {
	    return permalink;
    }
	
	public void setPermalink(String permalink) {
	    this.permalink = permalink;
    }
	
	public String getGender() {
	    return gender;
    }
	
	public void setGender(String gender) {
	    this.gender = gender;
    }
	
    public String getTitle() {
		return title;
	}
    
    public void setTitle(String title) {
		this.title = title;
	}
    
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDefaultLocale() {
		return defaultLocale;
	}
	
	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isValidated() {
    	return validated;
    }

	public void setValidated(boolean validated) {
    	this.validated = validated;
    }
	
	public boolean isAnonymous() {
    	return anonymous;
    }

	public void setAnonymous(boolean anonymous) {
    	this.anonymous = anonymous;
    }

	public Set<CustomerCredential> getCredentials() {
	    return credentials;
    }
	
	public CustomerCredential getCurrentCredential() {
		if(credentials != null
		        && Hibernate.isInitialized(credentials)){
			List<CustomerCredential> sortedObjects = new LinkedList<CustomerCredential>(credentials);
			Collections.sort(sortedObjects, new Comparator<CustomerCredential>() {
				@Override
				public int compare(CustomerCredential o1, CustomerCredential o2) {
					if(o1 != null
							&& o2 != null){
						Date order1 = o1.getDateCreate();
						Date order2 = o2.getDateCreate();
						if(order1 != null
								&& order2 != null){
							return order1.compareTo(order2);				
						} else {
							return o1.getId().compareTo(o2.getId());	
						}
					}
					return 0;
				}
			});
			if(sortedObjects != null && sortedObjects.size() > 0){
	            return sortedObjects.get(0);
			}
		}
	    return null;
    }
	
	public void setCredentials(Set<CustomerCredential> credentials) {
	    this.credentials = credentials;
    }
	
    public Set<CustomerAddress> getAddresses() {
        return addresses;
    }

    public boolean hasAddress() {
        if (addresses != null 
                && Hibernate.isInitialized(addresses) 
                && !addresses.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean hasAddressForThisCountry(final String countryCode) {
        if (addresses != null
                && Hibernate.isInitialized(addresses)) {
            for (Iterator<CustomerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
                CustomerAddress customerAddress = (CustomerAddress) iterator.next();
                if (customerAddress.getCountryCode().equals(countryCode)) {
                    return true;
                }
            }
        }
        return false;
    }

	public CustomerAddress getAddress(final Long customerAddressId) {
		CustomerAddress customerAddressToReturn = null;
		if(addresses != null
                && Hibernate.isInitialized(addresses)){
	        for (Iterator<CustomerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
	            CustomerAddress customerAddress = (CustomerAddress) iterator.next();
	            if(customerAddress.getId().equals(customerAddressId)) {
	                customerAddressToReturn = customerAddress;
	            }
	        }
		}
		return customerAddressToReturn;
	}
	
	public void setAddresses(Set<CustomerAddress> addresses) {
		this.addresses = addresses;
	}
	
	public Long getDefaultShippingAddressId() {
        if(defaultShippingAddressId != null){
            return defaultShippingAddressId;
        } else {
            if(addresses != null
                    && Hibernate.isInitialized(addresses)
                    && addresses.size() > 0){
                return getAddresses().iterator().next().getId();
            }
        }
        return null;
    }
	
	public void setDefaultShippingAddressId(Long defaultShippingAddressId) {
        this.defaultShippingAddressId = defaultShippingAddressId;
    }
	
	public Long getDefaultBillingAddressId() {
	    if(defaultBillingAddressId != null){
	        return defaultBillingAddressId;
	    } else {
            if(addresses != null
                    && Hibernate.isInitialized(addresses)
                    && addresses.size() > 0){
	            return getAddresses().iterator().next().getId();
	        }
	    }
	    return null;
    }
	
	public void setDefaultBillingAddressId(Long defaultBillingAddressId) {
        this.defaultBillingAddressId = defaultBillingAddressId;
    }
	
	public Set<CustomerConnectionLog> getConnectionLogs() {
		return connectionLogs;
	}
	
    public List<CustomerConnectionLog> getSortedConnectionLogs() {
        List<CustomerConnectionLog> sortedConnectionLogs = null;
        if (connectionLogs != null 
                && Hibernate.isInitialized(connectionLogs)) {
            sortedConnectionLogs = new LinkedList<CustomerConnectionLog>(connectionLogs);
            Collections.sort(sortedConnectionLogs, new Comparator<CustomerConnectionLog>() {
                @Override
                public int compare(CustomerConnectionLog o1, CustomerConnectionLog o2) {
                    if (o1 != null && o1.getLoginDate() != null && o2 != null && o2.getLoginDate() != null) {
                        return o1.getLoginDate().compareTo(o2.getLoginDate());
                    }
                    return 0;
                }
            });
        }
        return sortedConnectionLogs;
    }
    
	public void setConnectionLogs(Set<CustomerConnectionLog> connectionLogs) {
		this.connectionLogs = connectionLogs;
	}
	
	public Set<CustomerMarketArea> getCustomerMarketAreas() {
		return customerMarketAreas;
	}
	
	public CustomerMarketArea getCurrentCustomerMarketArea(Long marketAreaId) {
		CustomerMarketArea currentCustomerMarketArea = null;
		if(customerMarketAreas != null
		        && Hibernate.isInitialized(customerMarketAreas)) {
			for (Iterator<CustomerMarketArea> iterator = customerMarketAreas.iterator(); iterator.hasNext();) {
				CustomerMarketArea customerMarketArea = (CustomerMarketArea) iterator.next();
				if(customerMarketArea.getMarketAreaId().equals(marketAreaId)) {
					currentCustomerMarketArea = customerMarketArea;
				}
			}
		}
		return currentCustomerMarketArea;
	}
	
	public void setCustomerMarketAreas(Set<CustomerMarketArea> customerMarketAreas) {
		this.customerMarketAreas = customerMarketAreas;
	}
	
	public Set<CustomerAttribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Set<CustomerAttribute> attributes) {
		this.attributes = attributes;
	}
	
    public Set<CustomerGroup> getGroups() {
        return groups;
    }
	
	public void setGroups(Set<CustomerGroup> groups) {
		this.groups = groups;
	}
	
	public Set<CustomerToken> getTokens() {
        return tokens;
    }
	
    public CustomerToken getToken(String token, String tokenType) {
        CustomerToken customerToken = getToken(tokenType);
        if(customerToken != null 
                && token.equals(customerToken.getToken())){
            return customerToken;
        }
        return null;
    }
    
    public CustomerToken getToken(String tokenType) {
        if(tokens != null 
                && Hibernate.isInitialized(tokens)){
            for (Iterator<CustomerToken> iterator = tokens.iterator(); iterator.hasNext();) {
                CustomerToken token = (CustomerToken) iterator.next();
                if(tokenType.equals(token.getType())){
                    return token;
                }
            }
        }
        return null;
    }
    
	public void setTokens(Set<CustomerToken> tokens) {
        this.tokens = tokens;
    }
	
	public Set<CustomerPayment> getPaymentInformations() {
        return payments;
    }
	
	public void setPaymentInformations(Set<CustomerPayment> paymentInformations) {
        this.payments = paymentInformations;
    }
	
	public CustomerOrderAudit getCustomerOrderAudit() {
	    return customerOrderAudit;
    }
	
	public void setCustomerOrderAudit(CustomerOrderAudit customerOrderAudit) {
	    this.customerOrderAudit = customerOrderAudit;
    }
	
	public String getPlatformOrigin() {
    	return platformOrigin;
    }

	public void setPlatformOrigin(String platformOrigin) {
    	this.platformOrigin = platformOrigin;
    }

	public String getNetworkOrigin() {
    	return networkOrigin;
    }

	public void setNetworkOrigin(String networkOrigin) {
    	this.networkOrigin = networkOrigin;
    }

	public String getMarketAreaOrigin() {
        return marketAreaOrigin;
    }
	
	public void setMarketAreaOrigin(String marketAreaOrigin) {
        this.marketAreaOrigin = marketAreaOrigin;
    }
	
	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
	public List<CustomerRole> getRoles() {
		List<CustomerRole> roles = null;
		Set<CustomerGroup> customerGroups = getGroups();
		if(customerGroups != null
		        && Hibernate.isInitialized(customerGroups)){
	        roles = new ArrayList<CustomerRole>();
	        Iterator<CustomerGroup> it = customerGroups.iterator();
	        while (it.hasNext()) {
	            CustomerGroup customerGroup = (CustomerGroup) it.next();
	            roles.addAll(customerGroup.getRoles());
	        }
		}
		return roles;
	}
	
	// Attributes
	
	public CustomerAttribute getCustomerAttribute(String attributeCode) {
		return getCustomerAttribute(attributeCode, null, null);
	}
	
	public CustomerAttribute getCustomerAttribute(String attributeCode, String localizationCode) {
		return getCustomerAttribute(attributeCode, null, localizationCode);
	}
	
	public CustomerAttribute getCustomerAttribute(String attributeCode, Long marketAreaId) {
		return getCustomerAttribute(attributeCode, marketAreaId, null);
	}
	
	public CustomerAttribute getCustomerAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		CustomerAttribute customerAttributeToReturn = null;
		if(attributes != null && Hibernate.isInitialized(attributes)) {
			List<CustomerAttribute> customerAttributesFilter = new ArrayList<CustomerAttribute>();
			for (CustomerAttribute customerAttribute : attributes) {
				AttributeDefinition attributeDefinition = customerAttribute.getAttributeDefinition();
				if (attributeDefinition != null && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					customerAttributesFilter.add(customerAttribute);
				}
			}
			if(marketAreaId != null) {
				for (Iterator<CustomerAttribute> iterator = customerAttributesFilter.iterator(); iterator.hasNext();) {
					CustomerAttribute customerAttribute = iterator.next();
					AttributeDefinition attributeDefinition = customerAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						if(customerAttribute.getMarketAreaId() != null && BooleanUtils.negate(customerAttribute.getMarketAreaId().equals(marketAreaId))){
							iterator.remove();
						}
					}
				}
				if(customerAttributesFilter.size() == 0){
					// TODO : throw error ?
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<CustomerAttribute> iterator = customerAttributesFilter.iterator(); iterator.hasNext();) {
					CustomerAttribute customerAttribute = iterator.next();
					AttributeDefinition attributeDefinition = customerAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						String attributeLocalizationCode = customerAttribute.getLocalizationCode();
						if(StringUtils.isNotEmpty(attributeLocalizationCode) && BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
							iterator.remove();
						}
					}
				}
				if(customerAttributesFilter.size() == 0){
					// TODO : throw error ?

					for (CustomerAttribute customerAttribute : attributes) {
						// TODO : get a default locale code from setting database ?

						if (customerAttribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)) {
							customerAttributeToReturn = customerAttribute;
						}
					}
					
				}
			}
			if(customerAttributesFilter.size() == 1){
				customerAttributeToReturn = customerAttributesFilter.get(0);
			} else {
				// TODO : throw error ?
			}
		}
		return customerAttributeToReturn;
	}
	
	public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
		CustomerAttribute customerAttribute = getCustomerAttribute(attributeCode, marketAreaId, localizationCode);
		if(customerAttribute != null) {
			return customerAttribute.getValue();
		}
		return null;
	}
	
	public String getScreenName() {
        String screenName = (String) getValue(CustomerAttribute.CUSTOMER_ATTRIBUTE_SCREENAME, null, null);
        if (StringUtils.isEmpty(screenName)) {
            screenName = getFirstname();
        }
        if (StringUtils.isEmpty(screenName)) {
            screenName = StringUtils.substring(getLastname(), 0, 1).toUpperCase() + ".";
        }
        return screenName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", version=" + version + ", code=" + code + ", login=" + login + ", avatarImg=" + avatarImg + ", permalink=" + permalink + ", gender=" + gender + ", title="
                + title + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password + ", defaultLocale=" + defaultLocale + ", active=" + active
                + ", validated=" + validated + ", anonymous=" + anonymous + ", defaultShippingAddressId=" + defaultShippingAddressId
                + ", defaultBillingAddressId=" + defaultBillingAddressId + ", platformOrigin=" + platformOrigin + ", networkOrigin=" + networkOrigin + ", dateCreate=" + dateCreate + ", dateUpdate="
                + dateUpdate + "]";
    }


}