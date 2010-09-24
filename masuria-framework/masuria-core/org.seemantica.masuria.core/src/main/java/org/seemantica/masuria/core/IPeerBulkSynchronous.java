package org.seemantica.masuria.core;

public interface IPeerBulkSynchronous {

	/**
	 * Method executed after all other peers finished given global step.
	 *
	 * @return whether given component votes to finish;
	 */
	boolean postStep();
}
