/******************************************************************************* 
 * Copyright (c) 2011 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.tools.cdi.seam.config.core.definition;

import org.eclipse.jdt.core.IType;

/**
 * 
 * @author Viacheslav Kabanovich
 *
 */
public class SeamVirtualFieldDefinition extends AbstractSeamFieldDefinition {
	protected IType returnType;

	public SeamVirtualFieldDefinition() {}

	public void setType(IType returnType) {
		this.returnType = returnType;
	}

	public IType getType() {
		return returnType;
	}

}
