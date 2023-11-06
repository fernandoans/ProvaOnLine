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
		setPergunta(pergunta);
		if (tipo == 'O') {
			setOpcaoA(opcaoA);
			setOpcaoB(opcaoB);
			setOpcaoC(opcaoC);
			setOpcaoD(opcaoD);
			setOpcaoE("Todas as questões acima.");
			setOpcaoF("Nenhuma das questões acima.");
		}
		setResposta(resposta);
		setArea(area);
		setSemestre(semestre);
		setNumQuestao(numQuestao);
		setOpcaoEscolhida("");
	}

	public int getNumQuestao() {
		return numQuestao;
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

	public String getOpcaoE() {
		return opcaoE;
	}

	public void setOpcaoE(String opcaoE) {
		this.opcaoE = opcaoE;
	}

	public String getOpcaoF() {
		return opcaoF;
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
		return ((double)Atributo.notaSub) / Atributo.totQuestaoS; 
	}

	public String toString() {
		return(new StringBuilder("  "))
			.append(Atributo.colocaZero(numQuestao, 3)).append("    ")
			.append(Atributo.montarTam(pergunta, 36)).append("    ")
			.append(Atributo.montarTam(opcaoEscolhida, 44)).append("    ")
			.append(marcar ? "Sim" : "N\343o").toString();
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
