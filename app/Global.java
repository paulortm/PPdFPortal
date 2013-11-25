import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;

import java.util.*;

public class Global extends GlobalSettings {
	
    @Override
    public void onStart(Application app) {
    	new PPdFPortal(1, 1, 1).save();
    }
    
}