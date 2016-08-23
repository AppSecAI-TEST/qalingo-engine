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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name="TECO_PRODUCT_SKU_CUSTOMER_COMMENT")
public class ProductSkuCustomerComment extends AbstractEntity<ProductSkuCustomerComment> implements DomainEntity {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 1424510557043858148L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
    @Column(name = "TITLE")
    private String title;
    
    @Column(name="COMMENT")
    private String comment;

	@Column(name="PRODUCT_SKU_CUSTOMER_COMMENT_ID")
	private Long productSkuCustomerCommentId;
	
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Customer.class)
    @JoinColumn(name = "CUSTOMER_ID", insertable = true, updatable = true)
    private Customer customer;

    @Column(name="MARKET_AREA_ID")
    private Long marketAreaId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.ProductSku.class)
    @JoinColumn(name = "PRODUCT_SKU_ID", insertable = true, updatable = true)
    private ProductSku productSku;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuCustomerComment.class)
    @JoinColumn(name="PRODUCT_SKU_CUSTOMER_COMMENT_ID")
	private Set<ProductSkuCustomerComment> customerComments = new HashSet<ProductSkuCustomerComment>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

    @Column(name="RATE")
    private Integer rate;

	public ProductSkuCustomerComment() {
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
        return title;
    }
	
	public void setTitle(String title) {
        this.title = title;
    }
	
	public String getComment() {
    	return comment;
    }

	public void setComment(String comment) {
    	this.comment = comment;
    }

	public Long getProductSkuCustomerCommentId() {
        return productSkuCustomerCommentId;
    }
	
	public void setProductSkuCustomerCommentId(Long productSkuCustomerCommentId) {
        this.productSkuCustomerCommentId = productSkuCustomerCommentId;
    }

	public Customer getCustomer() {
    	return customer;
    }

	public void setCustomer(Customer customer) {
    	this.customer = customer;
    }
	
	public Long getMarketAreaId() {
        return marketAreaId;
    }
	
	public void setMarketAreaId(Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }

	public ProductSku getProductSku() {
        return productSku;
    }
	
	public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }
	
	public Set<ProductSkuCustomerComment> getCustomerComments() {
    	return customerComments;
    }

	public void setCustomerComments(Set<ProductSkuCustomerComment> customerComments) {
    	this.customerComments = customerComments;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((marketAreaId == null) ? 0 : marketAreaId.hashCode());
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
        ProductSkuCustomerComment other = (ProductSkuCustomerComment) obj;
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
        if (marketAreaId == null) {
            if (other.marketAreaId != null)
                return false;
        } else if (!marketAreaId.equals(other.marketAreaId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductMarketingCustomerComment [id=" + id + ", comment=" + comment + ", productSkuCustomerCommentId=" + productSkuCustomerCommentId + ", marketAreaId=" + marketAreaId
                + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}