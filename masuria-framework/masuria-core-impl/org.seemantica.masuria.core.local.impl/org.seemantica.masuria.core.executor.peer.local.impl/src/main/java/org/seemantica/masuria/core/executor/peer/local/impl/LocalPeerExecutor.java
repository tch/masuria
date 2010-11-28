package org.seemantica.masuria.core.executor.peer.local.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.seemantica.masuria.core.PeerLevelComponentBase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.executor.IMasterExecutor;
import org.seemantica.masuria.core.executor.IPeerExecutor;
import org.seemantica.masuria.core.executor.JobExecutionContext;
import org.seemantica.masuria.core.partitioner.IPeerPartitioner;
import org.seemantica.masuria.core.program.IJob;
import org.seemantica.masuria.core.program.IMessage;
import org.seemantica.masuria.core.program.IProgram;
import org.seemantica.masuria.core.router.IPeerRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LocalPeerExecutor extends PeerLevelComponentBase<IMasterExecutor>
                                      implements IPeerExecutor {

	private Logger logger = LoggerFactory.getLogger(LocalPeerExecutor.class);
	
	private int executorThreadPoolSize=2;
	private int jobTimeOutMinutes=10;
	
	private IProgram program;

	private long localIteration=0;
	

	public LocalPeerExecutor() {
	}
	

	/**
	 * @return the program
	 */
	public IProgram getProgram() {
		return program;
	}


	/**
	 * @param program the program to set
	 */
	public void setProgram(IProgram program) {
		this.program = program;
	}
	
	
	
	protected void superstepExecution(final Iterable<IElementId> elementIds) {

		IPeerRouter router = peerManager.getMessageRouter();
		
		ExecutorService executor = Executors.newFixedThreadPool(executorThreadPoolSize);
		
		try {
			
			for(final IElementId eId : elementIds) {
				
				final IJob<? extends IElement, ? extends IMessage> aJob = program.getJobInstance(eId, new JobExecutionContext(router, this, peerManager.getDatabase()) );
				
				//logger.debug("enqueuing job at id {}", aJob.getAddress() );
				
				executor.execute(new Runnable() {
					                   public void run() {
					                	            aJob.superStep();
					                            } 
					                });
				//logger.debug(" job at id {} enqueued", aJob.getAddress() );
			}
			logger.debug("finished enqueuing all jobs on partition {}", peerManager.getDescriptor().toString() ); //TODO;tch: peer~partition assumption
			
		} finally {
			
			executor.shutdown();
			
			try {
				executor.awaitTermination(jobTimeOutMinutes, TimeUnit.MINUTES);
				logger.debug("Executor terminated" );
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		logger.debug("Finished local superstep no {}", localIteration);
		
	}
	

	@Override
	public long getIteration() {
		
		return this.localIteration;
	}


	@Override
	public void start() {
		
		localIteration=0;
		
		Iterable<IElementId> startingElements = program.getStartingElements();
		
		Iterable<IElementId> startingElementsOnThisPartition;
		
		IPeerPartitioner partitioner = peerManager.getPartitioner();
		
		if(startingElements!=null) {
						
			startingElementsOnThisPartition = new HashSet<IElementId>();
			
			for(IElementId eId: startingElements) {
				if(partitioner.isOnThisPeerPartition(eId)) {
					((Set<IElementId>)startingElementsOnThisPartition).add(eId);
				}
			}
			
		} else {

			startingElementsOnThisPartition = peerManager.getDatabase().getElementIds(); 
		}
		

		superstepExecution( startingElementsOnThisPartition );
	}


	@Override
	public void step() {
		
		localIteration++;
		
		Iterable<IElementId> recipients = this.peerManager.getMessageRouter().getAllRecipients();
		
		superstepExecution(recipients);
	}

	
	@Override
	public boolean postStep() {
		
		return this.peerManager.getMessageRouter().postStep();
	}


	@Override
	public IMasterExecutor getClusterLevelComponent() {
		
		return this.peerManager.getClusterManager().getProgramExecutor();
	}

}
