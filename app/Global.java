import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;

import java.util.*;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		if (PPdFPortal.find.byId(1) == null) {
			Integer i;
			
			for(i = 1; i < 13 ; i++) {
				new Volume(i, true).save();
			}
			new Year("2013/2014", Volume.find.all(), true).save();
			new PPdFPortal(1, 1, 1, 2014, Year.find.byId("2013/2014")).save();
			new Administrator("adm0", "root", "rootroot", "0", "Rua root")
					.save();
		}
	}
}