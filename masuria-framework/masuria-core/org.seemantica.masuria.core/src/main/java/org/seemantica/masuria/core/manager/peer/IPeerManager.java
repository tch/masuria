/**
 * Copyright (C) 2010 Tomasz Chodakowski <*tch (at) wp.eu*>
 *
 * This file is part of Project Masuria.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.seemantica.masuria.core.manager.peer;

import org.seemantica.masuria.core.IMachineManager;
import org.seemantica.masuria.core.barrier.IPeerBarrier;
import org.seemantica.masuria.core.database.IPeerDatabase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.executor.IPeerExecutor;
import org.seemantica.masuria.core.manager.master.IMasterManager;
import org.seemantica.masuria.core.partitioner.IPeerPartitioner;
import org.seemantica.masuria.core.registry.IDescribable;
import org.seemantica.masuria.core.registry.IDescriptor;
import org.seemantica.masuria.core.router.IPeerRouter;



public interface IPeerManager extends IDescribable, IMachineManager {
	
	//--descriptor routines sections
	IDescriptor getDescriptor();
	
	//--cluster routines sections
	IMasterManager getClusterManager();
	
	void setClusterManager(IMasterManager clusterManager);
	
	
	//--program execution routines section
	IPeerExecutor getProgramExecutor();

	void setProgramExecutor(final IPeerExecutor programExecutor);
	
	//--barrier routines section
	void setBarrier(final IPeerBarrier router);

	IPeerBarrier getBarrier( );
	
	
	//--message routing routines section
	void setMessageRouter(final IPeerRouter router);

	IPeerRouter getMessageRouter( );

	
	//--data partitioning routines section
	void setPartitioner(final IPeerPartitioner partitioner);

	IPeerPartitioner getPartitioner( );

	//--database routines section
	void setDatabase(final IPeerDatabase<? extends IElement> database);

	IPeerDatabase<? extends IElement> getDatabase( );

}