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
