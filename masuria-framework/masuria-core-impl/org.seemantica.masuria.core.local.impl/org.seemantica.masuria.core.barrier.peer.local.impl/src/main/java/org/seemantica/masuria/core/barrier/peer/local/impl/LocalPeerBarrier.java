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
package org.seemantica.masuria.core.barrier.peer.local.impl;

import org.seemantica.masuria.core.PeerLevelComponentBase;
import org.seemantica.masuria.core.barrier.IMasterBarrier;
import org.seemantica.masuria.core.barrier.IPeerBarrier;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.program.IJob;
import org.seemantica.masuria.core.program.IMessage;


public class LocalPeerBarrier extends PeerLevelComponentBase<IMasterBarrier> implements IPeerBarrier {

	@Override
	public void await(final IJob<? extends IElement, ? extends IMessage> job) {
		

	}

	@Override
	public void abort(IElementId address) {
		// TODO Auto-generated method stub

	}

	@Override
	public IMasterBarrier getClusterLevelComponent() {

		return this.peerManager.getClusterManager().getBarrier();
	}

	@Override
	public boolean postStep() {
		
		return false;
	}

}
