package org.seemantica.masuria.core;

import org.seemantica.masuria.core.manager.peer.IPeerManager;

public interface IPeerLevelComponent<C> {

	void associateWithPeerManager(IPeerManager peerManager);
	
	C getClusterLevelComponent();
}
