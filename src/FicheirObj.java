import java.io.*;

class FicheirObj {
    private ObjectInputStream iS;
    private ObjectOutputStream oS;


    public boolean openRead(String nameFile) {
        try{
            iS = new ObjectInputStream(new FileInputStream (nameFile));
            return true;
        }
        catch(IOException e){
            return false;
        }
    }

    public void openWrite(String nameFile) throws IOException {
        oS = new ObjectOutputStream(new FileOutputStream (nameFile));
    }


    public Object readObj() throws IOException, ClassNotFoundException {
        return iS.readObject();
    }


    public void writeObj(Object o) throws IOException {
        oS.writeObject(o);
    }


    public void closeRead() throws IOException {
        iS.close();
    }


    public void closeWrite() throws IOException {
        oS.close();
    }

}