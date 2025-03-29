package dto;

public class User 
{
	public static long count = 0;
	public long id;
	
	public long balance;
	
	public String userName;
	public String password;
	
	public User(String userName, String password)
	{
		id = count++;
		
		this.userName = userName;
		this.password = password;
		
		balance = 0;
	}
}