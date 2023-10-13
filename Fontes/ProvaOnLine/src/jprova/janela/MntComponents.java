package jprova.janela;

import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jprova.util.Atributo;

public final class MntComponents {

	public static JLabel getJLabel(String titulo, int p1, int p2, int p3, int p4) {
		JLabel label = new JLabel(titulo);
		label.setBounds(new Rectangle(p1, p2, p3, p4));
		return label;
	}
	
	public static JScrollPane getJScrollPane(Component view, int p1, int p2, int p3, int p4) {
		JScrollPane scroll = new JScrollPane(view);
		scroll.setHorizontalScrollBarPolicy(31);
		scroll.setBounds(new Rectangle(p1, p2, p3, p4));
		return scroll;
	}
	
	public static JTextArea getJTextArea() {
		JTextArea texto = new JTextArea();
		texto.setLineWrap(true);
		texto.setFont(new Font("Arial", 0, 16));
		texto.setWrapStyleWord(true);
		texto.setEditable(false);
		return texto;
	}
	
	public static JButton getJButtonTxt(String txt, int p1, int p2, int p3, int p4, ActionListener acao) {
		JButton button = new JButton(txt);
		button.setBounds(new Rectangle(p1, p2, p3, p4));
		button.addActionListener(acao);
		return button;
	}
	
	public static JButton getJButtonImg(String img, int p1, int p2, int p3, int p4, ActionListener acao) {
		JButton button = new JButton();
		button.setIcon(Atributo.getImage(img));
		button.setBounds(new Rectangle(p1, p2, p3, p4));
		button.addActionListener(acao);
		return button;
	}
	
	public static JTextField getJTextField(int p1, int p2, int p3, int p4) {
	    JTextField texto = new JTextField();
	    texto.setBounds(new Rectangle(p1, p2, p3, p4));
	    texto.setFont(new Font("Arial", 0, 16));
	    return texto;
	}
	
	public static JPanel getJPanel(int p1, int p2, int p3, int p4) {
		JPanel painel = new JPanel();
		painel.setBounds(new Rectangle(p1, p2, p3, p4));
		painel.setLayout(null);
		return painel;
	}

	public static JRadioButton getJRadioButton(String txt, int p1, int p2, int p3, int p4, ActionListener acao) {
		JRadioButton radio = new JRadioButton(txt);
		radio.setBounds(new Rectangle(p1, p2, p3, p4));
		radio.addActionListener(acao);
		return radio;
	}
}
