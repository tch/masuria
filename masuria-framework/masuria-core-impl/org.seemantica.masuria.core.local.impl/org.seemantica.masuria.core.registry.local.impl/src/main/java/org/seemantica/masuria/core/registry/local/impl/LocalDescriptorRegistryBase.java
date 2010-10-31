package org.seemantica.masuria.core.registry.local.impl;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.ComponentContext;
import org.seemantica.masuria.core.registry.IDescribable;
import org.seemantica.masuria.core.registry.IDescriptor;
import org.seemantica.masuria.core.registry.IDescriptorRegistry;
import org.seemantica.masuria.core.registry.UUIDDescriptorBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDescriptorRegistryBase<E extends IDescribable> implements IDescriptorRegistry<E>  {
	
	private Logger logger = LoggerFactory.getLogger(LocalDescriptorRegistryBase.class);

	private Map<IDescriptor, E> elements;
	private Map<IDescriptor, Integer> sequences;

	
	public LocalDescriptorRegistryBase()  {
		elements = new HashMap<IDescriptor, E>();
		sequences = new HashMap<IDescriptor, Integer>();
	}
	
	
	@Override
	public IDescriptor register(final E element) {
	
		IDescriptor descriptor = createDescriptor(element);
		elements.put(descriptor, element);
		sequences.put(descriptor, sequences.size());
		
		element.setDescriptor(descriptor);

		return descriptor;
	}
	
	@Override
	public E getElement(final IDescriptor descriptor) {
		return elements.get(descriptor);
	}

	
	protected IDescriptor createDescriptor(final E element)  {
		
		return new UUIDDescriptorBase();
	}


	@Override
	public int getTotalNumberOfElements() {

		return elements.size();
	}


	@Override
	public int getElementSequenceNumber(IDescriptor descriptor) {

		return sequences.get(descriptor);
	}


	//OSGI routines
    protected void activate(ComponentContext context) {
        logger.debug("LocalDescriptorRegistry component activated");
        //throw new RuntimeException("Runtime Exception");
    }
    
    protected void deactivate(ComponentContext context) {
        logger.debug("LocalDescriptorRegistry component deactivated");
    }
	
}	
