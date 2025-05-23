package Controller;


import jakarta.persistence.EntityManagerFactory;
import OrmModel.Color;
import OrmModel.Owner;
import OrmModel.Pet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import Repositories.PetRepository;
import Services.PetService;

public class PetController  {
	private final Owner owner;
    private final Scanner scanner;
    private final PetService petService;
    private final EntityManagerFactory emf;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public PetController(EntityManagerFactory factory, Owner own) {
    	owner = own;
        this.scanner = new Scanner(System.in);
        this.emf = factory;
        this.petService = new PetService(new PetRepository(emf));
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                    	addNewPet();
                    	break;
                    case 2:
                    	listAllPets();
                    	break;
                    case 3:
                    	findPetById();
                    	break;
                    case 4:
                    	deletePet();
                    	break;
                    case 5:
                    	addPetFriend();
                    	break;
                    case 0:
                    	running = false;
                    	break;
                    default: 
                    	System.out.println("Неверный выбор. Попробуйте снова.");
                    	break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число.");
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Управление питомцами ===");
        System.out.println("1. Добавить нового питомца");
        System.out.println("2. Показать всех питомцев");
        System.out.println("3. Найти питомца по ID");
        System.out.println("4. Удалить питомца");
        System.out.println("5. Добавить друга питомцу");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void addNewPet() {
        System.out.println("\n=== Добавление нового питомца ===");
        
        System.out.print("Введите имя питомца: ");
        String name = scanner.nextLine();

        LocalDate birthDate = readDate("Введите дату рождения (дд.мм.гггг): ");
        if (birthDate == null) return;

        System.out.print("Введите породу: ");
        String breed = scanner.nextLine();
        
        System.out.print("Введите цвет: 0 - Черный, 1 - Белый, 2 - Cерый, 3 - Желтый, 4 - Рыжий");
        int colorI = scanner.nextInt();
        Color color = Color.values()[colorI];
        
        if (color == null) return;
       
        Pet pet = new Pet();
        pet.setBirthDate(birthDate);
        pet.setName(name);
        pet.setBreed(breed);
        pet.setColor(color);
   
        petService.addPet(pet);
        owner.getPets().add(pet);
        System.out.println("Питомец успешно добавлен");
    }

    private void listAllPets() {
        System.out.println("\n=== Список всех питомцев ===");
        List<Pet> pets = owner.getPets();
        if (pets.isEmpty()) {
            System.out.println("Питомцев пока нет");
            return;
        }

        for (Pet pet : pets) {
            displayPetInfo(pet);
        }
    }

    private void findPetById() {
        System.out.print("Введите ID питомца: ");
        try {
        	long id = scanner.nextLong();
            for (Pet pet: owner.getPets())
            {
            	if (pet.getId() == id)
            	{
            		displayPetInfo(pet);
            		break;
            	}
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ID");
        }
    }

  
    private void deletePet() {
        System.out.print("Введите ID питомца для удаления: ");
        Boolean found = false;
        try {
        	long id = scanner.nextLong();
            Iterator<Pet> it = owner.getPets().iterator();
            while (it.hasNext()) {
              Pet pet = it.next();
              if (pet.getId() == id) {
            	  found = true;
                  it.remove();
                  break;
              }
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ID");
        }
        
        if (found == false) {
        	System.out.print("Питомец с данным ID не найден");
        }
    }

    private void addPetFriend() {
        System.out.print("Введите ID питомца: ");
        Boolean found = false;
        Pet current = new Pet();
        try {
        	long id = scanner.nextLong();
            for (Pet pet: owner.getPets())
            {
            	if (pet.getId() == id)
            	{
            		found = true;
            		current = pet;
            		break;
            	}
            }
            if (found == false) {
                System.out.println("Питомец с ID " + id + " не найден");
                return;
            }

            System.out.print("Введите ID друга: ");
            long idFriend = scanner.nextLong();

            if (id == idFriend) {
                System.out.println("Питомец не может дружить сам с собой");
                return;
            }

            Pet friend = petService.getById(idFriend);
            if (friend == null) {
                System.out.println("Питомец с ID " + idFriend + " не найден");
                return;
            }

            current.getFriends().add(friend);
            petService.update(current);
            System.out.println("Друг успешно добавлен!");
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ID");
        }
    }

    private void displayPetInfo(Pet pet) {
        System.out.printf("ID: %d, Имя: %s, Дата рождения: %s, Порода: %s, Цвет: %s%n",
                pet.getId(),
                pet.getName(),
                pet.getBirthDate().format(DATE_FORMATTER),
                pet.getBreed(),
                pet.getColor().toString());
        
        if (pet.getOwner() != null) {
            System.out.printf("Владелец: %s%n", pet.getOwner().getName());
        }

        if (!pet.getFriends().isEmpty()) {
            System.out.print("Друзья: ");
            pet.getFriends().forEach(friend -> System.out.print(friend.getName() + ", "));
            System.out.println();
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateStr = scanner.nextLine();
            try {
                return LocalDate.parse(dateStr, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Некорректный формат даты. Используйте формат дд.мм.гггг");
                System.out.print("Хотите попробовать снова? (да/нет): ");
                if (!scanner.nextLine().equalsIgnoreCase("да")) {
                    return null;
                }
            }
        }
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
} 