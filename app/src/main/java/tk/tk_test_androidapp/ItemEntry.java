package tk.tk_test_androidapp;

/**
 * Created by P16099 on 2015-09-17.
 */
public class ItemEntry {
    private String name;
    private String phoneNo;
    private int photo;

    public ItemEntry(String _name, String _pn, int _photo){
        this.name= _name;
        this.phoneNo= _pn;
        this.photo= _photo;
    }

    public String getName(){
        return name;
    }

    public String getPhoneNo(){
        return phoneNo;
    }

    public int getPhotoId(){
        return photo;
    }
}
