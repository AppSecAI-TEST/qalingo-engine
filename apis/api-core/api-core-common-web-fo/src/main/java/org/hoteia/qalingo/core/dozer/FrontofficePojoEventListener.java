/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dozer;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuPrice;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.AssetPojo;
import org.hoteia.qalingo.core.pojo.cart.FoCartItemPojo;
import org.hoteia.qalingo.core.pojo.deliverymethod.FoDeliveryMethodPojo;
import org.hoteia.qalingo.core.pojo.product.ProductSkuPojo;
import org.hoteia.qalingo.core.service.CartService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "frontofficePojoEventListener")
public class FrontofficePojoEventListener implements DozerEventListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected EngineSettingService engineSettingService;
    
    @Autowired 
    protected UrlService urlService;

    @Autowired
    protected RequestUtil requestUtil;


    @Autowired
    protected CartService cartService;

    @Autowired 
    private HttpServletRequest httpServletRequest;

    public FrontofficePojoEventListener() {
    }

    @Override
    public void mappingStarted(DozerEvent event) {
        logger.debug("mapping started, SourceObject: " + event.getSourceObject());
    }

    @Override
    public void preWritingDestinationValue(DozerEvent event) {
        logger.debug("pre writing destination value, SourceObject: " + event.getSourceObject());
    }

    @Override
    public void postWritingDestinationValue(DozerEvent event) {
        logger.debug("post writing destination value, SourceObject: " + event.getSourceObject());
        if(event.getDestinationObject() instanceof ProductSkuPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                ProductSku productSku = (ProductSku) event.getSourceObject();
                ProductSkuPojo productSkuPojo = (ProductSkuPojo) event.getDestinationObject();
                try {
                    final RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    final MarketArea marketArea = requestData.getMarketArea();
                    final Localization localization = requestData.getMarketAreaLocalization();
                    final String localizationCode = localization.getCode();
                    
                    // ASSETS
                    if (Hibernate.isInitialized(productSku.getAssets()) && productSku.getAssets() != null) {
                        for (Asset asset : productSku.getAssets()) {
                            AssetPojo assetPojo = new AssetPojo();
                            assetPojo.setName(asset.getName());
                            assetPojo.setDescription(asset.getDescription());
                            assetPojo.setType(asset.getType());
                            assetPojo.setPath(asset.getPath());

                            final String path = engineSettingService.getProductSkuImageWebPath(asset);
                            assetPojo.setRelativeWebPath(path);
                            assetPojo.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
                            productSkuPojo.getAssets().add(assetPojo);
                        }
                    }
                    
                    productSkuPojo.setI18nName(productSku.getI18nName(localizationCode));
                    
                    ProductSkuPrice price = productSku.getBestPrice(marketArea.getId());
                    if(price != null){
                        productSkuPojo.setPriceWithStandardCurrencySign(price.getPriceWithStandardCurrencySign());
                    }

                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with ProductSkuPojo", e);
                }
            }
        } else if(event.getDestinationObject() instanceof FoDeliveryMethodPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                DeliveryMethod deliveryMethod = (DeliveryMethod) event.getSourceObject();
                FoDeliveryMethodPojo deliveryMethodPojo = (FoDeliveryMethodPojo) event.getDestinationObject();
                try {
                    final RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    final Cart cart = requestData.getCart();

                    if(cart != null) {
                        SimpleDateFormat simpleDateFormat = requestUtil.getFormatDate(requestData);
                        String maxDay = deliveryMethod.getDeliveryTimeValueMax();
                        if(maxDay != null){
                            GregorianCalendar arrivalTime = new GregorianCalendar(); 
                            int day = arrivalTime.get(GregorianCalendar.DAY_OF_YEAR);
                            arrivalTime.set(GregorianCalendar.DAY_OF_YEAR, day + Integer.parseInt(maxDay));
                            deliveryMethodPojo.setArrivalTime(simpleDateFormat.format(arrivalTime.getTime()));
                        }
                        deliveryMethodPojo.setPrice(deliveryMethod.getPriceWithStandardCurrencySign(cart.getCurrency().getId()));
                        if (cart.getDeliveryMethods().contains(deliveryMethod)) {
                            deliveryMethodPojo.setSelected(true);
                        }
                    }
                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with FoDeliveryMethodPojo", e);
                }
            }
        }
    }

    public String buildDefaultAsset(final RequestData requestData, final ProductSku productSku) throws Exception{
        // TEMPORARY FIX : ASSET
        Set<Asset> assets = productSku.getAssets();
        Asset defaultAsset = null;
        if(assets != null){
            for (Asset asset : assets) {
                if ("PACKSHOT".equalsIgnoreCase(asset.getType()) && asset.isDefault()) {
                    defaultAsset = asset;
                }
            }
            if(defaultAsset == null && assets.iterator().hasNext()){
                defaultAsset = assets.iterator().next();
            }
        }
        if(defaultAsset == null && productSku.getProductMarketing() != null && Hibernate.isInitialized(productSku.getProductMarketing())){
            if(productSku.getProductMarketing().getAssets() != null && Hibernate.isInitialized(productSku.getProductMarketing().getAssets())){
                assets = productSku.getProductMarketing().getAssets();
                for (Asset asset : assets) {
                    if ("PACKSHOT".equalsIgnoreCase(asset.getType()) && asset.isDefault()) {
                        defaultAsset = asset;
                    }
                }
                if(defaultAsset == null && assets.iterator().hasNext()){
                    defaultAsset = assets.iterator().next();
                }
            }
        }
        if(defaultAsset == null){
            defaultAsset = new Asset();
            defaultAsset.setType("default");
            defaultAsset.setPath("default-product.png");
        }
        return urlService.buildAbsoluteUrl(requestData, buildAssetPath(productSku, defaultAsset));
    }

    protected String buildAssetPath(final ProductSku productSku, final Asset defaultAsset) throws Exception{
        return engineSettingService.getProductSkuImageWebPath(defaultAsset);
    }


    @Override
    public void mappingFinished(DozerEvent event) {
        logger.debug("mapping finished, SourceObject: " + event.getSourceObject());
    }
    
}