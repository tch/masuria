package org.seemantica.masuria.core.database;

import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;

public interface IDatabase<E extends IElement> {
	
	long getNumberOfElements();
		
	void clear();

	//obtaining elements via cluster-level db shouldn't be necessary
	Iterable<E> getElements();
	
	Iterable<IElementId> getElementIds();
	
	IElement getElement(IElementId id);

}
