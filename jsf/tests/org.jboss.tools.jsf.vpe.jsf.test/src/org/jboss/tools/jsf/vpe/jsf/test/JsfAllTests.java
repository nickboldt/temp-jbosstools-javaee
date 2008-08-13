/******************************************************************************* 
 * Copyright (c) 2007 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.jsf.vpe.jsf.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE1105Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE1460Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE1479Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE1484Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE1494Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE1615Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE1720Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE1730Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE1744Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE2010Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE2119Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE2219Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE2297Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE2434Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE2505Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE2582Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE2584Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE2594Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE675Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE788Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JBIDE924Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JsfJbide1467Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JsfJbide1501Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JsfJbide1568Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JsfJbide1718Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JsfJbide2170Test;
import org.jboss.tools.jsf.vpe.jsf.test.jbide.JsfJbide2362Test;
import org.jboss.tools.vpe.ui.test.VpeTestSetup;
import org.jboss.tools.vpe.ui.test.beans.ImportBean;

/**
 * Class for testing all RichFaces components
 * 
 * @author sdzmitrovich
 * 
 */

public class JsfAllTests {

	public static final String IMPORT_PROJECT_NAME = "jsfTest"; //$NON-NLS-1$
	
	public static Test suite() {

		TestSuite suite = new TestSuite("Tests for Vpe Jsf components"); // $NON-NLS-1$ //$NON-NLS-1$
		// $JUnit-BEGIN$
		suite.addTestSuite(JsfComponentTest.class);
		suite.addTestSuite(JsfJbide1467Test.class);
		suite.addTestSuite(JsfJbide1501Test.class);
		suite.addTestSuite(JBIDE1484Test.class);
		suite.addTestSuite(JsfJbide1568Test.class);
		suite.addTestSuite(JBIDE1615Test.class);
		suite.addTestSuite(JBIDE1479Test.class);
		suite.addTestSuite(JBIDE788Test.class);
		suite.addTestSuite(JBIDE1105Test.class);
		suite.addTestSuite(JBIDE1744Test.class);
		suite.addTestSuite(JBIDE675Test.class);
		suite.addTestSuite(JBIDE1460Test.class);
		suite.addTestSuite(JBIDE1720Test.class);
		suite.addTestSuite(JsfJbide1718Test.class);
		suite.addTestSuite(JBIDE1730Test.class);
		suite.addTestSuite(JBIDE1494Test.class);
		suite.addTestSuite(JBIDE2297Test.class);
		suite.addTestSuite(JsfJbide2170Test.class);
		suite.addTestSuite(JBIDE2434Test.class);
		suite.addTestSuite(JsfJbide2362Test.class);
		suite.addTestSuite(JBIDE2119Test.class);
		suite.addTestSuite(JBIDE2219Test.class);
		suite.addTestSuite(JBIDE2505Test.class);
		suite.addTestSuite(JBIDE2584Test.class);
	    suite.addTestSuite(ElPreferencesTestCase.class);
	    suite.addTestSuite(JBIDE2010Test.class);
	    suite.addTestSuite(JBIDE2582Test.class);
	    suite.addTestSuite(JBIDE2594Test.class);
		suite.addTestSuite(JBIDE924Test.class);
		// $JUnit-END$
		// added by Max Areshkau
		// add here projects which should be imported for junit tests
		List<ImportBean> projectToImport = new ArrayList<ImportBean>();
		ImportBean importBean = new ImportBean();
		importBean.setImportProjectName(JsfAllTests.IMPORT_PROJECT_NAME);
		importBean.setImportProjectPath(JsfTestPlugin.getPluginResourcePath());
		projectToImport.add(importBean);

		return new VpeTestSetup(suite, projectToImport);

	}

}
