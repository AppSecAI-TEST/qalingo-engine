/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public abstract class AbstractPojo<P> implements Serializable {

    private static final long serialVersionUID = 1L;

	public AbstractPojo() {
	}
	
	@JsonAnySetter
	public void handleUnknown(String key, Object value) {
		// do something: put to a Map; log a warning, whatever
	}
	
}