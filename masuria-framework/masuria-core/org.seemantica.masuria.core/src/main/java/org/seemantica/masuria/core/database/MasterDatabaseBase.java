package org.seemantica.masuria.core.database;

import java.util.HashSet;
import java.util.Set;

import org.seemantica.masuria.core.MasterLevelComponentBase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.manager.peer.IPeerManager;
import org.seemantica.masuria.core.registry.IDescriptor;


public abstract class MasterDatabaseBase<E extends IElement> extends MasterLevelComponentBase implements IMasterDatabase<E> {

	@Override
	public long getNumberOfElements() {
		
		long totalSize=0;
		
		for( IPeerManager pm : clusterManager.getPeerManagers() ) {
			
			totalSize+=pm.getDatabase().getNumberOfElements();
		}
		
		return totalSize;
	}


	private Iterable<IElementId> elementIds(Iterable<E> elements) {
		
		Set<IElementId> elementIds = new HashSet<IElementId>();
		
		for(E el: elements) {

			elementIds.add(el.getId());
		}
		
		return elementIds;
	}

	
	@Override
	public Iterable<IElementId> getElementIds(final IDescriptor partition) {
		
		return elementIds(getElements(partition));
	}


	@Override
	public Iterable<IElementId> getElementIds() {

		return elementIds(getElements());
	}
	

	
	@Override
	public void clear() {
		
		for( IPeerManager pm : clusterManager.getPeerManagers() ) {
			
			pm.getDatabase().clear();
		}
		
	}

}
