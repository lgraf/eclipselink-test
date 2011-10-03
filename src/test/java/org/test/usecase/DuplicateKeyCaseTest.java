package org.test.usecase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author lgf
 */
public class DuplicateKeyCaseTest
{

	private static EntityManagerFactory emf;
	private EntityManager em;


	@BeforeClass
	public static void createEntityManagerFactory()
	{
		emf = Persistence.createEntityManagerFactory( "eclipselinkTestPU" );
	}


	@Before
	public void setUpDBAndBeginTransaction()
	{
		createEntityManager();
		setUpDBInOwnTransaction();
		em.getTransaction().begin();
	}


	@After
	public void commitTransactionAndCleanUpDB()
	{
		if( !em.getTransaction().getRollbackOnly() )
		{
			em.getTransaction().commit();
		}
		else
		{
			em.getTransaction().rollback();
			// use new EntityManager when an exception was throwed during test execution
			destroyEntityManager();
			createEntityManager();
		}
		cleanUpDBInOwnTransaction();
		destroyEntityManager();
	}


	@AfterClass
	public static void destroyEntityManagerFactory()
	{
		if ( null != emf )
		{
			emf.close();
			emf = null;
		}
	}


	@Test
	public void forceDuplicateKeyExceptionWithParentEmbeddedSetChildren()
	{
		new DuplicateKeyCase( em ).embeededChildrenSetCase();
	}
	
	
	@Test
	public void forceDuplicateKeyExceptionWithParentEmbeddedListChildren()
	{
		new DuplicateKeyCase( em ).embeededChildrenListCase();
	}
	
	
	@Test
	public void forceDuplicateKeyExceptionWithParentEntityChildren()
	{
		new DuplicateKeyCase( em ).entityChildrenCase();
	}


	private void createEntityManager()
	{
		em = emf.createEntityManager();
	}


	private void destroyEntityManager()
	{
		if ( null != em )
		{
			em.close();
			em = null;
		}
	}


	private void setUpDBInOwnTransaction()
	{
		em.getTransaction().begin();
		em.createNativeQuery( "INSERT INTO EntityRelation VALUES (1, 'relation1', 'STRING_T')" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO EntityRelation VALUES (2, 'relation2', 'INTEGER_T')" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO EntityRelation VALUES (3, 'relation3', 'DATE_T')" ).executeUpdate();

		
		em.createNativeQuery( "INSERT INTO ParentEmbeddedSetChildren VALUES (1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedSetChildren VALUES (2)" ).executeUpdate();
		
		em.createNativeQuery( "INSERT INTO ParentEmbeddedSetChildren_Children VALUES ('child1', 1, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedSetChildren_Children VALUES ('child2', 2, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedSetChildren_Children VALUES ('child3', 3, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedSetChildren_Children VALUES ('child4', 1, 2)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedSetChildren_Children VALUES ('child5', 2, 2)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedSetChildren_Children VALUES ('child6', 3, 2)" ).executeUpdate();
		
		
		em.createNativeQuery( "INSERT INTO ParentEmbeddedListChildren VALUES (1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedListChildren VALUES (2)" ).executeUpdate();

		em.createNativeQuery( "INSERT INTO ParentEmbeddedListChildren_Children VALUES ('child1', 1, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedListChildren_Children VALUES ('child2', 2, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedListChildren_Children VALUES ('child3', 3, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedListChildren_Children VALUES ('child4', 1, 2)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedListChildren_Children VALUES ('child5', 2, 2)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEmbeddedListChildren_Children VALUES ('child6', 3, 2)" ).executeUpdate();

		
		em.createNativeQuery( "INSERT INTO ParentEntityChildren VALUES (1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO ParentEntityChildren VALUES (2)" ).executeUpdate();

		em.createNativeQuery( "INSERT INTO EntityChild VALUES (1, 'child1', 1, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO EntityChild VALUES (2, 'child1', 2, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO EntityChild VALUES (3, 'child1', 3, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO EntityChild VALUES (4, 'child1', 1, 2)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO EntityChild VALUES (5, 'child1', 2, 2)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO EntityChild VALUES (6, 'child1', 3, 2)" ).executeUpdate();
		em.getTransaction().commit();
	}


	private void cleanUpDBInOwnTransaction()
	{
		em.getTransaction().begin();
		em.createNativeQuery( "DELETE FROM ParentEmbeddedSetChildren_Children" ).executeUpdate();
		em.createNativeQuery( "DELETE FROM ParentEmbeddedSetChildren" ).executeUpdate();
		
		em.createNativeQuery( "DELETE FROM ParentEmbeddedListChildren_Children" ).executeUpdate();
		em.createNativeQuery( "DELETE FROM ParentEmbeddedListChildren" ).executeUpdate();

		em.createNativeQuery( "DELETE FROM EntityChild" ).executeUpdate();
		em.createNativeQuery( "DELETE FROM ParentEntityChildren" ).executeUpdate();

		em.createNativeQuery( "DELETE FROM EntityRelation" ).executeUpdate();
		em.getTransaction().commit();
	}

}
