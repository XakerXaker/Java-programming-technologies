package firstLabTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bankService.BankService;
import dto.User;
import repositories.AccountRepository;
import repositories.OperationHistoryRepository;

/**
 * Unit test for simple firstLabTest.
 */
public class FirstLabTest 
{

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldThrowExceptionWhenUserWithdrawedInvalidAmount() 
    {
    	AccountRepository repo = new AccountRepository();
    	User user1 = new User("alala", "lalal");
    	repo.CreateAccount(user1);
    	
        assertThrows(UnsupportedOperationException.class, () -> {
            repo.WithdrawMoney(user1, 1);
        });
    }
    
    @Test
    public void shouldThrowExceptionWhenUserAlreadyExist() 
    {
    	AccountRepository repo = new AccountRepository();
    	User user = new User("lala", "lala");
    	repo.CreateAccount(user);
    	
    	
        assertThrows(IllegalStateException.class, () -> {
        	repo.CreateAccount(user);
        });
    }
    
    @Test
    public void shouldThrowExceptionWhenUserNotAuthorizated() 
    {
    	BankService service = new BankService(new AccountRepository(), new OperationHistoryRepository());
    	service.TryToCreateAccount("alla", "lalal");
    	
    	
    	assertThrows(Exception.class, () -> {
            service.CheckBalance();
        });
    }
    
}
