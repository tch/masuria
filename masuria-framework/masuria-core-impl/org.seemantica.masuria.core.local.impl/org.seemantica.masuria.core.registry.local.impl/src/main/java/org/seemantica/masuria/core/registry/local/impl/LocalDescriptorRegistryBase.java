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
	private Map<Integer, IDescriptor> descriptors;

	
	public LocalDescriptorRegistryBase()  {

		elements = new HashMap<IDescriptor, E>();
		sequences = new HashMap<IDescriptor, Integer>();
		
		descriptors = new HashMap<Integer,IDescriptor>();
	}
	
	
	@Override
	public IDescriptor register(final E element) {
	
		IDescriptor descriptor = createDescriptor(element);
		elements.put(descriptor, element);
		sequences.put(descriptor, sequences.size());
		descriptors.put(descriptors.size(), descriptor);
		
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
	public int getDescriptorSequenceNumber(final IDescriptor descriptor) {

		return sequences.get(descriptor);
	}

	@Override
	public IDescriptor getDescriptorFromSequenceNumber(int seq) {

		return descriptors.get(seq);
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
