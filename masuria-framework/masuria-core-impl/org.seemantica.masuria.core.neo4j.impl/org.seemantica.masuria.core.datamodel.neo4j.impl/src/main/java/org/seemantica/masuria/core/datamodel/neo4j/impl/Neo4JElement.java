package org.seemantica.masuria.core.datamodel.neo4j.impl;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.seemantica.masuria.core.datamodel.IEdge;
import org.seemantica.masuria.core.datamodel.IVertex;


public class Neo4JElement extends Neo4JPropertyContainerBase<Node> implements IVertex {

	public Neo4JElement(final Node node, final GraphDatabaseService database) {
		super(node, database);
	}
	
	@Override
	public LongElementId getId() {

		return new LongElementId( container.getId() );
	}


	
	@Override
	public Iterable<IEdge> getOutgoingEdges(final RelationshipType type) {
		
		Iterable<Relationship> rels = container.getRelationships(type, Direction.OUTGOING);
		
		Set<IEdge> r = new HashSet<IEdge>();
		
		for( Relationship relationship : rels) {
			
			r.add(new Neo4JRelationship(relationship, database));
		}
		
		return r;
	}
	
	
	Node getNode() {
		
		return container;
	}

	@Override
	public Iterable<IEdge> getOutgoingEdges() {
		
		Iterable<Relationship> rels = container.getRelationships(Direction.OUTGOING);
		
		Set<IEdge> r = new HashSet<IEdge>();
		
		for( Relationship relationship : rels) {
			
			r.add(new Neo4JRelationship(relationship, database));
		}
		
		return r;

	}

	@Override
	public boolean hasOutgoingEdges() {
		
		return container.hasRelationship();
	}

}
