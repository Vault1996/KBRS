package test.by.epam.cinemarating;

import by.epam.cinemarating.database.ConnectionPool;
import org.junit.Assert;
import org.junit.Test;

public class SingletonTest {
	@Test(timeout = 100)
	public void singletonTest() {
		ConnectionPool firstInstance = ConnectionPool.getInstance();
		ConnectionPool secondInstance = ConnectionPool.getInstance();
		Assert.assertSame(firstInstance, secondInstance);
		ConnectionPool.getInstance().closePool();
	}
}
