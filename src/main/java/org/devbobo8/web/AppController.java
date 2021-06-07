package org.devbobo8.web;


import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AppController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String names[] = request.getParameterValues("username");
        if (names != null && names.length > 0) {
            request.getSession().setAttribute("user", names[0]);
        }
        String referrer = request.getHeader("referer");
        if (Strings.isNotEmpty(referrer)) {
            try {
                response.sendRedirect(referrer);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("/index");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referrer = request.getHeader("referer");
        request.getSession().removeAttribute("user");
        if (Strings.isNotEmpty(referrer)) {
            try {
                response.sendRedirect(referrer);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("/index");
    }

    @RequestMapping(value = "/health")
    public void health(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println("Product page is healthy");
    }
}
