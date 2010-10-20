package org.seemantica.masuria.core.manager.master.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Activator implements BundleActivator {
	
	private Logger logger = LoggerFactory.getLogger(Activator.class);

	
//	public Activator() {
//		
//	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		
        logger.debug("MasterManager component activator start");

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
        logger.debug("MasterManager component activator stop");

	}

}
