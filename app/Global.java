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
			new Year("2013/2014", true).save();
			Year y = Year.find.byId("2013/2014");
			for(i = 1; i < 13 ; i++) {
				Volume v = Volume.find.byId(i);
				v.year = y;
				y.volumes.add(v);
				v.update();
				y.update();
			}
			new PPdFPortal(1, 1, 1, 2014, Year.find.byId("2013/2014")).save();
			new Administrator("adm0", "root", "rootroot", "0", "Rua root")
					.save();
		}
	}
}