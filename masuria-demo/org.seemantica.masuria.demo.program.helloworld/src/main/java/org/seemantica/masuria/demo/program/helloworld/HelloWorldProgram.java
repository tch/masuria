package org.seemantica.masuria.demo.program.helloworld;

import org.seemantica.masuria.core.datamodel.neo4j.impl.LongElementId;
import org.seemantica.masuria.core.program.IProgram;
import org.seemantica.masuria.core.program.SPMDProgramBase;


public class HelloWorldProgram extends SPMDProgramBase implements IProgram {
	
	public HelloWorldProgram(final LongElementId... startingElements) {
		super(HelloWorldJob.class, startingElements);
		
	}

}
