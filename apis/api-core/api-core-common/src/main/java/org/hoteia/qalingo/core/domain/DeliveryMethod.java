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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

@Entity
@Table(name = "TECO_DELIVERY_METHOD")
public class DeliveryMethod extends AbstractEntity<DeliveryMethod> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6023814328491726235L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;
    
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "DELIVERY_TIME")
    private String deliveryTime;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_METHOD_ID")
    private Set<DeliveryMethodCountry> countries = new HashSet<DeliveryMethodCountry>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="DELIVERY_METHOD_ID")
    private Set<DeliveryMethodPrice> prices = new HashSet<DeliveryMethodPrice>(); 
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE }, targetEntity = org.hoteia.qalingo.core.domain.Warehouse.class)
    @JoinTable(name = "TECO_WAREHOUSE_DELIVERY_METHOD_REL", joinColumns = @JoinColumn(name = "DELIVERY_METHOD_ID"), inverseJoinColumns = @JoinColumn(name = "WAREHOUSE_ID"))
    private Set<Warehouse> warehouses = new HashSet<Warehouse>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public DeliveryMethod() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }
    
    public String getDeliveryTimeValue() {
        if(StringUtils.isNotEmpty(deliveryTime) && deliveryTime.contains(";")){
            String[] deliveryTimeValues = deliveryTime.split(";");
            return deliveryTimeValues[0];
        }
        return null;
    }
    
    public String getDeliveryTimeValueMin() {
        if(StringUtils.isNotEmpty(deliveryTime) && deliveryTime.contains(";")){
            String[] deliveryTimeValues = deliveryTime.split(";");
            if(StringUtils.isNotEmpty(deliveryTime) && deliveryTime.contains("-")){
                String[] deliveryTimeValueRanges = deliveryTimeValues[0].split("-");
                return deliveryTimeValueRanges[0];
            } else {
                return deliveryTimeValues[0];
            }
        }
        return null;
    }
    
    public String getDeliveryTimeValueMax() {
        if(StringUtils.isNotEmpty(deliveryTime) && deliveryTime.contains(";")){
            String[] deliveryTimeValues = deliveryTime.split(";");
            if(StringUtils.isNotEmpty(deliveryTime) && deliveryTime.contains("-")){
                String[] deliveryTimeValueRanges = deliveryTimeValues[0].split("-");
                return deliveryTimeValueRanges[1];
            } else {
                return deliveryTimeValues[0];
            }
        }
        return null;
    }

    public String getDeliveryTimeType() {
        if(StringUtils.isNotEmpty(deliveryTime) && deliveryTime.contains(";")){
            String[] deliveryTimeValues = deliveryTime.split(";");
            return deliveryTimeValues[1];
        }
        return null;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Set<DeliveryMethodCountry> getCountries() {
        return countries;
    }

    public void setCountries(Set<DeliveryMethodCountry> countries) {
        this.countries = countries;
    }

    public Set<DeliveryMethodPrice> getPrices() {
        return prices;
    }

    @Nullable
    public DeliveryMethodPrice getDeliveryMethodPrice(final Long currencyId){
        if(prices != null
                && Hibernate.isInitialized(prices)){
            for (DeliveryMethodPrice deliveryMethodPrice : prices) {
                if(deliveryMethodPrice.getCurrency() != null
                        && deliveryMethodPrice.getCurrency().getId().equals(currencyId) ) {
                    return deliveryMethodPrice;
                }
            }    
        }
        return null;
    }

    @Nullable
    public BigDecimal getPrice(final Long currencyId){
        DeliveryMethodPrice deliveryMethodPrice = getDeliveryMethodPrice(currencyId);
        if(deliveryMethodPrice != null){
            return deliveryMethodPrice.getPrice(); 
        }
        return null;
    }

    @Nullable
    public String getPriceWithStandardCurrencySign(final Long currencyId){
        DeliveryMethodPrice deliveryMethodPrice = getDeliveryMethodPrice(currencyId);
        if(deliveryMethodPrice != null){
            return deliveryMethodPrice.getPriceWithStandardCurrencySign(); 
        }
        return null;
    }
    
    public void setPrices(Set<DeliveryMethodPrice> prices) {
        this.prices = prices;
    }
    
    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }
    
    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
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
        DeliveryMethod other = (DeliveryMethod) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
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
        return "DeliveryMethod [id=" + id + ", version=" + version + ", code=" + code + ", name=" + name + ", description=" + description + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
                + "]";
    }

}