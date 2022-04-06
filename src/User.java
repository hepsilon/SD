import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {


    protected String username;

    protected String password;
    protected ArrayList<String> notice;
    protected ArrayList<String> history;
    private Boolean admin;

    public User(){

    }

    public User(String username, String password, Boolean admin){
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.notice =new ArrayList<String>();
        this.history = new ArrayList<String>();

    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public ArrayList<String> getNotice(){
        return notice;
    }
    public ArrayList<String> getHistory(){
        return history;
    }
    public Boolean getAdmin(){
        return admin;
    }
    public void clearnotice(){
        notice.clear();
    }
    public void addNotice(String notice){
        this.notice.add(notice);
    }
    public void addHistory(String history){
        this.history.add(history);
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setAdmin(Boolean admin){
        this.admin=admin;
    }
}
