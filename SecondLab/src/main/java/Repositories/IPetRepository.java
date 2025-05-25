package Repositories;

import java.util.List;

import OrmModel.Pet;

public interface IPetRepository
{
	public Pet save(Pet entity);
	public void deleteById(long id);
	public void deleteByEntity(Pet entity);
	public void deleteAll();
	public Pet update(Pet entity);
	public Pet getById(long id);
	public List<Pet> getAll();
}