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

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.seemantica.masuria.core.datamodel.IEdge;
import org.seemantica.masuria.core.datamodel.IElement;



public class Neo4JRelationship extends Neo4JPropertyContainerBase<Relationship> implements IEdge {
	
	//private Relationship relationship;
	
	public Neo4JRelationship(final Relationship relationship, final GraphDatabaseService database) {
		super(relationship, database);
	}
	
	
	@Override
	public IElement getOtherElement(final IElement vertex) {

		return new Neo4JElement( container.getOtherNode( (Node) ((Neo4JElement)vertex).getNode() ), database );
	}

}
