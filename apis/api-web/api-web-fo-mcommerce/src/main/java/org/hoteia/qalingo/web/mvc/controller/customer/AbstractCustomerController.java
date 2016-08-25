/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ValueBean;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 
 */
public abstract class AbstractCustomerController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @ModelAttribute(ModelConstants.TITLES)
    public List<ValueBean> getTitles(HttpServletRequest request) throws Exception {
		List<ValueBean> titlesValues = new ArrayList<ValueBean>();
		try {
	        final RequestData requestData = requestUtil.getRequestData(request);
	        final Locale locale = requestData.getLocale();
	        
			final Map<String, String> titles = referentialDataService.getTitlesByLocale(locale);
			if(titles != null){
				Set<String> titlesKey = titles.keySet();
				for (final String titleKey : titlesKey) {
					titlesValues.add(new ValueBean(titleKey.replace(Constants.TITLE_MESSAGE_PREFIX, ""), titles.get(titleKey)));
				}
				Collections.sort(titlesValues, new Comparator<ValueBean>() {
					@Override
					public int compare(ValueBean o1, ValueBean o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				});
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return titlesValues;
    }
    
    @ModelAttribute(ModelConstants.COUNTRIES)
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
		Localization currentMarketAreaLocalization = requestUtil.getCurrentMarketAreaLocalization(request);
		return getCountries(currentMarketAreaLocalization.getLocale());
    }
    
    @ModelAttribute(ModelConstants.CUSTOMER_DETAILS_LINKS_VIEW_BEAN)
    public List<MenuViewBean> getCutomerMenus(HttpServletRequest request) throws Exception {
    	return frontofficeViewBeanFactory.buildListViewBeanCutomerMenu(requestUtil.getRequestData(request));
    }

}