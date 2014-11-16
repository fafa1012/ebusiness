package ebusiness.myapp.PlacesPackage;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Fabian on 11.11.2014.
 */
@ParseClassName("Place")
public class HolderForPlace extends ParseObject{

    public HolderForPlace(){
        //default constructor is required
    };

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public String getDescription(){
        return  getString("description");
    }

    public void setDescription(String description){
        put("description", description);
    }

    public String getPlz(){
        return getString("plz");
    }

    public void setPlz(String plz){
        put("plz", plz);
    }

    public String getStreet(){
        return getString("street");
    }

    public void setStreet(String street){
        put("street", street);
    }
        
    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser user) {
        put("author", user);
    }

    public String getRating() {
        return getString("rating");
    }

    public void setRating(String rating) {
        put("rating", rating);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }


}
