package org.jboss.jsr299.tck.tests.jbt.quickfixes;

import javax.ejb.Stateful;
import javax.enterprise.inject.Disposes;
import javax.persistence.EntityManager;

@Stateful
public class NotBusinessMethod_BrokenNoInterface {

	void disposeEntityManager(@Disposes EntityManager em) {
	}
}