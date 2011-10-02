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
	public void forceDuplicateKeyException() throws Exception
	{
		new DuplicateKeyCase( em ).forceDuplicateKeyException();
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
		em.createNativeQuery( "INSERT INTO parent VALUES (1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO parent VALUES (2)" ).executeUpdate();

		em.createNativeQuery( "INSERT INTO entityrelation VALUES (1, 'relation1', 'STRING_T')" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO entityrelation VALUES (2, 'relation2', 'INTEGER_T')" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO entityrelation VALUES (3, 'relation3', 'DATE_T')" ).executeUpdate();

		em.createNativeQuery( "INSERT INTO parent_children VALUES ('child1', 1, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO parent_children VALUES ('child2', 2, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO parent_children VALUES ('child3', 3, 1)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO parent_children VALUES ('child4', 1, 2)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO parent_children VALUES ('child5', 2, 2)" ).executeUpdate();
		em.createNativeQuery( "INSERT INTO parent_children VALUES ('child6', 3, 2)" ).executeUpdate();
		em.getTransaction().commit();
	}

	private void cleanUpDBInOwnTransaction()
	{
		em.getTransaction().begin();
		em.createNativeQuery( "DELETE FROM parent_children" ).executeUpdate();
		em.createNativeQuery( "DELETE FROM parent" ).executeUpdate();
		em.createNativeQuery( "DELETE FROM entityrelation" ).executeUpdate();
		em.getTransaction().commit();
	}

}
