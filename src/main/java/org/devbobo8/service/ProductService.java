package org.devbobo8.service;

import org.devbobo8.service.module.Product;

public interface ProductService {
    Product[] GetProducts();
    Product GetProduct(String id);
}
