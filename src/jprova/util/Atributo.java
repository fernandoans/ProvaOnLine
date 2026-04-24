package jprova.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

	// Autor e Versao atual
	public static final String COPYRIGHT = "2026 \251 Fernando Anselmo";
	public static final String CFVERSAO = "Vers\343o 2.6";
	public static final String OPCAO_E = "Todas as questões acima.";
	public static final String OPCAO_F = "Nenhuma das questões acima.";
	public static final String OPCAO_VERDADEIRO = "Verdadeiro";
	public static final String OPCAO_FALSO = "Falso";

	// Título da Prova 
	public static String titulo;
	// Tempo em milisegundos 
	public static int tempo = 14400;
	// Qtd questões objetivas 
	public static int totQuestaoO = -1;
	// Qtd questões subjetivas 
	public static int totQuestaoS = -1;
	// Qtd questões binárias
	public static int totQuestaoB = -1;
	// Qual semestre (ex. semestre 2 abrange tb o 1)
	public static int semestre = -1;
	// Valor da nota das objetivas
	public static int notaObj = 0;
	// Valor da nota das subjetivas
	public static int notaSub = 0;
	// Valor da nota das binárias
	public static int notaBin = 0;
	// Verifica qtd processos antes e depos se o atributo for maior que 0
	public static boolean veFraude = false;
	public static List<String> ac = new ArrayList<>();
	public static String areaEsc = "";
	private static final String arqOpcao = "opcao.sim";

	public static void carAtributo() {
		List<String> opcoes = abreArquivo();
		try {
            for (String linha : opcoes) {
                switch (linha.substring(0, linha.indexOf('='))) {
                    case "TITULO" -> titulo = linha.substring(linha.indexOf('=') + 1);
                    case "TEMPO" -> {
                        linha = linha.substring(linha.indexOf('=') + 1);
                        StringTokenizer tok = new StringTokenizer(linha, "*");
                        int hora;
                        for (hora = 1; tok.hasMoreTokens(); hora *= Integer.parseInt(tok.nextToken())) ;
                        tempo = hora;
                    }
                    case "TOTAL_QUESTAO_O" -> totQuestaoO = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
                    case "TOTAL_QUESTAO_S" -> totQuestaoS = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
                    case "TOTAL_QUESTAO_B" -> totQuestaoB = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
                    case "AREA_ESCOLHIDA" -> areaEsc = linha.substring(linha.indexOf('=') + 1);
                    case "AC" -> {
                        String ret = linha.substring(linha.indexOf('=') + 1);
                        for (StringTokenizer tok = new StringTokenizer(ret, ";"); tok.hasMoreTokens(); ac.add(tok.nextToken()))
                            ;
                    }
                    case "SEMESTRE" -> semestre = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
                    case "NOTA_OBJ" -> notaObj = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
                    case "NOTA_SUB" -> notaSub = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
					case "NOTA_BIN" -> notaBin = Integer.parseInt(linha.substring(linha.indexOf('=') + 1));
                    case "VE_FRAUDE" -> veFraude = Integer.parseInt(linha.substring(linha.indexOf('=') + 1)) > 0;
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
		URL url = getResource("jprova/imagens/" + s);
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
		for (ret = String.valueOf(num); ret.length() < tam; ret = "0" + ret);
		return ret;
	}

	public static String montarTam(String texto, int tam) {
		String ret = texto;
		if (ret == null) {
			ret = "";
		}
		if (ret.length() > tam + 3)
			ret = ret.substring(0, tam) + "...";
		for (; ret.length() < tam + 3; ret += " ");
		return ret;
	}

	public static List<String> abreArquivo() {
		List<String> lista = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(arqOpcao);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader arquivo = new BufferedReader(isr);
			for (String linMnt; (linMnt = arquivo.readLine()) != null;)
				lista.add(linMnt);
			arquivo.close();
		} catch (IOException e) {
			System.out.println("Erro no arquivo '" + arqOpcao + "': " +
                    e.getMessage());
		}
		return lista;
	}

}