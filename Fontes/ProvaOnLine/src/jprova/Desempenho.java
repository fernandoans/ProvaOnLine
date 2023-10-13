package jprova;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.Image;
import java.io.FileOutputStream;
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
		labAC = new JLabel[Atributo.ac.size()];
		LOCAL = new Locale("pt", "BR");
		format = new DecimalFormat("##0.00", new DecimalFormatSymbols(LOCAL));
		aAC = new int[Atributo.ac.size()];
		tAC = new int[Atributo.ac.size()];
		this.questoes = questoes;
		this.totalTempo = totalTempo;
		computarAcertos();
		mostrar();
	}

	public final void mostrar() {
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(238, 238, 238));
		setSize(600, 380);
		setLocationRelativeTo(null);
		setTitle("Desempenho");
		setResizable(false);
		setModal(true);
		Tempo tempo = new Tempo();
		objeto0 = new JLabel("De Tempo:");
		objeto0.setBounds(new java.awt.Rectangle(10, 10, 80, 13));
		getContentPane().add(objeto0, null);
		tempo.setResHora(Atributo.tempo - totalTempo);
		labGasto = new JLabel((new StringBuilder("Gasto: ")).append(
			tempo.transHora()).toString());
		labGasto.setBounds(new java.awt.Rectangle(10, 30, 240, 13));
		getContentPane().add(labGasto, null);
		tempo.setResHora(totalFeitos != 0 ? tempo.getResHora() / totalFeitos : 0);
		labMedia = new JLabel((new StringBuilder(
			"M\351dia gasta por quest\343o: ")).append(tempo.transHora())
			.toString());
		labMedia.setBounds(new java.awt.Rectangle(10, 47, 350, 13));
		getContentPane().add(labMedia, null);
		objeto2 = new JLabel("De Quest\365es:");
		objeto2.setBounds(new java.awt.Rectangle(10, 80, 100, 13));
		getContentPane().add(objeto2, null);
		labAcertadas = new JLabel((new StringBuilder("Quantidade Acertadas: "))
			.append(acerto).append(" de ").append(questoes.size())
			.toString());
		labAcertadas.setBounds(new java.awt.Rectangle(10, 105, 400, 13));
		getContentPane().add(labAcertadas, null);
		labPercentual = new JLabel((new StringBuilder("Percentual Acertado: "))
			.append(mstPercentual(acerto, questoes.size())).append(" %")
			.toString());
		labPercentual.setBounds(new java.awt.Rectangle(10, 120, 400, 13));
		getContentPane().add(labPercentual, null);
		objeto4 = new JLabel("Por \301rea de conhecimento:");
		objeto4.setBounds(new java.awt.Rectangle(10, 150, 220, 13));
		getContentPane().add(objeto4, null);
		int posTop = 175;
		for (int i = 0; i < Atributo.ac.size(); i++) {
			labAC[i] = new JLabel((new StringBuilder(
				String.valueOf((String) Atributo.ac.get(i)))).append(": ")
				.append(aAC[i]).append(" de ").append(tAC[i]).append(" (")
				.append(mstPercentual(aAC[i], tAC[i])).append(" %)")
				.toString());
			labAC[i].setBounds(new java.awt.Rectangle((i % 2 == 0)?10:300, posTop, 280, 13));
			getContentPane().add(labAC[i], null);
			if (i % 2 != 0) posTop += 15;
		}

		labNome = new JLabel("Seu Nome:");
		labNome.setBounds(new java.awt.Rectangle(10, 320, 80, 30));
		getContentPane().add(labNome, null);
		edtNome = new JTextField("");
		edtNome.setBounds(new java.awt.Rectangle(90, 320, 300, 30));
		getContentPane().add(edtNome, null);
		
		btSalvar = new JButton("Salvar");
		btSalvar.setBounds(new java.awt.Rectangle(490, 320, 100, 30));
		getContentPane().add(btSalvar, null);
		btSalvar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				salvar();
			}
		});
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
		if (val2 == 0)
			return "0,00";
		else
			return format.format(((double) val1 * 100D) / (double) val2)
					.toString();
	}

	private void salvar() {
		Document document = new Document(PageSize.A4, 50F, 50F, 50F, 50F);
		try {
			@SuppressWarnings("unused")
			PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("desempenho.pdf"));
			document.open();
			document.add(Image.getInstance(Atributo.getResource("jprova/imagens/ProvaOnLine.jpg")));
			document.add(new Paragraph(Atributo.titulo, FontFactory.getFont(
				"Helvetica", 22F, 1, new BaseColor(0, 83, 117))));
			document.add(new Paragraph("Aluno: " + edtNome.getText(), FontFactory.getFont(
					"Helvetica", 15F, 3, new BaseColor(0, 69, 98))));
			document.add(new Paragraph("Desempenho", FontFactory.getFont(
				"Helvetica", 18F, 3, new BaseColor(0, 69, 98))));
			document.add(new Paragraph("De Tempo:", FontFactory.getFont(
				"Helvetica", 14F, 3, new BaseColor(190, 214, 218))));
			document.add(new Paragraph(labGasto.getText()));
			document.add(new Paragraph(labMedia.getText()));
			document.add(new Paragraph("De Quest\365es:", FontFactory.getFont(
				"Helvetica", 14F, 3, new BaseColor(190, 214, 218))));
			document.add(new Paragraph(labAcertadas.getText()));
			document.add(new Paragraph(labPercentual.getText()));

			document.add(new Paragraph("Por \301rea de conhecimento:",
				FontFactory.getFont("Helvetica", 14F, 3, 
					new BaseColor(190, 214, 218))));
			for (int i = 0; i < Atributo.ac.size(); i++)
				document.add(new Paragraph(labAC[i].getText()));

			document.add(new Paragraph("Resumo das Quest\365es", FontFactory
				.getFont("Helvetica", 18F, 1, new BaseColor(0, 69, 98))));
			String resultado = "";
			boolean acertou = false;
			for (Iterator<Questao> iterator = questoes.iterator(); iterator.hasNext();) {
				Questao qst = (Questao) iterator.next();
				acertou = qst.isCorrigir();
				if (acertou)
					resultado = "Correta";
				else if (qst.getOpcaoEscolhida().length() == 0)
					resultado = "N\343o respondida";
				else
					resultado = "Incorreta";
				
				document.add(new Paragraph(
					qst.getIdentificacao() + 
					" - " + resultado + 
					" - Área: " + qst.getArea())); 

				if (qst.getTipo() == 'O') {
					String marcou;
					if (qst.getOpcaoEscolhida().length() == 0) {
						marcou = "Você não respondeu"; 
					} else {
						marcou = "Você marcou " + qst.getOpcaoEscolhida();
						if (!acertou) {
							marcou += " e a opção correta é " + qst.getResposta();
						}
					}
					document.add(new Paragraph(marcou,
							FontFactory.getFont("Courier", 9F, 0, new BaseColor(0, 0, 0))));
					switch (qst.getResposta().charAt(0)) {
					case 65: // 'A'
						resultado = qst.getOpcaoA();
						break;
					case 66: // 'B'
						resultado = qst.getOpcaoB();
						break;
					case 67: // 'C'
						resultado = qst.getOpcaoC();
						break;
					case 68: // 'D'
						resultado = qst.getOpcaoD();
						break;
					case 69: // 'E'
						resultado = "Todas as questões acima.";
						break;
					case 70: // 'F'
						resultado = "Nenhuma das questões acima.";
						break;
					}
				}
				if (qst.getTipo() == 'S') {
					String marcou;
					if (qst.getOpcaoEscolhida().length() == 0) {
						marcou = "Você não respondeu"; 
					} else {
						marcou = "Você escreveu: " + qst.getOpcaoEscolhida();
					}
					document.add(new Paragraph(marcou,
							FontFactory.getFont("Courier", 9F, 0, new BaseColor(0, 0, 0))));
					resultado = qst.getResposta();
				}
				document.add(new Paragraph("Pergunta:", 
					FontFactory.getFont("Courier", 9F, 0, new BaseColor(0, 0, 0))));
				document.add(new Paragraph(qst.getPergunta(), 
						FontFactory.getFont("Courier", 9F, 0, new BaseColor(0, 0, 0))));
				document.add(new Paragraph("Resposta Correta:", 
						FontFactory.getFont("Courier", 9F, 0, new BaseColor(0, 0, 0))));
				document.add(new Paragraph(resultado, 
						FontFactory.getFont("Courier", 9F, 0, new BaseColor(0, 0, 0))));
			}
			document.close();
			JOptionPane.showMessageDialog(this,
				"O arquivo 'desempenho.pdf' foi salvo corretamente.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
				"Erro na Gera\347\343o do Arquivo de Desempenho", "Erro", 0);
		}
	}

	private void computarAcertos() {
		totalFeitos = 0;
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
			}
		}
	}

	private JLabel labNome;
	private JTextField edtNome;

	private JLabel objeto0;
	private JLabel labGasto;
	private JLabel objeto2;
	private JLabel labAcertadas;
	private JLabel objeto4;
	private JLabel labMedia;
	private JLabel labPercentual;
	private JButton btSalvar;
	private JLabel labAC[];
	private List<Questao> questoes;
	private int totalTempo;
	private int totalFeitos;
	private Locale LOCAL;
	private DecimalFormat format;
	private int acerto;
	private int aAC[];
	private int tAC[];
}