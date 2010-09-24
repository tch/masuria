package org.seemantica.masuria.core.barrier.peer.local.impl;

import org.seemantica.masuria.core.PeerLevelComponentBase;
import org.seemantica.masuria.core.barrier.IMasterBarrier;
import org.seemantica.masuria.core.barrier.IPeerBarrier;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.program.IJob;
import org.seemantica.masuria.core.program.IMessage;


public class LocalPeerBarrier extends PeerLevelComponentBase<IMasterBarrier> implements IPeerBarrier {

	@Override
	public void await(final IJob<? extends IElement, ? extends IMessage> job) {
		

	}

	@Override
	public void abort(IElementId address) {
		// TODO Auto-generated method stub

	}

	@Override
	public IMasterBarrier getClusterLevelComponent() {

		return this.peerManager.getClusterManager().getBarrier();
	}

	@Override
	public boolean postStep() {
		
		return false;
	}

}
