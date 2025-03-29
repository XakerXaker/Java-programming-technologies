package bankService;

import java.util.ArrayList;

import dto.Operation;
import dto.OperationType;
import dto.User;
import repositories.IAccountRepository;
import repositories.IOperationHistoryRepository;

public class BankService 
{
	private User user = null;

	private IAccountRepository accounts;
	private IOperationHistoryRepository operations;
	
	public BankService(IAccountRepository accounts, IOperationHistoryRepository operations)
	{
		this.accounts = accounts;
		this.operations = operations;
		
	}
	
	public void DepositMoney(long amount)
	{
		accounts.DepositMoney(user, amount);
		
		Operation operation = new Operation(OperationType.DEPOSIT_MONEY, user.id);
		operations.SaveOperation(operation);
	}
	
	public void TryToCreateAccount(String userName, String password)
	{
		user = new User(userName, password);
		accounts.CreateAccount(user);
		
		Operation operation = new Operation(OperationType.CREATE_ACCOUNT, user.id);
		operations.SaveOperation(operation);
		
		user = null; 
	}
	
	public boolean TryToLogIn(String userName, String password)
	{
		user = accounts.FindUserByUserNameAndPassword(userName, password);

		if (user != null) 
		{
			Operation operation = new Operation(OperationType.LOG_IN, user.id);
			operations.SaveOperation(operation);	
			return true;
		}
		
		return false;
	}
	
	public void WithdrawMoney(long amount)
	{
		accounts.WithdrawMoney(user, amount);
		
		Operation operation = new Operation(OperationType.WITHDRAW_MONEY, user.id);
		operations.SaveOperation(operation);
	}
	
	public long CheckBalance()
	{
		Operation operation = new Operation(OperationType.CHECK_BALANCE, user.id);
		operations.SaveOperation(operation);
		
		return accounts.CheckBalance(user);
	}
	
	public ArrayList<Operation> OperationHistory()
	{
		return operations.ShowHistory(user);
	}
}
