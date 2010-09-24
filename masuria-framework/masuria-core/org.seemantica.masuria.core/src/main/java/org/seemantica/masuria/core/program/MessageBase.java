/**
 * 
 */
package org.seemantica.masuria.core.program;

import org.seemantica.masuria.core.datamodel.IElementId;

/**
 * @author tch
 *
 */
public abstract class MessageBase implements IMessage {

	private final IElementId sender;
	private final IElementId recipient;

	
	public MessageBase(final IElementId recipient, final IElementId sender)  {
		this.sender = sender;
		this.recipient = recipient;
	}

	public IElementId getSender()  {
		return sender;
	}


	public IElementId getRecipient() {
		return recipient;
	}

}
