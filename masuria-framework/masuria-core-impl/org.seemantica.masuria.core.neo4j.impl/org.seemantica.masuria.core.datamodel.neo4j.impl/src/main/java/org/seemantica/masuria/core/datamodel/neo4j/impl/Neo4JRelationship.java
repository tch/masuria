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
