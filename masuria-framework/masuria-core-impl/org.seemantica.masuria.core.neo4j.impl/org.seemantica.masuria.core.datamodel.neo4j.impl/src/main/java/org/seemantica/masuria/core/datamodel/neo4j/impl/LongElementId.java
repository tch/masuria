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
