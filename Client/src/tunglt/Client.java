package tunglt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Le Thanh Tung
 */
public class Client implements Runnable {

    public Socket server;
    public ClientUI ui;
    public ObjectInputStream In;
    public ObjectOutputStream Out;
    
    public Client(ClientUI ui) throws IOException{
        this.ui = ui;
        server = new Socket("192.168.1.3", 9999);
        Out = new ObjectOutputStream(server.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(server.getInputStream());
    }
    
    @Override
    public void run() {
        while(true){
            try{
                Data msg = (Data) In.readObject();
                XuLyDuLieu(msg);
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(ui, "Mất kết nối với máy chủ.");
                System.exit(0);
            }
        }
    }
    
    public void XuLyDuLieu(Data msg){
        ui.taResult.setText("");
        
        for (int i=0; i<msg.m1; i++) {
            for (int j=0; j<msg.n1; j++) {
                ui.taResult.append(msg.maTran1[i][j]+"\t");
            }
            ui.taResult.append("\n\n");
        }
    }
    
    public void send(Data msg){
        try {
            Out.writeObject(msg);
            Out.flush();
        } 
        catch (IOException ex) {
            JOptionPane.showMessageDialog(ui, "Mất kết nối với máy chủ.");
            System.exit(0);
        }
    }
}
