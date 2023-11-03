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
@SuppressWarnings("serial")
public class SobreSistema extends JDialog {

	public SobreSistema() {
		setTitle(Atributo.CFVERSAO);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 255, 255));
		setSize(400, 550);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		
		JLabel labLogo = new JLabel("", Atributo.getImage("ProvaOnLine2.png"), 0);
		labLogo.setBounds(new Rectangle(10, 17, 380, 400));
		getContentPane().add(labLogo, null);

		JLabel lab01 = new JLabel(Atributo.titulo, null, 0);
		lab01.setBounds(new Rectangle(10, 410, 350, 38));
		lab01.setFont(new Font("Arial", 1, 20));
		getContentPane().add(lab01, null);

		JLabel lab02 = new JLabel("2023 \251 Prova OnLine", null, 0);
		lab02.setBounds(new Rectangle(10, 450, 350, 13));
		lab02.setFont(new Font("Arial", 2, 12));
		getContentPane().add(lab02, null);

		JButton iniciar = MntComponents.getJButtonTxt("Iniciar Simulado", 110, 480, 150, 25,
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aoFechar();
				}
			});
		getContentPane().add(iniciar, null);

		setVisible(true);
	}

	private void aoFechar() {
		dispose();
	}
}