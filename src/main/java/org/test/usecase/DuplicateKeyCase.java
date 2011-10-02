package org.test.usecase;

import java.util.List;

import javax.persistence.EntityManager;

import org.test.entity.embeddedmapping.ParentEmbeddedChildren;
import org.test.entity.entitymapping.ParentEntityChildren;
import org.test.entity.shared.Child;
import org.test.entity.shared.Parent;


class DuplicateKeyCase
{

	private final EntityManager em;


	DuplicateKeyCase( EntityManager em )
	{
		this.em = em;
	}


	void embeededChildrenCase()
	{
		List<ParentEmbeddedChildren> parents = em.createNamedQuery(
				"findAllParentWithEmbeddedChildren", ParentEmbeddedChildren.class ).getResultList();
		forceDuplicateKeyException( parents );
	}


	void entityChildrenCase()
	{
		List<ParentEntityChildren> parents = em.createNamedQuery(
				"findAllParentWithEntityChildren", ParentEntityChildren.class ).getResultList();
		forceDuplicateKeyException( parents );
	}


	void forceDuplicateKeyException( List<? extends Parent> parents )
	{
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