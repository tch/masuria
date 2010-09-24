/**
 * 
 */
package org.seemantica.masuria.core.barrier;

import org.seemantica.masuria.core.IPeerBulkSynchronous;
import org.seemantica.masuria.core.IPeerLevelComponent;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.program.IJob;
import org.seemantica.masuria.core.program.IMessage;


/**
 * @author tch
 *
 */
public interface IPeerBarrier extends IBarrier, IPeerLevelComponent<IMasterBarrier>,
                                      IPeerBulkSynchronous {
	
	void await(IJob<? extends IElement, ? extends IMessage> job);

}
