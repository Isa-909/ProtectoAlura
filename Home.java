package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.JToolBar;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import java.awt.Cursor;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Home extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setTitle("Conversor de unidades");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		BorderLayout bl_contentPane = new BorderLayout();
		bl_contentPane.setVgap(50);
		contentPane.setLayout(bl_contentPane);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(20, 70, 20, 70));
		panel.setSize(new Dimension(100, 100));
		panel.setLayout(new GridLayout(2, 1, 10, 10));
		
		contentPane.add(panel, BorderLayout.CENTER);
		
		JComboBox cbConversores = new JComboBox();
		cbConversores.setMaximumRowCount(3);
		cbConversores.setModel(new DefaultComboBoxModel(new String[] {"Conversor de monedas", "Conversor de unidades"}));
		cbConversores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.add(cbConversores);
		
		JButton btnContinuar = new JButton("Continuar");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String opcion= (String) cbConversores.getSelectedItem();
				if(opcion=="Conversor de unidades") {
					List<List<String>> lstOpciones= new ArrayList<>();
					String[] valores = new String[10];
					int indiceValores=0;
					lstOpciones.add(new ArrayList<String>(Arrays.asList("M","KM","Metro", "Kilometro", "1000")));
					lstOpciones.add(new ArrayList<String>(Arrays.asList("M","MILLA","Metro", "Milla", "1609.34")));
					lstOpciones.add(new ArrayList<String>(Arrays.asList("M","CM","Metro", "Centímetro", "0.01")));
					lstOpciones.add(new ArrayList<String>(Arrays.asList("M","MM","Metro", "Milímetro", "0.001")));
					lstOpciones.add(new ArrayList<String>(Arrays.asList("M","DM","Metro", "Decametro", "0.1")));
					for (int i = 0; i < lstOpciones.size(); i++) {
						valores[indiceValores] = "De "+lstOpciones.get(i).get(2)+" a "+lstOpciones.get(i).get(3);
						indiceValores++;
						valores[indiceValores] = "De "+lstOpciones.get(i).get(3)+" a "+lstOpciones.get(i).get(2);
						indiceValores++;
					}
					Object opcionMoneda = JOptionPane.showInputDialog(
							null,
							"Seleccione una opción de conversión:",
							"Conversor de unidades",
							JOptionPane.PLAIN_MESSAGE,
							null,
							valores,
							valores[0]);
					if(opcionMoneda != null) {
						Object input = JOptionPane.showInputDialog(
								null,
								"Ingrese el valor que deseas convertir: ",
								"Conversor de unidades",
								JOptionPane.QUESTION_MESSAGE,
								null,
								null,
								null);
						if(input != null) {
							try {
								String opcionConversion = (String) opcionMoneda, opInicial,opFinal;
								Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
							    
							    if (!pattern.matcher(input.toString()).matches()) {
							      throw new NumberFormatException();
							    }
								double valor = Double.parseDouble(input.toString()),conversion=0,cambio=0;
					            opcionConversion = opcionConversion.replace("De ", "");
					            opcionConversion = opcionConversion.replace(" a ", ";");
					            String[] subCadenas = opcionConversion.split(";");
					            opInicial = subCadenas[0];
					            opFinal = subCadenas[1];
					            for (List<String> lista : lstOpciones) {
					            	if ((opInicial.equals(lista.get(2)) && opFinal.equals(lista.get(3))) ||
					            		(opInicial.equals(lista.get(3)) && opFinal.equals(lista.get(2)))) {
										cambio = Double.parseDouble(lista.get(4));
										break;
									}
								}
					            if(opInicial.equals("Metro")) {
					            	conversion = valor / cambio;
					            } else {
					            	conversion = valor * cambio;
					            }
					            
					            DecimalFormat df = new DecimalFormat("#.##");
					            
					            JOptionPane.showMessageDialog(null, "El resultado es "+df.format(conversion)+" "+opFinal+"s", "Resultado", JOptionPane.INFORMATION_MESSAGE);
					            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar usando el programa?", "Confirmación", JOptionPane.YES_NO_CANCEL_OPTION);
					            
					            if(respuesta == JOptionPane.NO_OPTION) {
					            	JOptionPane.showMessageDialog(null, "Programa finalizado", "Información", JOptionPane.INFORMATION_MESSAGE);
					            	System.exit(0);
					            }
							}
							catch(NumberFormatException ex){
								JOptionPane.showMessageDialog(null, "El valor ingresado no es un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}     
					}
				}
				else if(opcion == "Conversor de monedas"){
					List<List<String>> lstOpciones= new ArrayList<>();
					String[] valores = new String[10];
					int indiceValores=0;
					lstOpciones.add(new ArrayList<String>(Arrays.asList("COP","USD","Peso colombiano", "Dolar estadounidense", "4506.23")));
					lstOpciones.add(new ArrayList<String>(Arrays.asList("COP","EU","Peso colombiano", "Euro", "4947.14")));
					lstOpciones.add(new ArrayList<String>(Arrays.asList("COP","GBP","Peso colombiano", "Libra esterlina", "5670.51")));
					lstOpciones.add(new ArrayList<String>(Arrays.asList("COP","JPY","Peso colombiano", "Yen japonés", "33.31")));
					lstOpciones.add(new ArrayList<String>(Arrays.asList("COP","KRW","Peso colombiano", "Won surcoreano", "3.40")));
					for (int i = 0; i < lstOpciones.size(); i++) {
						valores[indiceValores] = "De "+lstOpciones.get(i).get(2)+" a "+lstOpciones.get(i).get(3);
						indiceValores++;
						valores[indiceValores] = "De "+lstOpciones.get(i).get(3)+" a "+lstOpciones.get(i).get(2);
						indiceValores++;
					}
					Object opcionMoneda = JOptionPane.showInputDialog(
							null,
							"Seleccione una opción de conversión:",
							"Conversor de monedas",
							JOptionPane.PLAIN_MESSAGE,
							null,
							valores,
							valores[0]);
					if(opcionMoneda != null) {
						Object input = JOptionPane.showInputDialog(
								null,
								"Ingrese la cantidad de dinero que deseas convertir: ",
								"Conversor de monedas",
								JOptionPane.QUESTION_MESSAGE,
								null,
								null,
								null);
						if(input != null) {
							try {
								String opcionConversion = (String) opcionMoneda, opInicial,opFinal;
								Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
							    
							    if (!pattern.matcher(input.toString()).matches()) {
							      throw new NumberFormatException();
							    }
								double valor = Double.parseDouble(input.toString()),conversion=0,cambio=0;
					            opcionConversion = opcionConversion.replace("De ", "");
					            opcionConversion = opcionConversion.replace(" a ", ";");
					            String[] subCadenas = opcionConversion.split(";");
					            opInicial = subCadenas[0];
					            opFinal = subCadenas[1];
					            for (List<String> lista : lstOpciones) {
					            	if ((opInicial.equals(lista.get(2)) && opFinal.equals(lista.get(3))) ||
					            		(opInicial.equals(lista.get(3)) && opFinal.equals(lista.get(2)))) {
										cambio = Double.parseDouble(lista.get(4));
										break;
									}
								}
					            if(opInicial.equals("Peso colombiano")) {
					            	conversion = valor / cambio;
					            } else {
					            	conversion = valor * cambio;
					            }
					            
					            DecimalFormat df = new DecimalFormat("#.##");
					            
					            JOptionPane.showMessageDialog(null, "Tienes $ "+df.format(conversion)+" "+opFinal, "Resultado", JOptionPane.INFORMATION_MESSAGE);
					            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar usando el programa?", "Confirmación", JOptionPane.YES_NO_CANCEL_OPTION);
					            
					            if(respuesta == JOptionPane.NO_OPTION) {
					            	JOptionPane.showMessageDialog(null, "Programa finalizado", "Información", JOptionPane.INFORMATION_MESSAGE);
					            	System.exit(0);
					            }
							}
							catch(NumberFormatException ex){
								JOptionPane.showMessageDialog(null, "El valor ingresado no es un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}     
					}					
				}
			}
		});
		panel.add(btnContinuar);
		
		JLabel lblTitulo = new JLabel("<html>Bienvenido al conversor de unidades<br>Seleccione una opción de conversión</html>");
		lblTitulo.setVerticalAlignment(SwingConstants.TOP);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Monospaced", Font.PLAIN, 14));
		lblTitulo.setBorder(new EmptyBorder(50, 0, 0, 0));
		contentPane.add(lblTitulo, BorderLayout.NORTH);
        
        
		
	}

}
