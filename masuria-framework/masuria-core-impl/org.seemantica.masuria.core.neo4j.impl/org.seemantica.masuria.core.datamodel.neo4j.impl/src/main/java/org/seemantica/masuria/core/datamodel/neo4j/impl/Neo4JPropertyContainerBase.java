package org.seemantica.masuria.core.datamodel.neo4j.impl;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Transaction;
import org.seemantica.masuria.core.datamodel.IPropertyContainer;


public abstract class Neo4JPropertyContainerBase<C extends PropertyContainer> implements IPropertyContainer {
	
	protected final C container;
	
	protected GraphDatabaseService database; //NOTE;tch: workaround for handling transactions via elements
	
	public Neo4JPropertyContainerBase(final C container, final GraphDatabaseService database) {
		this.container = container;
		this.database = database;
	}
	

	@Override
	public boolean hasProperty(String key) {
		
		return container.hasProperty(key);
	}
	
	@Override
	public Object getProperty(String key) {

		Object property;
		
		Transaction tx = database.beginTx();
		
		try {
			
			property = container.getProperty(key);

			tx.success();
		}
		 finally  {
			tx.finish();
		}

		return property;
	}
	
	@Override
	public Object getProperty(String key, Object defaultValue) {

		Object property;
		
		Transaction tx = database.beginTx();
		
		try {
			
			property = container.getProperty(key, defaultValue);

			tx.success();
		}
		 finally  {
			tx.finish();
		}

		return property;
	}
	
	@Override
	public void setProperty(String key, Object value) {

		Transaction tx = database.beginTx();
		
		try {
			
			container.setProperty(key, value);

			tx.success();
		}
		 finally  {
			tx.finish();
		}
	
	}
	
	@Override
	public Object removeProperty(String key) {
		
		Object property;
		
		Transaction tx = database.beginTx();
		
		try {
			
			property = container.removeProperty(key);

			tx.success();
		}
		 finally  {
			tx.finish();
		}

		return property;
	}
	
	@Override
	public Iterable<String> getPropertyKeys() {
		
		Iterable<String> keys;
		
		Transaction tx = database.beginTx();
		
		try {
			
			keys = getPropertyKeys();

			tx.success();
		}
		 finally  {
			tx.finish();
		}

		return keys;
	}

	
}
