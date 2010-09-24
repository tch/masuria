package org.seemantica.masuria.core.executor;

import org.seemantica.masuria.core.IMasterLevelComponent;

public interface IMasterExecutor extends IExecutor, IMasterLevelComponent {
	
	public void run();
	
	public boolean isFinished();
	
}
