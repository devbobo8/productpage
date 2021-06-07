package org.devbobo8.serviceclients.impl;

import org.devbobo8.serviceclients.ReviewClient;
import org.devbobo8.serviceclients.model.RequestResult;
import org.devbobo8.serviceclients.model.Reviews;
import org.devbobo8.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReviewClientImpl implements ReviewClient {

    private final RestTemplate restTemplate;

    @Value("${services.review.name}")
    private String reviewServiceName;
    @Value("${services.review.domain}")
    private String reviewServiceDomain;
    @Value("${services.review.port}")
    private int reviewServicePort;
    @Value("${services.review.path}")
    private String reviewServicePath;

    public ReviewClientImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public RequestResult<Reviews> GetReviews(String productId, HttpHeaders headers){
        ResponseEntity<Reviews> responseEntity = HttpRequestUtil.GetObject(getReviewServiceUrl(productId), restTemplate, Reviews.class, null, headers);
        RequestResult<Reviews> result = new RequestResult<>();
        if (responseEntity.getStatusCode() == HttpStatus.OK){
            result.setData(responseEntity.getBody());
        } else {
            result.setError("Sorry, product reviews are currently unavailable for this book.");
            result.setStatusCode(responseEntity.getStatusCode().value());
        }
        return result;
    }

    private String getReviewServiceUrl(String productId){
        return String.format("http://%s%s:%d/%s/%s", reviewServiceName, reviewServiceDomain, reviewServicePort, reviewServicePath, productId);
    }
}

