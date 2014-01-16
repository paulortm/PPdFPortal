import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import util.Constants;

import java.util.*;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		if (PPdFPortal.find.byId(1) == null) {
			Integer i;

			for (i = 1; i <= Constants.MAX_VOLUMES; i++) {
				new Volume(i).save();
			}
			new Year("2012/2013", true).save();
			Year y = Year.find.byId("2012/2013");
			for (i = 1; i <= Constants.MAX_VOLUMES; i++) {
				Volume v = Volume.find.byId(i);
				v.year = y;
				y.volumes.add(v);
				v.update();
				y.update();
			}
			new PPdFPortal(1, 1, 1, 2013, Year.find.byId("2012/2013")).save();
			new Administrator("adm0", "root", "rootroot", "0", "root@root.com",
					"Rua root").save();

			PPdFPortal portal = PPdFPortal.find.byId(1);
			portal.createStudent("José Maria Pereira Esteves", "214587123",
					"jmesteves@gmail.com", "Rua Coiso nº1", "2007-11-03", null,
					null, null, null, 0, "Maria Esteves", "121212121");
			portal.createStudent("Claudia Teixeira Costa", "214587123",
					"cmcosta@gmail.com", "Rua Coiso nº2", "2004-01-03", null,
					null, null, null, 4, "Rodrigo Costa", "121212121");
			portal.createStudent("João Francisco Sénica Pereira", "214587123",
					"jsenica@gmail.com", "Rua Coiso nº3", "2005-11-20", null,
					null, null, null, 3, "Maria Esteves", "121212121");
			portal.createStudent("Rodrigo Tavares Fernandes", "214587123",
					"rtfernandes@gmail.com", "Rua Coiso nº4", "2000-05-12", null,
					null, null, null, 7, "Maria Fernandes", "121212121");
			portal.createStudent("Joana Filipa Saldanha Gasalho", "214587123",
					"jfilipagasalho@gmail.com", "Rua Coiso nº5", "1999-08-04", null,
					null, null, null, 9, "Maria Esteves", "121212121");
			portal.createStudent("João Manuel Dias Maurício Ferreira Mendes", "964331828",
					"jhonmendes@gmail.com", "Rua Central nº10", "1997-06-19", null,
					null, null, null, 9, "Ana Maria Dias Ferreira Mendes", "962541379");
		}
	}
}