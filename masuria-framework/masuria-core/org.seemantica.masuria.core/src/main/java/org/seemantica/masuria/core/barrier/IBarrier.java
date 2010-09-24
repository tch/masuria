/**
 * 
 */
package org.seemantica.masuria.core.barrier;

import org.seemantica.masuria.core.datamodel.IElementId;

/**
 * @author tch
 *
 * TODO;tch: refactor the interface to follow the dual component model, ie. cluster/peer
 */
public interface IBarrier {

	void abort(final IElementId address);

}
