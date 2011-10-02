package org.test.entity.entitymapping;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.test.entity.shared.Child;
import org.test.entity.shared.Parent;


/**
 * @author sbn
 */
@Entity
@NamedQueries( 
{
	@NamedQuery( name = "findAllParentWithEntityChildren", query = "SELECT parent FROM ParentEntityChildren parent order by parent.id asc" )
} )
public class ParentEntityChildren implements Parent, Serializable
{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;
		

	@OneToMany
	@JoinColumn( name="parent_id", nullable = false )
	private Set<EntityChild> children = new HashSet<EntityChild>();
	
	
	public ParentEntityChildren() {}
	public ParentEntityChildren( Set<EntityChild> children )
	{
		this.children = children;
	}

	
	public Long getId()
	{
		return id;
	}

	
	@Override
	public Set<? extends Child> getChildren()
	{
		return Collections.unmodifiableSet( children );
	}
	
	
	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		// TODO: Warning - this method won't work in the case the id fields are not set
		ParentEntityChildren other = (ParentEntityChildren) obj;
		if ( id == null )
		{
			if ( other.id != null )
				return false;
		}
		else if ( !id.equals( other.id ) )
			return false;
		return true;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}