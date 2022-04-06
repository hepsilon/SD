import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer extends UnicastRemoteObject implements RMIInterface {
	/**
	 * 
	 */
	private String MULTICAST_ADDRESS = "224.3.2.0";
	private int MULTPORT = 5000;
	private int RMIPORT= 7000;
    private static ArrayList<String> clientStringlist;
	private static final long serialVersionUID = 1L;

	public RMIServer() throws RemoteException {
		super();
	}
	//envia protocolo para saber os urls que apontam para String url
	public String urlfollow(String url) throws IOException {
		String type = "type|urlfollow;url|"+url;
		MulticastSocket rcvsock = new MulticastSocket(RMIPORT);
		MulticastSocket sndsock = new MulticastSocket();
		System.out.println("print do lado do servidor...!.");
		InetAddress multigroup = InetAddress.getByName(MULTICAST_ADDRESS);
		rcvsock.joinGroup(multigroup);
		byte[] string_to_bytes = type.getBytes();
		DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, multigroup, MULTPORT);
		sndsock.send(send_to_mc);

		byte[] msg_rcv = new byte[1024];
		DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
		rcvsock.receive(packet);
		String received = new String(packet.getData(), 0, packet.getLength());

		return received;

	}
	//envia pedido para receber notificações
	public String notify(String user) throws IOException {
		String type = "type|notify;user|"+user;
		MulticastSocket rcvsock = new MulticastSocket(RMIPORT);
		MulticastSocket sndsock = new MulticastSocket();
		System.out.println("print do lado do servidor...!.");
		InetAddress multigroup = InetAddress.getByName(MULTICAST_ADDRESS);
		rcvsock.joinGroup(multigroup);
		byte[] string_to_bytes = type.getBytes();
		DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, multigroup, MULTPORT);
		sndsock.send(send_to_mc);

		byte[] msg_rcv = new byte[1024];
		DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
		rcvsock.receive(packet);
		String received = new String(packet.getData(), 0, packet.getLength());

		return received;
	}
	//envia e recebe protocolo para registar um utilizador
	public String register(String user,String pass) throws IOException {
		String type = "type|register;username|"+user+";password|"+pass;
		MulticastSocket rcvsock = new MulticastSocket(RMIPORT);
		MulticastSocket sndsock = new MulticastSocket();
		System.out.println("print do lado do servidor...!.");
		InetAddress multigroup = InetAddress.getByName(MULTICAST_ADDRESS);
		rcvsock.joinGroup(multigroup);
		byte[] string_to_bytes = type.getBytes();
		DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, multigroup, MULTPORT);
		sndsock.send(send_to_mc);

		byte[] msg_rcv = new byte[1024];
		DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
		rcvsock.receive(packet);
		String received = new String(packet.getData(), 0, packet.getLength());

		return received;
	}
	//envia protocolo para fazer login de um utilizador
	public String login(String user,String pass) throws IOException, NotBoundException {
		String type = "type|login;username|"+user+";password|"+pass;
		MulticastSocket rcvsock = new MulticastSocket(RMIPORT);
		MulticastSocket sndsock = new MulticastSocket();
		System.out.println("print do lado do servidor...!.");
		InetAddress multigroup = InetAddress.getByName(MULTICAST_ADDRESS);
		rcvsock.joinGroup(multigroup);
		byte[] string_to_bytes = type.getBytes();
		DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, multigroup, MULTPORT);
		sndsock.send(send_to_mc);

		byte[] msg_rcv = new byte[1024];
		DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
		rcvsock.receive(packet);
		String received = new String(packet.getData(), 0, packet.getLength());


		return received;
	}
	//envia protocolo para pesquisar termos
	public String searchterm(String term,int logged,String user) throws IOException {
		String array[] = term.split(" ");
        String type = "type|search;logged|";
        if(logged==1){
            type+="1;user|"+user;
        }
        else{
            type+="0;user|null";
        }
		for(String i:array){
		    type+= ";term|"+i;
        }
		MulticastSocket rcvsock = new MulticastSocket(RMIPORT);
		MulticastSocket sndsock = new MulticastSocket();
		System.out.println("print do lado do servidor...!.");
		InetAddress multigroup = InetAddress.getByName(MULTICAST_ADDRESS);
		rcvsock.joinGroup(multigroup);
		byte[] string_to_bytes = type.getBytes();
		DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, multigroup, MULTPORT);
		sndsock.send(send_to_mc);

		byte[] msg_rcv = new byte[1024];
		DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
		rcvsock.receive(packet);
		String received = new String(packet.getData(), 0, packet.getLength());

		return received;
	}
	//envia protocolo para devolver o historico do utilizador
	public String searchistory(String user) throws IOException{
	    String type = "type|history;user|"+user;
        MulticastSocket rcvsock = new MulticastSocket(RMIPORT);
        MulticastSocket sndsock = new MulticastSocket();
        System.out.println("print do lado do servidor...!.");
        InetAddress multigroup = InetAddress.getByName(MULTICAST_ADDRESS);
        rcvsock.joinGroup(multigroup);
        byte[] string_to_bytes = type.getBytes();
        DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, multigroup, MULTPORT);
        sndsock.send(send_to_mc);
        byte[] msg_rcv = new byte[1024];
        DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
        rcvsock.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());

        return received;
    }
    //envia protocolo para dar admin a um user
	public String giveadmin(String user) throws IOException {
		String type = "type|giveadmin;user|"+user;
		MulticastSocket rcvsock = new MulticastSocket(RMIPORT);
		MulticastSocket sndsock = new MulticastSocket();
		System.out.println("print do lado do servidor...!.");
		InetAddress multigroup = InetAddress.getByName(MULTICAST_ADDRESS);
		rcvsock.joinGroup(multigroup);
		byte[] string_to_bytes = type.getBytes();
		DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, multigroup, MULTPORT);
		sndsock.send(send_to_mc);

		byte[] msg_rcv = new byte[1024];
		DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
		rcvsock.receive(packet);
		String received = new String(packet.getData(), 0, packet.getLength());

		return received;
	}
	//envia protocolo para adicionar um url novo ao servidor
	public String addurl(String url) throws IOException {
		String type = "type|addurl;url|"+url;
		MulticastSocket rcvsock = new MulticastSocket(RMIPORT);
		MulticastSocket sndsock = new MulticastSocket();

		InetAddress multigroup = InetAddress.getByName(MULTICAST_ADDRESS);
		rcvsock.joinGroup(multigroup);
		byte[] string_to_bytes = type.getBytes();
		DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, multigroup, MULTPORT);
		sndsock.send(send_to_mc);

		byte[] msg_rcv = new byte[1024];
		DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
		rcvsock.receive(packet);
		String received = new String(packet.getData(), 0, packet.getLength());

		return received;
	}
	//envia um pedido hello para testar o servidor
	public String sayHello() throws IOException {
		MulticastSocket rcvsock = new MulticastSocket(RMIPORT);
		MulticastSocket sndsock = new MulticastSocket();
		System.out.println("print do lado do servidor...!.");
		InetAddress multigroup = InetAddress.getByName(MULTICAST_ADDRESS);
		rcvsock.joinGroup(multigroup);
		String oi = "vou mandar isto para o multicast";
		byte[] string_to_bytes = oi.getBytes();
		DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, multigroup, MULTPORT);
		sndsock.send(send_to_mc);

		byte[] msg_rcv = new byte[1024];
		DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
		rcvsock.receive(packet);

		String received = new String(packet.getData(), 0, packet.getLength());

		System.out.println("Recebi do Multicast: " + received);





		return received;
	}
	//verifica se existe um servidor se nao existir assume papel de rmi primario
	public static void RMIcheck(){
        Config config = new Config("config.cfg");
        System.out.println("NomeRMI: " + config.getName()+"\nPortoRMI: " + config.getPort()+"\nHostRMI: " + config.getHost());
        int tries = 5;
        while(tries>0) {
            try {
                RMIInterface server = (RMIInterface) Naming.lookup ("rmi://" + config.getHost() + ":" + config.getPort() + "/" + config.getName());
                server.sayHello();
                tries = 5;
            } catch (RemoteException e) {
                //e.printStackTrace();
                System.out.println("Failed");
                tries -= 1;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                //e.printStackTrace();
                System.out.println("Failed");
                tries -= 1;
            }
        }
        try{
            RMIServer h = new RMIServer();
            Naming.rebind("rmi://localhost:7000/ucBusca", h);
            System.out.println("Hello Server ready.");
        }catch(RemoteException|MalformedURLException e){
            e.printStackTrace();
        }
    }
	

	// =========================================================
	public static void main(String args[]) throws AlreadyBoundException, NotBoundException {
	    RMIcheck();
	}

}