package jprova.util;

/**
 * Verifica o tempo
 * 
 * @author Fernando
 */
public class Tempo {

	public Tempo() {
		intHora = 0;
    }

	public Tempo(int intHora) {
		resHora = 0;
		this.intHora = intHora;
	}

	public boolean isMaiorZero() {
		return intHora > 0;
	}

	public void reduz() {
		intHora--;
		setResHora(intHora);
	}

	public int getIntHora() {
		return intHora;
	}

	public void setResHora(int resHora) {
		this.resHora = resHora;
	}

	public int getResHora() {
		return resHora;
	}

	public String transHora() {
		int horHora = 0;
		int minHora = 0;
		int segHora = 0;
		if (resHora > 0) {
			horHora = resHora / 60 / 60;
			resHora -= horHora * 60 * 60;
			if (resHora > 0) {
				minHora = resHora / 60;
				segHora = resHora - minHora * 60;
			}
		}
		return Atributo.colocaZero(horHora, 2) +
                ":" + Atributo.colocaZero(minHora, 2) +
                ":" + Atributo.colocaZero(segHora, 2);
	}

	private int intHora;
	private int resHora;
}