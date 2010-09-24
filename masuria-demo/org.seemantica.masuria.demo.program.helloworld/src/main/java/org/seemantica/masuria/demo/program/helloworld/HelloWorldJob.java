/**
 * 
 */
package org.seemantica.masuria.demo.program.helloworld;

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

/**
 * @author tch
 *
 */
public class HelloWorldJob extends JobBase<Neo4JElement, HelloMessage> implements IJob<Neo4JElement, HelloMessage> {
	private Logger logger = LoggerFactory.getLogger(HelloWorldJob.class);
	
	public HelloWorldJob(final IElementId id, final IProgram program, final JobExecutionContext context)  {
		super(id, program, context);
	}


	@Override
	public void superStep() {
		
		if(getIteration()==0)  {
			
			IVertex v = getElement();
			
			String elementName = (String) v.getProperty("nodeName");
			
			logger.debug("starting superstep on element: {} ({})", elementName, this.getId() );
			
			List<Long> ids = new LinkedList<Long>(); 
				
			for(IEdge r: v.getOutgoingEdges(DemoRelationshipTypes.KNOWS) )  {
				
				IElement recipient = r.getOtherElement(v);
				
				this.sendMessage( new HelloMessage("Hello job "+recipient.getId()+", says job "+elementName+" ("+this.getId()+")", recipient.getId(), this.getId() ) );
				
				ids.add(recipient.getId().toLong());
			}
			
			logger.debug("element: {} send messages to following elements: {}", elementName, ids );
			
			return; //NOTE;tch: serves as a halt() method
			
		} else {

						
			for(HelloMessage msg: messages() ) {
				
				IElementId sender = msg.getSender();
				String content = msg.getContent();
				
				String elementName = (String) this.getElement().getProperty("nodeName");
				
				logger.info("Element "+elementName+" received message from job {}, saying '{}'", sender, content );
				
			}
			
			return;
		}
			 	
	}

}
