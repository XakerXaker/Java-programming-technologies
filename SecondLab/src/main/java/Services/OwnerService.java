package Services;

import java.time.LocalDate;
import java.util.List;

import OrmModel.Owner;
import Repositories.IOwnerRepository;

public class OwnerService {

	    private final IOwnerRepository repo;

	    public OwnerService(IOwnerRepository repository)
	    {
	        repo = repository;
	    }

	    public Owner getOwnerById(long id)
	    {
	        return repo.getById(id);
	    }

	    public void updateOwnerName(long id, String newName)
	    {
	    	Owner owner = repo.getById(id);
	    	owner.setName(newName);
	    	repo.save(owner);
	    }

	    public void addOwner(String name, LocalDate birthDate)
	    {
	    	Owner owner = new Owner();
	    	owner.setName(name);
	    	owner.setBirthDate(birthDate);
	    	
	    	repo.save(owner);
	    }
	    
	    public void deleteOwner(long ownerId)
	    {
	    	repo.deleteById(ownerId);
	    }

		public List<Owner> getAll() {
			
			return repo.getAll();
		}

}
