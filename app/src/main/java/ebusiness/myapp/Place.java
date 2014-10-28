package ebusiness.myapp;


/**
 * Created by Fabian on 24.10.2014.
 */

public class Place {
    private String title;
    private String description;

    public Place(String title) {
        this.title = title;
        //this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return title;
    }
}
