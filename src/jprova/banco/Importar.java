package jprova.banco;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jprova.janela.MntComponents;

import java.io.*;

public class Importar extends JFrame {

	public Importar() {
		super("Importar Quest\365es");
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(238, 238, 238));
		setSize(481, 170);
		setLocationRelativeTo(null);
		setResizable(false);
		
		getContentPane().add(MntComponents.getJLabel("Caminho do Arquivo CSV:", 10, 18, 198, 13), null);
		arquivo = MntComponents.getJLabel("[arquivo CSV]", 10, 45, 440, 13);
		getContentPane().add(arquivo, null);
		registro = MntComponents.getJLabel("Registros: [registros]", 10, 65, 440, 13);
		getContentPane().add(registro, null);
		
		JButton btArquivo = MntComponents.getJButtonTxt("Selecionar", 11, 100, 100, 30,
                e -> selecionar());
		getContentPane().add(btArquivo, null);

		JButton btCriar = MntComponents.getJButtonTxt("Criar", 180, 100, 100, 30,
                e -> criar());
		getContentPane().add(btCriar, null);

		JButton btImportar = MntComponents.getJButtonTxt("Importar", 358, 100, 100, 30,
                e -> importar());
		getContentPane().add(btImportar, null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void selecionar() {
		FileDialog dig = new FileDialog(this, "Selecionar Arquivo", 0);
		dig.setDirectory("");
		dig.setFile("*.csv");
		dig.setFilenameFilter((dir, name) -> name.toLowerCase().endsWith(".csv"));
		dig.setVisible(true);
		String nomArq = dig.getDirectory() +
                dig.getFile();
		if ((new File(nomArq)).exists())
			arquivo.setText(nomArq);
		else
			JOptionPane.showMessageDialog(this,
					"Erro: Arquivo n\343o Encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
	}

	private void criar() {
		if ((new TratarArquivo()).criarDatabase()) {
			JOptionPane.showMessageDialog(this,
					"Banco de Dados criado sem problemas");
			verificarRegistros();
		}
	}

	private void importar() {
		if ((new File(arquivo.getText())).exists()) {
			String nomArq = arquivo.getText();
			TratarArquivo banco = new TratarArquivo();
			int linhaImp = banco.importarDados(nomArq);
			JOptionPane.showMessageDialog(this, "Foram importadas " +
                    linhaImp + " linhas.");
			verificarRegistros();
		} else {
			JOptionPane.showMessageDialog(this,
					"Erro: Arquivo n\343o Encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void verificarRegistros() {
		registro.setText("Registros: " +
                (new TratarArquivo()).totalRegistro());
	}

	public static void main(String[] args) {
		new Importar();
	}

	private final JLabel arquivo;
	private final JLabel registro;
}