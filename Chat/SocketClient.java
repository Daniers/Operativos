import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
 
public class SocketClient extends JFrame {
 
        // Elementos Gráficos
	static JTextField tcomando;
	JButton benviar;
	static JTextArea tresultado;
	JScrollPane spane;
        static String aux;
     
        // Elementos de Hilos
        
        ServerSocket Server = null;
        Socket Sock = null;
        BufferedReader Clien = null;
        PrintWriter Servi = null;
        
	// Oyente de clic de botón Aportes
	ActionListener alenviar;
       
        public SocketClient (){
        
        setSize ( 700 , 600 );
        setTitle (" Cliente ");
	setDefaultCloseOperation ( JFrame . EXIT_ON_CLOSE );
        graficos();
}
        
        private void acciones(){
		alenviar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
                            String envia = tcomando.getText();
                            Servi.println(envia);
                            tresultado.append("Servidor: " + envia + "\n");
                            tcomando.setText(null);
			}
		};
        }
           
        private void graficos(){
		getContentPane().setLayout(null);
		//cuadro de texto
		tcomando = new JTextField();
		tcomando.setBounds(50,50,250,30);
		add(tcomando);
		//botón para ejecutar comando
		benviar = new JButton("Enviar");
		benviar.setBounds(350,50,150,30);
		add(benviar);
		benviar.addActionListener(alenviar);
                
		//área de texto
		tresultado = new JTextArea();
		tresultado.setBounds(50,130,600,370);
		tresultado.setBackground(Color.BLACK);
		tresultado.setForeground(Color.WHITE);
		//scroll pane
		spane = new JScrollPane(tresultado);
		spane.setBounds(50,120,500,400);
		add(spane);
                this.getContentPane().setBackground(Color.WHITE);
		//
		setVisible(true);
               
                Thread Inici = new Thread(new Runnable() {
               
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
        Inici.start();
	}
     
            public void leer() {
        Thread hleer = new Thread(new Runnable() {
            public void run() {
                try {
                    Clien = new BufferedReader(new InputStreamReader(Sock.getInputStream()));
                    while (true) {
                        String mrecib = Clien.readLine();
                        tresultado.append("Cliente: " + mrecib + "\n");
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
        
	public static void main(String args[]) {

        new SocketClient ();
        }
}