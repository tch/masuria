package org.seemantica.masuria.core.datamodel.neo4j.impl;

import org.seemantica.masuria.core.datamodel.IElementId;

public class LongElementId implements Comparable<LongElementId>, IElementId {
	
	private long id;

	public LongElementId(final long id) {
		
		this.id = id;
	}
	

	/* (non-Javadoc)
	 * @see org.seemantica.masuria.core.datamodel.IElementId#toLong()
	 */
	@Override
	public long toLong() {

		return id;
	}
	
	/* (non-Javadoc)
	 * @see org.seemantica.masuria.core.datamodel.IElementId#toString()
	 */
	@Override
	public String toString() {
		return Long.toString(id);
	}

		
	/* (non-Javadoc)
	 * @see org.seemantica.masuria.core.datamodel.IElementId#compareTo(org.seemantica.masuria.core.datamodel.LongElementId)
	 */
	@Override
	public int compareTo(final LongElementId o) {

		if( this.id < o.id )
			return -1;
		else if ( this.id > o.id )
			return 1;
		else return 0;
	}

	
	/* (non-Javadoc)
	 * @see org.seemantica.masuria.core.datamodel.IElementId#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see org.seemantica.masuria.core.datamodel.IElementId#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LongElementId other = (LongElementId) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
