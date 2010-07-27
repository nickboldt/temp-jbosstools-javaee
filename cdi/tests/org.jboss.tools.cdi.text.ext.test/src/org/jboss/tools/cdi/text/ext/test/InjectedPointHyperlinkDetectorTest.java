package org.jboss.tools.cdi.text.ext.test;

import java.util.ArrayList;

import org.eclipse.jface.text.Region;
import org.jboss.tools.cdi.text.ext.hyperlink.InjectedPointHyperlinkDetector;
import org.jboss.tools.cdi.text.ext.hyperlink.ProducerDisposerHyperlinkDetector;

import junit.framework.Test;
import junit.framework.TestSuite;


public class InjectedPointHyperlinkDetectorTest extends HyperlinkDetectorTest {

	public void testInjectedPointHyperlinkDetector() throws Exception {
		ArrayList<Region> regionList = new ArrayList<Region>();
		regionList.add(new Region(115, 6)); // Inject
		regionList.add(new Region(133, 6)); // Logger
		regionList.add(new Region(140, 6)); // logger
		regionList.add(new Region(196, 6)); // logger
		regionList.add(new Region(250, 6)); // logger
		
		checkRegions("JavaSource/org/jboss/jsr299/tck/tests/lookup/injectionpoint/LoggerConsumer.java", regionList, new InjectedPointHyperlinkDetector());
	}

	public void testInjectedProducerMethodParametersHyperlinkDetector() throws Exception {
		ArrayList<Region> regionList = new ArrayList<Region>();
		regionList.add(new Region(551, 9)); // order
		
		checkRegions("JavaSource/org/jboss/jsr299/tck/tests/jbt/openon/CustomProducerImpl.java", regionList, new InjectedPointHyperlinkDetector());
	}
	
	public void testInjectedConstructorParametersHyperlinkDetector() throws Exception {
		ArrayList<Region> regionList = new ArrayList<Region>();
		regionList.add(new Region(880, 6));
		regionList.add(new Region(894, 3)); // Fox
		regionList.add(new Region(898, 3));
		regionList.add(new Region(975, 3));
		regionList.add(new Region(979, 3));
		regionList.add(new Region(1017, 3));

		checkRegions("JavaSource/org/jboss/jsr299/tck/tests/context/dependent/FoxFarm.java", regionList, new InjectedPointHyperlinkDetector());
	}
	
	public void testInjectedInitializerParametersHyperlinkDetector() throws Exception {
		ArrayList<Region> regionList = new ArrayList<Region>();
		regionList.add(new Region(880, 6));
		regionList.add(new Region(894, 3)); // Fox
		regionList.add(new Region(898, 3));
		regionList.add(new Region(972, 3));
		regionList.add(new Region(976, 3));
		regionList.add(new Region(1014, 3));

		checkRegions("JavaSource/org/jboss/jsr299/tck/tests/context/dependent/FoxHole.java", regionList, new InjectedPointHyperlinkDetector());
	}

}