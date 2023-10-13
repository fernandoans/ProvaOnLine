package jprova;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Dados do Software
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SobreSistema extends JDialog {

	private JLabel labLogo;
	private JLabel lab01;
	private JLabel lab02;

	/** Cria a janela dos dados */
	public SobreSistema() {
		setTitle(Atributo.CFVERSAO);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 255, 255));
		setSize(400, 550);
		setLocationRelativeTo(null);
		labLogo = new JLabel("", Atributo.getImage("ProvaOnLine2.png"), 0);
		labLogo.setBounds(new Rectangle(10, 17, 380, 400));
		getContentPane().add(labLogo, null);
		lab01 = new JLabel(Atributo.titulo, null, 0);
		lab01.setFont(new Font("Arial", 1, 20));
		lab01.setBounds(new Rectangle(10, 410, 350, 38));
		getContentPane().add(lab01, null);
		lab02 = new JLabel("2023 \251 Prova OnLine", null, 0);
		lab02.setBounds(new Rectangle(10, 450, 350, 13));
		lab02.setFont(new Font("Arial", 2, 12));
		getContentPane().add(lab02, null);
		JButton iniciar = new JButton("Iniciar Simulado");
		iniciar.setBounds(new Rectangle(110, 480, 150, 25));
		getContentPane().add(iniciar, null);
		iniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aoFechar();
			}
		});
		setModal(true);
		setResizable(false);
		setVisible(true);
	}

	private void aoFechar() {
		dispose();
	}
}