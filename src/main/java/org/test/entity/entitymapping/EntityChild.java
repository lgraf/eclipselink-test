package org.test.entity.entitymapping;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.test.entity.shared.Child;
import org.test.entity.shared.EntityRelation;


/**
 * @author lgf
 */
@Entity
public class EntityChild implements Child, Serializable
{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;
	
	@ManyToOne( optional = false )
	private EntityRelation entityRelation;
	
	private String childName;
	
	
	public EntityChild() {}
	public EntityChild( String childName, EntityRelation entityRelation )
	{
		this.childName = childName;
		this.entityRelation = entityRelation;
	}
	
	
	public Long getId()
	{
		return id;
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
		final EntityChild other = ( EntityChild ) obj;
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