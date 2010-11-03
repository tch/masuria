package org.seemantica.masuria.core.manager.master.impl;


import org.osgi.service.component.ComponentContext;

import java.util.ArrayList;
import java.util.List;

import org.seemantica.masuria.core.barrier.IMasterBarrier;
import org.seemantica.masuria.core.database.IMasterDatabase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.executor.IMasterExecutor;
import org.seemantica.masuria.core.manager.master.IMasterManager;
import org.seemantica.masuria.core.manager.peer.IPeerManager;
import org.seemantica.masuria.core.partitioner.IMasterPartitioner;
import org.seemantica.masuria.core.program.IMessage;
import org.seemantica.masuria.core.program.IProgram;
import org.seemantica.masuria.core.registry.IDescriptor;
import org.seemantica.masuria.core.registry.IDescriptorRegistry;
import org.seemantica.masuria.core.router.IMasterRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MasterManager implements IMasterManager {

	private Logger logger = LoggerFactory.getLogger(MasterManager.class);
	
	/**
	 * Collection of peers that belong to this cluster
	 */
	private List<IDescriptor> peers; 
	
	/**
	 * Collection of programs registered with this cluster
	 */
	//TODO;tch: this ultimately will be a collection of programs
	private IProgram program;


	private IDescriptorRegistry<IPeerManager> peerRegistry;
	
	private IMasterExecutor programExecutor;
	
	private IMasterPartitioner partitioner;
	
	private IMasterRouter router;
	
	private IMasterDatabase<? extends IElement> database;

	private IMasterBarrier barrier;
	
	


	public MasterManager() {
		
		this.peers = new ArrayList<IDescriptor>();
		
	}


	@Override
	public void runProgram(final IProgram program) {

		this.programExecutor.run();
	}

	@Override
	public void stopProgram(IProgram program) {
		
	}
	
	
	@Override
	public void startProgram(final IProgram program) {
		
		this.programExecutor.start();
	}

	@Override
	public void stepProgram(final IProgram program) {
		
		this.programExecutor.step();
	}


	@Override
	public void isFinishedProgram(IProgram program) {
		
		this.programExecutor.isFinished();

	}
	
	@Override
	public void addProgram(final IProgram program) {

		this.program  = program;
		this.programExecutor.setProgram(this.program);
	}


	@Override
	public void removeProgram(final IProgram program) {

		this.resetProgram(program);
		this.program = null;
	}


	@Override
	public void resetProgram(final IProgram program) {
	}


	
	@Override
	public void associatePeerRegistry(final IDescriptorRegistry<IPeerManager> peerRegistry) {
		
		this.peerRegistry = peerRegistry;
		
		logger.debug("PeerRegistry component bound");
	}
	
	
	@Override
	public void addPeer(final IDescriptor descriptor) {
		
		peers.add(descriptor);
		
		IPeerManager nodeManager = peerRegistry.getElement(descriptor);
		nodeManager.setClusterManager(this);
		
	}

	@Override
	public Iterable<IPeerManager> getPeerManagers( ) {
		
		final List<IPeerManager> peerManagers = new ArrayList<IPeerManager>(peers.size());
		
		for(IDescriptor descriptor : peers) {
			
			IPeerManager peerManager = peerRegistry.getElement(descriptor);
			peerManagers.add(peerManager);
		}
		
		return peerManagers;
	}


	//-- program executor routines section
	@Override
	public IMasterExecutor getProgramExecutor() {

		return this.programExecutor;
	}


	@Override
	public void associateProgramExecutor(final IMasterExecutor executor) {
		
		executor.associateWithClusterManager(this);
		
		this.programExecutor = executor;
	}

	
	//-- data partitioning routines section
	@Override
	public IMasterPartitioner getPartitioner() {

		return this.partitioner;
	}


	@Override
	public void associatePartitioner(final IMasterPartitioner partitioner) {
		
		partitioner.associateWithClusterManager(this);
		
		this.partitioner = partitioner;
	}

	@Override
	public IPeerManager getPeerManagerForElement(final IElementId element) {

		IDescriptor descriptor = partitioner.getPartition(element); 
		
		return peerRegistry.getElement(descriptor); //TODO;tch: peer~partition assumption
	}


	@Override
	public IMasterRouter getMessageRouter() {

		return this.router;
	}


	@Override
	public void associateMessageRouter(final IMasterRouter router) {
		
		router.associateWithClusterManager(this);
		
		this.router = router;
	}


	@Override
	public void send(final IElementId recipient, final IMessage message, final IElementId sender) {
		
		this.router.dispatch(message);
	}


	//database routines section

	@Override
	public IMasterDatabase<? extends IElement> getDatabase() {

		return this.database;
	}


	@Override
	public void associateDatabase(final IMasterDatabase<? extends IElement> database) {
		
		database.associateWithClusterManager(this);
		
		this.database  = database;
	}

	
	//barrier routines
	@Override
	public IMasterBarrier getBarrier() {
		
		return this.barrier;
	}


	@Override
	public void associateBarrier(final IMasterBarrier barrier) {
		
		barrier.associateWithClusterManager(this);
		
		this.barrier = barrier;		
	}
	

	//OSGI routines
    protected void activate(ComponentContext context) {
        logger.debug("MasterManager component activated");
        //throw new RuntimeException("Runtime Exception");
    }
    
    protected void deactivate(ComponentContext context) {
        logger.debug("MasterManager component deactivated");
    }
	
}
