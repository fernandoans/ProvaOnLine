package jprova;

// Importa os pacotes necessarios
import java.util.List;
import java.awt.event.*;
import javax.swing.*;

import jprova.banco.Questao;
import jprova.banco.TratarArquivo;
import jprova.janela.Desempenho;
import jprova.janela.MntComponents;
import jprova.janela.Resumo;
import jprova.janela.SobreSistema;
import jprova.util.Atributo;
import jprova.util.Tempo;
import jprova.util.VerProcs;

import java.awt.Rectangle;
import java.awt.Font;

/**
 * Inicial Projeto..: Tela Inicial
 * 
 * @author Fernando Anselmo © Jun - 2011
 * @version 1.0
 */
public class Inicial extends JFrame implements Runnable {

	public Inicial() {
		super(Atributo.titulo + " - " + Atributo.CFVERSAO);
		th = new Thread(this);
		qstAtual = 1;
		parar = true;
		tempo = new Tempo(Atributo.tempo);
		questoes = (new TratarArquivo()).obterDados(
			Atributo.totQuestaoO, Atributo.totQuestaoS, Atributo.totQuestaoB);
		qstTotal = questoes.size();
		mostrar();
	}

	public final void mostrar() {
		getContentPane().setLayout(null);
		setSize(870, 630);
		setLocationRelativeTo(null);
		setResizable(false);

		// Titulo - Parte 1
		labQuestao = MntComponents.getJLabel("Quest\343o 999 de 200", 10, 10, 140, 21);
		getContentPane().add(labQuestao, null);

        JButton imgAnt = MntComponents.getJButtonImg("back.gif", 23, 35, 40, 40,
                e -> antQuestao());
		getContentPane().add(imgAnt, null);
		getContentPane().add(MntComponents.getJLabel("Anterior", 20, 80, 60, 13), null);

        JButton imgProx = MntComponents.getJButtonImg("forward.gif", 95, 35, 40, 40,
                e -> prxQuestao());
		getContentPane().add(imgProx, null);
		getContentPane().add(MntComponents.getJLabel("Pr\363ximo", 90, 80, 57, 13), null);

		// Titulo - Parte 2
		getContentPane().add(MntComponents.getJLabel("Ir Para:", 160, 49, 57, 13), null);
		edtIrQuestao = MntComponents.getJTextField(215, 46, 50, 21);
		getContentPane().add(edtIrQuestao, null);
		edtIrQuestao.addActionListener(e -> pularQuestao());

		chkMarcar = new JCheckBox("Marcar para revis\343o");
		chkMarcar.setBounds(new Rectangle(370, 40, 200, 30));
		getContentPane().add(chkMarcar, null);
		chkMarcar.addActionListener(e -> marcarQuestao());

		// Titulo - Parte 3
		labTempo = MntComponents.getJLabel("Tempo Transcorrido: HH:MM:SS", 600, 10, 250, 21);
		getContentPane().add(labTempo, null);

        JButton butResumo = MntComponents.getJButtonTxt("Resumo", 730, 40, 100, 30,
                e -> mostrarResumo());
		getContentPane().add(butResumo, null);

		// Area da Pergunta
		getContentPane().add(MntComponents.getJLabel("Pergunta:", 10, 110, 90, 30), null);
		txaPerg = MntComponents.getJTextArea();
		txaPerg.setFont(new Font(FONTE, Font.PLAIN, 18));
		scrPerg = MntComponents.getJScrollPane(txaPerg, 110, 110, 720, 100);
		getContentPane().add(scrPerg, null);

		// Area das Respostas
		pnObjetivas = MntComponents.getJPanel(10, 220, 820, 300);
		montaObjetivas();
		getContentPane().add(pnObjetivas, null);

		pnSubjetivas = MntComponents.getJPanel(10, 450, 820, 50);
		montaSubjetivas();
		getContentPane().add(pnSubjetivas, null);

        pnBinarias = MntComponents.getJPanel(10, 420, 820, 100);
		montaBinarias();
		getContentPane().add(pnBinarias, null);

		// Rodapé
        JButton butFinalizar = MntComponents.getJButtonTxt("Finalizar", 730, 540, 100, 30,
                e -> finalizar());
		getContentPane().add(butFinalizar, null);

		labProcs = MntComponents.getJLabel("", 10, 540, 250, 30);
		getContentPane().add(labProcs, null);
		
		getContentPane().add(MntComponents.getJLabel(Atributo.COPYRIGHT, 320, 540, 300, 30), null);

		// Ações a realizar
		mostrarQuestao();
		if (VerProcs.SO == 0) {
			String inicial = System.getenv("windir") + "\\system32\\taskkill /f /im ";
			try {
				Runtime.getRuntime().exec(inicial + "explorer.exe");
			} catch (Exception ignored) {
			}
		}
		th.start();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finalizar();
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private void montaSubjetivas() {
		pnSubjetivas.add(MntComponents.getJLabel("Resposta:", 0, 0, 90, 30), null);
		txSubjetiva = MntComponents.getJTextField(100, 0, 720, 50);
		txSubjetiva.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				marcarSubjetiva();
			}
		});
		pnSubjetivas.add(txSubjetiva, null);
	}

	private void montaBinarias() {
		// Botões de Respostas
		opcoes = new ButtonGroup();
		radOpcA = MntComponents.getJRadioButton(Atributo.OPCAO_VERDADEIRO, 0, 0, 200, 30,
				e -> marcarOpcao("A"));
		radOpcA.setFont(new Font(FONTE, Font.BOLD, 16));
		opcoes.add(radOpcA);
		pnBinarias.add(radOpcA, null);

		radOpcB = MntComponents.getJRadioButton(Atributo.OPCAO_FALSO, 0, 60, 200, 30,
				e -> marcarOpcao("B"));
		radOpcB.setFont(new Font(FONTE, Font.BOLD, 16));
		opcoes.add(radOpcB);
		pnBinarias.add(radOpcB, null);

	}

	private void montaObjetivas() {
		txaOpcA = MntComponents.getJTextArea();
		txaOpcA.setFont(new Font(FONTE, Font.BOLD, 16));
		pnObjetivas.add(MntComponents.getJScrollPane(txaOpcA, 100, 0, 720, 50), null);

		txaOpcB = MntComponents.getJTextArea();
		txaOpcB.setFont(new Font(FONTE, Font.BOLD, 16));
		pnObjetivas.add(MntComponents.getJScrollPane(txaOpcB, 100, 60, 720, 50), null);

		txaOpcC = MntComponents.getJTextArea();
		txaOpcC.setFont(new Font(FONTE, Font.BOLD, 16));
		pnObjetivas.add(MntComponents.getJScrollPane(txaOpcC, 100, 120, 720, 50), null);

		txaOpcD = MntComponents.getJTextArea();
		txaOpcD.setFont(new Font(FONTE, Font.BOLD, 16));
		pnObjetivas.add(MntComponents.getJScrollPane(txaOpcD, 100, 180, 720, 50), null);

		JLabel txaOpcE = MntComponents.getJLabel(Atributo.OPCAO_E, 100, 240, 720, 20);
		txaOpcE.setFont(new Font(FONTE, Font.BOLD, 16));
		pnObjetivas.add(txaOpcE, null);

		JLabel txaOpcF = MntComponents.getJLabel(Atributo.OPCAO_F, 100, 270, 720, 20);
		txaOpcF.setFont(new Font(FONTE, Font.BOLD, 16));
		pnObjetivas.add(txaOpcF, null);

		// Botões de Respostas
		opcoes = new ButtonGroup();
		radOpcA = MntComponents.getJRadioButton("Op\347\343o A:", 0, 0, 90, 30,
                e -> marcarOpcao("A"));
		opcoes.add(radOpcA);
		pnObjetivas.add(radOpcA, null);
		
		radOpcB = MntComponents.getJRadioButton("Op\347\343o B:", 0, 60, 90, 30,
                e -> marcarOpcao("B"));
		opcoes.add(radOpcB);
		pnObjetivas.add(radOpcB, null);
		
		radOpcC = MntComponents.getJRadioButton("Op\347\343o C:", 0, 120, 90, 30,
                e -> marcarOpcao("C"));
		opcoes.add(radOpcC);
		pnObjetivas.add(radOpcC, null);
		
		radOpcD = MntComponents.getJRadioButton("Op\347\343o D:", 0, 180, 90, 30,
                e -> marcarOpcao("D"));
		opcoes.add(radOpcD);
		pnObjetivas.add(radOpcD, null);

		radOpcE = MntComponents.getJRadioButton("Op\347\343o E:", 0, 240, 90, 20,
                e -> marcarOpcao("E"));
		opcoes.add(radOpcE);
		pnObjetivas.add(radOpcE, null);
		
		radOpcF = MntComponents.getJRadioButton("Op\347\343o F:", 0, 270, 90, 20,
                e -> marcarOpcao("F"));
		opcoes.add(radOpcF);
		pnObjetivas.add(radOpcF, null);
	}

	private void antQuestao() {
		if (qstAtual > 1) {
			qstAtual--;
			mostrarQuestao();
		}
	}

	private void prxQuestao() {
		if (qstAtual < qstTotal) {
			qstAtual++;
			mostrarQuestao();
		}
	}

	private void mostrarQuestao() {
		if (!questoes.isEmpty()) {
			Questao qst = questoes.get(qstAtual - 1);
			
			labQuestao.setText("Quest\343o " + Atributo.colocaZero(qstAtual, 3) +
                    " de " + Atributo.colocaZero(qstTotal, 3));
			txaPerg.setText(qst.getPergunta());
			txaPerg.setCaretPosition(0);
			
			// Apresentar o painel correto
			pnObjetivas.setVisible(qst.getTipo() == 'O');
			pnSubjetivas.setVisible(qst.getTipo() == 'S');
			pnBinarias.setVisible(qst.getTipo() == 'B');

			if (qst.getTipo() == 'O') {
				scrPerg.setBounds(new Rectangle(110, 110, 720, 100));

				txaOpcA.setText(qst.getOpcaoA());
				txaOpcA.setCaretPosition(0);
				txaOpcB.setText(qst.getOpcaoB());
				txaOpcB.setCaretPosition(0);
				txaOpcC.setText(qst.getOpcaoC());
				txaOpcC.setCaretPosition(0);
				txaOpcD.setText(qst.getOpcaoD());
				chkMarcar.setSelected(qst.isMarcar());
				opcoes.clearSelection();
				if (qst.getOpcaoEscolhida() != null && !qst.getOpcaoEscolhida().isEmpty() && qst.getTipo() == 'O') {
					switch (qst.getOpcaoEscolhida().charAt(0)) {
						case 65: // 'A'
							radOpcA.setSelected(true);
							break;
						case 66: // 'B'
							radOpcB.setSelected(true);
							break;
						case 67: // 'C'
							radOpcC.setSelected(true);
							break;
						case 68: // 'D'
							radOpcD.setSelected(true);
							break;
						case 69: // 'E'
							radOpcE.setSelected(true);
							break;
						case 70: // 'F'
							radOpcF.setSelected(true);
							break;
					}
				}
			} else if (qst.getTipo() == 'B') {
				scrPerg.setBounds(new Rectangle(110, 110, 720, 300));
				txaOpcA.setText(qst.getOpcaoA());
				txaOpcA.setCaretPosition(0);
				txaOpcB.setText(qst.getOpcaoB());
				txaOpcB.setCaretPosition(0);
				chkMarcar.setSelected(qst.isMarcar());
				opcoes.clearSelection();
				if (qst.getOpcaoEscolhida() != null && !qst.getOpcaoEscolhida().isEmpty() && qst.getTipo() == 'B') {
					switch (qst.getOpcaoEscolhida().charAt(0)) {
						case 65: // 'A'
							radOpcA.setSelected(true);
							break;
						case 66: // 'B'
							radOpcB.setSelected(true);
							break;
					}
				}
			} else if (qst.getTipo() == 'S') {
				scrPerg.setBounds(new Rectangle(110, 110, 720, 330));
				txSubjetiva.setText(qst.getOpcaoEscolhida());
			}
		}
	}

	private void pularQuestao() {
		try {
			qstAtual = Integer.parseInt(edtIrQuestao.getText());
			irQuestao();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Informar um N\372mero de Quest\343o v\341lido",
					"Erro Informa\347\343o", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void irQuestao() {
		int questao = qstAtual;
		try {
			if (questao < 1)
				throw new Exception("menor");
			if (questao > qstTotal)
				throw new Exception("maior");
            mostrarQuestao();
			edtIrQuestao.setText("");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "N\372mero informado \351 " + e.getMessage() +
                    " que o total da quest\343o", "Erro Informa\347\343o", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void marcarSubjetiva() {
		questoes.get(qstAtual - 1).setOpcaoEscolhida(txSubjetiva.getText());
	}
	
	private void marcarOpcao(String opc) {
		questoes.get(qstAtual - 1).setOpcaoEscolhida(opc);
	}

	private void marcarQuestao() {
		questoes.get(qstAtual - 1).setMarcar(chkMarcar.isSelected());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void mostrarResumo() {
        JList<?> lista = new JList(questoes.toArray());
		Resumo resumo = new Resumo(lista, tempo);
		if (resumo.isFinalizar())
			finalizar();
		if (resumo.getQstAtual() > -1) {
			qstAtual = resumo.getQstAtual();
			irQuestao();
		}
	}

	private void finalizar() {
		if (JOptionPane.showConfirmDialog(this, "Confirma o T\351rmino do Simulado", "Finalizar?", JOptionPane.YES_NO_OPTION) == 0) {
			parar = false;
			if (VerProcs.SO == 0) {
				try {
					Runtime.getRuntime().exec("explorer.exe");
				} catch (Exception ignored) {
				}
			}
			new Desempenho(questoes, tempo.getIntHora());
			System.exit(0);
		}
	}

	@Override
	public void run() {
        VerProcs fraude = new VerProcs();
		fraude.iniciarContagem();
		while (parar) {
			labTempo.setText("Tempo Transcorrido: " + tempo.transHora());
			tempo.reduz();
			try {
				Thread.sleep(1000L);
			} catch (Exception ignored) {
			}
			if (!tempo.isMaiorZero()) {
				JOptionPane.showMessageDialog(this, "Tempo Terminado. Programa ser\341 finalizado!");
				new Desempenho(questoes, tempo.getIntHora());
				System.exit(0);
			}
			if (Atributo.veFraude) {
				fraude.procsAtuais();
				labProcs.setText(fraude.obterValor());
				if (fraude.isDiferente()) {
					JOptionPane.showMessageDialog(this, "Processo MOFICADO. Prova foi finalizada!");
					JOptionPane.showMessageDialog(this, "Seu RESULTADO é ZERO!");
					System.exit(0);
				}
			}
		}
	}
	
	public static void main(String [] args) {
		Atributo.carAtributo();
		new SobreSistema();
		new Inicial();
	}

	private JPanel pnObjetivas;
	private JPanel pnSubjetivas;
	private JPanel pnBinarias;
    private JTextField txSubjetiva;
	private JTextField edtIrQuestao;
	private JTextArea txaPerg;
	private JScrollPane scrPerg;
	// Opcoes Objetivas
	private JTextArea txaOpcA;
	private JTextArea txaOpcB;
	private JTextArea txaOpcC;
	private JTextArea txaOpcD;
	private ButtonGroup opcoes;
	private JRadioButton radOpcA;
	private JRadioButton radOpcB;
	private JRadioButton radOpcC;
	private JRadioButton radOpcD;
	private JRadioButton radOpcE;
	private JRadioButton radOpcF;
	// ----
	private JLabel labQuestao;
    private JCheckBox chkMarcar;
	private JLabel labTempo;
	private JLabel labProcs;
    private final Tempo tempo;
	private final Thread th;
	private int qstAtual;
	private final int qstTotal;
	private final List<Questao> questoes;
    private boolean parar;

	private final String FONTE = "Arial";
}