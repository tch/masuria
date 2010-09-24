package org.seemantica.masuria.demo.program.sssp;

import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.program.MessageBase;


public class DistanceMessage extends MessageBase {

	private final int distance;

	public DistanceMessage(final int distance, final IElementId recipient, final IElementId sender) {
		
		super(recipient, sender);
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}
	
	
}
