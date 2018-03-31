package tunglt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Le Thanh Tung
 */
public class ServerThread extends Thread {
    public Server server;
    public Socket socket;
    public int pos;
    public ObjectInputStream In;
    public ObjectOutputStream Out;
    public ServerUI ui;

    public ServerThread(Server server, Socket socket, int pos) {
    	super();
        this.server = server;
        this.socket = socket;
        ui = server.ui;
        this.pos = pos;
    }
    
    @SuppressWarnings("deprecation")
    public void run(){
        while (true){
    	    try{  
                Data msg = (Data) In.readObject();
    	    	XuLyDuLieu(msg);
            }
            catch(Exception ioe){}
        }
    }
    
    public void XuLyDuLieu(Data msg){
        if(msg.type.equals("exit")){
            ui.taServer.append(ui.nowTime()+": "+socket+" đã thoát.\n");
            server.remove(pos);
            try{
                socket.close();
                In.close();
                Out.close();
            }catch(Exception ioe){}
            stop();
            
        }
        else{
            float[][] result = new float[msg.m1][msg.n2];
            for (int i=0; i<msg.m1; i++) {
                for (int j=0; j<msg.n2; j++) {
                    result[i][j] = 0;
                    for (int k=0; k<msg.n1; k++) {
                        result[i][j] = result[i][j] + msg.maTran1[i][k] * msg.maTran2[k][j];
                    }
                }
            }
            
            send(new Data("", result, msg.m1, msg.n2, null, 0, 0));
            
            ui.taServer.append(ui.nowTime()+": "+socket+".\n");
            
            for (int i=0; i<msg.m1; i++) {
                for (int j=0; j<msg.n1; j++) {
                    ui.taServer.append(msg.maTran1[i][j]+"\t");
                }
                ui.taServer.append("\n");
            }
            ui.taServer.append("\n\t*\n\n");
            for (int i=0; i<msg.m2; i++) {
                for (int j=0; j<msg.n2; j++) {
                    ui.taServer.append(msg.maTran2[i][j]+"\t");
                }
                ui.taServer.append("\n");
            }
            ui.taServer.append("\n\t=\n\n");
            for (int i=0; i<msg.m1; i++) {
                for (int j=0; j<msg.n2; j++) {
                    ui.taServer.append(result[i][j]+"\t");
                }
                ui.taServer.append("\n");
            }
        }
    }
    
    public void open() throws IOException {  
        Out = new ObjectOutputStream(socket.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(socket.getInputStream());
    }
    
    public void send(Data msg){
        try {
            Out.writeObject(msg);
            Out.flush();
        } 
        catch (IOException ex) {
            System.out.println("Lỗi gửi socket máy khách");
        }
    }
}
