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
package org.jboss.tools.struts.text.ext.hyperlink;

import org.jboss.tools.jst.text.ext.hyperlink.jsp.JSPTagAttributeValueHyperlinkPartitioner;

/**
 * @author Jeremy
 *
 */
public class StrutsBeanNameHyperlinkPartitioner extends JSPTagAttributeValueHyperlinkPartitioner {
	public static final String STRUTS_JSP_BEAN_NAME_PARTITION = "org.jboss.tools.common.text.ext.jsp.STRUTS_JSP_BEAN_NAME";

	/**
	 * @see org.jboss.tools.common.text.ext.hyperlink.JSPTagAttributeValueHyperlinkPartitioner#getPartitionType()
	 */
	protected String getPartitionType() {
		return STRUTS_JSP_BEAN_NAME_PARTITION;
	}
}