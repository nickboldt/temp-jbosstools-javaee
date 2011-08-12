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
package org.jboss.tools.seam.ui.test.el;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.QualifiedName;
import org.jboss.tools.jst.text.ext.hyperlink.ELHyperlink;
import org.jboss.tools.jst.text.ext.hyperlink.ELHyperlinkDetector;
import org.jboss.tools.jst.text.ext.test.HyperlinkTestUtil;
import org.jboss.tools.jst.text.ext.test.HyperlinkTestUtil.TestHyperlink;
import org.jboss.tools.jst.text.ext.test.HyperlinkTestUtil.TestRegion;
import org.jboss.tools.test.util.TestProjectProvider;

public class ELExprPartitionerTest extends TestCase {
	TestProjectProvider provider = null;
	IProject project = null;
	boolean makeCopy = false;
	private static final String PROJECT_NAME = "numberguess";
	private static final String PAGE_NAME = "/web/giveup.jspx";

	public static Test suite() {
		return new TestSuite(ELExprPartitionerTest.class);
	}
	
	public static final QualifiedName IS_KB_NATURES_CHECK_NEED = new QualifiedName(
			"", "Is KB natures check"); //$NON-NLS-1$
	public static final QualifiedName IS_JSF_NATURES_CHECK_NEED = new QualifiedName(
			"", "Is JSF natures check"); //$NON-NLS-1$
	public static final QualifiedName IS_JSF_CHECK_NEED = new QualifiedName(
			"", "Is JSF check"); //$NON-NLS-1$
	private String isKbNatureCheck = null;
	private String isJsfNatureCheck = null;
	private String isJsfCheck = null;
	
	public void setUp() throws Exception {
		//System.out.println(">>>> ELExprPartitionerTest >>>>");
		provider = new TestProjectProvider("org.jboss.tools.seam.ui.test", "projects/" + PROJECT_NAME, PROJECT_NAME, makeCopy); 
		project = provider.getProject();
		Throwable exception = null;
		
		assertNull("An exception caught: " + (exception != null? exception.getMessage() : ""), exception);
		if (project != null) {
			isKbNatureCheck = project.getPersistentProperty(IS_KB_NATURES_CHECK_NEED);
//			System.out.println("Before: Is KB natures check: '" + isKbNatureCheck + "'");
			project.setPersistentProperty(IS_KB_NATURES_CHECK_NEED, //$NON-NLS-1$
					Boolean.toString(false));
			isJsfNatureCheck = project.getPersistentProperty(IS_JSF_NATURES_CHECK_NEED);
//			System.out.println("Before: Is JSF natures check: '" + isJsfNatureCheck + "'");
			project.setPersistentProperty(IS_JSF_NATURES_CHECK_NEED, //$NON-NLS-1$
					Boolean.toString(false));
			isJsfCheck = project.getPersistentProperty(IS_JSF_CHECK_NEED);
//			System.out.println("Before: Is JSF check: '" + isJsfCheck + "'");
			project.setPersistentProperty(IS_JSF_CHECK_NEED, //$NON-NLS-1$
					Boolean.toString(false));
//			System.out.println("While Testing: Is KB natures check: '" + project.getPersistentProperty(IS_KB_NATURES_CHECK_NEED) + "'");
//			System.out.println("While Testing: Is JSF natures check: '" + project.getPersistentProperty(IS_JSF_NATURES_CHECK_NEED) + "'");
//			System.out.println("While Testing: Is JSF check: '" + project.getPersistentProperty(IS_JSF_CHECK_NEED) + "'");
		}
	}

	protected void tearDown() throws Exception {
		if (project != null) {
			project.setPersistentProperty(IS_KB_NATURES_CHECK_NEED, //$NON-NLS-1$
					isKbNatureCheck);
//			System.out.println("Restored default: Is KB natures check: '" + project.getPersistentProperty(IS_KB_NATURES_CHECK_NEED) + "'");
			project.setPersistentProperty(IS_JSF_NATURES_CHECK_NEED, //$NON-NLS-1$
					isJsfNatureCheck);
//			System.out.println("Restored default: Is JSF natures check: '" + project.getPersistentProperty(IS_JSF_NATURES_CHECK_NEED) + "'");
			project.setPersistentProperty(IS_JSF_CHECK_NEED, //$NON-NLS-1$
					isJsfCheck);
//			System.out.println("Restored default: Is JSF check: '" + project.getPersistentProperty(IS_JSF_CHECK_NEED) + "'");
		}
		if(provider != null) {
			provider.dispose();
		}
		//System.out.println("<<<< ELExprPartitionerTest <<<<");
	}

	public void testELExprPartitioner() throws Exception{

		ArrayList<TestRegion> regionList = new ArrayList<TestRegion>();
		//regionList.add(new TestRegion("#{", new TestHyperlink[]{}));
		regionList.add(new TestRegion(673, 6/*"Message"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open bundle 'demo.bundle.Messages'", "Messages.properties")}));
		regionList.add(new TestRegion(681, 7/*"question"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open property 'question' of bundle 'demo.bundle.Messages'", "Messages.properties")}));
		
		//regionList.add(new TestRegion("#{", new TestHyperlink[]{}));
		regionList.add(new TestRegion(756, 6/*"Message"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open bundle 'demo.bundle.Messages'", "Messages.properties")}));
		regionList.add(new TestRegion(764, 7/*"question"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open property 'question' of bundle 'demo.bundle.Messages'", "Messages.properties")}));
		
		//regionList.add(new TestRegion("#{", new TestHyperlink[]{}));
		regionList.add(new TestRegion(863, 6/*"Message"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open bundle 'demo.bundle.Messages'", "Messages.properties")}));
		regionList.add(new TestRegion(871, 9/*"info_start"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open property 'info_start' of bundle 'demo.bundle.Messages'", "Messages.properties")}));
		
		//regionList.add(new TestRegion("#{", new TestHyperlink[]{}));
		regionList.add(new TestRegion(909, 10/*"numberGuess"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open 'NumberGuess - org.jboss.seam.example.numberguess'", "NumberGuess.java")}));
		regionList.add(new TestRegion(921, 15/*"remainingGuesses"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open 'NumberGuess.getRemainingGuesses() - org.jboss.seam.example.numberguess'", "NumberGuess.java")}));
		
		//regionList.add(new TestRegion("#{", new TestHyperlink[]{}));
		regionList.add(new TestRegion(964, 6/*"Message"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open bundle 'demo.bundle.Messages'", "Messages.properties")}));
		regionList.add(new TestRegion(972, 10/*"info_finish"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open property 'info_finish' of bundle 'demo.bundle.Messages'", "Messages.properties")}));
		
		//regionList.add(new TestRegion("#{", new TestHyperlink[]{}));
		regionList.add(new TestRegion(1022, 6/*"Message"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open bundle 'demo.bundle.Messages'", "Messages.properties")}));
		regionList.add(new TestRegion(1030, 9/*"button_yes"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open property 'button_yes' of bundle 'demo.bundle.Messages'", "Messages.properties")}));
		
		//regionList.add(new TestRegion("#{", new TestHyperlink[]{}));
		regionList.add(new TestRegion(1091, 6/*"Message"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open bundle 'demo.bundle.Messages'", "Messages.properties")}));
		regionList.add(new TestRegion(1099, 8/*"button_no"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open property 'button_no' of bundle 'demo.bundle.Messages'", "Messages.properties")}));
		
		//regionList.add(new TestRegion("#{", new TestHyperlink[]{}));
		regionList.add(new TestRegion(1157, 10/*"numberGuess"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open 'NumberGuess - org.jboss.seam.example.numberguess'", "NumberGuess.java")}));
		regionList.add(new TestRegion(1169, 12/*"possibilities"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open 'NumberGuess.getPossibilities() - org.jboss.seam.example.numberguess'", "NumberGuess.java")}));
		
		//regionList.add(new TestRegion("#{", new TestHyperlink[]{}));
		regionList.add(new TestRegion(1237, 13/*"_localVariable"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open 'Iterator.next() - java.util'")}));
		regionList.add(new TestRegion(1252, 7/*"intValue"*/, new TestHyperlink[]{new TestHyperlink(ELHyperlink.class, "Open 'Integer.intValue() - java.lang'")}));
		
		HyperlinkTestUtil.checkRegions(project, PAGE_NAME, regionList, new ELHyperlinkDetector());
		
	}
	
}
