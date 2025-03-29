package repositories;

import java.util.ArrayList;

import dto.*;

public class OperationHistoryRepository implements IOperationHistoryRepository
{
	private ArrayList<Operation> operationList = new ArrayList<>();
	
	public void SaveOperation(Operation operation)
	{
		operationList.add(operation);
	}
	
	public ArrayList<Operation> ShowHistory(User user)
	{
		ArrayList<Operation> operationHistory = new ArrayList<Operation>();
		
		for (Operation operation : operationList)
		{
			if (operation.userId == user.id)
			{
				operationHistory.add(operation);
			}
		}
		
		return operationHistory;	
	}
}
