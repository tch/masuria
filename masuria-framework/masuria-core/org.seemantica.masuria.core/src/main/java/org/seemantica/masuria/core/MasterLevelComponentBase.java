package org.seemantica.masuria.core;

import org.seemantica.masuria.core.manager.master.IMasterManager;

public abstract class MasterLevelComponentBase implements IMasterLevelComponent {

	protected IMasterManager clusterManager;
	
	@Override
	public void associateWithClusterManager(final IMasterManager clusterManager) {
		
		this.clusterManager = clusterManager;
	}


}
