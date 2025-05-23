package org.SecondLab;

import Controller.OwnerController;
import Controller.PetController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App
{
	public static void main(String[] args)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myunit");
		
		OwnerController ownerController = new OwnerController(emf);
		ownerController.start();
		
		if (ownerController.owner != null) {
			PetController petController = new PetController(emf, ownerController.owner);
			petController.start();
		}
		System.out.print("Finished!");
	}
}
