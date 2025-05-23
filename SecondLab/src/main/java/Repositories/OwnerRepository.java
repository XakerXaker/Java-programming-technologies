package Repositories;

import java.util.List;

import OrmModel.Owner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class OwnerRepository implements IOwnerRepository {
	EntityManagerFactory emf;
	
	public OwnerRepository(EntityManagerFactory factory)
	{
		emf = factory;
	}
	
	@Override
	public Owner save(Owner entity) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
		}
		catch (Exception e)
		{
			entityManager.getTransaction().rollback();
		}
		finally {
			entityManager.close();
		}
		
		return entity;
	}

	@Override
	public void deleteById(long id) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Owner owner = entityManager.find(Owner.class, id);
			entityManager.remove(owner);
			entityManager.getTransaction().commit();
		}
		catch (Exception e)
		{
			entityManager.getTransaction().rollback();
		}
		finally {
			entityManager.close();
		}
	}

	@Override
	public void deleteByEntity(Owner entity) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();	
		}
		catch (Exception e)
		{
			entityManager.getTransaction().rollback();
		}
		finally
		{
			entityManager.close();
		}
	}

	@Override
	public void deleteAll() {
		EntityManager entityManager = emf.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.createQuery("DELETE FROM owner").executeUpdate();
			entityManager.getTransaction().commit();	
		}
		catch (Exception e) 
		{
			entityManager.getTransaction().rollback();
		}
		finally {
			entityManager.close();
		}
	}

	@Override
	public Owner update(Owner entity) {
		EntityManager entityManager = emf.createEntityManager();
		try {	
			entityManager.getTransaction().begin();
			entityManager.merge(entity);
			entityManager.getTransaction().commit();
		}
		catch (Exception e) 
		{
			entityManager.getTransaction().rollback();
		}
		finally {
			entityManager.close();
		}
		return entity;
	}

	@Override
	public Owner getById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Owner.class, id);
        } finally {
            em.close();
        }
    }

	@Override
	public List<Owner> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT o FROM Owner o", Owner.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
