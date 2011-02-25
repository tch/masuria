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
package org.seemantica.masuria.core.database.peer.neo4j.impl;

import org.seemantica.masuria.core.PeerLevelComponentBase;
import org.seemantica.masuria.core.database.IMasterDatabase;
import org.seemantica.masuria.core.database.IPeerDatabase;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.datamodel.neo4j.impl.Neo4JElement;


public class LocalNeo4JPeerDatabase extends PeerLevelComponentBase<IMasterDatabase<Neo4JElement>> implements IPeerDatabase<Neo4JElement> {

	public LocalNeo4JPeerDatabase() {

	}
	
	@Override
	public long getNumberOfElements() {
		
		return this.getClusterLevelComponent().getNumberOfElements(peerManager.getDescriptor()); //TODO;tch: partion~peer
	}

	@Override
	public void clear() {
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public IMasterDatabase<Neo4JElement> getClusterLevelComponent() {

		return (IMasterDatabase<Neo4JElement>) this.peerManager.getClusterManager().getDatabase();
	}

	@Override
	public Iterable<Neo4JElement> getElements() {

		return this.getClusterLevelComponent().getElements(peerManager.getDescriptor()); //TODO;tch: assumption peer~partition
	}

	@Override
	public Neo4JElement getElement(final IElementId id) {
		
		return (Neo4JElement) this.getClusterLevelComponent().getElement(id);
	}

	@Override
	public Iterable<IElementId> getElementIds() {
		return this.getClusterLevelComponent().getElementIds(peerManager.getDescriptor()); //TODO;tch: assumption peer~partition
	}


}
