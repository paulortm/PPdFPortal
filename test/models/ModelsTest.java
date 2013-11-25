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
	public void createAndRetrieveAdministrator() {
		new Administrator("Bob", "secret", "999999999", "Rua 1").save();
		User bob = Administrator.find.where().eq("id", 1).findUnique();
		assertNotNull(bob);
		assertEquals("Bob", bob.name);
	}

	@Test
	public void createAndRetrieveStudent() {
		new Student("Bob", "secret", "999999999", "Rua 1", "Manuela",
				"111111111").save();
		User bob = Student.find.where().eq("id", 1).findUnique();
		assertNotNull(bob);
		assertEquals("Bob", bob.name);
		assertEquals("Manuela", ((Student)bob).guardianName);
	}

	@Test
	public void tryAuthenticateUser() {
		new Administrator("Bob", "secret", "999999999", "Rua 1").save();

		assertNotNull(User.authenticate(1, "secret"));
		assertNull(User.authenticate(1, "badpassword"));
		assertNull(User.authenticate(2, "secret"));
	}
}