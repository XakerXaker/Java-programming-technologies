package repositories;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import dto.User;

public class AccountRepository implements IAccountRepository
{
	private ArrayList<User> users = new ArrayList<>();
	
	public User FindUser(User targetUser)
	{
		for (User user : users)
		{
			if (user.id == targetUser.id)
				return user;
		}
		
		return null;
	}
	
	
	public void CreateAccount(User targetUser)
	{
		User user = FindUser(targetUser);
		if (user != null)
			throw new IllegalStateException("User already exists");
		
		users.add(targetUser);
	}
	
	public void WithdrawMoney(User targetUser, long amount)
	{
		User user = FindUser(targetUser);
		if (user == null)
			throw new NoSuchElementException("You cannot withdraw money from your account if you are not logged in");
		
		if (targetUser.balance < amount)
			throw new UnsupportedOperationException("Invalid amount for withdraw money operation");
		
		targetUser.balance -= amount;
	}
	
	public void DepositMoney(User targetUser, long amount)
	{
		User user = FindUser(targetUser);
		if (user == null)
			throw new NoSuchElementException("You cannot deposit money from your account if you are not logged in");
		
		user.balance += amount;
	}
	
	public long CheckBalance(User targetUser)
	{
		User user = FindUser(targetUser);
		if (user == null)
			throw new NoSuchElementException("You cannot check balance your account if you are not logged in");
		
		return user.balance;
	}


	public User FindUserByUserNameAndPassword(String userName, String password) 
	{
		for (User user : users)
		{
			if (user.userName.equals(userName) && user.password.equals(password))
			{
				return user;
			}
		}
			
		return null;
	}
}
