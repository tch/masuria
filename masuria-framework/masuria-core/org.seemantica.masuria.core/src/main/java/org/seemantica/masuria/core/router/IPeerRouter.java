package org.seemantica.masuria.core.router;

import org.seemantica.masuria.core.IPeerBulkSynchronous;
import org.seemantica.masuria.core.IPeerLevelComponent;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.program.IMessage;


public interface IPeerRouter extends IRouter, IPeerLevelComponent<IMasterRouter>,
                                            IPeerBulkSynchronous {

	/**
	 *  Deposits the message in this particular peer message router. It's just like send but it assumes the message
	 *  belongs to a partition that exists on the same peer manager as the peer message router . 
	 * 
	 * 
	 * @param message message to be delivered
	 */
	public void deposit(IMessage message);
	
	Iterable<IElementId> getAllRecipients();
	
	int nOfMessages();
	
	int nOfMessagesInFuture();
	
}
