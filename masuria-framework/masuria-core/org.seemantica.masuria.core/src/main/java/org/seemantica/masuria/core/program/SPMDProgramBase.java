/**
 * 
 */
package org.seemantica.masuria.core.program;

import java.lang.reflect.InvocationTargetException;

import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.executor.JobExecutionContext;


/**
 * @author tch
 *
 */
public abstract class SPMDProgramBase extends ProgramBase implements IProgram {

	private final Class<? extends IJob<? extends IElement, ? extends IMessage>> jobClass;
	
	public SPMDProgramBase(final Class<? extends IJob<? extends IElement, ? extends IMessage>> jobClass)  {
		
		this.jobClass = jobClass;
	}


	public SPMDProgramBase(final Class<? extends IJob<? extends IElement, ? extends IMessage>> jobClass, final IElementId... startingElements)  {
		super(startingElements);
		
		this.jobClass = jobClass;
	}
	
	public IJob<? extends IElement, ? extends IMessage> getJobInstance(final IElementId address, final JobExecutionContext context) {
		IJob<? extends IElement, ? extends IMessage> theJob = null;
		
		try {
			
			theJob = jobClass.getConstructor(IElementId.class, IProgram.class, JobExecutionContext.class).newInstance(address, this, context);
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return theJob;
	}

}
