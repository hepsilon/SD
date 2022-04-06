import java.io.IOException;
import java.rmi.NotBoundException;

public class ActBean extends Bean {


    public ActBean(){
        super();
    }

    public String validateLogin(String username,String password){
        String text ="";
        try {
            text = server.login(username,password);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        if(text.contains("success")){
            if(text.contains("admin|yes"))
               return("admin");
            return("success");
        }
        else{
            return("failed");
        }
    }
    public String register(String username,String password){


        String text = null;
        try {
            text = server.register(username,password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(text.contains("success")){
            return "success";
        }
        else{
            return "failed";
        }
        //System.out.println(text);
    }

    //pedido para adicionar um url
    public String addurl(String url) throws IOException {
        String text = server.addurl(url);
        return "success";
    }
    //pedido para pesquisa por termos
    public String searchterm(String term,String username) throws IOException {
        String text = server.searchterm(term,1,username);
        return text;
    }
    public String searchterm2(String term) throws IOException {
        String text = server.searchterm(term,0,"");
        return text;
    }
    //pedido para ver search history
    public String searchistory(String username) throws IOException {
        String text = server.searchistory(username);
        return text;
    }
    //pedido para dar admin a um utilizador
    public String giveaddmin(String username) throws IOException{
        String text = server.giveadmin(username);
        if(text.contains("success"))
            return "successs";
        return "failed";
    }

}
