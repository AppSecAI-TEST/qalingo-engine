/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.security;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("loginController")
public class LoginController extends AbstractMCommerceController {

    @RequestMapping(FoUrls.LOGIN_URL)
    public ModelAndView login(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.LOGIN.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

        // SANITY CHECK: Customer logged
        if (securityUtil.isAuthenticated()) {
            final String url = urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(url));
        }

        // SANITY CHECK : Param from spring-security
        String error = request.getParameter(RequestConstants.REQUEST_PARAMETER_AUTH_ERROR);
        if (BooleanUtils.toBoolean(error)) {
            model.addAttribute(ModelConstants.AUTH_HAS_FAIL, BooleanUtils.toBoolean(error));
            model.addAttribute(ModelConstants.AUTH_ERROR_MESSAGE, getCommonMessage(ScopeCommonMessage.AUTH, "login_or_password_are_wrong", locale));
        }
        
        overrideDefaultPageTitle(request, modelAndView, FoUrls.LOGIN.getKey());
        
        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));

        return modelAndView;
    }
    
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData) {
        final Locale locale = requestData.getLocale();
        
        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.LOGIN.getKey(), locale));
        
        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setKey(FoUrls.LOGIN.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.LOGIN.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.LOGIN, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);
        
        return breadcrumbViewBean;
    }
    
    @RequestMapping(FoUrls.CART_AUTH_URL)
    public ModelAndView checkoutAuth(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.LOGIN.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

        // SANITY CHECK: Customer logged
        final Customer currentCustomer = requestData.getCustomer();
        if (currentCustomer != null) {
            final String url = urlService.generateRedirectUrl(FoUrls.CART_DELIVERY, requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(url));
        }

        // SANITY CHECK : Param from spring-security
        String error = request.getParameter(RequestConstants.REQUEST_PARAMETER_AUTH_ERROR);
        if (BooleanUtils.toBoolean(error)) {
            model.addAttribute(ModelConstants.AUTH_HAS_FAIL, BooleanUtils.toBoolean(error));
            model.addAttribute(ModelConstants.AUTH_ERROR_MESSAGE, getCommonMessage(ScopeCommonMessage.AUTH, "login_or_password_are_wrong", locale));
        }
        
        overrideDefaultPageTitle(request, modelAndView, FoUrls.CART_AUTH.getKey());
        
        modelAndView.addObject(ModelConstants.CHECKOUT_STEP, 2);

        return modelAndView;
    }

    @RequestMapping(FoUrls.LOGIN_CHECK_URL)
    public ModelAndView loginCheck(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        ModelAndView modelAndView = new ModelAndView(FoUrls.LOGIN.getVelocityPage());

        final Customer currentCustomer = requestData.getCustomer();
        if (currentCustomer != null) {
            final String urlRedirect = urlService.generateRedirectUrl(FoUrls.HOME, requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(urlRedirect));
        }

        return modelAndView;
    }

}