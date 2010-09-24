/**
 * 
 */
package org.seemantica.masuria.core.router;

import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.program.IMessage;


/**
 * @author tch
 *
 * TODO;tch: provide iterable interface for querying elements that received any message
 */
public interface IRouter {

	void dispatch(final IMessage message);

	Iterable<? extends IMessage> messages(final IElementId recipient);
	
}
