package org.devbobo8.serviceclients.impl;

import org.devbobo8.serviceclients.RatingClient;
import org.devbobo8.serviceclients.model.Ratings;
import org.devbobo8.serviceclients.model.RequestResult;
import org.devbobo8.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RatingClientImpl implements RatingClient {

    private final RestTemplate restTemplate;

    @Value("${services.rating.name}")
    private String ratingServiceName;
    @Value("${services.rating.domain}")
    private String ratingServiceDomain;
    @Value("${services.rating.port}")
    private int ratingServicePort;
    @Value("${services.rating.path}")
    private String ratingServicePath;

    public RatingClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RequestResult<Ratings> GetRatings(String productId, HttpHeaders headers){
        ResponseEntity<Ratings> responseEntity = HttpRequestUtil.GetObject(getRatingServiceUrl(productId), restTemplate, Ratings.class, null, headers);
        RequestResult<Ratings> result = new RequestResult<>();
        if (responseEntity.getStatusCode() == HttpStatus.OK){
            result.setData(responseEntity.getBody());
        } else {
            result.setError("Sorry, product ratings are currently unavailable for this book.");
            result.setStatusCode(responseEntity.getStatusCode().value());
        }
        return result;
    }

    private String getRatingServiceUrl(String productId){
        return String.format("http://%s%s:%d/%s/%s", ratingServiceName, ratingServiceDomain, ratingServicePort, ratingServicePath, productId);
    }
}
