package jprova.banco;

import jprova.util.Atributo;

/**
 * Base de Questoes
 * 
 * @author Fernando
 */
public final class Questao {

	public Questao(char tipo, String identificacao, String pergunta, String opcaoA,
			String opcaoB, String opcaoC, String opcaoD, String resposta,
			String area, int semestre, int numQuestao) {
		setTipo(tipo);
		setIdentificacao(identificacao);
		pergunta = pergunta.replaceAll("%SL% ", ""+'\n');
		pergunta = pergunta.replaceAll("%TB% ", "    ");
		setPergunta(pergunta);
		if (tipo == 'O') {
			setOpcaoA(opcaoA);
			setOpcaoB(opcaoB);
			setOpcaoC(opcaoC);
			setOpcaoD(opcaoD);
			setOpcaoE("Todas as questões acima.");
			setOpcaoF("Nenhuma das questões acima.");
		} else if (tipo == 'B') {
			setOpcaoA(opcaoA);
			setOpcaoB(opcaoB);
		}
		setResposta(resposta);
		setArea(area);
		setSemestre(semestre);
		setNumQuestao(numQuestao);
		setOpcaoEscolhida("");
	}

	public void setNumQuestao(int numQuestao) {
		this.numQuestao = numQuestao;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public String getOpcaoA() {
		return opcaoA;
	}

	public void setOpcaoA(String opcaoA) {
		this.opcaoA = opcaoA;
	}

	public String getOpcaoB() {
		return opcaoB;
	}

	public void setOpcaoB(String opcaoB) {
		this.opcaoB = opcaoB;
	}

	public String getOpcaoC() {
		return opcaoC;
	}

	public void setOpcaoC(String opcaoC) {
		this.opcaoC = opcaoC;
	}

	public String getOpcaoD() {
		return opcaoD;
	}

	public void setOpcaoD(String opcaoD) {
		this.opcaoD = opcaoD;
	}

	public void setOpcaoE(String opcaoE) {
		this.opcaoE = opcaoE;
    }

	public void setOpcaoF(String opcaoF) {
		this.opcaoF = opcaoF;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public String getOpcaoEscolhida() {
		return opcaoEscolhida;
	}

	public void setOpcaoEscolhida(String opcaoEscolhida) {
		this.opcaoEscolhida = opcaoEscolhida;
	}

	public boolean isMarcar() {
		return marcar;
	}

	public void setMarcar(boolean marcar) {
		this.marcar = marcar;
	}

	public boolean isCorrigir() {
		return resposta.equalsIgnoreCase(opcaoEscolhida);
	}
	
	public double getValorQst() {
		if (tipo == 'O') {
			return ((double)Atributo.notaObj) / Atributo.totQuestaoO; 
		}
		if (tipo == 'B') {
			return ((double)Atributo.notaBin) / Atributo.totQuestaoB;
		}
		return ((double)Atributo.notaSub) / Atributo.totQuestaoS;
	}

	public String toString() {
		String opcEsc = opcaoEscolhida;
		if (tipo == 'B') {
			opcEsc = (opcaoEscolhida.equals("A"))?Atributo.OPCAO_VERDADEIRO:Atributo.OPCAO_FALSO;
		}
		return "  " + Atributo.colocaZero(numQuestao, 3) +
			"    " + Atributo.montarTam(pergunta, 36) +
			"    " + Atributo.montarTam(opcEsc, 44) +
			"    " + (marcar ? "Sim" : "N\343o");
	}

	private int numQuestao;
	private char tipo;
	private String identificacao;
	private String pergunta;
	private String opcaoA;
	private String opcaoB;
	private String opcaoC;
	private String opcaoD;
	private String opcaoE;
    private String opcaoF;
	private String resposta;
	private String area;
	private int semestre;
	private String opcaoEscolhida;
	private boolean marcar;
}
