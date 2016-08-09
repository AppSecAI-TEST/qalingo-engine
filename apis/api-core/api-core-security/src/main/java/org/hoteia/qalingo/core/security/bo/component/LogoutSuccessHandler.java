/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.security.bo.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.security.RedirectStrategy;
import org.hoteia.qalingo.core.service.BackofficeUrlService;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component(value="logoutSuccessHandler")
public class LogoutSuccessHandler extends org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected BackofficeUrlService backofficeUrlService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected RedirectStrategy redirectStrategy;
	
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            					Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            // do something 
        }
        
		try {
		    requestUtil.cleanCurrentUser(request);
		    
	        String url = backofficeUrlService.generateRedirectUrl(BoUrls.HOME, requestUtil.getRequestData(request));
	        setDefaultTargetUrl(url);
	        
		} catch (Exception e) {
			logger.error("", e);
		}
        
        String targetUrl = determineTargetUrl(request, response);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

}