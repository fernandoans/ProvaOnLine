package jprova.janela;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jprova.util.Atributo;

/**
 * Dados do Software
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class SobreSistema extends JDialog {

	public SobreSistema() {
		setTitle(Atributo.CFVERSAO);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 255, 255));
		setSize(400, 550);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		
		JLabel labLogo = new JLabel("", Atributo.getImage("ProvaOnLine2.png"), SwingConstants.CENTER);
		labLogo.setBounds(new Rectangle(10, 17, 380, 400));
		getContentPane().add(labLogo, null);

		JLabel lab01 = new JLabel(Atributo.titulo, null, SwingConstants.CENTER);
		lab01.setBounds(new Rectangle(10, 410, 350, 38));
		lab01.setFont(new Font("Arial", Font.BOLD, 20));
		getContentPane().add(lab01, null);

		JLabel lab02 = new JLabel(Atributo.COPYRIGHT, null, SwingConstants.CENTER);
		lab02.setBounds(new Rectangle(10, 450, 350, 13));
		lab02.setFont(new Font("Arial", Font.ITALIC, 12));
		getContentPane().add(lab02, null);

		JButton iniciar = MntComponents.getJButtonTxt("Iniciar Simulado", 110, 480, 150, 25,
                e -> aoFechar());
		getContentPane().add(iniciar, null);

		setVisible(true);
	}

	private void aoFechar() {
		dispose();
	}
}