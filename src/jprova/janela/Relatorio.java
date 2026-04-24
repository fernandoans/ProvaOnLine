package jprova.janela;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.swing.JLabel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jprova.banco.Questao;
import jprova.util.Atributo;

public class Relatorio {

	private final JLabel[] labAC;
	private final List<Questao> questoes;
	private Questao qst;
    private double notaProva = 0.0;
	
	private String nome;
	private String gasto;
	private String media;
	private String acertadas;
	private String percentual;
	private Document document;

    private final DecimalFormat FORMAT;

	public Relatorio(JLabel[] labAC, List<Questao> questoes) {
		this.labAC = labAC;
		this.questoes = questoes;
        Locale LOCAL = new Locale("pt", "BR");
		FORMAT = new DecimalFormat("##0.00", new DecimalFormatSymbols(LOCAL));
	}
	
	public void salvar(double notaProva, String nome) throws DocumentException, IOException {
		this.notaProva = notaProva;
		document = new Document(PageSize.A4, 50F, 50F, 50F, 50F);
		@SuppressWarnings("unused")
		PdfWriter writer = PdfWriter.getInstance(document,
			new FileOutputStream("Desemp_" + letras(nome) + ".pdf"));
		document.open();
		document.add(Image.getInstance(Atributo.getResource("jprova/imagens/ProvaOnLine.jpg")));

		gerarCapa();
		gerarTotais();
		gerarResumoQuestoes();

		document.close();
	}
	
	private String letras(String nome) {
		nome = nome.toUpperCase().trim();
		StringBuilder ret = new StringBuilder("" + nome.charAt(0));
		for (int i = 1; i < nome.length(); i++) {
			if (nome.charAt(i) == ' ') {
				ret.append(nome.charAt(i + 1));
			}
		}
		return ret.toString();
	}
	
	private void gerarCapa() throws DocumentException {
		document.add(new Paragraph(Atributo.titulo, FontFactory.getFont(
				"Helvetica", 22F, 1, new BaseColor(0, 83, 117))));
		document.add(new Paragraph("Aluno: " + nome, FontFactory.getFont(
				"Helvetica", 15F, 3, new BaseColor(0, 69, 98))));
		document.add(new Paragraph("Sua Nota foi: " + FORMAT.format(notaProva), FontFactory.getFont(
				"Helvetica", 15F, 3, new BaseColor(0, 69, 98))));
	}
	
	private void gerarTotais() throws DocumentException {
		document.add(new Paragraph("Desempenho", FontFactory.getFont(
				"Helvetica", 18F, 3, new BaseColor(0, 69, 98))));
		document.add(new Paragraph("De Tempo:", FontFactory.getFont(
			"Helvetica", 14F, 3, new BaseColor(190, 214, 218))));
		document.add(new Paragraph(gasto));
		document.add(new Paragraph(media));
		document.add(new Paragraph("De Quest\365es:", FontFactory.getFont(
			"Helvetica", 14F, 3, new BaseColor(190, 214, 218))));
		document.add(new Paragraph(acertadas));
		document.add(new Paragraph(percentual));
		
		document.add(new Paragraph("Por \301rea de conhecimento:",
			FontFactory.getFont("Helvetica", 14F, 3, 
				new BaseColor(190, 214, 218))));
		for (int i = 0; i < Atributo.ac.size(); i++)
			document.add(new Paragraph(labAC[i].getText()));
	}
	
	public void gerarResumoQuestoes() throws DocumentException {
		document.add(new Paragraph("Resumo das Quest\365es", FontFactory
				.getFont("Helvetica", 18F, 1, new BaseColor(0, 69, 98))));
		boolean acertou;
        for (Questao questoe : questoes) {
            qst = questoe;
            acertou = qst.isCorrigir();

            document.add(new Paragraph(
                    qst.getIdentificacao() +
                            " - " + getResultado(acertou) +
                            " - Área: " + qst.getArea()));

            String resultado;
            if (qst.getTipo() == 'O') {
				document.add(new Paragraph(getMarcouObj(acertou), getFontePadrao()));
				resultado = getResObj();
			} else if (qst.getTipo() == 'B') {
				document.add(new Paragraph(getMarcouBin(acertou), getFontePadrao()));
				resultado = getResBin();
            } else { // Tanto S como B
                document.add(new Paragraph(getMarcouSubj(), getFontePadrao()));
                resultado = qst.getResposta();
            }
            document.add(new Paragraph("Pergunta:", getFontePadrao()));
            document.add(new Paragraph(qst.getPergunta(), getFontePadrao()));
            document.add(new Paragraph("Resposta Correta:", getFontePadrao()));
            document.add(new Paragraph(resultado, getFontePadrao()));
        }
	}
	
	private Font getFontePadrao() {
		return FontFactory.getFont("Courier", 9F, 0, new BaseColor(0, 0, 0));
	}
	
	private String getResultado(boolean acertou) { 
		if (acertou)
			return "Correta";
		if (qst.getOpcaoEscolhida().isEmpty())
			return "N\343o respondida";
		return "Incorreta";
	}
	
	private String getResObj() {
        return switch (qst.getResposta().charAt(0)) {
            case 65 -> // 'A'
                    qst.getOpcaoA();
            case 66 -> // 'B'
					qst.getOpcaoB();
            case 67 -> // 'C'
					qst.getOpcaoC();
            case 68 -> // 'D'
					qst.getOpcaoD();
            case 69 -> // 'E'
					Atributo.OPCAO_E;
            case 70 -> // 'F'
					Atributo.OPCAO_F;
            default -> "";
        };
    }

	private String getResBin() {
		return switch (qst.getResposta().charAt(0)) {
			case 65 -> // 'A'
					Atributo.OPCAO_VERDADEIRO;
			case 66 -> // 'B'
					Atributo.OPCAO_FALSO;
			default -> "";
		};
	}

	private String getMarcouObj(boolean acertou) {
		if (qst.getOpcaoEscolhida().isEmpty())
			return "Você não respondeu"; 

		String ret = "Você marcou " + qst.getOpcaoEscolhida();
		if (!acertou) {
			ret += " e a opção correta é " + qst.getResposta();
		}
		return ret;
	}

	private String getMarcouBin(boolean acertou) {
		if (qst.getOpcaoEscolhida().isEmpty())
			return "Você não respondeu";

		String ret = "Você marcou " + (Objects.equals(qst.getOpcaoEscolhida(), "A")?Atributo.OPCAO_VERDADEIRO:Atributo.OPCAO_FALSO);
		if (!acertou) {
			ret += " e a opção correta é " + (Objects.equals(qst.getResposta(), "A")?Atributo.OPCAO_VERDADEIRO:Atributo.OPCAO_FALSO);
		}
		return ret;
	}

	private String getMarcouSubj() { 
		if (qst.getOpcaoEscolhida().isEmpty()) {
			return "Você não respondeu"; 
		}
		return "Você escreveu: " + qst.getOpcaoEscolhida();
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setGasto(String gasto) {
		this.gasto = gasto;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public void setAcertadas(String acertadas) {
		this.acertadas = acertadas;
	}
	public void setPercentual(String percentual) {
		this.percentual = percentual;
	}
}
