package org.seemantica.masuria.core.datamodel;

import org.neo4j.graphdb.RelationshipType;


public interface IVertex extends IElement {
	
	Iterable<IEdge> getOutgoingEdges();
	
	Iterable<IEdge> getOutgoingEdges( RelationshipType type ); //TODO:Neo4J hackery
	
	boolean hasOutgoingEdges();

}
