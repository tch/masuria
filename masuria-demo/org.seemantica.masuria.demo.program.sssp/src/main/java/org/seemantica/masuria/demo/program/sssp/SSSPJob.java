package org.seemantica.masuria.demo.program.sssp;

import java.util.LinkedList;
import java.util.List;

import org.seemantica.masuria.core.datamodel.IEdge;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.datamodel.IVertex;
import org.seemantica.masuria.core.datamodel.neo4j.impl.Neo4JElement;
import org.seemantica.masuria.core.executor.JobExecutionContext;
import org.seemantica.masuria.core.program.IJob;
import org.seemantica.masuria.core.program.IProgram;
import org.seemantica.masuria.core.program.JobBase;
import org.seemantica.masuria.demo.DemoRelationshipTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SSSPJob extends JobBase<Neo4JElement, DistanceMessage>
					 implements	IJob<Neo4JElement, DistanceMessage> {
	
	private Logger logger = LoggerFactory.getLogger(SSSPJob.class);

	public SSSPJob(final IElementId id, final IProgram program, final JobExecutionContext executionContext) {
		super(id, program, executionContext);

	}

	@Override
	public void superStep() {
		
		int mindist = isStartingElement() ? 0 : Integer.MAX_VALUE;
				
		IVertex v = getElement();
		
		String elementName = (String) v.getProperty("nodeName");
		
		logger.debug("Starting superstep on element: {} ({})", elementName, this.getId() );
		
		logger.debug("Is starting element: {}", this.isStartingElement() );
		

		for(DistanceMessage msg: messages()) {
			logger.debug("Proposed distance received from {} is {}",  msg.getSender(), msg.getDistance());
			mindist = Math.min(mindist, msg.getDistance() );
		}
		
		
		if(mindist < getCurrentDistance() ) {
			
			logger.debug(elementName+": new minimal distance is: {}, was {}", mindist, getCurrentDistance() );
			setCurrentDistance(mindist);
			
			List<Long> ids = new LinkedList<Long>();
			
			for(IEdge r: v.getOutgoingEdges(DemoRelationshipTypes.KNOWS) )  {
				
				IElement recipient = r.getOtherElement(v);
				
				int rdist = (Integer)r.getProperty("relationshipDistance");
				
				this.sendMessage(new DistanceMessage(mindist+rdist, recipient.getId(), this.getId()));
				
				ids.add(recipient.getId().toLong());
			}
			
			logger.debug("element: {} send messages to following elements: {}", elementName, ids );
		}

		
	}

	public int getCurrentDistance() {
		
		if(getElement().hasProperty("distanceToSource")) {
			
			return (Integer)getElement().getProperty("distanceToSource");
		}
		else {
			
			getElement().setProperty("distanceToSource", Integer.MAX_VALUE);
			
			return Integer.MAX_VALUE;
		}
	}
	
	public void setCurrentDistance(int distance) {
				
		getElement().setProperty("distanceToSource", distance);
	}
	
}
