package org.seemantica.masuria.core.manager.peer;

import org.seemantica.masuria.core.barrier.IPeerBarrier;
import org.seemantica.masuria.core.database.IPeerDatabase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.executor.IPeerExecutor;
import org.seemantica.masuria.core.manager.master.IMasterManager;
import org.seemantica.masuria.core.partitioner.IPeerPartitioner;
import org.seemantica.masuria.core.program.IProgram;
import org.seemantica.masuria.core.registry.IDescriptor;
import org.seemantica.masuria.core.router.IPeerRouter;


public class PeerManager implements IPeerManager {
	
	private IDescriptor descriptor; //TODO;tch: currently Peer descriptor is equal to partition descriptor - ie. peer~partition or one partition per peer 
	
	private IMasterManager clusterManager;
	
	private IPeerExecutor programExecutor;
	
	private IPeerBarrier barrier;
	
	private IPeerRouter router;
	
	private IPeerPartitioner partitioner;
	
	

	//TODO;tch: to become collection of program eventually
	private IProgram program;

	private IPeerDatabase<? extends IElement> database;

	public PeerManager()  {

	}
	
	//-- program execution routines sections
	@Override
	public IPeerExecutor getProgramExecutor() {
		return this.programExecutor;
	}

	@Override
	public void setProgramExecutor(final IPeerExecutor programExecutor) {
		
		this.programExecutor = programExecutor;
		this.programExecutor.associateWithPeerManager(this);
	}

	
	@Override
	public void addProgram(final IProgram program) {
		
		this.program = program;
		this.programExecutor.setProgram(program);
	}

	@Override
	public void removeProgram(final IProgram program) {
		
		this.program = null;
		this.programExecutor.setProgram(null);
	}

	
	@Override
	public void resetProgram(final IProgram program) {

		
	}

	
	//-- message routing routines section
	
	@Override
	public void setMessageRouter(final IPeerRouter router) {
		
		this.router = router;	
		this.router.associateWithPeerManager(this);
	}


	@Override
	public IPeerRouter getMessageRouter() {

		return this.router;
	}


	//-- data partitioning routines section
	@Override
	public void setPartitioner(IPeerPartitioner partitioner) {
		
		this.partitioner = partitioner;
		
		this.partitioner.associateWithPeerManager(this);
	}

	@Override
	public IPeerPartitioner getPartitioner() {

		return this.partitioner;
	}

	
	//-- Database routines section
	@Override
	public void setDatabase(final IPeerDatabase<? extends IElement> database) {
		
		this.database = database;
		
		this.database.associateWithPeerManager(this);
	}

	@Override
	public IPeerDatabase<? extends IElement> getDatabase() {

		return this.database;
	}
	

	
	//-- cluster routines section
	@Override
	public IMasterManager getClusterManager() {

		return this.clusterManager;
	}

	@Override
	public void setClusterManager(final IMasterManager clusterManager) {
		
		this.clusterManager = clusterManager;
	}

	
	//-- registry routines section
	
	@Override
	public IDescriptor getDescriptor() {

		return descriptor;
	}

	@Override
	public void setDescriptor(final IDescriptor descriptor) {
		
		this.descriptor = descriptor;
	}

	@Override
	public void setBarrier(final IPeerBarrier barrier) {

		this.barrier = barrier;
		
		this.barrier.associateWithPeerManager(this);
		
		
	}

	@Override
	public IPeerBarrier getBarrier() {
		
		return barrier;
	}

}
