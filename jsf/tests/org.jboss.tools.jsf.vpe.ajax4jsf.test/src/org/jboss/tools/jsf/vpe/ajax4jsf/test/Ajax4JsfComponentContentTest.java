package org.jboss.tools.jsf.vpe.ajax4jsf.test;

import org.jboss.tools.vpe.ui.test.ComponentContentTest;

public class Ajax4JsfComponentContentTest extends ComponentContentTest {

	
	
	public Ajax4JsfComponentContentTest(String name) {
		super(name);
		setCheckWarning(false);
	}

	public void testAjaxListener() throws Throwable {
		performInvisibleTagTest("components/ajaxListener.xhtml", "ajaxListener"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public void testKeepAlive() throws Throwable {
		performContentTest("components/keepAlive.xhtml");//$NON-NLS-1$
	}
	
	public void testActionparam() throws Throwable {
		performInvisibleTagTest("components/actionparam.xhtml", "actionparam1"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public void testCommandButton() throws Throwable {
		performContentTest("components/commandButton.xhtml");//$NON-NLS-1$
	}
	
	public void testCommandLink() throws Throwable {
		performContentTest("components/commandLink.xhtml");//$NON-NLS-1$
	}
	
	public void _testForm() throws Throwable {
		performContentTest("components/form.xhtml");//$NON-NLS-1$
	}
	
	public void testHtmlCommandLink() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testJsFunction() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testInclude() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testLoadBundle() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testLoadScript() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testLoadStyle() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testLog() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testMediaOutput() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testOutputPanel() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testPage() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testPoll() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testPortlet() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testPush() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testRegion() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testRepeat() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testStatus() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	public void testSupport() throws Throwable {
		assertTrue("it is necessary to add a body of the test ", false);//$NON-NLS-1$
	}
	
	protected String getTestProjectName() {
		return Ajax4JsfAllTests.IMPORT_PROJECT_NAME;
	}

}
