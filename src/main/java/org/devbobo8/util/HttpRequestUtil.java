package org.devbobo8.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class HttpRequestUtil {

    public static <T> ResponseEntity<T> GetObject(String url, RestTemplate restTemplate, Class<T> responseType, HttpHeaders headers){
        HttpEntity requestEntity = new HttpEntity<>("", headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
    }

    private static HashSet<String> forwardHeaderKeys;
    public static HashSet<String> GetForwardHeaderKeys(){
        if (forwardHeaderKeys == null){
            synchronized (HttpRequestUtil.class) {
                if (forwardHeaderKeys == null){
                    forwardHeaderKeys = new HashSet<>(Arrays.asList(new String[]{
                            "x-request-id", "x-ot-span-context", "x-datadog-trace-id", "x-datadog-parent-id",
                            "x-datadog-sampling-priority", "traceparent", "tracestate", "x-cloud-trace-context",
                            "grpc-trace-bin", "x-b3-traceid", "x-b3-spanid", "x-b3-parentspanid", "x-b3-sampled",
                            "x-b3-flags", "user-agent"}));
                }
            }
        }
        return  forwardHeaderKeys;
    }

    public static HttpHeaders GetForwardHttpHeaders(HttpServletRequest request){
        Map<String, List<String>> headersMap = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(request.getHeaders(h))
                ));
        HttpHeaders headers = new HttpHeaders();
        HashSet<String> keys = GetForwardHeaderKeys();
        headersMap.forEach((key, values) -> {
            if (keys.contains(key)) {
                headers.addAll(key, values);
            }
        });

        return headers;
    }

}
