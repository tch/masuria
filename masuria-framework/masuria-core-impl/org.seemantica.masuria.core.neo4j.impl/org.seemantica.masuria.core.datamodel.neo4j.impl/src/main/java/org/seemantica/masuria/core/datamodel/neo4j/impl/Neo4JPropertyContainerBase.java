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
