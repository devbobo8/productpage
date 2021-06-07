package org.devbobo8.serviceclients;

import org.devbobo8.serviceclients.model.BookDetail;
import org.devbobo8.serviceclients.model.RequestResult;
import org.springframework.http.HttpHeaders;

public interface BookDetailClient {

    RequestResult<BookDetail> GetDetail(String productId, HttpHeaders headers);
}
