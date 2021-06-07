package org.devbobo8.serviceclients.impl;

import org.devbobo8.serviceclients.BookDetailClient;
import org.devbobo8.serviceclients.model.BookDetail;
import org.devbobo8.serviceclients.model.RequestResult;
import org.devbobo8.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookDetailsClientImpl implements BookDetailClient {

    private final RestTemplate restTemplate;

    @Value("${services.detail.name}")
    private String detailServiceName;
    @Value("${services.detail.domain}")
    private String detailServiceDomain;
    @Value("${services.detail.port}")
    private int detailServicePort;
    @Value("${services.detail.path}")
    private String detailServicePath;

    @Autowired
    public BookDetailsClientImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public RequestResult<BookDetail> GetDetail(String productId, HttpHeaders headers){
        ResponseEntity<BookDetail> responseEntity = HttpRequestUtil.GetObject(getDetailServiceUrl(productId), restTemplate, BookDetail.class, null, headers);
        RequestResult<BookDetail> result = new RequestResult<>();
        if (responseEntity.getStatusCode() == HttpStatus.OK){
            result.setData(responseEntity.getBody());
        } else {
            result.setError("Sorry, product details are currently unavailable for this book.");
            result.setStatusCode(responseEntity.getStatusCode().value());
        }
        return result;
    }

    private String getDetailServiceUrl(String productId){
        return String.format("http://%s%s:%d/%s/%s", detailServiceName, detailServiceDomain, detailServicePort, detailServicePath, productId);
    }
}
