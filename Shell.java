import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Shell extends JFrame{
	
	//elementos gráficos
	JTextField tComando;
	JButton bEjecutar;
	JButton bLimpiar;
	JTextArea tResultado;
	JScrollPane sPane;

	//oyente de click de botón
	ActionListener alEjecutar;
	ActionListener alLimpiar;

	public Shell(){
		setSize(700,600);
		setTitle(System.getProperty("os.name"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void graficos(){
		getContentPane().setLayout(null);
		//cuadro de texto
		tComando = new JTextField();
		tComando.setBounds(50,50,250,30);
		add(tComando);
		//botón para ejecutar comando
		bEjecutar = new JButton("Ejecutar");
		bLimpiar = new JButton("Limpiar");
		bEjecutar.setBounds(350,50,150,30);
		bLimpiar.setBounds(530,50,150,30);
		add(bEjecutar);
		add(bLimpiar);
		bEjecutar.addActionListener(alEjecutar);
		bLimpiar.addActionListener(alLimpiar);

		//área de texto
		tResultado = new JTextArea();
		tResultado.setBounds(50,130,600,370);
		tResultado.setBackground(Color.BLACK);
		tResultado.setForeground(Color.WHITE);
		//scroll pane
		sPane = new JScrollPane(tResultado);
		sPane.setBounds(50,120,500,400);
		add(sPane);
           this.getContentPane().setBackground(Color.CYAN);
		//
		setVisible(true);
	}

	private void acciones(){
		alEjecutar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ejecutar();
			}
		};
		alLimpiar = new ActionListener(){
			public void actionPerformed(ActionEvent f){
				limpiar();
			}
		};

	}

	private void limpiar(){
		tResultado.setText(null);
	}


	private void ejecutar(){

		Process proc; 
		InputStream is_in;
		String s_aux;
		BufferedReader br;

		try
		{
			proc = Runtime.getRuntime().exec(tComando.getText());
			is_in=proc.getInputStream();
			br=new BufferedReader (new InputStreamReader (is_in));
			s_aux = br.readLine();
            while (s_aux!=null)
            {
            	tResultado.setText(tResultado.getText()+s_aux+"\n");
                s_aux = br.readLine();
            } 
		}
		catch(Exception e)
		{
			e.getMessage();
		}


	}

	public static void main(String args[]){
		Shell ventana = new Shell();
		ventana.acciones();	
		ventana.graficos();	
	}

}