package runnable;

import admin.*;
import java.io.IOException;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.border.LineBorder;
import javax.swing.JMenuItem;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private static String REGISTRO = "COPYRIGTH: ADRIÁN RODRÍGUEZ 2020" + "\n" + "-----------" + "\n"
			+ "VERSIÓN: 1.2 || 20/01/2020" + "Añadida opción de cambiar contraseña" + "\n" + "Añadido Icono" + "\n"
			+ "Solucion de errores menores" + "VERSIÓN: 1.1 || 10/01/2020" + "\n" + "Solución de problemas:" + "\n"
			+ "-Correcta seleción después de clicar \"Borrar Campos\"" + "\n"
			+ "-Errores relacionados con el nombre de los campos" + "\n" + "Nuevo:" + "\n"
			+ "-Popup al cerrar: aviso de guardado" + "\n" + "-Ahora los títulos pueden exceder la capacidad del cuadro"
			+ "\n" + "Menus:" + "\n" + "-Acerca de..." + "\n\n" + "VERSIÓN: 1.0 || 06/01/2020" + "\n"
			+ "Programa creado!!!";
	private final static String secretKey = "18376117953852183";
	private static String pass = null, lastOne, selectedMat;
	private static Base base = null;
	private static boolean modoNoct = false, changed = false;
	private static JList<String> titles;
	private static JComboBox<String> comboBox;
	private static JTextArea textArea;
	private static JTextField title;
	private static JTextField materia;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					frame.addComponentListener(new ComponentAdapter() {
						public void componentHidden(ComponentEvent e) {
							boolean exit = false;
							if (titles.isVisible() == true
									&& (title.getText().equals("") == false && materia.getText().equals("") == false
											&& textArea.getText().equals("") == false)) {
								Apunte a = find(title.getText(), materia.getText());
								if (a == null) {
									changed = true;
								} else {
									if (a.getText().equals(textArea.getText()) == false) {
										changed = true;
									}
								}
								if (changed == true) {
									int input = JOptionPane.showConfirmDialog(null, "GUARDAR CAMBIOS?");
									if (input == 0) {
										save(textArea, comboBox, titles);
										exit = true;
									} else if (input == 1) {
										exit = true;
									} else {
										frame.setVisible(true);
									}
								} else {
									exit = true;
								}
							} else {
								exit = true;
							}
							if (exit == true) {
								frame.dispose();
								System.exit(0);
							}
						}
					});
				} catch (Exception e) {
					return;
				}
			}
		});
	}

	public Main() throws IOException {
	
		setRootPaneCheckingEnabled(false);
		setIconImage(new ImageIcon("appIcon.png").getImage());
		setResizable(false);
		setBackground(Color.WHITE);
		setTitle("MyApuntes");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(200, 100, 1000, 600);
		JMenuBar menuBar = new JMenuBar();
		JMenu mnCONFIG = new JMenu("CONFIGURACI\u00D3N");
		JCheckBoxMenuItem chckbxmntmModoNocturno = new JCheckBoxMenuItem("MODO NOCTURNO");
		JMenuItem mntmAcercaDe = new JMenuItem("ACERCA DE...");
		menuBar.setBackground(Color.WHITE);
		menuBar.setForeground(Color.WHITE);
		setJMenuBar(menuBar);
		mnCONFIG.setForeground(Color.BLACK);
		menuBar.add(mnCONFIG);
		mnCONFIG.add(chckbxmntmModoNocturno);
		JMenuItem mntmCambiarContrasea = new JMenuItem("CAMBIAR CONTRASE\u00D1A");
		mnCONFIG.add(mntmCambiarContrasea);
		mnCONFIG.add(mntmAcercaDe);
		JPanel fondo = new JPanel();
		JLabel lblNewPassword = new JLabel("INSERTE NUEVA CONTRASE\u00D1A");
		JButton btnGuardar = new JButton("GUARDAR");
		JPanel panel = new JPanel();
		JPasswordField passwordField = new JPasswordField();
		JButton btnEnter = new JButton("ENTER");
		fondo.setForeground(Color.WHITE);
		fondo.setBackground(Color.WHITE);
		fondo.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(fondo);
		fondo.setLayout(null);
		JPanel select = new JPanel();
		select.setBounds(10, 39, 183, 489);
		fondo.add(select);
		select.setLayout(null);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 41, 180, 448);
		select.add(scroll);
		titles = new JList<String>(new DefaultListModel<String>());
		scroll.setViewportView(titles);
		titles.setToolTipText("Lista de T\u00EDtulos.\r\nClick derecho para eliminar el seleccionado.");
		titles.setBorder(new LineBorder(Color.GRAY));
		titles.setBackground(Color.WHITE);
		titles.setForeground(Color.BLACK);
		titles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		titles.setVisibleRowCount(10);
		comboBox = new JComboBox<String>();
		comboBox.setBounds(0, 0, 180, 36);
		select.add(comboBox);
		comboBox.setForeground(Color.BLACK);
		comboBox.setBackground(Color.WHITE);
		JPanel editar = new JPanel();
		editar.setBounds(250, 39, 724, 395);
		fondo.add(editar);
		editar.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 44, 714, 351);
		editar.add(scrollPane);
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setBackground(Color.WHITE);
		textArea.setForeground(Color.BLACK);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		JButton btnLimpiar = new JButton("BORRAR CAMPOS");
		btnLimpiar.setBounds(574, 11, 140, 23);
		editar.add(btnLimpiar);
		title = new JTextField();
		title.setBounds(0, 0, 230, 29);
		editar.add(title);
		title.setToolTipText("TITULO");
		title.setFont(new Font("Tahoma", Font.PLAIN, 19));
		title.setColumns(10);
		materia = new JTextField();
		materia.setBounds(302, 0, 230, 29);
		editar.add(materia);
		materia.setBackground(Color.WHITE);
		materia.setForeground(Color.BLACK);
		materia.setToolTipText("MATERIA");
		materia.setFont(new Font("Tahoma", Font.PLAIN, 19));
		materia.setColumns(10);
		btnGuardar.setBackground(Color.WHITE);
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnGuardar.setBounds(250, 445, 715, 84);
		fondo.add(btnGuardar);
		btnGuardar.setVisible(false);
		lblNewPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewPassword.setBounds(378, 11, 215, 17);
		fondo.add(lblNewPassword);
		panel.setForeground(Color.WHITE);
		panel.setBounds(376, 28, 215, 156);
		fondo.add(panel);
		panel.setLayout(null);
		passwordField.setBounds(0, 0, 215, 26);
		panel.add(passwordField);
		passwordField.setToolTipText("Contrase\u00F1a");
		panel.setVisible(true);
		btnEnter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnEnter.setBounds(49, 58, 127, 45);
		panel.add(btnEnter);
		editar.setVisible(false);
		select.setVisible(false);
		select.setBackground(null);
		editar.setBackground(null);

		try {
			String[] resp = TextEditor.LoadParams(3);
			if (resp[0] != null) {
				lblNewPassword.setVisible(false);
				pass = AES.decrypt(resp[0], secretKey);
			} else {
				pass = null;
			}
			if (resp[1].equals("true")) {
				modoNoct = true;
				ModoNoctOn(modoNoct, btnGuardar, btnLimpiar, fondo, editar, menuBar, mnCONFIG, chckbxmntmModoNocturno);
			}
			lastOne = resp[2];
		} catch (IOException e) {
			return;
		}

		mntmCambiarContrasea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (base == null) {
					JOptionPane.showMessageDialog(null,
							"SI TIENE PROBLEMAS PARA RECORDAR\nLA CONTRASEÑA PÓNGASE EN CONTACTO\nCON EL SERVICIO TÉCNICO EN\nadrian.role.business@gmail.com",
							"CAMBIAR CONTRASEÑA", JOptionPane.PLAIN_MESSAGE);
				} else {
					try {
						ChangePassword changePass = new ChangePassword(pass, secretKey, modoNoct, lastOne);
						changePass.setVisible(true);
					} catch (IOException e1) {
						return;
					}
				}
			}
		});
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (charge(lblNewPassword, titles, comboBox, btnGuardar, passwordField, editar, panel, select,
						lblNewPassword) == true) {
					if (lastOne.equals("null") == false && lastOne != null) {
						String s[] = lastOne.split("%");
						Apunte a = find(s[1], s[0]);
						if (a != null) {
							find(titles, comboBox, a.getMateria(), a.getTitle());
							textArea.setText(a.getText());
							title.setText(a.getTitle());
							materia.setText(a.getMateria());
							textArea.setCaretPosition(0);
						}
					}
				}
			}
		});
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (charge(lblNewPassword, titles, comboBox, btnGuardar, passwordField, editar, panel, select,
						lblNewPassword) == true) {
					if (lastOne.equals("null") == false) {
						String s[] = lastOne.split("%");
						Apunte a = find(s[1], s[0]);
						if (a != null) {
							find(titles, comboBox, a.getMateria(), a.getTitle());
							textArea.setText(a.getText());
							title.setText(a.getTitle());
							materia.setText(a.getMateria());
							textArea.setCaretPosition(0);
						}
					}
				}
			}
		});
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DefaultListModel<String>) titles.getModel()).removeAllElements();
				Object o = comboBox.getSelectedItem();
				String s = (String) o;
				selectedMat = s;
				Apunte first = null;
				boolean flag = false;
				if (base.getAPUNTS().isEmpty() == false) {
					for (int i = 0; i < base.getAPUNTS().size(); i++) {
						Apunte a = base.getAPUNTS().get(i);
						if (a.getMateria().equals(s)) {
							((DefaultListModel<String>) titles.getModel()).addElement(a.getTitle());
							if (flag == false) {
								first = a;
								flag = true;
							}
						}
					}
					if (first != null) {
						textArea.setText(first.getText());
						title.setText(first.getTitle());
						materia.setText(first.getMateria());
						textArea.setCaretPosition(0);
					}
				}
			}
		});
		titles.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					String t = ((JList<String>) e.getSource()).getModel()
							.getElementAt((int) ((JList<String>) e.getSource()).locationToIndex(e.getPoint()));
					Apunte a = find(t, selectedMat);
					int input = JOptionPane.showConfirmDialog(null, "ELIMINAR " + t + " de " + selectedMat + "?");
					// 0=yes, 1=no, 2=cancel
					if ((input == 0) && (a != null)) {
						boolean ward = false;
						base.getAPUNTS().remove(a);
						for (int i = 0; i < base.getAPUNTS().size(); i++) {
							if (base.getAPUNTS().get(i).getMateria().toUpperCase()
									.equals(a.getMateria().toUpperCase())) {
								ward = true;
							}
						}
						if (ward == false) {
							base.getMATERIAS().remove(a.getMateria());
						}
						materia.setText("");
						title.setText("");
						textArea.setText("");
						TextEditor.reload(base);
						Inicialize(comboBox, titles);
					}
				} else {
					Object o = titles.getSelectedValue();
					String s = (String) o;
					for (Apunte ap : base.getAPUNTS()) {
						if (ap.getTitle().toUpperCase().equals(s.toUpperCase())
								&& ap.getMateria().toUpperCase().equals(selectedMat.toUpperCase())) {
							textArea.setText(ap.getText());
							title.setText(ap.getTitle());
							materia.setText(ap.getMateria());
							textArea.setCaretPosition(0);
							lastOne = ap.getMateria() + "%" + ap.getTitle();
							try {
								TextEditor.write(pass, modoNoct, lastOne);
							} catch (IOException ex) {
								return;
							}
						}
					}
				}
			}
		});
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (materia.getText().equals("") == false || title.getText().equals("") == false
						|| textArea.getText().equals("") == false) {
					materia.setText("");
					title.setText("");
					textArea.setText("");
				}
			}
		});
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, REGISTRO, "REGISTRO", JOptionPane.PLAIN_MESSAGE);
			}
		});
		chckbxmntmModoNocturno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxmntmModoNocturno.getState() == true) {
					modoNoct = true;
				} else {
					modoNoct = false;
				}
				try {
					ModoNoctOn(modoNoct, btnGuardar, btnLimpiar, fondo, editar, menuBar, mnCONFIG,
							chckbxmntmModoNocturno);
				} catch (IOException e) {
					return;
				}
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String t = title.getText(), m = materia.getText();
				save(textArea, comboBox, titles);
				lastOne = materia.getText() + "%" + title.getText();
				Apunte a = find(t, m);
				find(titles, comboBox, a.getMateria(), a.getTitle());
				textArea.setText(a.getText());
				title.setText(a.getTitle());
				materia.setText(a.getMateria());
				textArea.setCaretPosition(0);
			}
		});
	}

	private static void save(JTextArea textArea, JComboBox<String> comboBox, JList<String> titles) {
		if (title.getText().equals("") == false && materia.getText().equals("") == false
				&& textArea.getText().equals("") == false) {
			String[] splited = textArea.getText().split("aSdFlKjH999");
			String text = "";
			for (String s : splited) {
				text = text + s;
			}
			Apunte a = find(title.getText(), materia.getText());
			if (a != null) {
				base.getAPUNTS().get(base.getAPUNTS().indexOf(a)).setText(text);
			} else {
				Apunte b = new Apunte(materia.getText(), title.getText(), text);
				base.getAPUNTS().add(b);
			}
			TextEditor.reload(base);
			Inicialize(comboBox, titles);
			changed = false;
		}
	}

	private void find(JList<String> titles, JComboBox<String> j, String m, String title) {
		for (int i = 0; i < j.getModel().getSize(); i++) {
			if ((j.getItemAt(i)).toUpperCase().equals(m.toUpperCase())) {
				j.setSelectedIndex(i);
			}
		}
		for (int i = 0; i < titles.getModel().getSize(); i++) {
			if (((String) titles.getModel().getElementAt(i)).toUpperCase().equals(title.toUpperCase())) {
				titles.setSelectedIndex(i);
			}
		}
	}

	private static Apunte find(String t, String m) {
		Apunte a = null;
		for (Apunte ap : base.getAPUNTS()) {
			if (ap.getTitle().toUpperCase().equals(t.toUpperCase())
					&& ap.getMateria().toUpperCase().equals(m.toUpperCase())) {
				a = ap;
			}
		}
		return a;
	}

	private boolean charge(JLabel l, JList<String> t, JComboBox<String> b, JButton but, JPasswordField passwordField,
			JPanel editar, JPanel panel, JPanel select, JLabel lblNewPassword) {
		String c = String.valueOf(passwordField.getPassword());
		if (!c.equals("")) {
			try {
				if (pass == null) {
					pass = AES.encrypt(c, secretKey);
					TextEditor.write(pass, modoNoct, lastOne);
					base = new Base();
					TextEditor.LoadData(base);
					Inicialize(b, t);
					enter(editar, select, panel, but, l);
					return true;
				} else {
					if (c.equals(pass)) {
						base = new Base();
						TextEditor.LoadData(base);
						Inicialize(b, t);
						enter(editar, select, panel, but, l);
						return true;
					}
				}
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}

	private void enter(JPanel editar, JPanel s, JPanel panel, JButton but, JLabel l) {
		editar.setVisible(true);
		s.setVisible(true);
		but.setVisible(true);
		panel.setVisible(false);
		l.setVisible(false);
	}

	private static void Inicialize(JComboBox<String> j, JList<String> titles) {
		j.removeAllItems();
		for (Apunte a : base.getAPUNTS()) {
			((DefaultListModel<String>) titles.getModel()).removeElement(a);
		}
		for (int i = 0; i < base.getMATERIAS().size(); i++) {
			String m = base.getMATERIAS().get(i);
			j.addItem(m);
		}
	}

	private static void ModoNoctOn(boolean choice, JButton g, JButton lim, JPanel f, JPanel f2, JMenuBar menu,
			JMenu conf, JCheckBoxMenuItem che) throws IOException {
		if (choice == true) {
			TextEditor.write(pass, true, lastOne);
			titles.setBackground(Color.BLACK);
			titles.setForeground(Color.WHITE);
			comboBox.setBackground(Color.BLACK);
			comboBox.setForeground(Color.WHITE);
			g.setBackground(Color.BLACK);
			g.setForeground(Color.WHITE);
			lim.setBackground(Color.BLACK);
			lim.setForeground(Color.WHITE);
			title.setBackground(Color.BLACK);
			title.setForeground(Color.WHITE);
			materia.setBackground(Color.BLACK);
			materia.setForeground(Color.WHITE);
			textArea.setBackground(Color.BLACK);
			textArea.setForeground(Color.WHITE);
			textArea.setCaretColor(Color.WHITE);
			f.setBackground(Color.BLACK);
			f.setForeground(Color.WHITE);
			f2.setBackground(Color.BLACK);
			f2.setForeground(Color.WHITE);
			menu.setBackground(Color.BLACK);
			menu.setForeground(Color.WHITE);
			conf.setBackground(Color.BLACK);
			conf.setForeground(Color.WHITE);
			che.setSelected(true);
		} else {
			TextEditor.write(pass, false, lastOne);
			titles.setBackground(Color.WHITE);
			titles.setForeground(Color.BLACK);
			comboBox.setBackground(Color.WHITE);
			comboBox.setForeground(Color.BLACK);
			g.setBackground(Color.WHITE);
			g.setForeground(Color.BLACK);
			lim.setBackground(Color.WHITE);
			lim.setForeground(Color.BLACK);
			title.setBackground(Color.WHITE);
			title.setForeground(Color.BLACK);
			materia.setBackground(Color.WHITE);
			materia.setForeground(Color.BLACK);
			textArea.setBackground(Color.WHITE);
			textArea.setForeground(Color.BLACK);
			textArea.setCaretColor(Color.BLACK);
			f.setBackground(Color.WHITE);
			f.setForeground(Color.BLACK);
			f2.setBackground(Color.WHITE);
			f2.setForeground(Color.BLACK);
			menu.setBackground(Color.WHITE);
			menu.setForeground(Color.BLACK);
			conf.setBackground(Color.WHITE);
			conf.setForeground(Color.BLACK);
			che.setSelected(false);
		}
	}
}
