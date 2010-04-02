/*******************************************************************************
 * Copyright (c) 2007-2010 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.jsf.ui.editor.check;

import org.eclipse.core.resources.IProject;
import org.jboss.tools.jsf.ui.action.AddJSFNatureActionDelegate;

/**
 * 
 * @author yzhishko
 *
 */

public class AddJSFCapabilitiesDelegate extends AddJSFNatureActionDelegate {

	private static AddJSFCapabilitiesDelegate instance = new AddJSFCapabilitiesDelegate();

	private AddJSFCapabilitiesDelegate() {
	}

	public static AddJSFCapabilitiesDelegate getInstance(IProject project) {
		instance.setProject(project);
		return instance;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

}
