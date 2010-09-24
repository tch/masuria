package org.seemantica.masuria.core.registry;

import java.util.UUID;

public class UUIDDescriptorBase implements IDescriptor {
	private UUID id;

	public UUIDDescriptorBase() {
		this.id = UUID.randomUUID();
	}
	
	
	public UUIDDescriptorBase(final UUID id) {
		this.id = id;
	}

	public String toString() {

		return id.toString();
	}
}




