/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.struts.model.pv.handler;

import org.jboss.tools.common.meta.action.impl.handlers.*;
import org.jboss.tools.common.model.XModelObject;

public class StrutsProjectsConfigurationRedirectHandler extends DefaultRedirectHandler {

	protected XModelObject getTrueSource(XModelObject source) {
		return source.getModel().getByPath("FileSystems/WEB-INF");
	}

}
