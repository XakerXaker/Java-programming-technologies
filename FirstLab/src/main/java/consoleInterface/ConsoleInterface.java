package consoleInterface;

import java.util.Scanner;

import bankService.BankService;
import dto.Operation;
import repositories.AccountRepository;
import repositories.OperationHistoryRepository;

public class ConsoleInterface 
{
	private Scanner in = new Scanner(System.in);
	
	private  BankService service = new BankService(new AccountRepository(), new OperationHistoryRepository());
	
	public void Run()
	{
		while (true)
		{
			System.out.print("Choose option: 1 - Create account, 2 - Login Account, 3 - Withdraw Money, 4 - Deposit Money, 5 - Check Balance, 6 - Show operation history");
			
			int option = in.nextInt();
		
			switch (option)
			{
				case 1: Create(); 
						break;
				case 2: LogIn();
						break;
				case 3: WithdrawMoney();
						break;
				case 4: DepositMoney();
						break;
				case 5: CheckBalance();
						break;
				case 6: ShowOperationHistory();
						break;
				default: System.out.println("Incorrect input!");
						break;
			}
		}
	}
	
	public void Create()
	{
		System.out.println("Enter login");
		String login = in.next();
		
		System.out.println("Enter password");
		String password = in.next();
		
		service.TryToCreateAccount(login, password);
		System.out.println("Account is successfully created");
	}
	
	public void LogIn()
	{
		System.out.println("Enter login");
		String login = in.next();
		
		System.out.println("Enter password");
		String password = in.next();
		
		if (service.TryToLogIn(login, password))
		{
			System.out.println("You have logged in to your account successfully");
		}
		
		else System.out.println("Inccorect login or password");
	}
	
	public void DepositMoney()
	{
		System.out.println("How much money do you want to deposit?");
		int amount = in.nextInt();
		
		service.DepositMoney(amount);
		
		System.out.println("You have successfully deposited the money");
	}
	
	public void WithdrawMoney()
	{
		System.out.println("How much money do you want to get?");
		int amount = in.nextInt();
		
		service.WithdrawMoney(amount);
		
		System.out.println("You have successfully get the money");
	}
	
	public void CheckBalance()
	{
		long balance = service.CheckBalance();
		System.out.println("Your current balance is " + balance);
	}
	
	public void ShowOperationHistory() 
	{
		if (service.OperationHistory().isEmpty())
		{
			System.out.println("No any operations done");
			return;
		}
		
		for (Operation operation : service.OperationHistory())
		{
			System.out.println("Operation id: " + operation.id + " Operation type: "  + operation.type + " User id: " + operation.userId);
		}
	}
	

}