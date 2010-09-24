/**
 * 
 */
package org.seemantica.masuria.core.program;

import org.seemantica.masuria.core.datamodel.IElementId;

/**
 * @author tch
 *
 */
public interface IMessage {

	public IElementId getSender();

	public IElementId getRecipient();
	
}
