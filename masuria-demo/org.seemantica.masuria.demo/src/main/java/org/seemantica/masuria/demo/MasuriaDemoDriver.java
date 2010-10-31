package org.seemantica.masuria.demo;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.seemantica.masuria.core.barrier.IMasterBarrier;
import org.seemantica.masuria.core.barrier.IPeerBarrier;
import org.seemantica.masuria.core.barrier.master.local.impl.LocalMasterBarrier;
import org.seemantica.masuria.core.barrier.peer.local.impl.LocalPeerBarrier;
import org.seemantica.masuria.core.database.IMasterDatabase;
import org.seemantica.masuria.core.database.IPeerDatabase;
import org.seemantica.masuria.core.database.master.neo4j.impl.LocalNeo4JMasterDatabase;
import org.seemantica.masuria.core.database.peer.neo4j.impl.LocalNeo4JPeerDatabase;
import org.seemantica.masuria.core.datamodel.neo4j.impl.LongElementId;
import org.seemantica.masuria.core.datamodel.neo4j.impl.Neo4JElement;
import org.seemantica.masuria.core.executor.IMasterExecutor;
import org.seemantica.masuria.core.executor.IPeerExecutor;
import org.seemantica.masuria.core.executor.master.local.impl.LocalMasterExecutor;
import org.seemantica.masuria.core.executor.peer.local.impl.LocalPeerExecutor;
import org.seemantica.masuria.core.manager.master.IMasterManager;
import org.seemantica.masuria.core.manager.master.impl.MasterManager;
import org.seemantica.masuria.core.manager.peer.IPeerManager;
import org.seemantica.masuria.core.manager.peer.PeerManager;
import org.seemantica.masuria.core.partitioner.IMasterPartitioner;
import org.seemantica.masuria.core.partitioner.IPeerPartitioner;
import org.seemantica.masuria.core.partitioner.master.local.impl.LocalMasterPartitioner;
import org.seemantica.masuria.core.partitioner.peer.local.impl.LocalPeerPartitioner;
import org.seemantica.masuria.core.program.IProgram;
import org.seemantica.masuria.core.registry.IDescriptor;
import org.seemantica.masuria.core.registry.IDescriptorRegistry;
import org.seemantica.masuria.core.registry.local.impl.LocalDescriptorRegistryBase;
import org.seemantica.masuria.core.router.IMasterRouter;
import org.seemantica.masuria.core.router.IPeerRouter;
import org.seemantica.masuria.core.router.master.local.impl.LocalMasterRouter;
import org.seemantica.masuria.core.router.peer.local.impl.LocalPeerRouter;
import org.seemantica.masuria.demo.program.helloworld.HelloWorldProgram;
import org.seemantica.masuria.demo.program.sssp.SSSPProgram;

import org.seemantica.masuria.demo.DemoRelationshipTypes;





public class MasuriaDemoDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		IMasterManager clusterManager = new MasterManager();
		
		IDescriptorRegistry< IPeerManager >  peerRegistry = new LocalDescriptorRegistryBase<IPeerManager>();
		clusterManager.associatePeerRegistry(peerRegistry);
		
		IMasterPartitioner cPartitioner = new LocalMasterPartitioner();
		clusterManager.associatePartitioner(cPartitioner);
		
		//initialize neo4j cluster database
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase ( "./target/neo" );
		IMasterDatabase<Neo4JElement> cDatabase = new LocalNeo4JMasterDatabase(graphDb, cPartitioner);
		clusterManager.associateDatabase(cDatabase);

		
		IMasterExecutor cProgramExecutor = new LocalMasterExecutor();		
		clusterManager.associateProgramExecutor(cProgramExecutor);

		IMasterBarrier cBarrier = new LocalMasterBarrier();
		clusterManager.associateBarrier(cBarrier);

		IMasterRouter cRouter = new LocalMasterRouter();
		clusterManager.associateMessageRouter(cRouter);

		
		IPeerManager pm01;  { //peer01
			
			IPeerManager pm = new PeerManager();
			IDescriptor pmd = peerRegistry.register( pm );
			clusterManager.addPeer( pmd );

			IPeerBarrier barrier = new LocalPeerBarrier();
			pm.setBarrier(barrier);

			IPeerRouter router = new LocalPeerRouter();
			pm.setMessageRouter(router);
	
			IPeerExecutor executor = new LocalPeerExecutor();
			pm.setProgramExecutor(executor);
			
			IPeerPartitioner partitioner = new LocalPeerPartitioner();
			pm.setPartitioner(partitioner);
			
			IPeerDatabase<Neo4JElement> database = new LocalNeo4JPeerDatabase();
			pm.setDatabase(database);
			
			pm01=pm;
		}


		IPeerManager pm02;  { //peer02
			
			IPeerManager pm = new PeerManager();
			IDescriptor pmd = peerRegistry.register( pm );
			clusterManager.addPeer( pmd );

			IPeerBarrier barrier = new LocalPeerBarrier();
			pm.setBarrier(barrier);
			
			IPeerRouter router = new LocalPeerRouter();
			pm.setMessageRouter(router);
	
			IPeerExecutor executor = new LocalPeerExecutor();
			pm.setProgramExecutor(executor);
			
			IPeerPartitioner partitioner = new LocalPeerPartitioner();
			pm.setPartitioner(partitioner);
			
			IPeerDatabase<Neo4JElement> database = new LocalNeo4JPeerDatabase();
			pm.setDatabase(database);
			
			pm02=pm;
		}

		
		//fill in dataset, partitioners, etc.
		Transaction tx = graphDb.beginTx();
		
		cDatabase.clear();
		
		Set<LongElementId> startingElements=new HashSet<LongElementId>();
		
		try {

			Node n01p1, n02p1, n03p1, n04p1;
			Relationship rn01n02, rn02n03, rn01n04;
			{//partition01
				
				n01p1 = graphDb.createNode();
				n01p1.setProperty( "nodeName", "node01-p01" );
				((LocalMasterPartitioner)cPartitioner).addMaping(new Neo4JElement(n01p1, graphDb), pm01.getDescriptor());
				
				n02p1 = graphDb.createNode();
				n02p1.setProperty( "nodeName", "node02-p01" );
				((LocalMasterPartitioner)cPartitioner).addMaping(new Neo4JElement(n02p1, graphDb), pm01.getDescriptor());

				n03p1 = graphDb.createNode();
				n03p1.setProperty( "nodeName", "node03-p01" );
				((LocalMasterPartitioner)cPartitioner).addMaping(new Neo4JElement(n03p1, graphDb), pm01.getDescriptor());
				
				n04p1 = graphDb.createNode();
				n04p1.setProperty( "nodeName", "node04-p01" );
				((LocalMasterPartitioner)cPartitioner).addMaping(new Neo4JElement(n04p1, graphDb), pm01.getDescriptor());
				
				rn01n02 = n01p1.createRelationshipTo( n02p1, DemoRelationshipTypes.KNOWS );
				rn01n02.setProperty( "relationshipName", "node01-p1->node02-p1" );
				rn01n02.setProperty( "relationshipDistance", 1 );
				rn02n03 = n02p1.createRelationshipTo( n03p1, DemoRelationshipTypes.KNOWS );
				rn02n03.setProperty( "relationshipName", "node02-p1->node03-p1" );
				rn02n03.setProperty( "relationshipDistance", 1 );
				rn01n04 = n01p1.createRelationshipTo( n04p1, DemoRelationshipTypes.KNOWS );
				rn01n04.setProperty( "relationshipName", "node01-p1->node04-p1" );
				rn01n04.setProperty( "relationshipDistance", 10 );
				
				startingElements.add(new LongElementId(n01p1.getId()));
			}
			
			Node n01p2, n02p2, n03p2, n04p2, n05p2;
			Relationship rn01p2n02p2, rn02p2n03p2, rn03p2n04p2, rn04p2n05p2; 
			{//partition02
				
				n01p2 = graphDb.createNode();
				n01p2.setProperty( "nodeName", "node01-p02" );
				((LocalMasterPartitioner)cPartitioner).addMaping(new Neo4JElement(n01p2, graphDb), pm02.getDescriptor());
				
				n02p2 = graphDb.createNode();
				n02p2.setProperty( "nodeName", "node02-p02" );
				((LocalMasterPartitioner)cPartitioner).addMaping(new Neo4JElement(n02p2, graphDb), pm02.getDescriptor());
				
				n03p2 = graphDb.createNode();
				n03p2.setProperty( "nodeName", "node03-p02" );
				((LocalMasterPartitioner)cPartitioner).addMaping(new Neo4JElement(n03p2, graphDb), pm02.getDescriptor());

				n04p2 = graphDb.createNode();
				n04p2.setProperty( "nodeName", "node04-p02" );
				((LocalMasterPartitioner)cPartitioner).addMaping(new Neo4JElement(n04p2, graphDb), pm02.getDescriptor());

				n05p2 = graphDb.createNode();
				n05p2.setProperty( "nodeName", "node05-p02" );
				((LocalMasterPartitioner)cPartitioner).addMaping(new Neo4JElement(n05p2, graphDb), pm02.getDescriptor());

				
				rn01p2n02p2 = n01p2.createRelationshipTo( n02p2, DemoRelationshipTypes.KNOWS );
				rn01p2n02p2.setProperty( "relationshipName", "node01-p2->node02-p2" );
				rn01p2n02p2.setProperty( "relationshipDistance", 2 );
				rn02p2n03p2 = n02p2.createRelationshipTo( n03p2, DemoRelationshipTypes.KNOWS );
				rn02p2n03p2.setProperty( "relationshipName", "node02-p2->node03-p2" );
				rn02p2n03p2.setProperty( "relationshipDistance", 2 );
				rn03p2n04p2 = n03p2.createRelationshipTo( n04p2, DemoRelationshipTypes.KNOWS );
				rn03p2n04p2.setProperty( "relationshipName", "node03-p2->node04-p2" );
				rn03p2n04p2.setProperty( "relationshipDistance", 2 );
				rn04p2n05p2 = n04p2.createRelationshipTo( n05p2, DemoRelationshipTypes.KNOWS );
				rn04p2n05p2.setProperty( "relationshipName", "node04-p2->node05-p2" );
				rn04p2n05p2.setProperty( "relationshipDistance", 2 );
				
				//startingElements.add(new LongElementId(n01p2.getId()));
			}

			Relationship rn02p1n01p2, rn04p2n03p1, rn01p1n01p2, rn04p1n05p2, rn02p2n04p1;
			//inter-partition relations
			{
				rn02p1n01p2 = n02p1.createRelationshipTo( n01p2, DemoRelationshipTypes.KNOWS );
				rn02p1n01p2.setProperty( "relationshipName", "node02-p1->node01-p2" );
				rn02p1n01p2.setProperty( "relationshipDistance", 3 );
				
				rn04p2n03p1 = n04p2.createRelationshipTo( n03p1, DemoRelationshipTypes.KNOWS );
				rn04p2n03p1.setProperty( "relationshipName", "node04-p2->node03-p1" );
				rn04p2n03p1.setProperty( "relationshipDistance", 3 );

				rn01p1n01p2 = n01p1.createRelationshipTo( n01p2, DemoRelationshipTypes.KNOWS );
				rn01p1n01p2.setProperty( "relationshipName", "node01-p1->node01-p2" );
				rn01p1n01p2.setProperty( "relationshipDistance", 1 );

				rn04p1n05p2 = n04p1.createRelationshipTo( n05p2, DemoRelationshipTypes.KNOWS );
				rn04p1n05p2.setProperty( "relationshipName", "node04-p1->node05-p2" );
				rn04p1n05p2.setProperty( "relationshipDistance", 10 );

				rn02p2n04p1 = n02p2.createRelationshipTo( n04p1, DemoRelationshipTypes.KNOWS );
				rn02p2n04p1.setProperty( "relationshipName", "node02-p2->node04-p1" );
				rn02p2n04p1.setProperty( "relationshipDistance", 2 );
				
			}

			
		   tx.success();
		}
		finally  {
		   tx.finish();
		}		
		
		System.out.print(cDatabase.toString());
		
		
//		IProgram p1 = new HelloWorldProgram();
//		clusterManager.addProgram(p1);
//		clusterManager.runProgram(p1);

		IProgram p2 = new SSSPProgram(startingElements.toArray(new LongElementId[0]));
		clusterManager.addProgram(p2);
		clusterManager.runProgram(p2);

		System.out.print(cDatabase.toString());
		
		graphDb.shutdown();
	}

}
