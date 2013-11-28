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
	public void testPPdFPortal() {
		PPdFPortal portal = PPdFPortal.find.byId(1);
		assertNotNull(portal);
	}

	@Test
	public void createAndRetrieveAdministrator() {
		PPdFPortal portal = PPdFPortal.find.byId(1);

		portal.createAdministrator("Bob", "secret", "999999999", "Rua 1");
		User bob = portal.getUser("adm1");
		assertNotNull(bob);
		assertEquals("Bob", bob.name);
	}

	@Test
	public void createAndRetrieveStudent() {
		PPdFPortal portal = PPdFPortal.find.byId(1);

		portal.createStudent("Bob", "secret", "999999999", "Rua 1", "Manuela",
				"111111111");
		User bob = portal.getUser("cat1");
		assertNotNull(bob);
		assertEquals("Bob", bob.name);
		assertEquals("Manuela", ((Student) bob).guardianName);
	}

	@Test
	public void tryAuthenticateUser() {
		PPdFPortal portal = PPdFPortal.find.byId(1);

		portal.createAdministrator("Bob", "secret", "999999999", "Rua 1");

		assertNotNull(portal.authenticate("adm1", "secret"));
		assertNull(portal.authenticate("adm1", "badpassword"));
		assertNull(portal.authenticate("adm2", "secret"));
	}
}