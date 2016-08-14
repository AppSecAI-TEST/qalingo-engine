/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ResponseHeaderHandlerInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RequestUtil requestUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                             HttpServletResponse response, Object handler) throws Exception {
        try {

            // https://www.owasp.org/index.php/Content_Security_Policy
            
            // HTTP "Content-Security-Policy" (CSP)
            String policy = "default-src 'none'; script-src 'self' 'unsafe-inline' 'unsafe-eval' *.kxcdn.com *.sumome.com *.google-analytics.com *.googleapis.com *.gstatic.com *.google.com *.pingdom.net *.addthisedge.com *.addthis.com; connect-src 'self' sumome.com *.sumome.com *.addthis.com *.googleapis.com; img-src 'self' data: *.sumome.com *.pingdom.net *.gstatic.com *.google.com *.google-analytics.com *.googleapis.com; child-src 'self' *.addthis.com; style-src 'self' 'unsafe-inline' *.kxcdn.com *.googleapis.com; font-src 'self' *.gstatic.com";
            // Chrome
            response.addHeader("Content-Security-Policy", policy);
            // Safari
            response.addHeader("X-WebKit-CSP", policy);
            // Firefox, IE
            response.addHeader("X-Content-Security-Policy", policy);

            response.addHeader("Access-Control-Allow-Origin", "*.addthis.com *.sumome.com");

            // HTTP "X-Frame-Options"
            response.addHeader("X-Frame-Options", "DENY");
            
            // HTTP "X-Frame-Options"
            response.addHeader("X-XSS-Protection", "1; mode=block");
            
            // HTTP "X-Content-Type-Options"
            response.addHeader("X-Content-Type-Options", "nosniff");
            
            // HTTP Caches
            // No cache html
//            if("http".equalsIgnoreCase(request.getScheme())){
//                response.addHeader("Cache-Control", "public, max-age=86400, must-revalidate"); // 86400 1 jour
//                // response.addHeader("Expires", "Mon, 25 Jun 2012 21:31:12 GMT");
//            }
            
        } catch (Exception e) {
            logger.error("addClickstream failed", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception exception) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

}