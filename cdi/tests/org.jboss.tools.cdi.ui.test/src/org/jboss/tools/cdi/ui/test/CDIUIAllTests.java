/******************************************************************************* 
 * Copyright (c) 2009 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.tools.cdi.ui.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.jdt.internal.core.JavaModelManager;
import org.jboss.tools.cdi.ui.test.marker.CDIMarkerResolutionTest;
import org.jboss.tools.cdi.ui.test.perspective.CDIPerspectiveTest;
import org.jboss.tools.cdi.ui.test.preferences.CDIPreferencePageTest;
import org.jboss.tools.cdi.ui.test.wizard.NewCDIClassWizardFactoryTest;
import org.jboss.tools.cdi.ui.test.wizard.NewCDIWizardTest;

/**
 * @author Alexey Kazakov
 */
public class CDIUIAllTests {

	public static Test suite() {
		// it could be done here because it is not needed to be enabled back
		JavaModelManager.getIndexManager().disable();

		TestSuite suite = new TestSuite("CDI UI Tests");
		suite.addTestSuite(CDIMarkerResolutionTest.class);
		suite.addTestSuite(CATest.class);
		suite.addTestSuite(NewCDIWizardTest.class);
		suite.addTestSuite(CDIPreferencePageTest.class);
		suite.addTestSuite(NewCDIClassWizardFactoryTest.class);
		suite.addTestSuite(CDIPerspectiveTest.class);

		return suite;
	}
}