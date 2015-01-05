package ebusiness.myapp.Rating;

import com.parse.ParseClassName;
import com.parse.ParseObject;
/**
 * Created by Fabian on 24.10.2014.
 */
@ParseClassName("Place")
public class Places extends ParseObject {

    public String getTitle(){
        return getString("Title");
    }

    public void setTitle(String title){
        put("Title",title);
    }

    public String getDescription(){
        return getString("Description");
    }

    public void setDescription(String description){
        put("Description",description);
    }

    @Override
    public String toString(){
        return getString("Title") + "\n" + getString("Description");
    }

}
