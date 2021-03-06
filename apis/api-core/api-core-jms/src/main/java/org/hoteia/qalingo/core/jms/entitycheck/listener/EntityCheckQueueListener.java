/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.jms.entitycheck.listener;

import java.beans.ExceptionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hoteia.qalingo.core.mapper.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.hoteia.qalingo.core.jms.entitycheck.producer.EntityCheckMessageJms;

@Component(value = "entityCheckQueueListener")
public class EntityCheckQueueListener implements MessageListener, ExceptionListener {

    protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private ApplicationContext context;
	
    @Autowired
    protected XmlMapper xmlMapper;
    
    public EntityCheckQueueListener() {
    }

    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                String valueJMSMessage = tm.getText();
                
                if(StringUtils.isNotEmpty(valueJMSMessage)){
                    final EntityCheckMessageJms documentMessageJms = xmlMapper.getXmlMapper().readValue(valueJMSMessage, EntityCheckMessageJms.class);
                    
                    if (logger.isDebugEnabled()) {
                        logger.debug("Processed message, value: " + valueJMSMessage);
                    }

//                    try {
//                        SpringCamelContext camelContext = (SpringCamelContext) context.getBean("camelContext");
//                        
//                        if(!camelContext.getRouteStatus("entityCheckRoute").isStarted()){
//                            camelContext.startRoute("entityCheckRoute");
//                        }
//                        ProducerTemplate template = context.getBean("camelTemplate", ProducerTemplate.class);
//
//                        Map<String, Object> params = new HashMap<String, Object>();
//                        params.put("id", documentMessageJms.getObjectId());
//                        params.put("type", documentMessageJms.getObjectType());
//                        template.asyncSendBody("direct:startEntityCheckRoute", params);
//                        
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                } else {
                    logger.warn("Document generation: Jms Message is empty");
                }
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void exceptionThrown(Exception e) {
        logger.debug("Exception on queue listener: " + e.getCause() + ":" + e.getLocalizedMessage());
    }

}