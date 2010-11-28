package org.seemantica.masuria.core.partitioner.master.local.impl;

import org.seemantica.masuria.core.MasterLevelComponentBase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.partitioner.IMasterPartitioner;
import org.seemantica.masuria.core.registry.IDescriptor;


public class LocalMasterHashPartitioner extends MasterLevelComponentBase implements IMasterPartitioner {
	
	public LocalMasterHashPartitioner() {
		super();	
	}

	@Override
	public IDescriptor getPartition(final IElement element) {

		return (IDescriptor) clusterManager.getPeerRegistry().getDescriptorFromSequenceNumber(computePartitionSequenceNumber(element.getId()));
		
	}

	@Override
	public IDescriptor getPartition(IElementId elementId) {
		
		return (IDescriptor) clusterManager.getPeerRegistry().getDescriptorFromSequenceNumber(computePartitionSequenceNumber(elementId));
	}


	@Override
	public boolean isOnPeerPartition(final IElementId id, final IDescriptor partition) {

		return getPartition(id).equals(partition);
	}

	
	private int computePartitionSequenceNumber( final IElementId id ) {
		return (int) (id.toLong() % clusterManager.getPeerRegistry().getTotalNumberOfElements());
		
	}
}
