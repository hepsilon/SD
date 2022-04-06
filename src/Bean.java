import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Bean implements Serializable {
    RMIInterface server = null;
    public Bean() {
        try {
            this.server=(RMIInterface) Naming.lookup("rmi://localhost:7000/ucBusca");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
