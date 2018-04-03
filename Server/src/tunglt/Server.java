package tunglt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Le Thanh Tung
 */
public class Server implements Runnable {

    public ServerThread clients[] = new ServerThread[50];
    public ServerUI ui;
    public ServerSocket server;
    public Thread thread = null;
    public int clientCount = 0;
    
    public Server(ServerUI ui) {
        this.ui = ui;
        
        try{
            server = new ServerSocket(13000);
            thread = new Thread(this);
	    thread.start();
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(ui, "Khởi động Server thất bại.");
            System.exit(0);
	}
    }

    @Override
    public void run() {
        while (thread != null){  
            try{
	        addThread(server.accept());
	    }
	    catch(Exception ioe){ 
                JOptionPane.showMessageDialog(ui, "Chương trình đã dừng lại.");
                System.exit(0);
	    }
        }
    }
    
    private void addThread(Socket socket) {
        ui.taServer.append(ui.nowTime()+": "+socket+" đã kết nối.\n");
	if (clientCount < clients.length) {
	    clients[clientCount] = new ServerThread(this, socket, clientCount);
	    try {
	      	clients[clientCount].open();
	        clients[clientCount].start();
	        clientCount++;
	    }
	    catch(IOException ioe) {
	      	JOptionPane.showMessageDialog(ui, "Chương trình đã dừng lại.");
                System.exit(0);
	    } 
	}
        else {
            ui.taServer.append(ui.nowTime()+": "+socket+" bị ngắt kết nối (tối đa "+clients.length+" client truy cập).\n");
            //chưa báo hiệu cho client truy cập full
	}
    }
    
    public synchronized void remove(int pos){
        if (pos < clientCount-1){
            for (int i = pos+1; i < clientCount; i++){
                clients[i-1] = clients[i];
            }
        }
        clientCount--;
    }
}