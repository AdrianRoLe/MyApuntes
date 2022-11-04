package runnable;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import admin.AES;
import admin.TextEditor;

public class ChangePassword extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPasswordField currentPass;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;

	public ChangePassword(String pass, String secretKey, boolean modoNoct, String lastOne) throws IOException {
		setIconImage(new ImageIcon("appIcon.png").getImage());
		setResizable(false);
		setRootPaneCheckingEnabled(false);
		setBackground(Color.WHITE);
		setTitle("CAMBIAR CONTRASEÑA");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 340, 290);
		getContentPane().setLayout(null);

		JLabel lblCurrentPass = new JLabel("ESCRIBA LA CONTRASE\u00D1A ACTUAL");
		lblCurrentPass.setBounds(10, 22, 314, 14);
		getContentPane().add(lblCurrentPass);

		currentPass = new JPasswordField();
		currentPass.setBounds(10, 36, 314, 20);
		getContentPane().add(currentPass);
		currentPass.setColumns(10);

		JButton btnAct = new JButton("ACTUALIZAR CONTRASE\u00D1A");

		btnAct.setBounds(10, 219, 314, 31);
		getContentPane().add(btnAct);

		JLabel lblNewPass1 = new JLabel("ESCRIBA LA CONTRASE\u00D1A NUEVA");
		lblNewPass1.setBounds(10, 69, 314, 14);
		getContentPane().add(lblNewPass1);

		passwordField1 = new JPasswordField();
		passwordField1.setColumns(10);
		passwordField1.setBounds(10, 83, 314, 20);
		getContentPane().add(passwordField1);

		JLabel lblNewPass2 = new JLabel("ESCRIBA LA CONTRASE\u00D1A NUEVA DE NUEVO");
		lblNewPass2.setBounds(10, 117, 314, 14);
		getContentPane().add(lblNewPass2);

		passwordField2 = new JPasswordField();
		passwordField2.setColumns(10);
		passwordField2.setBounds(10, 131, 314, 20);
		getContentPane().add(passwordField2);

		JLabel lblError = new JLabel("Error: ");
		lblError.setForeground(Color.RED);
		lblError.setHorizontalAlignment(SwingConstants.LEFT);
		lblError.setBounds(39, 162, 260, 46);
		lblError.setVisible(false);
		getContentPane().add(lblError);

		btnAct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String actual = String.valueOf(currentPass.getPassword());
				String new1 = String.valueOf(passwordField1.getPassword());
				String new2 = String.valueOf(passwordField2.getPassword());
				if (actual.equals("") || new1.equals("") || new2.equals("")) {
					lblError.setVisible(true);
					lblError.setText("Error: RELLENE TODOS LOS CAMPOS");
				} else {
					if (actual.equals(pass)) {
						if (new2.equals(new1)) {
							try {
								TextEditor.write(AES.encrypt(new1, secretKey), modoNoct, lastOne);
								lblError.setVisible(false);
								setVisible(false);
								dispose();
							} catch (IOException e1) {
								return;
							}
						} else {
							lblError.setVisible(true);
							lblError.setText("Error: LAS CONTRASEÑAS NO COINCIDEN");
						}
					} else {
						lblError.setVisible(true);
						lblError.setText("Error: LA CONTRASEÑA ACTUAL ES INCORRECTA");
					}
				}
			}
		});
	}

}