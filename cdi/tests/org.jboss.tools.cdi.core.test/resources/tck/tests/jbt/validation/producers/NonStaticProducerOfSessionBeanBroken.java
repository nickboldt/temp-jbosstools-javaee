package org.jboss.jsr299.tck.tests.jbt.validation.producers;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;

@Stateless
public class NonStaticProducerOfSessionBeanBroken {

	@Produces public FunnelWeaver<String> anotherFunnelWeaver;
}