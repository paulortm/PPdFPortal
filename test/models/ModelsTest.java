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

		portal.createAdministrator("Bob", "999999999", "q", "Rua 1");
		User bob = portal.getUser("adm1");
		assertNotNull(bob);
		assertEquals("Bob", bob.name);
	}

	@Test
	public void createAndRetrieveStudent() {
		PPdFPortal portal = PPdFPortal.find.byId(1);

		portal.createStudent("José Esteves", "212121212", "q",
				"Rua Coiso nº1", "3/11/2007", null, null, null, null, 0,
				"Maria Esteves", "121212121");
		User bob = portal.getUser("cat1");
		assertNotNull(bob);
		assertEquals("José Esteves", bob.name);
		assertEquals("Maria Esteves", ((Student) bob).guardianName);
	}

	@Test
	public void tryAuthenticateUser() {
		PPdFPortal portal = PPdFPortal.find.byId(1);

		portal.createAdministrator("Bob", "999999999", "q", "Rua 1");

		assertNotNull(portal.authenticate("adm1", "secret"));
		assertNull(portal.authenticate("adm1", "badpassword"));
		assertNull(portal.authenticate("adm2", "secret"));
	}
}