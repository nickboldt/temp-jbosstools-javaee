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
package org.jboss.tools.seam.core;

import java.util.Set;

import org.eclipse.core.resources.IProjectNature;

public interface ISeamProject extends IProjectNature {

	public static String NATURE_ID = "org.jboss.tools.seam.core.seam";

	/**
	 * @param name of component.
	 * @return Set of ISeamComponents by name.
	 */
	public Set<ISeamComponent> getComponentsByName(String name);

	/**
	 * @param type of scope.
	 * @return Set of ISeamComponents by Scope Type.
	 */
	public Set<ISeamComponent> getComponentsByScope(ScopeType type);

	/**
	 * @param className
	 * @return Set of ISeamComponents by class name.
	 */
	public Set<ISeamComponent> getComponentsByClass(String className);

	/**
	 * @param id of component.
	 * @return ISeamComponent by model object ID.
	 */
	public ISeamComponent getComponent(String modelObjectId);

	/**
	 * @return Set of ISeamComponents
	 */
	public Set<ISeamComponent> getComponents();
}