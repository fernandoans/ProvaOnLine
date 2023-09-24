package jprova;

/**
 * Base de Questoes
 * 
 * @author Fernando
 */
public final class Questao {

	public Questao(String identificacao, String pergunta, String opcaoA,
			String opcaoB, String opcaoC, String opcaoD, char resposta,
			String area, int semestre, int numQuestao) {
		setIdentificacao(identificacao);
		setPergunta(pergunta);
		setOpcaoA(opcaoA);
		setOpcaoB(opcaoB);
		setOpcaoC(opcaoC);
		setOpcaoD(opcaoD);
		setResposta(resposta);
		setArea(area);
		setSemestre(semestre);
		setNumQuestao(numQuestao);
	}

	public int getNumQuestao() {
		return numQuestao;
	}

	public void setNumQuestao(int numQuestao) {
		this.numQuestao = numQuestao;
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

	public char getResposta() {
		return resposta;
	}

	public void setResposta(char resposta) {
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

	public char getOpcaoEscolhida() {
		return opcaoEscolhida;
	}

	public void setOpcaoEscolhida(char opcaoEscolhida) {
		this.opcaoEscolhida = opcaoEscolhida;
	}

	public boolean isMarcar() {
		return marcar;
	}

	public void setMarcar(boolean marcar) {
		this.marcar = marcar;
	}

	public boolean isCorrigir() {
		return resposta == opcaoEscolhida;
	}

	public String toString() {
		String opcao = "";
		switch (opcaoEscolhida) {
		case 65: // 'A'
			opcao = opcaoA;
			break;
		case 66: // 'B'
			opcao = opcaoB;
			break;
		case 67: // 'C'
			opcao = opcaoC;
			break;
		case 68: // 'D'
			opcao = opcaoD;
			break;
		case 69: // 'E'
			opcao = opcaoE;
			break;
		case 70: // 'F'
			opcao = opcaoF;
			break;
		}
		return(new StringBuilder("  "))
			.append(Atributo.colocaZero(numQuestao, 3)).append("    ")
			.append(Atributo.montarTam(pergunta, 36)).append("    ")
			.append(Atributo.montarTam(opcao, 44)).append("    ")
			.append(marcar ? "Sim" : "N\343o").toString();
	}

	private int numQuestao;
	private String identificacao;
	private String pergunta;
	private String opcaoA;
	private String opcaoB;
	private String opcaoC;
	private String opcaoD;
	private String opcaoE;
	private String opcaoF;
	private char resposta;
	private String area;
	private int semestre;
	private char opcaoEscolhida;
	private boolean marcar;
}
