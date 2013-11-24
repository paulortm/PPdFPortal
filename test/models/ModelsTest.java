package models;

import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;

public class ModelsTest extends WithApplication {
	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase()));
	}

	@Test
	public void createAndRetrieveUser() {
		new User("Bob", "secret").save();
		User bob = User.find.where().eq("id", 1).findUnique();
		assertNotNull(bob);
		assertEquals("Bob", bob.name);
	}
	
	@Test
    public void tryAuthenticateUser() {
        new User("Bob", "secret").save();
        
        assertNotNull(User.authenticate(1, "secret"));
        assertNull(User.authenticate(1, "badpassword"));
        assertNull(User.authenticate(2, "secret"));
    }
}