package OrmModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Pet> pets = new ArrayList<>();
    
    public String getName() {
    	return name;
    }
    
    public Long getId() {
    	return id;
    }
    
    public LocalDate getBirthDate() {
    	return birthDate;
    }
    
    public void setBirthDate(LocalDate date)
    {
    	birthDate = date;
    }
    
    public void setName(String newName)
    {
    	name = newName;
    }
    
    public List<Pet> getPets() {
    	return pets;
    }
}