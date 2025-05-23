package Repositories;

import java.util.List;

import OrmModel.Pet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PetRepository implements IPetRepository {
	EntityManagerFactory emf;
	
	public PetRepository(EntityManagerFactory factory)
	{
		emf = factory;
	}
	
	@Override
	public Pet save(Pet entity) {
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
			Pet pet = entityManager.find(Pet.class, id);
			entityManager.remove(pet);
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
	public void deleteByEntity(Pet entity) {
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
			entityManager.createQuery("DELETE FROM pet").executeUpdate();
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
	public Pet update(Pet entity) {
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
	public Pet getById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Pet.class, id);
        } finally {
            em.close();
        }
    }

	@Override
	public List<Pet> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM pet p", Pet.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}