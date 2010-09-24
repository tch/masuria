/**
 * 
 */
package org.seemantica.masuria.core.program;

import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.datamodel.IVertex;
import org.seemantica.masuria.core.executor.JobExecutionContext;


/**
 * @author tch
 *
 * TODO;tch: consider removing M parameter and base everything off IMessage so that jobs could be sending 
 * messages of different types
 */
public abstract class JobBase<E extends IElement,
                              M extends IMessage> implements IJob<E, M> {

	protected final IElementId id;
	
	protected final IProgram program;
	protected final JobExecutionContext context;
	

	public JobBase(final IElementId id, final IProgram program, final JobExecutionContext executionContext)  {
		this.id = id;
		
		this.program = program;
		this.context = executionContext;
	}


	
	@Override
	public IElementId getId() {
		return id;
	}


	@Override
	public void halt() {
		//context.getBarrier().await(this);
	}


	@Override
	public void abort() {
		//context.getBarrier().abort(id);
	}



	@Override
	public Iterable<M> messages() {
		
		return (Iterable<M>) context.getMessageRouter().messages(id);
	}


	@Override
	public void sendMessage(final IMessage message) {
		
		context.getMessageRouter().dispatch(message);
	}



	/* (non-Javadoc)
	 * @see org.seemantica.masuria.core.program.IJob#getIteration()
	 */
	@Override
	public long getIteration() {

		return context.getProgramExecutor().getIteration();
	}



	@Override
	public IVertex getElement() {

		return (IVertex)context.getDatabase().getElement(id);
	}
	
	@Override
	public boolean isStartingElement() {
		
		
		return this.program.getStartingElements().contains(id);
	}
	
}
