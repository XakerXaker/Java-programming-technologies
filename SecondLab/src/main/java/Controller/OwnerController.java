package Controller;

import jakarta.persistence.EntityManagerFactory;
import OrmModel.Owner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import Repositories.OwnerRepository;
import Services.OwnerService;

public class OwnerController  {
    private final Scanner scanner;
    private final OwnerService ownerService;
    public Owner owner;
    private final EntityManagerFactory emf;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public OwnerController(EntityManagerFactory factory) {
        this.scanner = new Scanner(System.in);
        this.emf = factory;
        this.ownerService = new OwnerService(new OwnerRepository(emf));
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                    	addNewOwner();
                    	break;
                    case 2:
                    	listAllOwners();
                    	break;
                    case 3:
                    	deleteOwner();
                    	break;
                    case 4:
                    	chooseOwner();
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
        System.out.println("\n=== Управление владельцами ===");
        System.out.println("1. Добавить нового владельца");
        System.out.println("2. Показать всех владельцев");
        System.out.println("3. Удалить владельца");
        System.out.println("4. Выбрать владельца");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }
    
    public void chooseOwner() {
    	System.out.println("Введите ID владельца для выбора");
    	long id = scanner.nextLong();
    	owner = ownerService.getOwnerById(id);
    }
    private void addNewOwner() {
        System.out.println("\n=== Добавление нового владельца ===");
        
        System.out.print("Введите имя владельца: ");
        String name = scanner.nextLine();

        LocalDate birthDate = readDate("Введите дату рождения (дд.мм.гггг): ");
        if (birthDate == null) return;
        

        ownerService.addOwner(name, birthDate);
        System.out.println("Владелец успешно добавлен");
    }

    private void listAllOwners() {
        System.out.println("\n=== Список всех владельцев ===");
   
        List<Owner> owners = ownerService.getAll();
            if (owners.isEmpty()) {
            System.out.println("Владельцев пока нет");
            return;
        }

        for (Owner owner : owners) {
            displayOwnerInfo(owner);
        }
    }


    private void displayOwnerInfo(Owner owner) {
        System.out.printf("ID: %d, Имя: %s, Дата рождения: %s",
                owner.getId(),
                owner.getName(),
                owner.getBirthDate().format(DATE_FORMATTER));
 
    }
    
    private void deleteOwner() {
        System.out.print("Введите ID владельца: ");
        Boolean found = false;
        try {
            long id = scanner.nextLong();
            List<Owner> owners = ownerService.getAll();
            for (Owner owner : owners) 
            {
            	if (id == owner.getId())
            	{
            		ownerService.deleteOwner(id);
            		found = true;
            		break;
            	}
            }
        
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ID");
        }
        
        if (found == false) {
        	System.out.print("Владелец с данным ID не найден");
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