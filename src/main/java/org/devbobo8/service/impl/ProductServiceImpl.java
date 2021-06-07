package org.devbobo8.service.impl;

import org.devbobo8.service.ProductService;
import org.devbobo8.service.module.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product[] GetProducts() {
        Product[] products = new Product[1];
        products[0] = new Product("0", "The Comedy of Errors",
                "<a href=\"https://en.wikipedia.org/wiki/The_Comedy_of_Errors\">Wikipedia Summary</a>: The Comedy of Errors is one of <b>William Shakespeare\\'s</b> early plays. It is his shortest and one of his most farcical comedies, with a major part of the humour coming from slapstick and mistaken identity, in addition to puns and word play.");
        return products;
    }

    @Override
    public Product GetProduct(String id) {
        Product[] products = GetProducts();
        int parseInt = Integer.parseInt(id);
        if (parseInt < products.length) {
            return products[parseInt];
        } else {
            return null;
        }
    }
}
