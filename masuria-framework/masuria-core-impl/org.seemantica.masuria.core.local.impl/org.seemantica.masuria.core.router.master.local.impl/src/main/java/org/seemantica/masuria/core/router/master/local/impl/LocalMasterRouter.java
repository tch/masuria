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
