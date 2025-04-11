package repositories;

import java.util.ArrayList;

import dto.Operation;
import dto.User;

public interface IOperationHistoryRepository 
{
	public void SaveOperation(Operation operation);
	
	public ArrayList<Operation> ShowHistory(User user);
}
