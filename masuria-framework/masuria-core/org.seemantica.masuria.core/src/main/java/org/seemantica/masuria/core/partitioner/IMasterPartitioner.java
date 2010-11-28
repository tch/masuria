package org.seemantica.masuria.core.partitioner;

import org.seemantica.masuria.core.IMasterLevelComponent;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.registry.IDescriptor;


public interface IMasterPartitioner extends IPartitioner, IMasterLevelComponent {


	//Iterable<IElementId> getElementIds(IDescriptor partition);
	
	boolean isOnPeerPartition(IElementId id, IDescriptor partition);

}
