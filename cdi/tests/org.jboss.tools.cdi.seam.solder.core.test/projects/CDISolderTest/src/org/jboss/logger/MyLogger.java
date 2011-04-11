package org.jboss.logger;

import javax.inject.Named;

import org.jboss.seam.solder.logging.MessageLogger;

@MessageLogger
@Named("logger1")
public interface MyLogger {
	public String getMessage();
}
