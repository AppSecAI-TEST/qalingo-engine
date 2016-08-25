/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.CustomerMarketArea;
import org.hoteia.qalingo.core.domain.CustomerOptin;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.mvc.form.CartForm;
import org.hoteia.qalingo.core.web.mvc.form.ContactForm;
import org.hoteia.qalingo.core.web.mvc.form.CreateAccountForm;
import org.hoteia.qalingo.core.web.mvc.form.CustomerAddressForm;
import org.hoteia.qalingo.core.web.mvc.form.CustomerCommentForm;
import org.hoteia.qalingo.core.web.mvc.form.CustomerContactForm;
import org.hoteia.qalingo.core.web.mvc.form.CustomerEditForm;
import org.hoteia.qalingo.core.web.mvc.form.FollowUsForm;
import org.hoteia.qalingo.core.web.mvc.form.PaymentForm;
import org.hoteia.qalingo.core.web.mvc.form.QuickSearchForm;
import org.hoteia.qalingo.core.web.mvc.form.SearchForm;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service("formFactory")
public class FormFactory {

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected UrlService urlService;
	
	public ContactForm buildContactForm(final RequestData requestData) throws Exception {
		final ContactForm contactUsForm = new ContactForm();
		String languageCode = requestData.getMarketAreaLocalization().getCode();
		if(languageCode.equals("en")) {
			contactUsForm.setCountry("US");
		} else if(languageCode.equals("fr")) {
			contactUsForm.setCountry("FR");
		}  else if(languageCode.equals("de")) {
			contactUsForm.setCountry("DE");
		}  else if(languageCode.equals("es")) {
			contactUsForm.setCountry("ES");
		}  else if(languageCode.equals("it")) {
			contactUsForm.setCountry("IT");
		}  else if(languageCode.equals("nl")) {
			contactUsForm.setCountry("NL");
		}  else if(languageCode.equals("pt")) {
			contactUsForm.setCountry("PT");
		} 
		
		return contactUsForm;
	}
	
	public SearchForm buildSearchForm(final RequestData requestData) throws Exception {
		final SearchForm searchForm = new SearchForm();
		return searchForm;
	}
	
	public QuickSearchForm buildQuickSearchForm(final RequestData requestData) throws Exception {
		final QuickSearchForm quickSearchForm = new QuickSearchForm();
		return quickSearchForm;
	}
	
	public FollowUsForm buildFollowUsForm(final RequestData requestData) throws Exception {
		final FollowUsForm followUsForm = new FollowUsForm();
		return followUsForm;
	}
	
	public CreateAccountForm buildCreateAccountForm(final RequestData requestData) throws Exception {
		final CreateAccountForm createAccountForm = new CreateAccountForm();
		String languageCode = requestData.getMarketAreaLocalization().getCode();
		if(languageCode.equals("en")) {
			createAccountForm.setCountryCode("US");
		} else if(languageCode.equals("fr")) {
			createAccountForm.setCountryCode("FR");
		}  else if(languageCode.equals("de")) {
			createAccountForm.setCountryCode("DE");
		}  else if(languageCode.equals("es")) {
			createAccountForm.setCountryCode("ES");
		}  else if(languageCode.equals("it")) {
			createAccountForm.setCountryCode("IT");
		}  else if(languageCode.equals("nl")) {
			createAccountForm.setCountryCode("NL");
		}  else if(languageCode.equals("pt")) {
			createAccountForm.setCountryCode("PT");
		} 
		
		return createAccountForm;
	}
	
	public CustomerEditForm buildCustomerEditForm(final RequestData requestData, final Customer customer) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		CustomerEditForm customerEditForm = new CustomerEditForm();
		if(customer != null){
			customerEditForm.setTitle(customer.getTitle());
			customerEditForm.setLastname(customer.getLastname());
			customerEditForm.setFirstname(customer.getFirstname());
			customerEditForm.setEmail(customer.getEmail());

			final CustomerMarketArea customerMarketContext = customer.getCurrentCustomerMarketArea(marketArea.getId());
			if(customerMarketContext != null){
				customerEditForm.setMobile(customerMarketContext.getMobile());
				customerEditForm.setPhone(customerMarketContext.getPhone());
				customerEditForm.setFax(customerMarketContext.getFax());
				CustomerOptin optinNewsletter = customerMarketContext.getOptins(CustomerOptin.OPTIN_TYPE_WWW_NEWSLETTER);
				if(optinNewsletter != null){
	                customerEditForm.setOptin(optinNewsletter.isActive());
				}
			}
		}
		return customerEditForm;
	}
	
	public CustomerAddressForm buildCustomerAddressForm(final RequestData requestData, final CustomerAddress customerAddress) throws Exception {
		final CustomerAddressForm customerAddressForm = new CustomerAddressForm();
		String languageCode = requestData.getMarketAreaLocalization().getCode();
		if(languageCode.equals("en")) {
			customerAddressForm.setCountryCode("US");
		} else if(languageCode.equals("fr")) {
			customerAddressForm.setCountryCode("FR");
		}  else if(languageCode.equals("de")) {
			customerAddressForm.setCountryCode("DE");
		}  else if(languageCode.equals("es")) {
			customerAddressForm.setCountryCode("ES");
		}  else if(languageCode.equals("it")) {
			customerAddressForm.setCountryCode("IT");
		}  else if(languageCode.equals("nl")) {
			customerAddressForm.setCountryCode("NL");
		}  else if(languageCode.equals("pt")) {
			customerAddressForm.setCountryCode("PT");
		} else if(languageCode.equals("ru")) {
            customerAddressForm.setCountryCode("RU");
        } 
		
		if(customerAddress != null){
            customerAddressForm.setId(customerAddress.getId().toString());
			customerAddressForm.setAddressName(customerAddress.getAddressName());
			customerAddressForm.setTitle(customerAddress.getTitle());
			customerAddressForm.setLastname(customerAddress.getLastname());
			customerAddressForm.setFirstname(customerAddress.getFirstname());
		    
			customerAddressForm.setAddress1(customerAddress.getAddress1());
			customerAddressForm.setAddress2(customerAddress.getAddress2());
			customerAddressForm.setAddressAdditionalInformation(customerAddress.getAddressAdditionalInformation());
			customerAddressForm.setPostalCode(customerAddress.getPostalCode());
			customerAddressForm.setCity(customerAddress.getCity());
			customerAddressForm.setStateCode(customerAddress.getStateCode());
			customerAddressForm.setAreaCode(customerAddress.getAreaCode());
			customerAddressForm.setCountryCode(customerAddress.getCountryCode());
		}
	    
		return customerAddressForm;
	}
	
	public CartForm buildCartForm(final RequestData requestData) throws Exception {
		final CartForm cartForm = new CartForm();
		Customer customer = requestData.getCustomer();
		if(customer != null) {
			 Set<CustomerAddress> addresses = customer.getAddresses();
			 for (Iterator<CustomerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
				CustomerAddress customerAddress = (CustomerAddress) iterator.next();
				if(customerAddress.isDefaultBilling()) {
					cartForm.setBillingAddressId(customerAddress.getId().toString());
				}
				if(customerAddress.isDefaultShipping()) {
					cartForm.setShippingAddressId(customerAddress.getId().toString());
				}
			}
		}
		return cartForm;
	}
	
	public PaymentForm buildPaymentForm(final RequestData requestData) throws Exception {
		final PaymentForm paymentForm = new PaymentForm();
		return paymentForm;
	}
	
    public CustomerCommentForm buildCustomerCommentForm(final RequestData requestData, final String objectCode) throws Exception {
        final CustomerCommentForm customerCommentForm = new CustomerCommentForm();
        customerCommentForm.setObjectCode(objectCode);
        final Customer customer = requestData.getCustomer();
        if(customer != null){
            if(StringUtils.isNotEmpty(customer.getScreenName())){
                customerCommentForm.setName(customer.getScreenName());
            } else {
                customerCommentForm.setName(customer.getLastname() + " " + customer.getFirstname());
            }
            customerCommentForm.setEmail(customer.getEmail());
        }
        
        return customerCommentForm;
    }
    
    public CustomerContactForm buildCustomerContactForm(final RequestData requestData, final String objectCode) throws Exception {
        final CustomerContactForm retailerContactForm = new CustomerContactForm();
        retailerContactForm.setObjectCode(objectCode);
        
        String languageCode = requestData.getMarketAreaLocalization().getCode();
        if(languageCode.equals("en")) {
            retailerContactForm.setCountry("US");
        } else if(languageCode.equals("fr")) {
            retailerContactForm.setCountry("FR");
        }  else if(languageCode.equals("de")) {
            retailerContactForm.setCountry("DE");
        }  else if(languageCode.equals("es")) {
            retailerContactForm.setCountry("ES");
        }  else if(languageCode.equals("it")) {
            retailerContactForm.setCountry("IT");
        }  else if(languageCode.equals("nl")) {
            retailerContactForm.setCountry("NL");
        }  else if(languageCode.equals("pt")) {
            retailerContactForm.setCountry("PT");
        } 
        
        return retailerContactForm;
    }
    
}