package ventana;

import javax.swing.JFrame;

import java.util.Date;

import javax.swing.*;

public class Ventana1 extends JFrame{

	 public Ventana1(String nombre, String correo, String tipoUsuario, Date fechaNacimiento) {
	        super("Ventana Secundaria 1");

	        JPanel panel = new JPanel();
	        panel.add(new JLabel("Nombre: " + nombre));
	        panel.add(new JLabel("Correo: " + correo));
	        panel.add(new JLabel("Tipo de Usuario: " + tipoUsuario));
	        panel.add(new JLabel("Fecha: " + fechaNacimiento));

	        getContentPane().add(panel);
	        setSize(300, 150);
	        setLocationRelativeTo(null);
	    }
}
