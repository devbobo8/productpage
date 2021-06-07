package org.devbobo8.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.devbobo8.service.ProductService;
import org.devbobo8.service.module.Product;
import org.devbobo8.serviceclients.RatingClient;
import org.devbobo8.serviceclients.ReviewClient;
import org.devbobo8.serviceclients.model.Ratings;
import org.devbobo8.serviceclients.model.RequestResult;
import org.devbobo8.serviceclients.model.Reviews;
import org.devbobo8.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/api/v1")
public class APIController {
    final ProductService productService;
    final RatingClient ratingClient;
    final ReviewClient reviewClient;
    final ObjectMapper objectMapper;

    @Autowired
    public APIController(ProductService productService, RatingClient ratingClient, ReviewClient reviewClient, ObjectMapper objectMapper){
        this.productService = productService;
        this.ratingClient = ratingClient;
        this.reviewClient = reviewClient;
        this.objectMapper = objectMapper;
    }

    @RequestMapping("/products")
    public void GetProducts(HttpServletResponse response) throws IOException {
        Product[] products = productService.GetProducts();
        response.getWriter().write(objectMapper.writeValueAsString(products));
        response.setStatus(HttpStatus.OK.value());
    }

    @RequestMapping("/products/{id}")
    public void GetProductById(@PathVariable String id, HttpServletResponse response) throws IOException {
        Product product = productService.GetProduct(id);
        response.getWriter().write(objectMapper.writeValueAsString(product));
        response.setStatus(HttpStatus.OK.value());
    }

    @RequestMapping("/products/{id}/ratings")
    public void GetProductRatings(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHeaders headers = HttpRequestUtil.GetForwardHttpHeaders(request);
        RequestResult<Ratings> result = ratingClient.GetRatings(id, headers);
        if (Strings.isNotEmpty(result.getError())) {
            response.getWriter().write(result.getError());
            response.setStatus(result.getStatusCode());
        } else {
            response.getWriter().write(objectMapper.writeValueAsString(result.getData()));
            response.setStatus(result.getStatusCode());
        }
    }

    @RequestMapping("/products/{id}/reviews")
    public void GetProductReviews(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHeaders headers = HttpRequestUtil.GetForwardHttpHeaders(request);
        RequestResult<Reviews> result = reviewClient.GetReviews(id, headers);
        if (Strings.isNotEmpty(result.getError())) {
            response.getWriter().write(result.getError());
            response.setStatus(result.getStatusCode());
        } else {
            response.getWriter().write(objectMapper.writeValueAsString(result.getData()));
            response.setStatus(result.getStatusCode());
        }
    }
}
