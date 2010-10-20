package org.seemantica.masuria.core.partitioner.master.local.impl;

import java.util.HashMap;
import java.util.Map;

import org.seemantica.masuria.core.MasterLevelComponentBase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.partitioner.IMasterPartitioner;
import org.seemantica.masuria.core.registry.IDescriptor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;


public class LocalMasterPartitioner extends MasterLevelComponentBase implements IMasterPartitioner {

	private Map<IElementId, IDescriptor> mapping = new HashMap<IElementId, IDescriptor>();
	
	private SetMultimap<IDescriptor, IElementId> partitions = HashMultimap.create();
	
	
	@Override
	public IDescriptor getPartition(final IElement element) {

		return mapping.get(element.getId());
	}

	@Override
	public IDescriptor getPartition(IElementId elementId) {
		
		return mapping.get(elementId);
	}


	public void addMaping(final IElement element, final IDescriptor descriptor)  {
		
		mapping.put(element.getId(), descriptor);
		partitions.put(descriptor, element.getId());
	}
	
	@Override
	public Iterable<IElementId> getElementIds(final IDescriptor descriptor)  {
		
		return partitions.get(descriptor); 
	}

	@Override
	public boolean isOnPeerPartition(final IElementId id, final IDescriptor partition) {

		return mapping.get(id).equals(partition);
	}

}
