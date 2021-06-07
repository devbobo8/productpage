package org.devbobo8.serviceclients;

import org.devbobo8.serviceclients.model.RequestResult;
import org.devbobo8.serviceclients.model.Reviews;
import org.springframework.http.HttpHeaders;

public interface ReviewClient {

    RequestResult<Reviews> GetReviews(String productId, HttpHeaders headers);
}
