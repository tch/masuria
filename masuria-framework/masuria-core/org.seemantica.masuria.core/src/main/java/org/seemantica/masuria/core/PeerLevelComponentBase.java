package org.seemantica.masuria.core;

import org.seemantica.masuria.core.manager.peer.IPeerManager;

public abstract class PeerLevelComponentBase<C> implements IPeerLevelComponent<C> {
	
	protected IPeerManager peerManager;  
	
	@Override
	public void associateWithPeerManager(final IPeerManager peerManager) {
		this.peerManager = peerManager;
		
	}

}
