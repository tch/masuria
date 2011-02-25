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
