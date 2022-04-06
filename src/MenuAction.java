import org.apache.struts2.interceptor.SessionAware;

import java.io.IOException;

public class MenuAction extends MainAction implements SessionAware {
    private String username;
    private String password;
    private String search;
    private String searchTerm;
    private String url;
   // private ActBean beanie= new ActBean();
    //private ArrayList<String> search;
    private int opt;
    private int opt2;

    public String login(){
        if(this.username.equals("a")&& this.password.equals("a")){
            return "success";
        }
        System.out.println(this.username);
        ActBean beanie = getActBean();
        if(beanie.validateLogin(this.username,this.password).contains("success")){
            session.put("username",this.username);

            return "success";
        }
        else if(beanie.validateLogin(this.username,this.password).contains("admin")){
            session.put("username",this.username);
            session.put("admin","admin");
            return "admin";
        }
        return "failed";
    }
    public String register(){
        if(this.username == null){
            return "error";
        }
        ActBean beanie = getActBean();
        if(beanie.register(this.username,this.password).contains("success")){
            return "success";
        }
        return "failed";
    }
    public String selectOpt(){
        if(this.opt == 1){
            return "1";
        }
        else if(this.opt == 2){
            return "2";
        }
        else if(this.opt == 3){
            return "3";
        }
        else if(this.opt == 4) {
            return "4";
        }
        else if(this.opt ==5){
            return"5";
        }
        return "error";
    }
    public String doSearchlogg(){
        ActBean beanie = getActBean();
        try {
            search = beanie.searchterm(this.searchTerm,(String)session.get("username"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(this.searchTerm);
        return "success";
    }
    public String doSearch(){
        ActBean beanie = getActBean();
        try {
            search = beanie.searchterm2(this.searchTerm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
    public String giveAdmin(){
        ActBean beanie = getActBean();
        try {
            if(beanie.giveaddmin(this.username).contains("success")){
                return"success";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failed";
    }
    public String searchHistory(){
        ActBean beanie = getActBean();
        try {
            //System.out.println("hahah"+(String)session.get("username"));
            search = beanie.searchistory((String)session.get("username"));
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failed";
    }
    public String addUrl() throws IOException {
        ActBean beanie =getActBean();
        if(beanie.addurl(this.url).contains("success"))
            return "success";
        return "failure";
    }
    public String getMenu(){
        if(session.containsKey("admin")){
            return "3";
        }
        else if(session.containsKey("username")){
            return "2";
        }
        return "1";
    }

    private ActBean getActBean () {
        if (!session.containsKey("ActBean")) {
            this.setActBean(new ActBean());
        }
        return (ActBean) session.get("ActBean");
    }

    private void setActBean(ActBean actBean) {
        session.put("ActBean", actBean);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public void setOpt2(int opt2) {
        this.opt2 = opt2;
    }

    public int getOpt2() {
        return opt2;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
