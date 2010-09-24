package org.seemantica.masuria.core.partitioner;

import org.seemantica.masuria.core.IPeerLevelComponent;
import org.seemantica.masuria.core.datamodel.IElementId;


public interface IPeerPartitioner extends IPartitioner, IPeerLevelComponent<IMasterPartitioner> {

	boolean isOnThisPeerPartition(IElementId id);
	
}
