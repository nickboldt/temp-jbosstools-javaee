/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.cdi.bot.test.quickfix.test;

import org.jboss.tools.cdi.bot.test.CDIAllBotTests;
import org.jboss.tools.cdi.bot.test.annotations.CDIWizardType;
import org.jboss.tools.cdi.bot.test.quickfix.base.BeansXMLQuickFixTestBase;
import org.jboss.tools.ui.bot.ext.RequirementAwareSuite;
import org.jboss.tools.ui.bot.ext.config.Annotations.Require;
import org.jboss.tools.ui.bot.ext.config.Annotations.Server;
import org.jboss.tools.ui.bot.ext.config.Annotations.ServerState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test operates on quick fixes used for validation errors of beans.xml
 * 
 * @author Jaroslav Jankovic
 */

@Require(clearProjects = true, perspective = "Java EE", 
		 server = @Server(state = ServerState.NotRunning,
		 version = "6.0", operator = ">="))
@RunWith(RequirementAwareSuite.class)
@SuiteClasses({ CDIAllBotTests.class })
public class BeansXMLValidationQuickFixTest extends BeansXMLQuickFixTestBase {

	@Override
	public String getProjectName() {
		return "CDIQuickFixBeanXMLTest";
	}
	
	@Test
	public void testNoBeanComponent() {

		String bean = "A1";
		beansHelper.createBeansXMLWithAlternative(getProjectName(), getPackageName(), bean);
		
		resolveAddNewAlternative(bean, getPackageName());
		
		assertTrue(isBeanXMLValidationProblemsEmpty());		
	}
	
	@Test
	public void testNoStereotypeAnnotation() {

		String stereotype = "S1";
		beansHelper.createBeansXMLWithStereotype(getProjectName(), getPackageName(), stereotype);
		
		resolveAddNewStereotype(stereotype, getPackageName());
		
		assertTrue(isBeanXMLValidationProblemsEmpty());		
		
	}
	
	@Test
	public void testNoAlternativeBeanComponent() {

		String bean = "B1";
		
		wizard.createCDIComponent(CDIWizardType.BEAN, bean, getPackageName(), null);
		
		beansHelper.createBeansXMLWithAlternative(getProjectName(), getPackageName(), bean);
		
		resolveAddAlternativeToExistingComponent(bean, getPackageName());
		
		assertTrue(isBeanXMLValidationProblemsEmpty());	
		
	}
	
	@Test
	public void testNoAlternativeStereotypeAnnotation() {

		String stereotype = "S2";
		
		wizard.createCDIComponent(CDIWizardType.STEREOTYPE, stereotype, getPackageName(), null);
		
		beansHelper.createBeansXMLWithStereotype(getProjectName(), getPackageName(), stereotype);
		
		resolveAddAlternativeToExistingComponent(stereotype, getPackageName());
		
		assertTrue(isBeanXMLValidationProblemsEmpty());	
		
	}
	
	@Test
	public void testNoInterceptor() {

		String interceptor = "I1";
		beansHelper.createBeansXMLWithInterceptor(getProjectName(), 
				getPackageName(), interceptor);
		
		resolveAddNewInterceptor(interceptor, getPackageName());
		
		assertTrue(isBeanXMLValidationProblemsEmpty());		
		
	}
	
	@Test
	public void testNoDecorator() {

		String decorator = "D1";
		beansHelper.createBeansXMLWithDecorator(getProjectName(), getPackageName(), decorator);
		
		resolveAddNewDecorator(decorator, getPackageName());
		
		assertTrue(isBeanXMLValidationProblemsEmpty());		
		
	}

}
