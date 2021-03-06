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
package org.seemantica.masuria.core.database.master.neo4j.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.seemantica.masuria.core.database.IMasterDatabase;
import org.seemantica.masuria.core.database.MasterDatabaseBase;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.datamodel.neo4j.impl.Neo4JElement;
import org.seemantica.masuria.core.partitioner.IMasterPartitioner;
import org.seemantica.masuria.core.registry.IDescriptor;



public class LocalNeo4JMasterDatabase extends MasterDatabaseBase<Neo4JElement> implements IMasterDatabase<Neo4JElement> {
	
	private GraphDatabaseService neo4JDb;
	

	public LocalNeo4JMasterDatabase(final GraphDatabaseService neo4jDb) {
		super();
		this.neo4JDb = neo4jDb;
	}

	public void setNeo4JGraphDatabaseService( final GraphDatabaseService neo4JDb ) {
		this.neo4JDb = neo4JDb;
	}

	@Override
	public long getNumberOfElements(IDescriptor descriptor) {
		
		long nOfElements=0;
		IMasterPartitioner mp = clusterManager.getPartitioner();
		
		for(Neo4JElement el : getElements())
		{
			if( mp.isOnPeerPartition(el.getId(), descriptor) )
					++nOfElements;
		}
		
		return nOfElements;
	}
	
	
	@Override
	public long getNumberOfElements() {

		Transaction tx = neo4JDb.beginTx();
		
		long size=0;
		try {
			
			size = countIterable(neo4JDb.getAllNodes());
			tx.success();
		}
		 finally  {
			   tx.finish();
			}
		
		return size;
	}


	static long countIterable(final Iterable<?> ible) {
		int i = 0;
		Iterator<?> itr = ible.iterator();

		while(itr.hasNext()) {
			
			++i;
		}
		
		return i;
	}
	


	@Override
	public void clear() {
		Transaction tx = neo4JDb.beginTx();
		
		try {
			
			Iterable<Node> itrN = neo4JDb.getAllNodes();
			
			for(Node n: itrN) {
				
				Iterable<Relationship> itrR = n.getRelationships();
				
				for(Relationship r: itrR) {
					r.delete();
				}
				
				n.delete();
			}

			tx.success();
		}
		 finally  {
			tx.finish();
		}
	}


	@Override
	public Iterable<Neo4JElement> getElements() {

		Set<Neo4JElement> elements = new HashSet<Neo4JElement>();
		
		Transaction tx = neo4JDb.beginTx();
		
		try {
			
			Iterable<Node> itr = neo4JDb.getAllNodes();
			
			for(Node n: itr) {

				elements.add( new Neo4JElement(n, neo4JDb) );
			}
			
			tx.success();
		}
		 finally  {
			tx.finish();
		}
		
		return elements;
	}


	@Override
	public Neo4JElement getElement(final IElementId id) {
		
		Transaction tx = neo4JDb.beginTx();
		
		Neo4JElement e=null;
		
		try {
			
			e = new Neo4JElement(neo4JDb.getNodeById(id.toLong()), neo4JDb );
			
			tx.success();
		} catch (NotFoundException ex) {

		}
	
		 finally  {
			tx.finish();
		}
		
		 return e;
	}


	@Override
	public Iterable<Neo4JElement> getElements(final IDescriptor partition) {

		Set<Neo4JElement> elements = new HashSet<Neo4JElement>();
		
		Transaction tx = neo4JDb.beginTx();
		
		try {
			
			Iterable<Node> itr = neo4JDb.getAllNodes();
			
			for(Node n: itr) {
				
				Neo4JElement e = new Neo4JElement(n, neo4JDb);
				
				if(clusterManager.getPartitioner().isOnPeerPartition(e.getId(), partition)) {
					
					elements.add( e );
				}


			}
			
			tx.success();
		}
		 finally  {
			tx.finish();
		}
		
		return elements;
	}


	@Override
	public Neo4JElement getElement(final IElementId id, final IDescriptor partition) {
		
		if(clusterManager.getPartitioner().isOnPeerPartition(id, partition)) {
			
			return getElement(id); 
		}
		
		return null; 
	}


	public String toString() {
		
		StringBuilder bldr = new StringBuilder("Database:\n");

		Transaction tx = neo4JDb.beginTx();
		
		try {
			
			for(Node n: neo4JDb.getAllNodes()) {
				
				bldr.append("Node "+n.getId()+" {\n");
				
				for(String pk: n.getPropertyKeys()) {
					
					bldr.append(" "+pk+" : "+n.getProperty(pk).toString()+"\n");
				}
				
				bldr.append(" relationships:\n");
				
				for(Relationship r : n.getRelationships(Direction.OUTGOING)) {
					
					bldr.append("  -("+r.getType()+","+r.getId()+")-> "+r.getOtherNode(n).getId()+" {\n");
					
						for(String pk: r.getPropertyKeys()) {
							
							bldr.append("   "+pk+" : "+r.getProperty(pk).toString()+"\n");
						}
						bldr.append("  }\n");
				}
				
				bldr.append("} Node "+n.getId()+"\n\n");
			}
			
			
			tx.success();
		}
		 finally  {
			tx.finish();
		}

		
		return bldr.toString();
	}

}
