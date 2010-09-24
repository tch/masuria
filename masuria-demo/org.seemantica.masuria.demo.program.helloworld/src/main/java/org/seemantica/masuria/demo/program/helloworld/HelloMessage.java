/**
 * 
 */
package org.seemantica.masuria.demo.program.helloworld;

import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.program.MessageBase;


/**
 * @author tch
 *
 */
public class HelloMessage extends MessageBase {
	
	private final String content;
	
	public HelloMessage(final String content, final IElementId recipient, final IElementId sender )  {
		
		super(recipient, sender);
		this.content = content;
	}

	
	public String getContent() {
		return content;
	}

}
