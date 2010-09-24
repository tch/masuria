/**
 * 
 */
package org.seemantica.masuria.core.program;

import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;


/**
 * @author tch
 *
 */
public interface IJob<E extends IElement,
                      M extends IMessage> {

	void superStep();
	
	long getIteration();
	
	void sendMessage(M message );
	
	Iterable<M> messages();
	
	void halt();
	
	void abort();
	
	boolean isStartingElement();
	
	IElementId getId();
	
	IElement getElement();
	
}
