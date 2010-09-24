package org.seemantica.masuria.core.manager.peer;

import org.seemantica.masuria.core.IMachineManager;
import org.seemantica.masuria.core.barrier.IPeerBarrier;
import org.seemantica.masuria.core.database.IPeerDatabase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.executor.IPeerExecutor;
import org.seemantica.masuria.core.manager.master.IMasterManager;
import org.seemantica.masuria.core.partitioner.IPeerPartitioner;
import org.seemantica.masuria.core.registry.IDescribable;
import org.seemantica.masuria.core.registry.IDescriptor;
import org.seemantica.masuria.core.router.IPeerRouter;



public interface IPeerManager extends IDescribable, IMachineManager {
	
	//--descriptor routines sections
	IDescriptor getDescriptor();
	
	//--cluster routines sections
	IMasterManager getClusterManager();
	
	void setClusterManager(IMasterManager clusterManager);
	
	
	//--program execution routines section
	IPeerExecutor getProgramExecutor();

	void setProgramExecutor(final IPeerExecutor programExecutor);
	
	//--barrier routines section
	void setBarrier(final IPeerBarrier router);

	IPeerBarrier getBarrier( );
	
	
	//--message routing routines section
	void setMessageRouter(final IPeerRouter router);

	IPeerRouter getMessageRouter( );

	
	//--data partitioning routines section
	void setPartitioner(final IPeerPartitioner partitioner);

	IPeerPartitioner getPartitioner( );

	//--database routines section
	void setDatabase(final IPeerDatabase<? extends IElement> database);

	IPeerDatabase<? extends IElement> getDatabase( );

}