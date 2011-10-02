package org.test.entity.embeddedmapping;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.test.entity.shared.Child;
import org.test.entity.shared.EntityRelation;


/**
 * @author lgf
 */
@Embeddable
public class EmbeddableChild implements Child, Serializable
{

	@ManyToOne( optional = false )
	private EntityRelation entityRelation;
	
	private String childName;
	
	
	public EmbeddableChild() {}
	public EmbeddableChild( String childName, EntityRelation entityRelation )
	{
		this.childName = childName;
		this.entityRelation = entityRelation;
	}

	
	public EntityRelation getEntityRelation()
	{
		return entityRelation;
	}
	
	
	public String getChildName()
	{
		return childName;
	}
	
	
	@Override
	public boolean equals( Object obj )
	{
		if( obj == null )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		final EmbeddableChild other = ( EmbeddableChild ) obj;
		if( this.entityRelation != other.entityRelation && (this.entityRelation == null || !this.entityRelation.equals( other.entityRelation )) )
			return false;
		return true;
	}


	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 97 * hash + (this.entityRelation != null ? this.entityRelation.hashCode() : 0);
		return hash;
	}
	
}