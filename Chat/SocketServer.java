import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;



public class SocketServer extends JFrame{

        // Elementos Gráficos
	JTextField tcomando2;
	JButton benviar2;
	JTextArea tresultado2;
	JScrollPane spane2;

         // Elementos de Hilos
        
        ServerSocket Server = null;
        Socket Sock = null;
        BufferedReader Clien = null;
        PrintWriter Servi = null;
        
	// Oyente de clic de botón Aportes
	ActionListener alenviar2;
        
        public SocketServer (){
        
        setSize ( 700 , 600 );
        setTitle (" Servidor ");
	setDefaultCloseOperation ( JFrame . EXIT_ON_CLOSE );
        graficos2();
}
    
     private void acciones(){
			alenviar2 = new ActionListener(){
			public void actionPerformed(ActionEvent e){
                            String envia = tcomando2.getText();
                            Servi.println(envia);
                            tresultado2.append("Cliente: " + envia + "\n");
                            tcomando2.setText(null);
			}
		};
        }
     
        private void graficos2(){
		getContentPane().setLayout(null);
		//cuadro de texto
		tcomando2 = new JTextField();
		tcomando2.setBounds(50,50,250,30);
		add(tcomando2);
		//botón para ejecutar comando
		benviar2 = new JButton("Enviar");
		benviar2.setBounds(350,50,150,30);
		add(benviar2);
		benviar2.addActionListener(alenviar2);
                
		//área de texto
		tresultado2 = new JTextArea();
		tresultado2.setBounds(50,130,600,370);
		tresultado2.setBackground(Color.BLACK);
		tresultado2.setForeground(Color.WHITE);
		//scroll pane
		spane2 = new JScrollPane(tresultado2);
		spane2.setBounds(50,120,500,400);
		add(spane2);
                this.getContentPane().setBackground(Color.WHITE);
		//
		setVisible(true);
                
          Thread principal = new Thread(new Runnable() {
               
                public void run() {
                try {
                    Server= new ServerSocket(8080);
                    while (true) {
                        Sock = Server.accept();
                        leer();
                        escribir();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        principal.start();
	}
     
            public void leer() {
        Thread hleer = new Thread(new Runnable() {
            public void run() {
                try {
                    Clien = new BufferedReader(new InputStreamReader(Sock.getInputStream()));
                    while (true) {
                        String mrecib = Clien.readLine();
                        tresultado2.append("Setver: " + mrecib + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        hleer.start();
    }
        
            public void escribir() {
        Thread hescribir = new Thread(new Runnable() {
            public void run() {
                try {
                    Servi = new PrintWriter(Sock.getOutputStream(),true);
                    acciones();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        hescribir.start();
    }
        
    
    public static void main(String[] args) throws IOException {    
			
	SocketServer ventana = new SocketServer ();
        ventana.acciones();	
        ventana.graficos2();	
    }
  }