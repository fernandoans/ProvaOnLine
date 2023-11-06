package jprova.janela;

import jprova.banco.Questao;
import jprova.util.Atributo;
import jprova.util.Tempo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.*;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Mostrar o Desempenho
 * @author Fernando
 */
@SuppressWarnings("serial")
public class Desempenho extends JDialog {

	public Desempenho(List<Questao> questoes, int totalTempo) {
		LOCAL = new Locale("pt", "BR");
		FORMAT = new DecimalFormat("##0.00", new DecimalFormatSymbols(LOCAL));

		labAC = new JLabel[Atributo.ac.size()];
		aAC = new int[Atributo.ac.size()];
		tAC = new int[Atributo.ac.size()];
		this.questoes = questoes;
		this.totalTempo = totalTempo;
		computarAcertos();
		mostrar();
	}

	public final void mostrar() {
		setTitle("Desempenho");
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(238, 238, 238));
		setSize(600, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		Tempo tempo = new Tempo();
		
		getContentPane().add(MntComponents.getJLabel("De Tempo:", 10, 10, 80, 13), null);
		tempo.setResHora(Atributo.tempo - totalTempo);

		labGasto = MntComponents.getJLabel("Gasto: " + tempo.transHora(), 
			10, 30, 240, 13);
		getContentPane().add(labGasto, null);
		tempo.setResHora(totalFeitos != 0 ? tempo.getResHora() / totalFeitos : 0);

		labMedia = MntComponents.getJLabel("M\351dia gasta por quest\343o: " + tempo.transHora(), 
			10, 47, 350, 13);
		getContentPane().add(labMedia, null);

		getContentPane().add(MntComponents.getJLabel("De Quest\365es:", 
			10, 80, 100, 13), null);

		labAcertadas = MntComponents.getJLabel(
			"Quantidade Acertadas: " + acerto + " de " + questoes.size(), 
			10, 105, 400, 13);
		getContentPane().add(labAcertadas, null);
		
		labPercentual = MntComponents.getJLabel(
			"Percentual Acertado: " + mstPercentual(acerto, questoes.size()) + " %", 
			10, 120, 400, 13);
		getContentPane().add(labPercentual, null);

		getContentPane().add(MntComponents.getJLabel("Por \301rea de conhecimento:", 10, 150, 220, 13), null);
		int posTop = 175;
		for (int i = 0; i < Atributo.ac.size(); i++) {
			labAC[i] = MntComponents.getJLabel((new StringBuilder(
				String.valueOf((String) Atributo.ac.get(i)))).append(": ")
				.append(aAC[i]).append(" de ").append(tAC[i]).append(" (")
				.append(mstPercentual(aAC[i], tAC[i])).append(" %)")
				.toString(), (i % 2 == 0)?10:300, posTop, 280, 13);
			getContentPane().add(labAC[i], null);
			if (i % 2 != 0) posTop += 15;
		}
		getContentPane().add(MntComponents.getJLabel("Sua Nota foi: " + FORMAT.format(notaProva), 230, 150, 400, 13), null);
		
		getContentPane().add(MntComponents.getJLabel("Seu Nome:", 10, 320, 80, 30), null);
		edtNome = new JTextField("");
		edtNome.setBounds(new java.awt.Rectangle(90, 320, 300, 30));
		getContentPane().add(edtNome, null);
		
		btSalvar = MntComponents.getJButtonTxt("Salvar", 490, 320, 100, 30, 
			new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					salvar();
				}
			});
		getContentPane().add(btSalvar, null);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				aoFechar();
			}
		});
		setVisible(true);
	}

	private void aoFechar() {
		dispose();
	}

	private String mstPercentual(int val1, int val2) {
		return (val2 == 0)?"0,00":
			FORMAT.format(((double) val1 * 100D) / (double) val2).toString();
	}

	private void salvar() {
		try {
			Relatorio relatorio = new Relatorio(labAC, questoes);
			relatorio.setNome(edtNome.getText());
			relatorio.setGasto(labGasto.getText());
			relatorio.setMedia(labMedia.getText());
			relatorio.setAcertadas(labAcertadas.getText());
			relatorio.setPercentual(labPercentual.getText());
			relatorio.salvar(notaProva);
			JOptionPane.showMessageDialog(this,
				"O arquivo 'desempenho.pdf' foi salvo corretamente.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
				"Erro na Gera\347\343o do Arquivo de Desempenho", "Erro", 0);
		}
	}

	private void computarAcertos() {
		totalFeitos = 0;
		notaProva = 0.0;
		for (Iterator<Questao> iterator = questoes.iterator(); iterator.hasNext();) {
			Questao qst = (Questao) iterator.next();
			if (qst.getOpcaoEscolhida().length() > 0)
				totalFeitos++;
			for (int i = 0; i < Atributo.ac.size(); i++) {
				if (!qst.getArea().equals(Atributo.ac.get(i)))
					continue;
				tAC[i]++;
				break;
			}
			if (qst.isCorrigir()) {
				for (int i = 0; i < Atributo.ac.size(); i++) {
					if (!qst.getArea().equals(Atributo.ac.get(i)))
						continue;
					aAC[i]++;
					break;
				}
				acerto++;
				notaProva += qst.getValorQst();
			}
		}
	}

	private final Locale LOCAL;
	private final DecimalFormat FORMAT;
	
	private double notaProva = 0.0;
	private JTextField edtNome;
	private JLabel labGasto;
	private JLabel labAcertadas;
	private JLabel labMedia;
	private JLabel labPercentual;
	private JButton btSalvar;
	private JLabel labAC[];
	private List<Questao> questoes;
	private int totalTempo;
	private int totalFeitos;
	private int acerto;
	private int aAC[];
	private int tAC[];
}