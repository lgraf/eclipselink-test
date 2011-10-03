package org.test.entity.embeddedmapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.test.entity.shared.Child;
import org.test.entity.shared.Parent;


/**
 * @author sbn
 */
@Entity
@NamedQueries( 
{
	@NamedQuery( name = "findAllParentWithEmbeddedListChildren", query = "SELECT parent FROM ParentEmbeddedListChildren parent order by parent.id asc" )
} )
public class ParentEmbeddedListChildren implements Parent, Serializable
{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;
		

	@ElementCollection
	@CollectionTable( joinColumns = @JoinColumn( name = "parent_id", nullable = false ) )
	private List<EmbeddableChild> children = new ArrayList<EmbeddableChild>();
	
	
	public ParentEmbeddedListChildren() {}
	public ParentEmbeddedListChildren( List<EmbeddableChild> children )
	{
		this.children = children;
	}

	
	public Long getId()
	{
		return id;
	}

	
	@Override
	public List<? extends Child> getChildren()
	{
		return Collections.unmodifiableList( children );
	}
	
	
	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		// TODO: Warning - this method won't work in the case the id fields are not set
		ParentEmbeddedListChildren other = (ParentEmbeddedListChildren) obj;
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