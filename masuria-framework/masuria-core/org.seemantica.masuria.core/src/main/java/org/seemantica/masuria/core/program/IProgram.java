/**
 * 
 */
package org.seemantica.masuria.core.program;

import java.util.Collection;

import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.executor.JobExecutionContext;


/**
 * Program defines a mapping between nodes and vertices, starting elements (if any) and specifies global actions for the program.
 * @author tch
 *
 */
public interface IProgram {

	IJob<? extends IElement, ? extends IMessage> getJobInstance(final IElementId address, final JobExecutionContext executionContext);
	
	Collection<IElementId> getStartingElements();
	
	void startAction();
	
	void superStepAction();
	
	void finishAction();
}
