import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class MulticastServer extends Thread {
    private String MULTICAST_ADDRESS = "224.3.2.0";
    private int PORT = 5000;
    private long SLEEP_TIME = 5000;
    //private ArrayList<String> userlist = new ArrayList<>();
    public ArrayList<User> userlist = new ArrayList<User>();
    public HashMap<String,ArrayList<String>> termlist = new HashMap<String,ArrayList<String>>();
    public HashMap<String,ArrayList<String>> urllist = new HashMap<String,ArrayList<String>>();


    public static void main(String[] args) {
        MulticastServer server = new MulticastServer();
        server.start();
    }
    // web crawler recebe um link e procura links dentro deste recursivamente
    public void WebCrawler(String url,int i) throws IOException {
        //4. Check if you have already crawled the URLs
        ArrayList<String> array;
        i-=1;
        int termbool;
        String strlink = new String();
        if (!urllist.containsKey(url) && i>0) {
            try {
                termbool=addTerm(url);
                if(termbool== 1) {
                    //4. (i) If not add it to the index
                    array = new ArrayList<String>();
                    urllist.put(url, array);

                    //2. Fetch the HTML code
                    Document document = Jsoup.connect(url).get();
                    //3. Parse the HTML to extract links to other URLs
                    Elements links = document.select("a[href]");
                    System.out.println("lol");
                    //5. For each extracted
                    // URL... go back to Step 4.
                    for (Element page : links) {

                        WebCrawler(page.attr("abs:href"), i);
                        strlink = page.attr("abs:href");

                        if (i > 1) {
                            array = urllist.get(strlink);
                            int flag =0;
                            for(String aux :array){
                                if (aux.equals(url))
                                    flag = 1;
                            }
                            if (flag == 0) {
                                System.out.println("link:" + url);
                                array.add(url);
                                urllist.replace(strlink, array);
                            }
                        }
                    }
                }
            }
            catch (IOException | IllegalArgumentException e) {
                System.err.println("For '" + url + "': " + e.getMessage());
            }
        }
    }
    //adiciona termos a lista e url
    public int addTerm(String url) throws IOException {
        Document doc;
        try{
            doc = Jsoup.connect(url).get();
        } catch (IllegalArgumentException e) {
            System.out.println("invallid url");
            return 0;
        }
        StringTokenizer tokens = new StringTokenizer(doc.text());
        ArrayList<String> array ;
        String tok;
        while (tokens.hasMoreElements()) {
            array = new ArrayList<String>();
            tok = tokens.nextToken().toLowerCase();
            if (termlist.containsKey(tok)) {
                array = termlist.get(tok);
                if(!array.contains(url)){
                    array.add(url);
                }
                termlist.replace(tok, array);
            } else {
                array.add(url);
                termlist.put(tok, array);
            }

        }
        return 1;
    }
    //codigo para inicializar os ficheiros de objetos
    public void Init() throws IOException, ClassNotFoundException {

        try {
            abreFicheiroUser();
        }
        catch(Exception e){
            escreveFicheiroUser();
        }
        try {
            abreFicheiroTermlist();
        }
        catch(Exception e){
            escreveFicheiroTermlist();
        }
        try {
            abreFicheiroUrl();
        }
        catch(Exception e){
            escreveFicheiroUrl();
        }
    }
    public  void abreFicheiroUser() throws IOException, ClassNotFoundException {
        FicheirObj obj =new FicheirObj();
        obj.openRead("ficheiroUserObj.obj");
        userlist = (ArrayList<User>)obj.readObj();
        obj.closeRead();
        System.out.println("try");
    }
    public  void abreFicheiroTermlist() throws IOException, ClassNotFoundException {
        FicheirObj obj =new FicheirObj();
        obj.openRead("ficheiroTermlistObj.obj");
        termlist= (HashMap<String,ArrayList<String>>)obj.readObj();
        obj.closeRead();
    }
    public void abreFicheiroUrl() throws IOException, ClassNotFoundException {
        FicheirObj obj =new FicheirObj();
        obj.openRead("ficheiroUrlObj.obj");
        urllist= (HashMap<String,ArrayList<String>>)obj.readObj();
        obj.closeRead();
    }
    public void escreveFicheiroUser() throws IOException, ClassNotFoundException {
        FicheirObj obj=new FicheirObj();
        obj.openWrite("ficheiroUserObj.obj");
        obj.writeObj(userlist);
        obj.closeWrite();
    }
    public void escreveFicheiroTermlist() throws IOException, ClassNotFoundException {
        FicheirObj obj=new FicheirObj();
        obj.openWrite("ficheiroTermlistObj.obj");
        obj.writeObj(termlist);
        obj.closeWrite();
    }
    public void escreveFicheiroUrl() throws IOException, ClassNotFoundException {
        FicheirObj obj=new FicheirObj();
        obj.openWrite("ficheiroUrlObj.obj");
        obj.writeObj(urllist);
        obj.closeWrite();
    }

    public MulticastServer() {
        super("Server " + (long) (Math.random() * 1000));
    }
    //envia o protocolo String type para o servidor rmi
    public void send(String type,MulticastSocket sndsock,InetAddress group) throws IOException {
        byte[] string_to_bytes = type.getBytes();
        DatagramPacket send_to_mc = new DatagramPacket(string_to_bytes, string_to_bytes.length, group, 7000);
        sndsock.send(send_to_mc);
    }
    //Recebe os protocolos do rmi e trata a informação
    public void run() {
        MulticastSocket sndsock = null;
        MulticastSocket rcvsock = null;
        long counter = 0;
        System.out.println(this.getName() + " running...");
        try {
            Init();
            sndsock = new MulticastSocket();  // create socket without binding it (only for sending)
            rcvsock = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            rcvsock.joinGroup(group);
            String useraccept;
             while (true) {

                escreveFicheiroTermlist();
                escreveFicheiroUrl();
                escreveFicheiroUser();
                byte[] msg_rcv = new byte[1024];
                DatagramPacket packet = new DatagramPacket(msg_rcv, msg_rcv.length);
                rcvsock.receive(packet);
                String receiveString = new String(packet.getData(), 0, packet.getLength());

                if(receiveString.contains("type|register")){
                    String [] splitString = receiveString.split(";");
                    String [] getUserString = splitString[1].split("\\|");
                    String [] getPassString = splitString[2].split("\\|");
                    String getUser = getUserString[1];
                    String getPass = getPassString[1];
                    System.out.println("Trying to register user with Username:"+getUser+" Password:"+getPass);
                    int check = 0;
                    for (User i :userlist){
                        if(i.getUsername().equals(getUser)){
                            check = 1;
                            break;
                        }
                    }
                    if(check == 1){
                        useraccept = "type|register;message|failed";
                    }
                    else {
                        User user;
                        if(userlist.isEmpty()){
                            user = new User(getUser,getPass,true);
                        }
                        else{
                            user = new User(getUser,getPass,false);
                        }
                        userlist.add(user);
                        useraccept = "type|register;message|success";
                    }
                    send(useraccept,sndsock,group);
                }

                else if(receiveString.contains("type|login")){
                    String [] splitString = receiveString.split(";");
                    String [] getUserString = splitString[1].split("\\|");
                    String [] getPassString = splitString[2].split("\\|");
                    String getUser = getUserString[1];
                    String getPass = getPassString[1];
                    int check = 0,admin=0;
                    for (User i :userlist){
                        if(i.getUsername().equals(getUser)){
                            if(i.getPassword().equals(getPass)) {
                                check = 1;
                                if (i.getAdmin())
                                    admin =1;
                                break;
                            }
                        }
                    }
                    if(check == 1){
                        useraccept = "type|login;message|success";
                        if(admin == 1){
                            useraccept += ";admin|yes";
                        }

                    }
                    else {
                        useraccept = "type|login;message|failed";
                    }
                    send(useraccept,sndsock,group);
                }
                else if(receiveString.contains("type|notify")){
                     String [] splitString = receiveString.split(";");
                     String [] getUserString = splitString[1].split("\\|");
                     String getUser = getUserString[1];
                     ArrayList<String> aux;
                     String type = "type|notify;empty|";
                     for (User i:userlist){
                         if(i.getUsername().equals(getUser)){
                            aux= i.getNotice();
                            if(!aux.isEmpty()){
                                type+="no" ;
                                for(String j :aux) {
                                    type+= ";notice|"+j;
                                }
                            }
                            else{
                                type+="yes";
                            }
                            i.clearnotice();
                            break;
                         }
                     }
                    send(type,sndsock,group);

                 }
                else if(receiveString.contains("type|urlfollow")){
                    String [] splitString = receiveString.split(";");
                    String [] getUrlString = splitString[1].split("\\|");
                    String getUrl = getUrlString[1];
                    String type = "type|urlfollow";
                    if(urllist.containsKey(getUrl)) {
                        ArrayList<String> straux = urllist.get(getUrl);
                        if (!straux.isEmpty())
                            for (String i : straux) {
                                type += ";url|" + i;
                            }
                    }
                    send(type,sndsock,group);
                }
                else if(receiveString.contains("type|addurl")){
                    String [] splitString = receiveString.split(";");
                    String [] getUrlString = splitString[1].split("\\|");
                    String getUrl = getUrlString[1];
                    WebCrawler(getUrl,10);
                    useraccept = "url added";
                    send(useraccept,sndsock,group);


                }
                else if(receiveString.contains("type|search")){
                    for (Map.Entry mapElement : urllist.entrySet()) {
                        String key = (String)mapElement.getKey();
                        ArrayList<String> url = (ArrayList<String>)mapElement.getValue();
                        System.out.println("key:"+key);
                        for(String str : url){
                            System.out.println("chave:"+str);
                        }
                    }
                    String [] splitString = receiveString.split(";");
                    String [] getTermString;
                    String getTerm;
                    String history = "";
                    String user = "";
                    ArrayList<String> arrayurl = new ArrayList<String>();
                    ArrayList<String> auxarray;
                    int i = 0;
                    int logged = 0;
                    for(String str:splitString){
                        if(i==1){
                            getTermString = str.split("\\|");
                            getTerm = getTermString[1];
                            logged = Integer.parseInt(getTerm);
                        }
                        else if(i==2){
                            if(logged == 1){
                                getTermString = str.split("\\|");
                                getTerm = getTermString[1];
                                for(User j:userlist){
                                    if(getTerm.equals(j.getUsername())){
                                        user = getTerm;
                                    }
                                }
                            }
                        }
                        else if(i > 2){
                            getTermString = str.split("\\|");
                            getTerm = getTermString[1];
                            history+=" "+getTerm;
                            if (termlist.containsKey(getTerm)){
                                if(i ==3){
                                    arrayurl =  termlist.get(getTerm);

                                }
                                else{
                                    auxarray = termlist.get(getTerm);
                                    arrayurl.retainAll(auxarray);

                                }
                            }
                        }
                        i++;
                    }
                    if(logged == 1) {
                        for (User j : userlist) {
                            if (j.getUsername().equals(user)) {
                                j.addHistory(history);
                            }
                        }
                    }
                    int n = arrayurl.size();
                    for (int j = 0; j < n-1; j++){
                        for (int k = 0; k < n-j-1; k++){
                            if (urllist.get(arrayurl.get(j)).size() > urllist.get(arrayurl.get(j+1)).size())
                            {
                                String temp = arrayurl.get(j);
                                arrayurl.set(j,arrayurl.get(j+1));
                                arrayurl.set(j+1,temp);
                            }
                        }

                    }
                    String type = "type|search";
                    for(String j : arrayurl){
                        type = type + ";url|"+j;

                    }
                    send(type,sndsock,group);
                }
                else if(receiveString.contains("type|history")){
                    String [] splitString = receiveString.split(";");
                    String [] getUserString = splitString[1].split("\\|");
                    String getUser = getUserString[1];
                    ArrayList<String> history = new ArrayList<String>();
                    for(User i:userlist){
                        if (getUser.equals(i.getUsername())){
                            history = i.getHistory();
                            break;
                        }
                    }
                    String type = "type|history";
                    for(String i:history){
                        type+= ";search|"+i;
                    }
                    send(type,sndsock,group);
                }
                else if(receiveString.contains("type|giveadmin")){
                    String [] splitString = receiveString.split(";");
                    String [] getTermString = splitString[1].split("\\|");
                    String getTerm = getTermString[1];
                    int accepted = 0;
                    for(User user: userlist){
                        if(user.getUsername().equals(getTerm)){
                            user.setAdmin(true);
                            user.addNotice("You are now an admin");
                            accepted =1;
                            break;
                        }
                    }
                    if(accepted == 1){
                        useraccept = "type|giveadmin;user|yes";

                    }
                    else{
                        useraccept = "type|giveadmin;user|no";

                    }
                    send(useraccept,sndsock,group);
                }

                try { sleep((long) (Math.random() * SLEEP_TIME)); } catch (InterruptedException e) { }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            sndsock.close();
            rcvsock.close();
        }
    }
}

