package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ventana.ventanaVisual;

public class VentanaTest {

	private ventanaVisual ventanaV;
	
	@Before
	public void setUp() throws Exception {
		ventanaV = new ventanaVisual();
		ventanaV.setVisible(true);
	}
	@After
    public void tearDown() {
        ventanaV.dispose();
    }

	@Test
	public void limpiarCampos() {
        // Simula la interacción del usuario
        ventanaV.nombreTextField.setText("John");
        ventanaV.correoTextField.setText("john@example.com");
        ventanaV.tipoUsuarioComboBox.setSelectedItem("Admin");

        // Ejecuta el método que se está probando
        ventanaV.limpiarCampos();

        // Verifica que los campos se hayan limpiado
        assertEquals("", ventanaV.nombreTextField.getText());
        assertEquals("", ventanaV.correoTextField.getText());
        assertEquals("Admin", ventanaV.tipoUsuarioComboBox.getSelectedItem());
    }

	@Test
    public void mostrarResultado() {
        // Simula la interacción del usuario
		ventanaV.nombreTextField.setText("Jane");
		ventanaV.correoTextField.setText("jane@example.com");
        ventanaV.tipoUsuarioComboBox.setSelectedItem("Usuario Regular");

        // Ejecuta el método que se está probando
        ventanaV.mostrarResultado();

        // Verifica que el resultado se haya mostrado en el JTextArea
        String expectedText = "Nombre: Jane\nCorreo: jane@example.com\nTipo de Usuario: Usuario Regular\n------------------------\n";
        assertEquals(expectedText, ventanaV.resultadoTextArea.getText());
    }

    @Test
    public void nombreSoloLetras() {
        // Simula la interacción del usuario
    	ventanaV.nombreTextField.setText("John123");

        // Ejecuta el método que se está probando
        ventanaV.mostrarResultado();

        // Verifica que el resultado no contenga el nombre incorrecto
        assertFalse(ventanaV.resultadoTextArea.getText().contains("John123"));
    }

    @Test
    public void correoFormatoCorrecto() {
    	// Simula la interacción del usuario
        ventanaV.correoTextField.setText("john.doe123@example.com");

        
        // Imprime el correo para depuración
        System.out.println("Correo: " + ventanaV.correoTextField.getText());
        
        
        // Ejecuta el método que se está probando
        ventanaV.mostrarResultado();

        // Imprime el contenido del área de texto para la depuración
        System.out.println("Resultado del área de texto: " + ventanaV.resultadoTextArea.getText());

        // Verifica que el resultado contenga el correo correcto
        assertTrue("El resultado no contiene el correo correcto",
                ventanaV.resultadoTextArea.getText().contains("john.doe123@example.com"));

        // Verifica que el resultado NO contenga un mensaje de "Nombre no válido"
        assertFalse("El resultado contiene un mensaje de 'Nombre no válido'",
                ventanaV.resultadoTextArea.getText().contains("Nombre no válido"));

        // Simula la interacción del usuario con un correo no válido
        ventanaV.correoTextField.setText("correo invalido");

        // Ejecuta el método que se está probando
        ventanaV.mostrarResultado();

        // Imprime el contenido del área de texto para la depuración
        System.out.println("Resultado del área de texto: " + ventanaV.resultadoTextArea.getText());

        // Verifica que el resultado no contenga el correo incorrecto
        assertFalse("El resultado contiene un correo no válido",
                ventanaV.resultadoTextArea.getText().contains("correo invalido"));
    }

    @Test
    public void correoFormatoIncorrecto() {
        // Simula la interacción del usuario
        ventanaV.correoTextField.setText("correoIncorrecto");

        // Ejecuta el método que se está probando
        ventanaV.mostrarResultado();

        // Verifica que el resultado no contenga el correo incorrecto
        assertFalse(ventanaV.resultadoTextArea.getText().contains("correoIncorrecto"));
    }
	
}
