package jprova;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;

/**
 * Versao atual e metodos gerais
 * 
 * @author Fernando Anselmo
 * @version 1.02
 */
public final class Atributo {

	/** Versao atual */
	public static final String COPYRIGHT = "2023 \251 Fernando Anselmo";
	public static final String CFVERSAO = "Vers\343o 2.0";
	public static String titulo;
	public static int tempo = 14400;
	public static int totQuestaoO = -1;
	public static int totQuestaoS = -1;
	public static int semestre = -1;
	public static List<String> ac = new ArrayList<String>();
	public static String areaEsc = "";
	private static final String arqOpcao = "opcao.sim";

	public static void carAtributo() {
		List<String> opcoes = abreArquivo();
		try {
			for (Iterator<String> iterator = opcoes.iterator(); iterator.hasNext();) {
				String linha = (String) iterator.next();
				if (linha.substring(0, linha.indexOf('=')).equals("TITULO")) {
					titulo = linha.substring(linha.indexOf('=') + 1);
				} else if (linha.substring(0, linha.indexOf('=')).equals("TEMPO")) {
					linha = linha.substring(linha.indexOf('=') + 1);
					StringTokenizer tok = new StringTokenizer(linha, "*");
					int hora;
					for (hora = 1; tok.hasMoreTokens(); hora *= Integer.parseInt(tok.nextToken()));
					tempo = hora;
				} else if (linha.substring(0, linha.indexOf('=')).equals("TOTAL_QUESTAO_O")) {
					totQuestaoO = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
				} else if (linha.substring(0, linha.indexOf('=')).equals("TOTAL_QUESTAO_S")) {
					totQuestaoS = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
				} else if (linha.substring(0, linha.indexOf('=')).equals("AREA_ESCOLHIDA")) {
					areaEsc = linha.substring(linha.indexOf('=') + 1);
				} else if (linha.substring(0, linha.indexOf('=')).equals("AC")) {
					String ret = linha.substring(linha.indexOf('=') + 1);
					for (StringTokenizer tok = new StringTokenizer(ret, ";"); tok.hasMoreTokens(); ac.add(tok.nextToken()));
				} else if (linha.substring(0, linha.indexOf('=')).equals("SEMESTRE")) {
					semestre = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("N\343o existe o arquivo '" + arqOpcao + "', usando valores padr\365es.");
		}
	}

	/**
	 * Devolve um objeto ImageIcon com determinada imagem
	 * 
	 * @param s String contendo o nome da imagem
	 * @return Objeto ImageIcon
	 */
	public static ImageIcon getImage(String s) {
		URL url = getResource((new StringBuilder("jprova/imagens/")).append(s).toString());
		if (url != null)
			return new ImageIcon(url);
		else
			return null;
	}

	public static URL getResource(String s) {
		return ClassLoader.getSystemResource(s);
	}

	public static String colocaZero(int num, int tam) {
		String ret;
		for (ret = (new StringBuilder()).append(num).toString(); ret.length() < tam; ret = (new StringBuilder("0")).append(ret).toString());
		return ret;
	}

	public static String montarTam(String texto, int tam) {
		String ret = texto;
		if (ret == null) {
			ret = "";
		}
		if (ret.length() > tam + 3)
			ret = (new StringBuilder(String.valueOf(ret.substring(0, tam)))).append("...").toString();
		for (; ret.length() < tam + 3; ret = (new StringBuilder(String.valueOf(ret))).append(" ").toString());
		return ret;
	}

	public static List<String> abreArquivo() {
		List<String> lista = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(arqOpcao);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader arquivo = new BufferedReader(isr);
			for (String linMnt = ""; (linMnt = arquivo.readLine()) != null;)
				lista.add(linMnt);
			arquivo.close();
		} catch (IOException e) {
			System.out.println((new StringBuilder(
				"Erro no arquivo '" + arqOpcao + "': ")).append(e.getMessage()).toString());
		}
		return lista;
	}

}