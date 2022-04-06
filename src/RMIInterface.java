import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;

public interface RMIInterface extends Remote{
	public String sayHello() throws IOException;
	public String register(String user, String pass) throws IOException;
	public String login(String user, String pass) throws IOException, NotBoundException;
	public String addurl(String url) throws IOException;
	public String searchterm(String term, int logged, String user) throws IOException;
	public String searchistory(String user) throws IOException;
	public String giveadmin(String user) throws IOException;
	//public String notify(String user) throws IOException;
	public String urlfollow(String url) throws IOException;
}
