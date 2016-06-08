/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hoteia.qalingo.core.dao.ProductDao;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CatalogCategoryMasterProductSkuRel;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuRel;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductBrandCustomerComment;
import org.hoteia.qalingo.core.domain.ProductBrandCustomerRate;
import org.hoteia.qalingo.core.domain.ProductBrandStoreRel;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuCustomerComment;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinition;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinitionType;
import org.hoteia.qalingo.core.domain.ProductSkuStorePrice;
import org.hoteia.qalingo.core.domain.ProductSkuStoreRel;
import org.hoteia.qalingo.core.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productService")
@Transactional
public class ProductService {

    @Autowired
    protected ProductDao productDao;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    // PRODUCT MARKETING

    public ProductMarketing getProductMarketingById(final Long productMarketingId, Object... params) {
        return productDao.getProductMarketingById(productMarketingId, params);
    }

    public ProductMarketing getProductMarketingById(final String rawProductMarketingId, Object... params) {
        long productMarketingId = -1;
        try {
            productMarketingId = Long.parseLong(rawProductMarketingId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductMarketingById(productMarketingId, params);
    }

    public ProductMarketing getProductMarketingByCode(final String productMarketingCode, Object... params) {
        return productDao.getProductMarketingByCode(productMarketingCode, params);
    }
    
    public CatalogCategoryVirtual getDefaultVirtualCatalogCategory(final ProductMarketing productMarketing, final List<CatalogCategoryVirtual> catalogCategories, boolean withFallback) {
        return getDefaultVirtualCatalogCategory(productMarketing.getDefaultProductSku(), catalogCategories, withFallback);
    }

    public CatalogCategoryVirtual getDefaultVirtualCatalogCategory(final ProductSku productSku, final List<CatalogCategoryVirtual> catalogCategories, boolean withFallback) {
        if(catalogCategories != null){
            for (Iterator<CatalogCategoryVirtual> iteratorCatalogCategoryVirtual = catalogCategories.iterator(); iteratorCatalogCategoryVirtual.hasNext();) {
                CatalogCategoryVirtual catalogCategoryVirtual = (CatalogCategoryVirtual) iteratorCatalogCategoryVirtual.next();
                for (Iterator<CatalogCategoryVirtualProductSkuRel> iteratorCatalogCategoryProductSkuRel = catalogCategoryVirtual.getCatalogCategoryProductSkuRels().iterator(); iteratorCatalogCategoryProductSkuRel.hasNext();) {
                    CatalogCategoryVirtualProductSkuRel catalogCategoryVirtualProductSkuRel = (CatalogCategoryVirtualProductSkuRel) iteratorCatalogCategoryProductSkuRel.next();
                    if(productSku.getCode().equals(catalogCategoryVirtualProductSkuRel.getProductSku().getCode()) 
                            && catalogCategoryVirtualProductSkuRel.isDefaultCategory()){
                        return catalogCategoryVirtual;
                    }
                }
            }
            if(withFallback
                    && catalogCategories.size() > 0){
                return catalogCategories.iterator().next();
            }
        }
        return null;
    }
    
    public Long countProductMarketing() {
        return productDao.countProductMarketing();
    }
    
    public List<ProductMarketing> findProductMarketings(Object... params) {
        return findAllProductMarketings(params);
    }
    
    public List<ProductMarketing> findAllProductMarketings(Object... params) {
        List<ProductMarketing> productMarketings = productDao.findAllProductMarketings(params);
        return productMarketings;
    }
    
    public List<Long> findAllProductMarketingIds() {
        return findAllProductMarketingIds(0);
    }
    
    public List<Long> findAllProductMarketingIds(int maxResults) {
        List<Long> productMarketings = productDao.findAllProductMarketingIds(maxResults);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingByRandom(int maxResults, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketingByRandom(maxResults, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingEnableB2CByRandom(int maxResults, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketingEnableB2CByRandom(maxResults, params);
        return productMarketings;
    }

    public List<ProductMarketing> findProductMarketings(final String text, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketings(text, params);
        return productMarketings;
    }

    public List<Long> findProductMarketingIdsByBrandId(final Long brandId, Object... params) {
        List<Long> productMarketings = productDao.findProductMarketingIdsByBrandId(brandId, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsByBrandId(final Long brandId, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsByBrandId(brandId, params);
        return productMarketings;
    }

    public List<ProductMarketing> findProductMarketingsByBrandCode(final String brandCode, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsByBrandCode(brandCode, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsNotInThisMasterCatalogCategoryId(final Long categoryId, Object... params){
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsNotInThisMasterCatalogCategoryId(categoryId, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsByMasterCatalogCategoryId(final Long categoryId, Object... params){
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsByMasterCatalogCategoryId(categoryId, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsNotInThisVirtualCatalogCategoryId(final Long categoryId, Object... params){
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsNotInThisVirtualCatalogCategoryId(categoryId, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsByVirtualCatalogCategoryId(final Long categoryId, Object... params){
    	List<ProductMarketing> productMarketings = productDao.findProductMarketingsByVirtualCatalogCategoryId(categoryId, params);
    	return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsByVirtualCatalogCategoryCode(final String catalogVirtualCode, final String categoryCode, Object... params){
        CatalogCategoryVirtual catalogCategoryVirtual = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, catalogVirtualCode, categoryCode, params);
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsByVirtualCatalogCategoryId(catalogCategoryVirtual.getId(), params);
        return productMarketings;
    }

    public ProductMarketing saveOrUpdateProductMarketing(final ProductMarketing productMarketing) {
        return productDao.saveOrUpdateProductMarketing(productMarketing);
    }

    public ProductMarketing createProductMarketing(final ProductMarketing productMarketing) {
        return productDao.createProductMarketing(productMarketing);
    }
    
    public ProductMarketing updateProductMarketing(final ProductMarketing productMarketing) {
        return productDao.updateProductMarketing(productMarketing);
    }

    public void deleteProductMarketing(final ProductMarketing productMarketing) {
        productDao.deleteProductMarketing(productMarketing);
    }

    // PRODUCT MARKETING COMMENT/RATE
    
    public ProductMarketingCustomerRate saveOrUpdateProductMarketingCustomerRate(final ProductMarketingCustomerRate customerRate) {
        return productDao.saveOrUpdateProductMarketingCustomerRate(customerRate);
    }

    public void deleteProductMarketingCustomerRate(final ProductMarketingCustomerRate customerRate) {
        productDao.deleteProductMarketingCustomerRate(customerRate);
    }
    
    public ProductMarketingCustomerComment saveOrUpdateProductMarketingCustomerComment(final ProductMarketingCustomerComment customerRate) {
        return productDao.saveOrUpdateProductMarketingCustomerComment(customerRate);
    }
    
    public List<ProductMarketingCustomerComment> findProductMarketingCustomerCommentsByProductMarketingId(final Long productMarketingId, Object... params){
        List<ProductMarketingCustomerComment> customerComments = productDao.findProductMarketingCustomerCommentsByProductMarketingId(productMarketingId, params);
        return customerComments;
    }
    
    public List<ProductMarketingCustomerComment> findProductMarketingCustomerCommentsByProductMarketingIdAndMarketAreaId(final Long productMarketingId, final Long marketAreaId, Object... params){
        List<ProductMarketingCustomerComment> customerComments = productDao.findProductMarketingCustomerCommentsByProductMarketingIdAndMarketAreaId(productMarketingId, marketAreaId, params);
        return customerComments;
    }
    
    public List<ProductMarketingCustomerComment> findProductMarketingCustomerCommentsByCustomerId(final Long customerId, Object... params){
        List<ProductMarketingCustomerComment> customerComments = productDao.findProductMarketingCustomerCommentsByCustomerId(customerId, params);
        return customerComments;
    }

    public List<ProductSkuCustomerComment> findProductSkuCustomerCommentsByCustomerId(final Long customerId, Object... params){
        List<ProductSkuCustomerComment> customerComments = productDao.findProductSkuCustomerCommentsByCustomerId(customerId, params);
        return customerComments;
    }

    public List<ProductMarketingCustomerRate> findProductMarketingCustomerRatesByProductMarketingId(final Long productMarketingId, final String type, Object... params) {
        List<ProductMarketingCustomerRate> customerRates = productDao.findProductMarketingCustomerRatesByProductMarketingId(productMarketingId, type, params);
        return customerRates;
    }
    
    public Float calculateProductMarketingCustomerRatesByProductMarketingId(final Long productMarketingId) {
        Float customerRate = productDao.calculateProductMarketingCustomerRatesByProductMarketingId(productMarketingId);
        return customerRate;
    }

    public void deleteProductMarketingCustomerComment(final ProductMarketingCustomerComment customerRate) {
        productDao.deleteProductMarketingCustomerComment(customerRate);
    }
    
    // PRODUCT MARKETING ASSET

    public Asset getProductMarketingAssetById(final Long assetId, Object... params) {
        return productDao.getProductMarketingAssetById(assetId, params);
    }

    public Asset getProductMarketingAssetById(final String rawAssetId, Object... params) {
        long assetId = -1;
        try {
            assetId = Long.parseLong(rawAssetId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductMarketingAssetById(assetId, params);
    }

    public Asset saveOrUpdateProductMarketingAsset(final Asset productMarketingAsset) {
        return productDao.saveOrUpdateProductMarketingAsset(productMarketingAsset);
    }

    public void deleteProductMarketingAsset(final Asset productMarketingAsset) {
        productDao.deleteProductMarketingAsset(productMarketingAsset);
    }

    // PRODUCT SKU

    public ProductSku getProductSkuById(final Long productSkuId, Object... params) {
        return productDao.getProductSkuById(productSkuId, params);
    }

    public ProductSku getProductSkuById(final String rawProductSkuId, Object... params) {
        long productSkuId = -1;
        try {
            productSkuId = Long.parseLong(rawProductSkuId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductSkuById(productSkuId, params);
    }

    public ProductSku getProductSkuByCode(final String skuCode, Object... params) {
        return productDao.getProductSkuByCode(skuCode, params);
    }

    public ProductSku getProductSkuByEAN(final String skuEAN, Object... params) {
        return productDao.getProductSkuByEAN(skuEAN, params);
    }
    
    public Long countProductSku() {
        return productDao.countProductSku();
    }
    
    public List<Long> findAllProductSkuIds() {
        return findProductSkuIds(0);
    }


    public List<Long> findProductSkuIds(int maxResults){
        return productDao.findProductSkuIds(maxResults);
    }

    public List<String> findAllProductSkuCode() {
        return findProductSkuCode(0);
    }

    public List<String> findProductSkuCode(int maxResults){
        return productDao.findProductSkuCode(maxResults);
    }
    
    public List<Long> findProductSkuIdsEnableB2COrderByDateUpdate(int maxResults){
        return productDao.findProductSkuIdsEnableB2COrderByDateUpdate(maxResults);
    }

    public List<Long> findProductSkuIdsByBrandId(final Long brandId, Object... params) {
        return productDao.findProductSkuIdsByBrandId(brandId, params);
    }

    public List<Long> findProductSkuIdsByBrandIdOrderByDateUpdate(final Long brandId, int maxResults, Object... params){
        return productDao.findProductSkuIdsByBrandIdOrderByDateUpdate(brandId, maxResults, params);
    }
    
    public List<ProductSku> findProductSkusByProductMarketingId(final Long productMarketingId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusByproductMarketingId(productMarketingId, params);
        return skus;
    }
    
    public List<ProductSku> findProductSkusByProductMarketingCode(final String productMarketingCode, Object... params) {
        ProductMarketing productMarketing = getProductMarketingByCode(productMarketingCode, params);
        List<ProductSku> skus = productDao.findProductSkusByproductMarketingId(productMarketing.getId(), params);
        return skus;
    }

    public List<ProductSku> findProductSkus(final String text, Object... params) {
        List<ProductSku> skus = productDao.findProductSkus(text, params);
        return skus;
    }

    public List<ProductSku> findProductSkusByMasterCatalogCategoryId(Long categoryId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusByMasterCatalogCategoryId(categoryId, params);
        return skus;
    }
    
    public List<ProductSku> findProductSkusNotInThisMasterCatalogCategoryId(Long categoryId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusNotInThisMasterCatalogCategoryId(categoryId, params);
        return skus;
    }
    
    public List<ProductSku> findProductSkusByVirtualCatalogCategoryId(Long categoryId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusByVirtualCatalogCategoryId(categoryId, params);
        return skus;
    }
    
    public List<ProductSku> findProductSkusNotInThisVirtualCatalogCategoryId(Long categoryId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusNotInThisVirtualCatalogCategoryId(categoryId, params);
        return skus;
    }

    public List<Long> getProductIds(List<ProductSku> productSkus) {
        List<Long> productSkuIds = new ArrayList<Long>();
        for (ProductSku productSku : productSkus) {
            productSkuIds.add(productSku.getId());
        }
        return productSkuIds;
    }
    
    public List<ProductSku> findProductSkusByStoreId(final Long storeId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusByStoreId(storeId, params);
        return skus;
    }
    
    public List<Long> findProductSkuIdsActiveByStoreId(final Long storeId, Object... params) {
        List<Long> skuIds = productDao.findProductSkuIdsActiveByStoreId(storeId, params);
        return skuIds;
    }

    public ProductSku saveOrUpdateProductSku(final ProductSku productSku) {
        return productDao.saveOrUpdateProductSku(productSku);
    }
    
    public ProductSku createProductSku(final ProductSku productSku) {
        return productDao.createProductSku(productSku);
    }
    
    public ProductSku updateProductSku(final ProductSku productSku) {
        return productDao.updateProductSku(productSku);
    }

    public void deleteProductSku(final ProductSku productSku) {
        productDao.deleteProductSku(productSku);
    }
    
    // PRODUCT SKU STORE

    public ProductSkuStoreRel findProductSkuStoreRelByStoreIdAndSpecificCode(final Long storeId, final String specificCode, Object... params) {
        return productDao.findProductSkuStoreRelByStoreIdAndSpecificCode(storeId, specificCode, params);
    }
    
    public List<ProductSkuStoreRel> findProductSkuStoreRelByProductSkuId(final Long productSkuId, Object... params) {
        return productDao.findProductSkuStoreRelByProductSkuId(productSkuId, params);
    }
    
    public ProductSkuStoreRel findProductSkuStoreRelByStoreIdAndProductId(final Long storeId, final Long productSkuId, Object... params) {
        return productDao.findProductSkuStoreRelByStoreIdAndProductId(storeId, productSkuId, params);
    }
    
    public List<ProductSkuStoreRel> findProductSkuStoreRelByStoreId(final Long storeId, Object... params) {
        return productDao.findProductSkuStoreRelByStoreId(storeId, params);
    }


    public List<ProductSkuStoreRel> getProductSkuStoreRelB2B(Object... params) {
        return productDao.getProductSkuStoreRelB2B(params);
    }

    public void saveNewProductSkuStoreRel(final ProductSkuStoreRel productSkuStoreRel)  {
        productDao.saveNewProductSkuStoreRel(productSkuStoreRel);
    }
    
    public ProductSkuStoreRel updateProductSkuStoreRel(final ProductSkuStoreRel productSkuStoreRel) {
        return productDao.updateProductSkuStoreRel(productSkuStoreRel);
    }
    
    public void deleteProductSkuStoreRel(final ProductSkuStoreRel productSkuStoreRel) {
        productDao.deleteProductSkuStoreRel(productSkuStoreRel);
    }
    
    // PRODUCT SKU ASSET

    public Asset getProductSkuAssetById(final Long assetId, Object... params) {
        return productDao.getProductSkuAssetById(assetId, params);
    }

    public Asset getProductSkuAssetById(final String rawAssetId, Object... params) {
        long assetId = -1;
        try {
            assetId = Long.parseLong(rawAssetId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductSkuAssetById(assetId);
    }

    public Asset saveOrUpdateProductSkuAsset(final Asset productSkuAsset) {
        return productDao.saveOrUpdateProductSkuAsset(productSkuAsset);
    }

    public void deleteProductSkuAsset(final Asset productSkuAsset) {
        productDao.deleteProductSkuAsset(productSkuAsset);
    }
   
    // PRODUCT SKU OPTION DEFINITION

    public ProductSkuOptionDefinition getProductSkuOptionDefinitionById(final Long productSkuOptionDefinitionId, Object... params) {
        return productDao.getProductSkuOptionDefinitionById(productSkuOptionDefinitionId, params);
    }

    public ProductSkuOptionDefinition getProductSkuOptionDefinitionById(final String rawProductSkuOptionDefinitionId, Object... params) {
        long productSkuOptionDefinitionId = -1;
        try {
            productSkuOptionDefinitionId = Long.parseLong(rawProductSkuOptionDefinitionId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductSkuOptionDefinitionById(productSkuOptionDefinitionId, params);
    }

    public ProductSkuOptionDefinition getProductSkuOptionDefinitionByCode(final String productSkuOptionDefinitionCode, Object... params) {
        return productDao.getProductSkuOptionDefinitionByCode(productSkuOptionDefinitionCode, params);
    }

    public List<ProductSkuOptionDefinition> findAllProductSkuOptionDefinitions(Object... params) {
        return productDao.findAllProductSkuOptionDefinitions(params);
    }
    
    public ProductSkuOptionDefinition saveOrUpdateProductSkuOptionDefinition(final ProductSkuOptionDefinition productSkuOptionDefinition) {
        return productDao.saveOrUpdateProductSkuOptionDefinition(productSkuOptionDefinition);
    }

    public void deleteProductSkuOptionDefinition(final ProductSkuOptionDefinition productSkuOptionDefinition) {
        productDao.deleteProductSkuOptionDefinition(productSkuOptionDefinition);
    }
    
    public ProductSkuOptionDefinitionType getProductSkuOptionDefinitionTypeById(final Long productSkuOptionDefinitionTypeId, Object... params) {
        return productDao.getProductSkuOptionDefinitionTypeById(productSkuOptionDefinitionTypeId, params);
    }

    public ProductSkuOptionDefinitionType getProductSkuOptionDefinitionTypeById(final String rawProductSkuOptionDefinitionTypeId, Object... params) {
        long productSkuOptionDefinitionTypeId = -1;
        try {
            productSkuOptionDefinitionTypeId = Long.parseLong(rawProductSkuOptionDefinitionTypeId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductSkuOptionDefinitionTypeById(productSkuOptionDefinitionTypeId, params);
    }

    public ProductSkuOptionDefinitionType getProductSkuOptionDefinitionTypeByCode(final String productSkuOptionDefinitionTypeCode, Object... params) {
        return productDao.getProductSkuOptionDefinitionTypeByCode(productSkuOptionDefinitionTypeCode, params);
    }

    public List<ProductSkuOptionDefinitionType> findAllProductSkuOptionDefinitionTypes(Object... params) {
        return productDao.findAllProductSkuOptionDefinitionTypes(params);
    }
    
    public ProductSkuOptionDefinitionType saveOrUpdateProductSkuOptionDefinitionType(final ProductSkuOptionDefinitionType productSkuOptionDefinitionType) {
        return productDao.saveOrUpdateProductSkuOptionDefinitionType(productSkuOptionDefinitionType);
    }

    public void deleteProductSkuOptionDefinitionType(final ProductSkuOptionDefinitionType productSkuOptionDefinitionType) {
        productDao.deleteProductSkuOptionDefinitionType(productSkuOptionDefinitionType);
    }
    
    // PRODUCT BRAND

    public ProductBrand getProductBrandById(final Long productBrandId, Object... params) {
        return productDao.getProductBrandById(productBrandId, params);
    }

    public ProductBrand getProductBrandById(final String rawProductBrandId, Object... params) {
        long productBrandId = -1;
        try {
            productBrandId = Long.parseLong(rawProductBrandId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductBrandById(productBrandId, params);
    }

    public ProductBrand getProductBrandByCode(final String productBrandCode, Object... params) {
        return productDao.getProductBrandByCode(productBrandCode, params);
    }
    
    public Long countProductBrand() {
        return productDao.countProductBrand();
    }

    public List<ProductBrand> findAllProductBrands(Object... params) {
        return productDao.findAllProductBrands(params);
    }

    public List<Long> findAllProductBrandIds() {
        return productDao.findAllProductBrandIds();
    }
    
    public List<Long> findAllProductBrandIdsEnabled(Object... params) {
        return productDao.findAllProductBrandIdsEnabled(params);
    }
    
    public List<Long> findAllProductBrandIdsEnabledB2B(Object... params) {
        return productDao.findAllProductBrandIdsEnabledB2B(params);
    }
    
    public List<Long> findAllProductBrandIdsEnabledB2C(Object... params) {
        return productDao.findAllProductBrandIdsEnabledB2C(params);
    }
    
    public List<ProductBrand> findAllProductBrandsEnabled(Object... params) {
        return productDao.findAllProductBrandsEnabled(params);
    }
    
    public List<ProductBrand> findProductBrandsByCatalogCategoryCode(final String categoryCode, Object... params) {
        return productDao.findProductBrandsByCatalogCategoryCode(categoryCode, params);
    }

    public List<ProductBrand> findProductBrandsByText(String text, Object... params) {
        return productDao.findProductBrandsByText(text, params);
    }
    
    public ProductBrand saveOrUpdateProductBrand(final ProductBrand productBrand) {
        return productDao.saveOrUpdateProductBrand(productBrand);
    }
    
    public ProductBrand updateProductBrand(final ProductBrand productBrand) {
        return productDao.updateProductBrand(productBrand);
    }

    public void deleteProductBrand(final ProductBrand productBrand) {
        productDao.deleteProductBrand(productBrand);
    }
    
    public ProductBrandStoreRel getProductBrandStoreRelById(final ProductBrand productBrand, final Store store, Object... params) {
        return productDao.getProductBrandStoreRelById(productBrand, store);
    }
    
    public List<Long> findStoreIdsByBrandId(final Long productBrandId, int maxResults, Object... params) {
        List<Long> storeIds = productDao.findStoreIdsByBrandId(productBrandId, maxResults, params);
        return storeIds;
    }
    
    public boolean salableBrandInStore(final Long storeId, final Long productBrandId, Object... params) {
        ProductBrandStoreRel productBrandStoreRel = productDao.findProductBrandStoreRelByStoreIdAndBrandId(storeId, productBrandId, params);
        if(productBrandStoreRel != null){
            return true;
        } else {
            return false;
        }
    }
    
    public List<Long> findProductBrandIdsByStoreId(final Long storeId, int maxResults, Object... params) {
        List<Long> productBrandIds = productDao.findProductBrandIdsByStoreId(storeId, maxResults, params);
        return productBrandIds;
    }
    
    public ProductBrandStoreRel saveOrUpdateProductBrandStoreRel(final ProductBrand productBrand, final Store store) {
        ProductBrandStoreRel productBrandStoreRel = new ProductBrandStoreRel(productBrand, store);
        return productDao.saveOrUpdateProductBrandStoreRel(productBrandStoreRel);
    }
    
    public void deleteProductBrandStoreRel(final ProductBrand productBrand, final Store store) {
        ProductBrandStoreRel productBrandStoreRel = getProductBrandStoreRelById(productBrand, store);
        productDao.deleteProductBrandStoreRel(productBrandStoreRel);
    }
    
    public void deleteAllProductBrandStoreRelByBrandId(final Long productBrandId) {
        productDao.deleteAllProductBrandStoreRelByBrandId(productBrandId);
    }
    
    // PRODUCT BRAND COMMENT/RATE
    
    public ProductBrandCustomerRate saveOrUpdateProductBrandCustomerRate(final ProductBrandCustomerRate customerRate) {
        return productDao.saveOrUpdateProductBrandCustomerRate(customerRate);
    }

    public void deleteProductBrandCustomerRate(final ProductBrandCustomerRate customerRate) {
        productDao.deleteProductBrandCustomerRate(customerRate);
    }
    
    public ProductBrandCustomerComment saveOrUpdateProductBrandCustomerComment(final ProductBrandCustomerComment customerRate) {
        return productDao.saveOrUpdateProductBrandCustomerComment(customerRate);
    }
    
    public List<ProductBrandCustomerComment> findProductBrandCustomerCommentsByProductBrandId(final Long productMarketingId, Object... params){
        List<ProductBrandCustomerComment> customerComments = productDao.findProductBrandCustomerCommentsByProductBrandId(productMarketingId, params);
        return customerComments;
    }
    
    public List<ProductBrandCustomerComment> findProductBrandCustomerCommentsByProductBrandIdAndMarketAreaId(final Long productMarketingId, final Long marketAreaId, Object... params){
        List<ProductBrandCustomerComment> customerComments = productDao.findProductBrandCustomerCommentsByProductBrandIdAndMarketAreaId(productMarketingId, marketAreaId, params);
        return customerComments;
    }
    
    public List<ProductBrandCustomerComment> findProductBrandCustomerCommentsByCustomerId(final Long customerId, Object... params){
        List<ProductBrandCustomerComment> customerComments = productDao.findProductBrandCustomerCommentsByCustomerId(customerId, params);
        return customerComments;
    }
    
    public List<ProductBrandCustomerRate> findProductBrandCustomerRatesByProductBrandId(final Long productMarketingId, final String type, Object... params) {
        List<ProductBrandCustomerRate> customerRates = productDao.findProductBrandCustomerRatesByProductBrandId(productMarketingId, type, params);
        return customerRates;
    }
    
    public void deleteProductBrandCustomerComment(final ProductBrandCustomerComment customerRate) {
        productDao.deleteProductBrandCustomerComment(customerRate);
    }
    
    // CATALOG CATEGORY MASTER PRODUCT SKU REL
    
    public void createCatalogCategoryMasterProductSkuRel(final CatalogCategoryMasterProductSkuRel catalogCategoryMasterProductSkuRel) {
    	productDao.createCatalogCategoryMasterProductSkuRel(catalogCategoryMasterProductSkuRel);
    }
    
    // CATALOG CATEGORY VIRTUAL PRODUCT SKU REL
    
    public void createCatalogCategoryVirtualProductSkuRel(final CatalogCategoryVirtualProductSkuRel catalogCategoryVirtualProductSkuRel) {
    	productDao.createCatalogCategoryVirtualProductSkuRel(catalogCategoryVirtualProductSkuRel);
    }

    public List<ProductSkuStorePrice> findProductSkuStorePrices(final Long storeId, final Long productSkuCode) {
        return productDao.findProductSkuStorePrices(storeId, productSkuCode);
    }
    
}