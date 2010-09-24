package org.seemantica.masuria.core.program;



import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.seemantica.masuria.core.datamodel.IElementId;

public abstract class ProgramBase implements IProgram {

	protected Set<IElementId> startingElements;

	public ProgramBase(final IElementId... startingElements) {
		
		this.startingElements = new HashSet<IElementId>(Arrays.asList(startingElements));

	}

	@Override
	public Collection<IElementId> getStartingElements() {

		return startingElements;
	}

	
	@Override
	public void finishAction() {
	}


	@Override
	public void startAction() {
	}


	@Override
	public void superStepAction() {
	}

}
