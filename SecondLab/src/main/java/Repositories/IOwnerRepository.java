package Repositories;

import java.util.List;

import OrmModel.Owner;

public interface IOwnerRepository
{
	public Owner save(Owner entity);
	public void deleteById(long id);
	public void deleteByEntity(Owner entity);
	public void deleteAll();
	public Owner update(Owner entity);
	public Owner getById(long id);
	public List<Owner> getAll();
}