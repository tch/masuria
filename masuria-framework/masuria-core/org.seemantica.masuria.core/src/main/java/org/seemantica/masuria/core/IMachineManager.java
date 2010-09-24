package org.seemantica.masuria.core;

import org.seemantica.masuria.core.program.IProgram;


/**
 * Machine is an abstraction of either single physical machine or a cluster of machines.
 *  It provides BSP program execution capabilities. 
 * 
 * @author tch
 */
public interface IMachineManager {


	void addProgram(final IProgram program);
	
	void removeProgram(final IProgram program);
	
	void resetProgram(final IProgram program);
	
}