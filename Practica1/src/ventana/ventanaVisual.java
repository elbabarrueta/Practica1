package ventana;

import javax.swing.*;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ventanaVisual extends JFrame {

/*
 * Esta aplicación utiliza la biblioteca SwingX
 * 		**Versión utilizada:** 1.6.4
 * Enlace para descargar la libreria:
 * 		https://mvnrepository.com/artifact/org.swinglabs.swingx/swingx-all/1.6.4
 */

	public JTextField nombreTextField;
    public JTextField correoTextField;
    public JComboBox<String> tipoUsuarioComboBox;
    public JTextArea resultadoTextArea;
    
    JXDatePicker datePicker;
    private static final Logger logger = Logger.getLogger(ventanaVisual.class.getName());


    public ventanaVisual() {
        setTitle("Ventana Principal");
        setSize(800, 600);
        configureLogger();

        // Crear componentes
        nombreTextField = new JTextField(20);
        correoTextField = new JTextField(20);
        datePicker = new JXDatePicker();
        
        tipoUsuarioComboBox = new JComboBox<>(new String[]{"Admin", "Usuario Regular"});
        resultadoTextArea = new JTextArea(10, 30);
        resultadoTextArea.setEditable(false);

        JButton botonAbrirVentana1 = new JButton("Abrir Ventana 1");
        JButton botonLimpiarCampos = new JButton("Limpiar Campos");
        JButton botonMostrarResultado = new JButton("Mostrar Resultado");

        JLabel resultadoLabel = new JLabel("Resultado:");

        // Configurar colores
        resultadoTextArea.setBackground(new Color(240, 240, 240));

        // Configurar el diseño
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(nombreTextField);
        panelCampos.add(new JLabel("Correo:"));
        panelCampos.add(correoTextField);
        panelCampos.add(new JLabel("Tipo de Usuario:"));
        panelCampos.add(tipoUsuarioComboBox);
        panelCampos.add(new JLabel("Fecha de Nacimiento:"));
        panelCampos.add(datePicker);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(botonAbrirVentana1);
        panelBotones.add(botonLimpiarCampos);
        panelBotones.add(botonMostrarResultado);

        panelPrincipal.add(panelCampos);
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(resultadoLabel);
        panelPrincipal.add(new JScrollPane(resultadoTextArea));

        // Configurar acciones de los botones
        botonAbrirVentana1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaSecundaria(1);
            }
        });

        botonLimpiarCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        botonMostrarResultado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarResultado();
            }
        });

        // Configurar la ventana principal
        getContentPane().add(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configureLogger() {
        try {
            // Configura el logger para escribir en archivos separados por niveles
            FileHandler infoFileHandler = new FileHandler("info.log");
            FileHandler warningFileHandler = new FileHandler("warning.log");
            FileHandler errorFileHandler = new FileHandler("error.log");

            infoFileHandler.setLevel(Level.INFO);
            warningFileHandler.setLevel(Level.WARNING);
            errorFileHandler.setLevel(Level.SEVERE);

            infoFileHandler.setFormatter(new SimpleFormatter());
            warningFileHandler.setFormatter(new SimpleFormatter());
            errorFileHandler.setFormatter(new SimpleFormatter());

            // Añade los FileHandlers al logger
            logger.addHandler(infoFileHandler);
            logger.addHandler(warningFileHandler);
            logger.addHandler(errorFileHandler);

            // Configura el nivel global del logger (puedes ajustar según tus necesidades)
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void abrirVentanaSecundaria(int numeroVentana) {
    	try {
            logger.info("Abriendo ventana secundaria " + numeroVentana);
            
            String nombre = nombreTextField.getText();
            String correo = correoTextField.getText();
            String tipoUsuario = (String) tipoUsuarioComboBox.getSelectedItem();
            Date fechaNacimiento = obtenerFechaNacimiento();

            
            if (!nombre.isEmpty() && !correo.isEmpty()) {
                switch (numeroVentana) {
                    case 1:
                        Ventana1 v1 = new Ventana1(nombre, correo, tipoUsuario, fechaNacimiento);
                        v1.setVisible(true);
                        break;
                    default:
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos antes de abrir una ventana secundaria.");
            }
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al abrir ventana secundaria", e);
        }
    	
    }
    private Date obtenerFechaNacimiento() {
        Date fechaNacimiento = datePicker.getDate();
        if(fechaNacimiento == null) { 
        	String patronFecha = "dd/MM/yyyy";
        	SimpleDateFormat formatoFecha = new SimpleDateFormat(patronFecha);
        	try {
				fechaNacimiento = formatoFecha.parse(obtenerFechaActualFormateada());
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
		return fechaNacimiento;
    }

    public void limpiarCampos() {
        nombreTextField.setText("");
        correoTextField.setText("");
        tipoUsuarioComboBox.setSelectedIndex(0);
    }
    
    public boolean validarCorreo(String correo) {
        // Validación de correo: letras, números, +, . antes y después de @
    	 boolean resultado =  correo.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");

     // Imprime mensajes
        System.out.println("Correo a validar: " + correo);
        System.out.println("Resultado de la validación de correo: " + resultado);

        return resultado;
    }

    public void mostrarResultado() {
    	try {
            logger.info("Mostrando resultado");
            String nombre = nombreTextField.getText();
    	    String correo = correoTextField.getText();
    	    String tipoUsuario = (String) tipoUsuarioComboBox.getSelectedItem();
            Date fechaNacimiento = obtenerFechaNacimiento();

            
    	    if (validarNombre(nombre) && validarCorreo(correo)) {
    	        resultadoTextArea.append("Nombre: " + nombre + "\n");
    	        resultadoTextArea.append("Correo: " + correo + "\n");
    	        resultadoTextArea.append("Tipo de Usuario: " + tipoUsuario + "\n");
                resultadoTextArea.append("Fecha de nacimiento: " + obtenerFechaFormateada(fechaNacimiento) + "\n");
    	        resultadoTextArea.append("------------------------\n");
    	    } else {
    	        resultadoTextArea.append("Nombre o correo no válido\n------------------------\n");
    	    }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al mostrar resultado", e);
        } 	
    }

    private String obtenerFechaActualFormateada() {
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatoFecha.format(fechaActual);
    }
    private String obtenerFechaFormateada(Date fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        return formatoFecha.format(fecha);
    }
    
    public boolean validarNombre(String nombre) {
        // Validación simple: permitir solo letras
        return nombre.matches("\\w*");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ventanaVisual().setVisible(true);
            }
        });
    }
}