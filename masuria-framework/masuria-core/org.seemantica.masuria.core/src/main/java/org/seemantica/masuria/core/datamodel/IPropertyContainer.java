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
