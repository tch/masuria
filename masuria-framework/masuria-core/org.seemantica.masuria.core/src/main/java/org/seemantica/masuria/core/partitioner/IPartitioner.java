package org.seemantica.masuria.core.partitioner;

import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.registry.IDescriptor;



public interface IPartitioner {

	IDescriptor getPartition(IElementId element );
	
	IDescriptor getPartition(IElement element );
}
