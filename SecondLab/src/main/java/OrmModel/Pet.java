package OrmModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
public class Pet {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;
    private LocalDate birthDate;
    private String breed;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToMany
    @JoinTable(
        name = "pet_friends",
        joinColumns = @JoinColumn(name = "pet_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Pet> friends = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    
    public void setName(String newName) {
    	name = newName;
    }
    
    public void setBreed(String newBreed) {
    	breed = newBreed;
    }
    
    public void setColor(Color newColor) {
    	color = newColor;
    }
    
    public void setBirthDate(LocalDate newBirthDate) {
    	birthDate = newBirthDate;
    }
    
    public String getName() {
    	return name;
    }
    
    public Long getId() {
    	return id;
    }    
    
    public LocalDate getBirthDate() {
    	return birthDate;
    }    
    
    public String getBreed() {
    	return breed;
    }
    
    public List<Pet> getFriends() {
    	return friends;
    }

	public Color getColor() {
		return color;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setId(long Id) {
		id = Id;
	}
}