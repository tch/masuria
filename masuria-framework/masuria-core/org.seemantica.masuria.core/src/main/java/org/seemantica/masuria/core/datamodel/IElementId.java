package org.seemantica.masuria.core.datamodel;

public interface IElementId {

	public abstract long toLong();

	public abstract String toString();

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

}