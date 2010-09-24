package org.seemantica.masuria.core.executor;

import org.seemantica.masuria.core.program.IProgram;

public interface IExecutor {

	
	IProgram getProgram();

	void setProgram(IProgram program);


	public void start();
	
	public void step();
	
	public long getIteration();
}