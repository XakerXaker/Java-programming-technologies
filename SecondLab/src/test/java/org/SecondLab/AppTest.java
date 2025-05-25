package org.SecondLab;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import OrmModel.Color;
import OrmModel.Pet;
import Repositories.PetRepository;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetDaoTest {

    @Mock
    private EntityManagerFactory emf;

    @Mock
    private EntityManager em;

    @Mock
    private EntityTransaction transaction;

    @Mock
    private TypedQuery<Pet> query;

    private PetRepository petDao;
    private Pet testPet;

    @BeforeEach
    void setUp() {
        petDao = new PetRepository(emf);
        testPet = createTestPet();

        when(emf.createEntityManager()).thenReturn(em);
        when(em.getTransaction()).thenReturn(transaction);
    }

    private Pet createTestPet() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("TestCat");
        pet.setBirthDate(LocalDate.now());
        pet.setBreed("TestBreed");
        pet.setColor(Color.BLACK);
        return pet;
    }

    @Test
    void saveShouldPersistPet() {
        doNothing().when(transaction).begin();
        doNothing().when(transaction).commit();
        doNothing().when(em).persist(any(Pet.class));

        Pet savedPet = petDao.save(testPet);
        
        assertNotNull(savedPet);
        assertEquals("TestCat", savedPet.getName());
        verify(em).persist(testPet);
        verify(transaction).commit();
    }


    @Test
    void updateShouldMergePet() {
        when(em.merge(testPet)).thenReturn(testPet);
        
        Pet updatedPet = petDao.update(testPet);
    
        assertNotNull(updatedPet);
        assertEquals("TestCat", updatedPet.getName());
        verify(em).merge(testPet);
        verify(transaction).commit();
    }

   

    @Test
    void deleteByIdShouldRemovePet() {
        when(em.find(Pet.class, 1L)).thenReturn(testPet);
        doNothing().when(em).remove(any(Pet.class));

        petDao.deleteById(1L);

        verify(em).remove(testPet);
        verify(transaction).commit();
    }

    @Test
    void deleteByIdShouldNotRemoveWhenPetNotFound() {
        when(em.find(Pet.class, 999L)).thenReturn(null);

        petDao.deleteById(999L);

        verify(em, never()).remove(any(Pet.class));
        verify(transaction).commit();
    }

} 