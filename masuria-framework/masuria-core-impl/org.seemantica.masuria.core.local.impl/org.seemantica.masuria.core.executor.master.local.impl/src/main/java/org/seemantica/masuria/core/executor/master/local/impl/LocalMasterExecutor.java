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
package org.seemantica.masuria.core.executor.master.local.impl;

import org.osgi.service.component.ComponentContext;
import org.seemantica.masuria.core.MasterLevelComponentBase;
import org.seemantica.masuria.core.executor.IMasterExecutor;
import org.seemantica.masuria.core.manager.peer.IPeerManager;
import org.seemantica.masuria.core.program.IProgram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LocalMasterExecutor extends MasterLevelComponentBase
                                         implements IMasterExecutor {
	
	private Logger logger = LoggerFactory.getLogger(LocalMasterExecutor.class);
	

	private IProgram program;
	
	private volatile long superStepIteration=0;
	
	private volatile boolean finished=true;

	
	@Override
	public IProgram getProgram() {
		
		return this.program;
	}

	@Override
	public void setProgram(final IProgram program) {
		
		this.program = program;

		Iterable<IPeerManager> pms = clusterManager.getPeerManagers();

		for(IPeerManager pm: pms) {
			
			pm.getProgramExecutor().setProgram(program);
		}
		
	}

	
	@Override
	public void run()  {
		
		start();
		
		while(!finished) {
			step();
		}
		
	}

	@Override
	public long getIteration()  {

		return superStepIteration;
	}


	@Override
	public void start()  {

		superStepIteration=0;
		
		logger.debug("Starting global superstep {}", getIteration());
		
		Iterable<IPeerManager> pms = clusterManager.getPeerManagers();

		
		
		//initial step

		for(IPeerManager pm: pms) {
			
			pm.getProgramExecutor().start();
		}


		int nOfMessages = 0;
		boolean finish = true;
		
		for(IPeerManager pm: pms) {
			
			finish = pm.getProgramExecutor().postStep() && finish;
			nOfMessages+= pm.getMessageRouter().nOfMessages();
		}

		logger.debug("Global superstep {} finished, n. of new messages: {}", getIteration(), nOfMessages );
		
		finished = finish;
	}
	
	
	@Override
	public void step()  {
		
		superStepIteration++;

		logger.debug("Starting global superstep {}", getIteration());
		
		
		Iterable<IPeerManager> pms = clusterManager.getPeerManagers();

		
		for(IPeerManager pm: pms) {
			
			pm.getProgramExecutor().step();
			
		}

		int nOfMessages = 0;
		boolean finish = true;
		
		for(IPeerManager pm: pms) {
			
			finish = pm.getProgramExecutor().postStep() && finish;
			nOfMessages+= pm.getMessageRouter().nOfMessages();
		}

		
		logger.debug("Global superstep {} finished, n. of new messages: {}", getIteration(), nOfMessages );

		finished = finish;
	}

	@Override
	public boolean isFinished() {

		return finished;
	}

	
    protected void activate(final ComponentContext context) {
        logger.debug("LocalMasterExecutor component activated");
    }
    
    protected void deactivate(final ComponentContext context) {
        logger.debug("LocalMasterExecutor component deactivated");
    }
	
	
	//NOTE;tch, scala: large parts od start/step differ only by a single function invocation,
	// with functions as first-class-objects it should be easy to generalize a common subroutine 
}