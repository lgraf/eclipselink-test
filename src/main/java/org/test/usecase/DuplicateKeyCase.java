package org.test.usecase;

import java.util.List;

import javax.persistence.EntityManager;

import org.test.entity.Child;
import org.test.entity.Parent;


class DuplicateKeyCase
{

	private final EntityManager em;


	DuplicateKeyCase( EntityManager em )
	{
		this.em = em;
	}


	void forceDuplicateKeyException()
	{
		List<Parent> parents = em.createNamedQuery( "findAllParents", Parent.class )
				.getResultList();
		for ( Parent parent : parents )
		{
			for ( Child child : parent.getChildren() )
			{
				child.getEntityRelation();
			}
			em.flush(); // seems the problem ...
		}
	}
}