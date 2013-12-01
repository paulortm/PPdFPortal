import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;

import java.util.*;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		if (PPdFPortal.find.byId(1) == null) {
			new PPdFPortal(1, 1, 1).save();
			new Administrator("adm0", "root", "rootroot", "0", "Rua root")
					.save();
		}
	}
}