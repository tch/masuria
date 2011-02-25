/**
 * Copyright (C) 2010 Tomasz Chodakowski <*tch (at) wp.eu*>
 *
 * This file is part of Project Masuria.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
