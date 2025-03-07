package com.novamart.search_service.repository;

import com.novamart.search_service.dto.ProductRequest;
import com.novamart.search_service.model.Product;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}]}}")
    List<Product> searchByName(String name);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"currencyCode\": \"?0\"}}]}}")
    List<Product> searchByCurrencyCode(String currencyCode);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"categories\": \"?0\"}}]}}")
    List<Product> searchByCategories(List<String> categories);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"attributes.color\": \"?0\"}}]}}")
    List<Product> searchByColorAttribute(Object attributes);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"currencyCode\": \"?1\"}}]}}")
    List<Product> searchByNameAndCurrencyCode(String name, String currencyCode);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"categories\": \"?1\"}}]}}")
    List<Product> searchByNameAndCategories(String name, List<String> categories);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"attributes.color\": \"?1\"}}]}}")
    List<Product> searchByNameAndColorAttribute(String name, Object attributes);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"currencyCode\": \"?0\"}}, {\"match\": {\"categories\": \"?1\"}}]}}")
    List<Product> searchByCurrencyCodeAndCategories(String currencyCode, List<String> categories);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"currencyCode\": \"?0\"}}, {\"match\": {\"attributes.color\": \"?1\"}}]}}")
    List<Product> searchByCurrencyCodeAndColorAttribute(String currencyCode, Object attributes);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"categories\": \"?0\"}}, {\"match\": {\"attributes.color\": \"?1\"}}]}}")
    List<Product> searchByCategoriesAndColorAttribute(List<String> categories, Object attributes);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"currencyCode\": \"?1\"}}, {\"match\": {\"categories\": \"?2\"}}]}}")
    List<Product> searchByNameCurrencyCodeAndCategories(String name, String currencyCode, List<String> categories);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"currencyCode\": \"?1\"}}, {\"match\": {\"attributes.color\": \"?2\"}}]}}")
    List<Product> searchByNameCurrencyAndColorAttribute(String name, String currencyCode, Object attributes);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"categories\": \"?1\"}}, {\"match\": {\"attributes.color\": \"?2\"}}]}}")
    List<Product> searchByNameCategoriesAndColorAttribute(String name, List<String> categories, Object attributes);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"currencyCode\": \"?0\"}}, {\"match\": {\"categories\": \"?1\"}}, {\"match\": {\"attributes.color\": \"?2\"}}]}}")
    List<Product> searchByCurrencyCodeCategoriesAndColorAttribute(String currencyCode, List<String> categories, Object attributes);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"currencyCode\": \"?1\"}}, {\"match\": {\"categories\": \"?2\"}}, {\"match\": {\"attributes.color\": \"?3\"}}]}}")
    List<Product> searchByAllFilters(String name, String currencyCode, List<String> categories, Object attributes);
}
