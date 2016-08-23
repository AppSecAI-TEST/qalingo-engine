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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name="TECO_CUSTOMER_CONNECTION_LOG")
public class CustomerConnectionLog extends AbstractEntity<CustomerConnectionLog> implements DomainEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 775786997410998065L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOGIN_DATE")
	private Date loginDate;
	
	@Column(name="APP_CODE")
	private String appCode;
	
	@Column(name="HOST")
	private String host;
	
    @Column(name = "PUBLIC_ADDRESS")
    private String publicAddress;

    @Column(name = "PRIVATE_ADDRESS")
    private String privateAddress;
	    
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	
	public CustomerConnectionLog() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public String getAppCode() {
        return appCode;
    }
	
	public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
    public String getPublicAddress() {
        return publicAddress;
    }
    
    public String getAddress() {
        return getPublicAddress();
    }
    
    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }
    
    public String getPrivateAddress() {
        return privateAddress;
    }
    
    public void setPrivateAddress(String privateAddress) {
        this.privateAddress = privateAddress;
    }
    
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appCode == null) ? 0 : appCode.hashCode());
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
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
        CustomerConnectionLog other = (CustomerConnectionLog) obj;
        if (appCode == null) {
            if (other.appCode != null)
                return false;
        } else if (!appCode.equals(other.appCode))
            return false;
        if (customerId == null) {
            if (other.customerId != null)
                return false;
        } else if (!customerId.equals(other.customerId))
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
        return "CustomerConnectionLog [id=" + id + ", loginDate=" + loginDate + ", appCode=" + appCode + ", host=" + host + ", publicAddress=" + publicAddress + ", privateAddress=" + privateAddress
                + ", customerId=" + customerId + "]";
    }

}