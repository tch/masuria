/**
 * Copyright (C) 2010 Tomasz Chodakowski <*tch (at) wp.eu*>
 *
 * This file is part of Project Masuria.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
