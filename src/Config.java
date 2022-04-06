import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    String name,host;
    int port;
    InputStream in = null;
    Properties properties = new Properties();
    Config(String file){
        
        try {
            in = new FileInputStream(file);
            properties.load(in);
            name = properties.getProperty("name");
            port = Integer.parseInt(properties.getProperty("port"));
            host = properties.getProperty("host");
        } catch(FileNotFoundException e) {
            System.out.println("\n FileNotFoundException: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("\n IOException: " + e.getMessage());
        }
        finally {
            if(in != null) {
                try {
                    in.close();
                } catch(IOException e) {
                    System.out.println("\nIOException: " + e.getMessage());
                }
            }
        }
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getName() {
        return name;
    }
}
