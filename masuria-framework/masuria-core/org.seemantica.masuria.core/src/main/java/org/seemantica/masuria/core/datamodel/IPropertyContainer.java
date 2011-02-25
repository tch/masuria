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
package org.seemantica.masuria.core.datamodel;

/**
 * @author tch
 *
 * NOTE;tch: almost-verbatim copy paste from neo4j 
 */
public interface IPropertyContainer {

    public boolean hasProperty( String key );

    public Object getProperty( String key );

    public Object getProperty( String key, Object defaultValue );

    public void setProperty( String key, Object value );

    public Object removeProperty( String key );

    public Iterable<String> getPropertyKeys();	
	
}
