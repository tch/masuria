package org.seemantica.masuria.core.database;

import org.seemantica.masuria.core.IMasterLevelComponent;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.registry.IDescriptor;


public interface IMasterDatabase<E extends IElement> extends IDatabase<E>, IMasterLevelComponent {

	long getNumberOfElements(IDescriptor partition);
	
	Iterable<E> getElements(IDescriptor partition);
	
	Iterable<IElementId> getElementIds(IDescriptor partition);
	
	E getElement(IElementId id, IDescriptor partition);
}
