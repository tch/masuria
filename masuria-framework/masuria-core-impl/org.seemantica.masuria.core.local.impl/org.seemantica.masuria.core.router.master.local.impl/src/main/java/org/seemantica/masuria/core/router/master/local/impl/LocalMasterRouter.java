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
package org.seemantica.masuria.core.router.master.local.impl;

import org.seemantica.masuria.core.MasterLevelComponentBase;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.manager.peer.IPeerManager;
import org.seemantica.masuria.core.program.IMessage;
import org.seemantica.masuria.core.router.IMasterRouter;



public class LocalMasterRouter extends MasterLevelComponentBase implements IMasterRouter {
	

	@Override
	public void dispatch(final IMessage message) {

		final IPeerManager pm = clusterManager.getPeerManagerForElement(message.getRecipient());
		
		pm.getMessageRouter().deposit(message);
	}

	@Override
	public Iterable<? extends IMessage> messages(final IElementId recipient) {
		
		final IPeerManager pm = clusterManager.getPeerManagerForElement(recipient);
		
		return pm.getMessageRouter().messages(recipient);
	}


}
