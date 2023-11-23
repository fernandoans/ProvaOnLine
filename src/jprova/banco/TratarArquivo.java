package jprova.banco;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

import jprova.util.Atributo;

/**
 * Metodos de Tratamento do Arquivo
 * @author Fernando
 */
public class TratarArquivo {

	public boolean abrirDatabase() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:file:SIMPMP.db", "sa", "");
			return true;
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, (new StringBuilder("Erro: ")).append(e.getMessage()).toString(), "Erro", 0);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, (new StringBuilder("Erro: ")).append(e.getMessage()).toString(), "Erro", 0);
		}
        return false;
    }

	public boolean criarDatabase() {
		boolean ret = false;
		if (abrirDatabase()) {
			try {
				Statement stm = con.createStatement();
				stm.executeUpdate("CREATE TABLE questoes (id IDENTITY PRIMARY KEY, "
						+ "tipo CHAR(1), identificacao CHAR(6), pergunta VARCHAR, "
						+ "opcaoA VARCHAR, opcaoB VARCHAR, opcaoC VARCHAR, "
						+ "opcaoD VARCHAR, resposta VARCHAR(30), area VARCHAR(16), "
						+ "semestre int)");
				stm.close();
				ret = true;
			} catch (SQLException ex) {
				System.out.println((new StringBuilder("criarDatabase: "))
						.append(ex.getMessage()).toString());
			}
			fecharDatabase();
		}
		return ret;
	}

	public void fecharDatabase() {
		try {
			con.close();
		} catch (SQLException ex) {
			System.out.println((new StringBuilder("fecharDatabase: "))
				.append(ex.getMessage()).toString());
		}
	}

	public List<Questao> obterDados(int totalQstO, int totalQstS) {
		List<Questao> lista = new ArrayList<Questao>();
		if (abrirDatabase()) {
			try {
				int numQst = 0;
				Statement stm = con.createStatement();
				if (totalQstO > 0) {
					numQst = carregarQuestoes(stm, lista, 'O', totalQstO, numQst);
				}
				if (totalQstS > 0) {
					numQst = carregarQuestoes(stm, lista, 'S', totalQstS, numQst);
				}
				stm.close();
			} catch (Exception ex) {
				System.out.println((new StringBuilder("obterDados: "))
					.append(ex.getMessage()).toString());
			}
			fecharDatabase();
		}
		Collections.shuffle(lista);
		return lista;
	}
	
	private int carregarQuestoes(Statement stm, List<Questao> lista, char tipo, int total, int numQst) throws SQLException {
		ResultSet res = stm.executeQuery(montarSql(tipo));
		int ct = 1;
		while (res.next()) {
			if (ct++ > total) {
				break;
			}
			Questao questoes = new Questao(
				res.getString(1).charAt(0),
				res.getString(2), 
				res.getString(3),
				res.getString(4), 
				res.getString(5),
				res.getString(6), 
				res.getString(7),
				res.getString(8), 
				res.getString(9), 
				res.getInt(10), numQst+1);
			lista.add(questoes);
		}
		res.close();
		return numQst;
	}

	private String montarSql(char tipo) {
		String monta = "SELECT tipo, identificacao, pergunta, opcaoA, opcaoB, opcaoC, opcaoD, resposta, area, semestre " +
				"FROM questoes WHERE tipo = '" + tipo + "' AND semestre <= " + Atributo.semestre;
		if (Atributo.areaEsc.length() > 0) {
			monta += " AND area = '" + Atributo.areaEsc + "'";
		}
		return monta + " ORDER BY rand()";
	}

	public int totalRegistro() {
		int total = 0;
		if (abrirDatabase()) {
			try {
				Statement stm = con.createStatement();
				ResultSet res = stm
						.executeQuery("SELECT count(*) FROM questoes");
				if (res.next())
					total = res.getInt(1);
				res.close();
				stm.close();
			} catch (Exception ex) {
				System.out.println((new StringBuilder("totalRegistro: "))
						.append(ex.getMessage()).toString());
			}
			fecharDatabase();
		}
		return total;
	}

	public int importarDados(String nomArq) {
		int total = 0;
		if (abrirDatabase()) {
			try {
				FileInputStream fis = new FileInputStream(nomArq);
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader arquivo = new BufferedReader(isr);
				total = montarDados(arquivo);
				arquivo.close();
				
				Statement stm = con.createStatement();
				stm.execute("CHECKPOINT;");
				con.commit();
			} catch (Exception ex) {
				System.out.println("Erro ao Importar Dados: " + ex.getMessage());
			}
			fecharDatabase();
		}
		return total;
	}
	
	private int montarDados(BufferedReader arquivo) throws SQLException, NumberFormatException, IOException {
		int total = 0;
		PreparedStatement pstm = con.prepareStatement(
				"INSERT INTO questoes (tipo, identificacao, pergunta, " +
				"opcaoA, opcaoB, opcaoC, opcaoD, resposta, area, semestre) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		String linMnt = ""; 
		String tok = "";
		while ((linMnt = arquivo.readLine()) != null) {
			if (linMnt.charAt(0) == '%' || linMnt.charAt(1) == '%') {
				continue;
			}
		    if (linMnt.trim().length() == 0) {
				continue;
			}
			StringTokenizer strTok = new StringTokenizer(linMnt, "«");
			String tipo = strTok.nextToken();
			pstm.setString(1, tipo);  // tipo
			tok = strTok.nextToken();
			System.out.println(tok);
			pstm.setString(2, tok);  // identificacao
			tok = strTok.nextToken();
			pstm.setString(3, tok); // pergunta
			if (tipo.equals("O")) { // Questoes objetivas 
				pstm.setString(4, strTok.nextToken()); // opcaoA
				pstm.setString(5, strTok.nextToken()); // opcaoB
				pstm.setString(6, strTok.nextToken()); // opcaoC
				pstm.setString(7, strTok.nextToken()); // opcaoD
				pstm.setString(8, strTok.nextToken());  // resposta
				pstm.setString(9, strTok.nextToken());  // area
				pstm.setInt(10, Integer.parseInt(strTok.nextToken()));  // semestre
			} else { // Questoes subjetivas
				pstm.setString(4, ""); // opcaoA
				pstm.setString(5, ""); // opcaoB
				pstm.setString(6, ""); // opcaoC
				pstm.setString(7, ""); // opcaoD
				tok = strTok.nextToken();
				pstm.setString(8, tok);  // resposta
				tok = strTok.nextToken();
				pstm.setString(9, tok);  // area
				tok = strTok.nextToken();
				pstm.setInt(10, Integer.parseInt(tok));  // semestre
			}
			pstm.executeUpdate();
			total++;
		}
		pstm.close();
		return total;
	}	

	private Connection con;
}