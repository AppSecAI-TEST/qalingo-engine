/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual_;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductBrand_;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuStorePrice_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.FoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.form.CustomerCommentForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogBreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductRatesViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SeoDataViewBean;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("productDetailsController")
public class ProductDetailsController extends AbstractMCommerceController {

	@Autowired
	protected CatalogCategoryService catalogCategoryService;
	
	@Autowired
	protected ProductService productService;
	
    protected List<SpecificFetchMode> productSkuFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> productMarketingFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> categoryVirtualFetchPlans = new ArrayList<SpecificFetchMode>();

    public ProductDetailsController() {
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName() + "." + ProductMarketing_.productBrand.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName() + "." + ProductSkuStorePrice_.currency.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.assets.getName()));
        
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productBrand.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productBrand.getName() + "." + ProductBrand_.attributes.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName() + "." + ProductSkuStorePrice_.currency.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productAssociationLinks.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.assets.getName()));
        
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategories.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.attributes.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName() + "." + CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.assets.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.catalogCategoryType.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.attributes.getName()));
    }
    
    @RequestMapping(FoUrls.PRODUCT_DETAILS_OLD_URL)
    public ModelAndView productDetailsOld(final HttpServletRequest request, final HttpServletResponse response ,final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode,
                                       @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE) final String productMarketingCode,
                                       @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_SKU_CODE) final String productSkuCode,
                                       @ModelAttribute(ModelConstants.CUSTOMER_COMMENT_FORM) CustomerCommentForm customerCommentForm) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);

        CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), new FetchPlan(categoryVirtualFetchPlans));
        ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, new FetchPlan(productMarketingFetchPlans));
        ProductSku productSku = productService.getProductSkuByCode(productSkuCode, new FetchPlan(productSkuFetchPlans));

        if (productMarketing.isEnabledB2C() && productSku.isEnabledB2C()) {
            final String urlRedirect = urlService.generateRedirectUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productSku);
            return new ModelAndView(new RedirectView(urlRedirect));
        }
        final String urlRedirect = urlService.generateRedirectUrl(FoUrls.HOME, requestData);
        return new ModelAndView(new RedirectView(urlRedirect));
    }
    
	@RequestMapping(FoUrls.PRODUCT_DETAILS_URL)
	public ModelAndView productDetails(final HttpServletRequest request, final HttpServletResponse response ,final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode,
			 						   @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE) final String productMarketingCode,
			 						   @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_SKU_CODE) final String productSkuCode,
			 						   @ModelAttribute(ModelConstants.CUSTOMER_COMMENT_FORM) CustomerCommentForm customerCommentForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PRODUCT_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

		CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), new FetchPlan(categoryVirtualFetchPlans));
		ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, new FetchPlan(productMarketingFetchPlans));
        ProductSku productSku = productService.getProductSkuByCode(productSkuCode, new FetchPlan(productSkuFetchPlans));
		
		final CatalogCategoryViewBean catalogCategoryViewBean = frontofficeViewBeanFactory.buildViewBeanVirtualCatalogCategory(requestUtil.getRequestData(request), catalogCategory);
		model.addAttribute(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, catalogCategoryViewBean);

        final ProductMarketingViewBean productMarketingViewBean = frontofficeViewBeanFactory.buildViewBeanProductMarketing(requestUtil.getRequestData(request), catalogCategory, productMarketing, productSku);
        model.addAttribute(ModelConstants.PRODUCT_MARKETING_VIEW_BEAN, productMarketingViewBean);
        
        final ProductSkuViewBean productSkuViewBean = frontofficeViewBeanFactory.buildViewBeanProductSku(requestData, catalogCategory, productMarketing, productSku);
        model.addAttribute(ModelConstants.PRODUCT_SKU_VIEW_BEAN, productSkuViewBean);
        
        final CatalogBreadcrumbViewBean catalogBreadcrumbViewBean = frontofficeViewBeanFactory.buildViewBeanCatalogBreadcrumb(requestUtil.getRequestData(request) , catalogCategory);
		model.addAttribute(ModelConstants.CATALOG_BREADCRUMB_VIEW_BEAN, catalogBreadcrumbViewBean);

        //for now, get the featured products in same category
        //TODO: define related products
        final List<ProductMarketingViewBean> relatedProducts = catalogCategoryViewBean.getFeaturedProductMarketings();
        model.addAttribute(ModelConstants.RELATED_PRODUCT_MARKETING_VIEW_BEAN, relatedProducts);
        
        final CustomerProductRatesViewBean customerProductRatesViewBean = frontofficeViewBeanFactory.getProductMarketingCustomerRateDetails(productMarketing.getId());
        model.addAttribute(ModelConstants.PRODUCT_MARKETING_RATES_VIEW_BEAN, customerProductRatesViewBean);
        
        customerCommentForm = formFactory.buildCustomerCommentForm(requestData, productMarketing.getCode());
        model.addAttribute(ModelConstants.CUSTOMER_COMMENT_FORM, customerCommentForm);
        model.addAttribute(ModelConstants.PRODUCT_COMMENT_SUBMIT_URL, urlService.generateUrl(FoUrls.PRODUCT_COMMENT, requestData, productMarketing));
        
        requestUtil.addOrUpdateRecentProductToCookie(request, response, requestData.getVirtualCatalogCode(), catalogCategory.getCode(), productMarketing.getCode(), productSku.getCode());
        
        Object[] params = { productMarketingViewBean.getI18nName() };
        overrideDefaultPageTitle(request, modelAndView, FoUrls.PRODUCT_DETAILS.getKey(), params);
        
        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData, catalogCategory, productMarketing));

        model.addAttribute(ModelConstants.SEO_DATA_VIEW_BEAN, initSeo(request, model, productMarketingViewBean));

        model.addAttribute(ModelConstants.PRODUCT_BRANDS_VIEW_BEAN, brandList(request, model));

        return modelAndView;
	}
	
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData, CatalogCategoryVirtual catalogCategory, ProductMarketing productMarketing) {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final Locale locale = requestData.getLocale();
        Object[] params = { productMarketing.getI18nName(localizationCode) };

        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.PRODUCT_DETAILS.getMessageKey(), params, locale));

        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        Object[] catalogCategoryParams = { productMarketing.getI18nName(localizationCode) };
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.CATEGORY_AS_LINE.getMessageKey(), catalogCategoryParams, locale));
        menu.setUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, catalogCategory));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setKey(FoUrls.PRODUCT_DETAILS.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.PRODUCT_DETAILS.getMessageKey(), params, locale));
        menu.setUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, productMarketing));
        menu.setActive(true);
        menuViewBeans.add(menu);

        return breadcrumbViewBean;
    }
    
    protected List<ProductBrandViewBean> brandList(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final List<Long> productBrandIds = productService.findAllProductBrandIdsEnabled();
        final List<ProductBrandViewBean> productBrandViewBeans = new ArrayList<ProductBrandViewBean>();
        for (Long productBrandId : productBrandIds) {
            ProductBrand productBrand = productService.getProductBrandById(productBrandId);
            ProductBrandViewBean productBrandViewBean = frontofficeViewBeanFactory.buildViewBeanProductBrand(requestData, productBrand);
            productBrandViewBeans.add(productBrandViewBean);
        }
        return productBrandViewBeans;
    }
    
    protected SeoDataViewBean initSeo(final HttpServletRequest request, final Model model, final ProductMarketingViewBean productMarketingViewBean) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

        SeoDataViewBean seoDataViewBean = frontofficeViewBeanFactory.buildViewSeoData(requestData);
        
        String seoPageTitle = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.PAGE_META_OG_TITLE, locale);
        if(seoPageTitle != null && !seoPageTitle.trim().endsWith("-")){
            seoPageTitle += " - ";
        }
        if(productMarketingViewBean.getBrand() != null){
            seoPageTitle += productMarketingViewBean.getBrand().getI18nName() + " ";
        }
        seoPageTitle += productMarketingViewBean.getI18nName();
        
        // SEO
        seoDataViewBean.setPageTitle(seoPageTitle);
        seoDataViewBean.setMetaDescription(productMarketingViewBean.getI18nDescription());

        seoDataViewBean.setMetaOgTitle(seoPageTitle);
        seoDataViewBean.setMetaOgDescription(productMarketingViewBean.getI18nDescription());
        if(productMarketingViewBean.getDefaultAsset() != null){
            seoDataViewBean.setMetaOgImage(productMarketingViewBean.getDefaultAsset().getAbsoluteWebPath());
        }

        return seoDataViewBean;
    }

}