package org.devbobo8.web;

import org.apache.logging.log4j.util.Strings;
import org.devbobo8.service.ProductService;
import org.devbobo8.serviceclients.BookDetailClient;
import org.devbobo8.serviceclients.ReviewClient;
import org.devbobo8.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class ProductController {

    final BookDetailClient bookDetailClient;
    final ReviewClient reviewClient;
    final ProductService productService;

    @Autowired
    public ProductController(BookDetailClient bookDetailClient, ReviewClient reviewClient, ProductService productService){
        this.bookDetailClient = bookDetailClient;
        this.reviewClient = reviewClient;
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String root() {
        return "view/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "view/index";
    }

    @RequestMapping("/productpage")
    public String productPage(Model model, HttpServletRequest request) {
        String productId = "0";
        String userName = (String) request.getSession().getAttribute("user");
        model.addAttribute("user", userName);
        HttpHeaders headers = HttpRequestUtil.GetForwardHttpHeaders(request);
        if (Strings.isNotEmpty(userName)){
            headers.add("end-user", userName);
        }
        model.addAttribute("product", productService.GetProduct(productId));
        model.addAttribute("detail", bookDetailClient.GetDetail(productId, headers));
        model.addAttribute("reviews", reviewClient.GetReviews(productId, headers));
        return "view/productpage";
    }
}
