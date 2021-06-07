package org.devbobo8.serviceclients;

import org.devbobo8.serviceclients.model.Ratings;
import org.devbobo8.serviceclients.model.RequestResult;
import org.springframework.http.HttpHeaders;

public interface RatingClient {

    RequestResult<Ratings> GetRatings(String productId, HttpHeaders headers);
}
