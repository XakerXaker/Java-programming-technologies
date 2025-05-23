package Services;

import java.util.List;

import OrmModel.Pet;
import Repositories.IPetRepository;

public class PetService {
    private final IPetRepository repo;

    public PetService(IPetRepository repository)
    {
    	repo = repository;
    }

    public Pet getById(long id)
    {
        return repo.getById(id);
    }

 
    public void addPet(Pet pet)
    {
    	repo.save(pet);
    }
    
    public void deletePetById(long petId)
    {
    	repo.deleteById(petId);
    }

	public List<Pet> getAll() {
		return repo.getAll();
	}

	public void update(Pet pet) {
		repo.update(pet);
	}
}
