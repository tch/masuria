package org.seemantica.masuria.core.partitioner.peer.local.impl;

import org.seemantica.masuria.core.PeerLevelComponentBase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.partitioner.IMasterPartitioner;
import org.seemantica.masuria.core.partitioner.IPeerPartitioner;
import org.seemantica.masuria.core.registry.IDescriptor;


/**
 * Simple local partitioner that delegates almost all of its calls to the cluster partitioner
 * that holds a map of elementId->peerManagerDesc.
 * 
 * @author tch
 *
 * NOTE;tch: the peer part of the partitioner is implemented by means of cluster part
 */
 
public class LocalPeerPartitioner extends PeerLevelComponentBase<IMasterPartitioner> implements IPeerPartitioner {
	

	@Override
	public boolean isOnThisPeerPartition(final IElementId eId) {
		
		IDescriptor descriptor = getClusterLevelComponent().getPartition(eId);
		
		//TODO;tch: assumption peer~partition
		return peerManager.getDescriptor().equals(descriptor);
	}

	@Override
	public IDescriptor getPartition(final IElementId element) {

		return getClusterLevelComponent().getPartition(element);
	}

	@Override
	public IDescriptor getPartition(final IElement element) {

		return getClusterLevelComponent().getPartition(element);
	}

	@Override
	public IMasterPartitioner getClusterLevelComponent() {

		return peerManager.getClusterManager().getPartitioner();
	}


}
