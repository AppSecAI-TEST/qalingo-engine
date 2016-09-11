/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.CustomerAttribute;
import org.hoteia.qalingo.core.domain.CustomerCredential;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.hoteia.qalingo.core.domain.CustomerMarketArea;
import org.hoteia.qalingo.core.domain.CustomerToken;
import org.hoteia.qalingo.core.exception.CustomerAttributeException;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.customer.FetchPlanGraphCustomer;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.springframework.stereotype.Repository;

@Repository("customerDao")
public class CustomerDao extends AbstractGenericDao {

	public Customer getCustomerById(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", customerId));
        Customer customer = (Customer) criteria.uniqueResult();
        if(customer != null){
            customer.setFetchPlan(fetchPlan);
        }
        return customer;
	}
	
	public Customer getCustomerByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", handleCodeValue(code)));
        Customer customer = (Customer) criteria.uniqueResult();
        if(customer != null){
            customer.setFetchPlan(fetchPlan);
        }
        return customer;
	}
	
	public Customer getCustomerByPermalink(final String permalink, Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("permalink", permalink));
        Customer customer = (Customer) criteria.uniqueResult();
        if(customer != null){
            customer.setFetchPlan(fetchPlan);
        }
        return customer;
	}

	public Customer getCustomerByLoginOrEmail(final String usernameOrEmail, Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.or(Restrictions.eq("login", usernameOrEmail), Restrictions.eq("email", usernameOrEmail)));
        Customer customer = (Customer) criteria.uniqueResult();
        if(customer != null){
            customer.setFetchPlan(fetchPlan);
        }
        return customer;
	}
	
	public List<Customer> findCustomers(Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        handleSpecificFetchMode(criteria, params);
        
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));

        @SuppressWarnings("unchecked")
        List<Customer> customers = criteria.list();
		return customers;
	}
	
	public Customer saveOrUpdateCustomer(final Customer customer) throws Exception {
		if(customer.getDateCreate() == null){
			customer.setDateCreate(new Date());
		}
		customer.setDateUpdate(new Date());
        if(StringUtils.isEmpty(customer.getCode())){
            customer.setCode(CoreUtil.generateEntityCode());
        }

		if(customer.getPermalink() == null){
			customer.setPermalink(UUID.randomUUID().toString());
		}

        for (CustomerAttribute customerAttribute : customer.getAttributes()) {
            // ATTRIBUTE DEFINITION CAN'T BE NULL
            if (customerAttribute.getAttributeDefinition() == null) {
                throw new CustomerAttributeException("Attribute Definition can't be null!");
            }
            // MARKET AREA CAN'T BE NULL IF ATTRIBUTE IS NOT GLOBAL
            if (!customerAttribute.getAttributeDefinition().isGlobal()
                    && customerAttribute.getMarketAreaId() == null) {
                throw new CustomerAttributeException("Market Area can't be null if Attribute is not global!");
            }
        }
        if (customer.getId() != null) {
            if(em.contains(customer)){
                em.refresh(customer);
            }
            Customer mergedCustomer = em.merge(customer);
            em.flush();
            return mergedCustomer;
        } else {
            em.persist(customer);
            return customer;
        }
	}
	
    public CustomerMarketArea saveOrUpdateCustomerMarketArea(final CustomerMarketArea customerMarketArea) throws Exception {
        if (customerMarketArea.getDateCreate() == null) {
            customerMarketArea.setDateCreate(new Date());
        }
        customerMarketArea.setDateUpdate(new Date());
        if (customerMarketArea.getId() != null) {
            if (em.contains(customerMarketArea)) {
                em.refresh(customerMarketArea);
            }
            CustomerMarketArea mergedCustomerMarketArea = em.merge(customerMarketArea);
            em.flush();
            return mergedCustomerMarketArea;
        } else {
            em.persist(customerMarketArea);
            return customerMarketArea;
        }
    }

	public void deleteCustomer(final Customer customer) {
		em.remove(em.contains(customer) ? customer : em.merge(customer));
	}
	
    // CUSTOMER ADDRESS

    public CustomerAddress getCustomerAddressById(final Long customerAddressId, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerAddress.class);
        criteria.add(Restrictions.eq("id", customerAddressId));
        CustomerAddress customerAddress = (CustomerAddress) criteria.uniqueResult();
        return customerAddress;
    }
    
    // CUSTOMER GROUP

    public CustomerGroup getCustomerGroupById(final Long customerGroupId, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerGroup.class);

        FetchPlan fetchPlan = handleSpecificCustomerGroupFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", customerGroupId));
        CustomerGroup customerGroup = (CustomerGroup) criteria.uniqueResult();
        if (customerGroup != null) {
            customerGroup.setFetchPlan(fetchPlan);
        }
        return customerGroup;
    }

    public CustomerGroup getCustomerGroupByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerGroup.class);

        FetchPlan fetchPlan = handleSpecificCustomerGroupFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", handleCodeValue(code)));
        CustomerGroup customerGroup = (CustomerGroup) criteria.uniqueResult();
        if (customerGroup != null) {
            customerGroup.setFetchPlan(fetchPlan);
        }
        return customerGroup;
    }

    public CustomerGroup saveOrUpdateCustomerGroup(CustomerGroup customerGroup) {
        if (customerGroup.getDateCreate() == null) {
            customerGroup.setDateCreate(new Date());
        }
        customerGroup.setDateUpdate(new Date());
        if (customerGroup.getId() != null) {
            if (em.contains(customerGroup)) {
                em.refresh(customerGroup);
            }
            CustomerGroup mergedCustomerGroup = em.merge(customerGroup);
            em.flush();
            return mergedCustomerGroup;
        } else {
            em.persist(customerGroup);
            return customerGroup;
        }
    }

    public void deleteCustomerGroup(CustomerGroup customerGroup) {
        em.remove(em.contains(customerGroup) ? customerGroup : em.merge(customerGroup));
    }

    protected FetchPlan handleSpecificCustomerGroupFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCustomer.defaultCustomerGroupFetchPlan());
        }
    }
	    
	// CREDENTIAL
	
	public CustomerCredential saveOrUpdateCustomerCredential(final CustomerCredential customerCredential) throws Exception {
		if(customerCredential.getDateCreate() == null){
			customerCredential.setDateCreate(new Date());
			if(StringUtils.isEmpty(customerCredential.getResetToken())){
				customerCredential.setResetToken(UUID.randomUUID().toString());
			}
		}
		customerCredential.setDateUpdate(new Date());
        if (customerCredential.getId() != null) {
            if(em.contains(customerCredential)){
                em.refresh(customerCredential);
            }
            CustomerCredential mergedCustomerCredential = em.merge(customerCredential);
            em.flush();
            return mergedCustomerCredential;
        } else {
            em.persist(customerCredential);
            return customerCredential;
        }
	}
	
    // OAUTH
    
    public CustomerToken saveOrUpdateCustomerToken(final CustomerToken customerToken) throws Exception {
        if(customerToken.getDateCreate() == null){
            customerToken.setDateCreate(new Date());
        }
        customerToken.setDateUpdate(new Date());
        if (customerToken.getId() != null) {
            if(em.contains(customerToken)){
                em.refresh(customerToken);
            }
            CustomerToken mergedCustomerToken = em.merge(customerToken);
            em.flush();
            return mergedCustomerToken;
        } else {
            em.persist(customerToken);
            return customerToken;
        }
    }
    
    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCustomer.fullCustomerFetchPlan());
        }
    }

}