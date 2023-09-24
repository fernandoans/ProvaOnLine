package jprova.banco;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

import jprova.Atributo;
import jprova.Questao;

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
				stm.executeUpdate("CREATE TABLE questoes (id IDENTITY PRIMARY KEY, identificacao CHAR(6), pergunta VARCHAR, opcaoA VARCHAR, opcaoB VARCHAR, opcaoC VARCHAR, opcaoD VARCHAR, resposta CHAR(1), area VARCHAR(16), semestre int)");
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

	public List<Questao> obterDados(int totalQst) {
		List<Questao> lista = new ArrayList<Questao>();
		if (abrirDatabase()) {
			try {
				Statement stm = con.createStatement();
				ResultSet res = stm.executeQuery(montaSql());
				int addQst = 0;
				while (res.next()) {
					Questao questoes = new Questao(res.getString(1),
						res.getString(2), res.getString(3),
						res.getString(4), res.getString(5),
						res.getString(6), res.getString(7).charAt(0),
						res.getString(8), res.getInt(9), addQst+1);
					lista.add(questoes);
					if (totalQst > -1 && ++addQst == totalQst)
						break;
				}
				res.close();
				stm.close();
			} catch (Exception ex) {
				System.out.println((new StringBuilder("obterDados: "))
					.append(ex.getMessage()).toString());
			}
			fecharDatabase();
		}
		return lista;
	}

	private String montaSql() {
		String monta = "SELECT identificacao, pergunta, opcaoA, opcaoB, opcaoC, opcaoD, resposta, area, semestre " +
				"FROM questoes WHERE semestre <= " + Atributo.semestre;
		if (Atributo.areaEsc.length() > 0) {
			monta = (new StringBuilder(String.valueOf(monta)))
				.append(" AND area = '").append(Atributo.areaEsc).append("'")
				.toString();
		}
		monta = (new StringBuilder(String.valueOf(monta))).append(" ORDER BY rand()").toString();
		return monta;
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
				PreparedStatement pstm = con.prepareStatement(
					"INSERT INTO questoes (identificacao, pergunta, opcaoA, opcaoB, opcaoC, opcaoD, resposta, area, semestre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				String linMnt = "";
				int num = 0;
				FileInputStream fis = new FileInputStream(nomArq);
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader arquivo = new BufferedReader(isr);
				while ((linMnt = arquivo.readLine()) != null)
					if (linMnt.charAt(0) != '%' && linMnt.trim().length() > 0) {
						StringTokenizer strTok = new StringTokenizer(linMnt, "«");
						num = strTok.countTokens();
						if (num > 0)
							pstm.setString(1, strTok.nextToken());
						if (num > 1)
							pstm.setString(2, strTok.nextToken());
						if (num > 2)
							pstm.setString(3, strTok.nextToken());
						if (num > 3)
							pstm.setString(4, strTok.nextToken());
						if (num > 4)
							pstm.setString(5, strTok.nextToken());
						if (num > 5)
							pstm.setString(6, strTok.nextToken());
						if (num > 6)
							pstm.setString(7, strTok.nextToken());
						if (num > 7)
							pstm.setString(8, strTok.nextToken());
						if (num > 8)
							pstm.setInt(9, Integer.parseInt(strTok.nextToken()));
						pstm.executeUpdate();
						total++;
					}
				arquivo.close();
				pstm.close();
				Statement stm = con.createStatement();
				stm.execute("CHECKPOINT;");
				con.commit();
			} catch (Exception ex) {
				System.out.println((new StringBuilder("importarDados: "))
						.append(ex.getMessage()).toString());
			}
			fecharDatabase();
		}
		return total;
	}

	private Connection con;
}