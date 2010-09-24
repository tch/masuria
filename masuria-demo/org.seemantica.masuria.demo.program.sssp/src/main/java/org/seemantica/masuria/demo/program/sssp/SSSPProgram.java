package org.seemantica.masuria.demo.program.sssp;

import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.program.IProgram;
import org.seemantica.masuria.core.program.SPMDProgramBase;


public class SSSPProgram extends SPMDProgramBase implements IProgram {

	public SSSPProgram(final IElementId... startingElements) {

		super(SSSPJob.class, startingElements);
		
	}


}
