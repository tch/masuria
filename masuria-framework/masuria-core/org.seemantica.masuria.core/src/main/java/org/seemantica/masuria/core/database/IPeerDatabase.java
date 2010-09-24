package org.seemantica.masuria.core.database;

import org.seemantica.masuria.core.IPeerLevelComponent;
import org.seemantica.masuria.core.datamodel.IElement;


public interface IPeerDatabase<E extends IElement> extends IDatabase<E>, IPeerLevelComponent<IMasterDatabase<E>>  {

}
