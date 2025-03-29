package dto;


public class Operation
{
	public static long count = 0;
	public long id;
	public long userId;
	
	public OperationType type;
	
	public Operation(OperationType operationType, long userId)
	{
		id = count++;
		type = operationType;
		this.userId = userId;
	}
}