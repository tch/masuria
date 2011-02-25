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
