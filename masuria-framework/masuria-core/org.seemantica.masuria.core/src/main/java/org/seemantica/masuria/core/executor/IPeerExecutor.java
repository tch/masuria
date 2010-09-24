package org.seemantica.masuria.core.executor;

import org.seemantica.masuria.core.IPeerBulkSynchronous;
import org.seemantica.masuria.core.IPeerLevelComponent;



public interface IPeerExecutor extends IExecutor, IPeerLevelComponent<IMasterExecutor>,
                                              IPeerBulkSynchronous {

}