package org.seemantica.masuria.core.registry;

public interface IDescriptorRegistry<E> {

	IDescriptor register(final E element);

	E getElement(final IDescriptor descriptor);

	int getTotalNumberOfElements();
	
	int getElementSequenceNumber( final IDescriptor descriptor );
}
