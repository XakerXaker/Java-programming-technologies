package repositories;


import dto.User;

public interface IAccountRepository 
{
	
	public User FindUser(User targetUser);
	
	public User FindUserByUserNameAndPassword(String userName, String password);
	
	public void CreateAccount(User targetUser);
	
	public void DepositMoney(User targetUser, long amount);
	
	public void WithdrawMoney(User targetUser, long amount);
	
	public long CheckBalance(User targetUser);
}
