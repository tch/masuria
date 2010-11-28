package org.seemantica.masuria.core.manager.master;

import org.seemantica.masuria.core.IMachineManager;
import org.seemantica.masuria.core.barrier.IMasterBarrier;
import org.seemantica.masuria.core.database.IMasterDatabase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.executor.IMasterExecutor;
import org.seemantica.masuria.core.manager.peer.IPeerManager;
import org.seemantica.masuria.core.partitioner.IMasterPartitioner;
import org.seemantica.masuria.core.partitioner.IPartitioner;
import org.seemantica.masuria.core.program.IMessage;
import org.seemantica.masuria.core.program.IProgram;
import org.seemantica.masuria.core.registry.IDescriptor;
import org.seemantica.masuria.core.registry.IDescriptorRegistry;
import org.seemantica.masuria.core.router.IMasterRouter;



public interface IMasterManager extends IMachineManager {	
	
	
	void runProgram(final IProgram program);
	void stopProgram(final IProgram program);
	
	void startProgram(final IProgram program);
	
	void stepProgram(final IProgram program);
	
	void isFinishedProgram(final IProgram program);

	
	void associatePeerRegistry(IDescriptorRegistry<IPeerManager> peerRegistry);
	
	IDescriptorRegistry<IPeerManager> getPeerRegistry();
	
	
	void addPeer(final IDescriptor nodeId);
	
	//TODO;tch: consider returning IDescriptors to dynamically resolve active nodes
	Iterable<IPeerManager> getPeerManagers( );

	
	//program execution routines section
	
	IMasterExecutor getProgramExecutor();
	
	void associateProgramExecutor(IMasterExecutor executor);
	

	//data partitioning routines section
	
	IMasterPartitioner getPartitioner();
	
	void associatePartitioner(IMasterPartitioner partitioner);


	/** 
	 * 
	 * @param element data element
	 * @return IPeerManager that manages the partition containing the element 
	 */
	IPeerManager getPeerManagerForElement(IElementId element );
	
	
	//messaging routines section
	
	IMasterRouter getMessageRouter();
	
	void associateMessageRouter(IMasterRouter router);
	
	void send(final IElementId recipient, final IMessage message, final IElementId sender);

	//synchronisation routines section
	
	IMasterBarrier getBarrier();
	
	void associateBarrier(IMasterBarrier barrier);

	
	//database routines section
	
	IMasterDatabase<? extends IElement> getDatabase();
	
	void associateDatabase(IMasterDatabase<? extends IElement> database);

		
}
