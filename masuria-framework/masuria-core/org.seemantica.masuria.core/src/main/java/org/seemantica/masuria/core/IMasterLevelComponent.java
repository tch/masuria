package org.seemantica.masuria.core;

import org.seemantica.masuria.core.manager.master.IMasterManager;

/**
 * @author tch
 *
 * @param <P>
 */
public interface IMasterLevelComponent {
	
	void associateWithClusterManager(IMasterManager clusterManager);

}
