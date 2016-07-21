/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.jms.entitycheck.producer;

import org.hoteia.qalingo.core.jms.AbstractMessageJms;

public class EntityCheckMessageJms extends AbstractMessageJms {

    private Long objectId;
    private String objectType;

    public Long getObjectId() {
        return objectId;
    }
    
    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
    
    public String getObjectType() {
        return objectType;
    }
    
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    
}