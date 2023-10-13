package jprova;

// Importa os pacotes necessarios
import java.util.List;
import java.awt.event.*;
import javax.swing.*;

import jprova.banco.TratarArquivo;

import java.awt.Rectangle;
import java.awt.Component;
import java.awt.Font;

/**
 * Inicial Projeto..: Tela Inicial
 * 
 * @author Fernando Anselmo © Jun - 2011
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Inicial extends JFrame implements Runnable {

	public Inicial() {
		super((new StringBuilder(String.valueOf(Atributo.titulo))).append(" - " + Atributo.CFVERSAO).toString());
		th = new Thread(this);
		qstAtual = 1;
		parar = true;
		tempo = new Tempo(Atributo.tempo);
		questoes = (new TratarArquivo()).obterDados(Atributo.totQuestaoO, Atributo.totQuestaoS);
		qstTotal = questoes.size();
		mostrar();
	}

	public final void mostrar() {
		getContentPane().setLayout(null);
		setSize(850, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		// Titulo - Parte 1
		labQuestao = getLabel("Quest\343o 999 de 200", 10, 10, 140, 21);
		getContentPane().add(labQuestao, null);

		imgAnt = new JButton();
		imgAnt.setIcon(Atributo.getImage("back.gif"));
		imgAnt.setBounds(new Rectangle(23, 35, 40, 40));
		getContentPane().add(imgAnt, null);
		imgAnt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				antQuestao();
			}
		});
		getContentPane().add(getLabel("Anterior", 20, 80, 60, 13), null);

		imgProx = new JButton();
		imgProx.setIcon(Atributo.getImage("forward.gif"));
		imgProx.setBounds(new Rectangle(95, 35, 40, 40));
		getContentPane().add(imgProx, null);
		imgProx.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prxQuestao();
			}
		});
		getContentPane().add(getLabel("Pr\363ximo", 90, 80, 57, 13), null);

		// Titulo - Parte 2
		getContentPane().add(getLabel("Ir Para:", 160, 49, 57, 13), null);
		edtIrQuestao = new JTextField();
		edtIrQuestao.setBounds(new Rectangle(215, 46, 50, 21));
		getContentPane().add(edtIrQuestao, null);
		edtIrQuestao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pularQuestao();
			}
		});

		chkMarcar = new JCheckBox("Marcar para revis\343o");
		chkMarcar.setBounds(new Rectangle(370, 40, 200, 30));
		getContentPane().add(chkMarcar, null);
		chkMarcar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarQuestao();
			}
		});

		// Titulo - Parte 3
		labTempo = getLabel("Tempo Transcorrido: HH:MM:SS", 600, 10, 250, 21);
		getContentPane().add(labTempo, null);
		
		butResumo = new JButton("Resumo");
		butResumo.setBounds(new Rectangle(730, 40, 100, 30));
		getContentPane().add(butResumo, null);
		butResumo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarResumo();
			}
		});

		// Area da Pergunta
		getContentPane().add(getLabel("Pergunta:", 10, 110, 90, 30), null);
		txaPerg = getJTextArea();
		getContentPane().add(getJScrollPane(txaPerg, 110, 110, 720, 100), null);

		// Area das Respostas
		pnObjetivas = new JPanel();
		montaObjetivas();
		getContentPane().add(pnObjetivas, null);

		pnSubjetivas = new JPanel();
		montaSubjetivas();
		getContentPane().add(pnSubjetivas, null);
		
		// Rodapé
		butFinalizar = new JButton("Finalizar");
		butFinalizar.setBounds(new Rectangle(730, 535, 100, 30));
		getContentPane().add(butFinalizar, null);
		butFinalizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finalizar();
			}
		});

		labDetalhe = getLabel(Atributo.COPYRIGHT, 320, 535, 300, 30);
		getContentPane().add(labDetalhe, null);
		mostrarQuestao();
		if (VerProcs.SO == 0) {
			String inicial = System.getenv("windir") + "\\system32\\taskkill /f /im ";
			try {
				Runtime.getRuntime().exec(inicial + "explorer.exe");
			} catch (Exception e) {
			}
		}
		th.start();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finalizar();
			}
		});
		setDefaultCloseOperation(0);
		setVisible(true);
	}

	private void montaSubjetivas() {
		pnSubjetivas.setBounds(new Rectangle(10, 220, 820, 300));
		pnSubjetivas.setLayout(null);

		pnSubjetivas.add(getLabel("Resposta:", 0, 0, 90, 30), null);
		txSubjetiva = new JTextField();
		txSubjetiva.setFont(new Font("Arial", 0, 16));
		txSubjetiva.setBounds(new Rectangle(100, 0, 720, 50));
		txSubjetiva.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				marcarSubjetiva();
			}
		});
		pnSubjetivas.add(txSubjetiva, null);
	}
	
	private void montaObjetivas() {
		pnObjetivas.setBounds(new Rectangle(10, 220, 820, 300));
		pnObjetivas.setLayout(null);

		txaOpcA = getJTextArea();
		pnObjetivas.add(getJScrollPane(txaOpcA, 100, 0, 720, 50), null);

		txaOpcB = getJTextArea();
		pnObjetivas.add(getJScrollPane(txaOpcB, 100, 60, 720, 50), null);

		txaOpcC = getJTextArea();
		pnObjetivas.add(getJScrollPane(txaOpcC, 100, 120, 720, 50), null);

		txaOpcD = getJTextArea();
		pnObjetivas.add(getJScrollPane(txaOpcD, 100, 180, 720, 50), null);

		txaOpcE = getLabel("Todas as questões acima.", 100, 240, 720, 20);
		txaOpcE.setFont(new Font("Arial", 0, 16));
		pnObjetivas.add(txaOpcE, null);

		txaOpcF = getLabel("Nenhuma das questões acima.", 100, 270, 720, 20);
		txaOpcF.setFont(new Font("Arial", 0, 16));
		pnObjetivas.add(txaOpcF, null);

		// Botões de Respostas
		opcoes = new ButtonGroup();
		radOpcA = new JRadioButton("Op\347\343o A:");
		radOpcA.setBounds(new Rectangle(0, 0, 90, 30));
		opcoes.add(radOpcA);
		pnObjetivas.add(radOpcA, null);
		radOpcA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao("A");
			}
		});
		radOpcB = new JRadioButton("Op\347\343o B:");
		radOpcB.setBounds(new Rectangle(0, 60, 90, 30));
		opcoes.add(radOpcB);
		pnObjetivas.add(radOpcB, null);
		radOpcB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao("B");
			}
		});
		radOpcC = new JRadioButton("Op\347\343o C:");
		radOpcC.setBounds(new Rectangle(0, 120, 90, 30));
		opcoes.add(radOpcC);
		pnObjetivas.add(radOpcC, null);
		radOpcC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao("C");
			}
		});
		radOpcD = new JRadioButton("Op\347\343o D:");
		radOpcD.setBounds(new Rectangle(0, 180, 90, 30));
		opcoes.add(radOpcD);
		pnObjetivas.add(radOpcD, null);
		radOpcD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao("D");
			}
		});
		radOpcE = new JRadioButton("Op\347\343o E:");
		radOpcE.setBounds(new Rectangle(0, 240, 90, 20));
		opcoes.add(radOpcE);
		pnObjetivas.add(radOpcE, null);
		radOpcE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao("E");
			}
		});
		radOpcF = new JRadioButton("Op\347\343o F:");
		radOpcF.setBounds(new Rectangle(0, 270, 90, 20));
		opcoes.add(radOpcF);
		pnObjetivas.add(radOpcF, null);
		radOpcF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao("F");
			}
		});
	}

	private JLabel getLabel(String titulo, int p1, int p2, int p3, int p4) {
		JLabel label = new JLabel(titulo);
		label.setBounds(new Rectangle(p1, p2, p3, p4));
		return label;
	}
	
	private JScrollPane getJScrollPane(Component view, int p1, int p2, int p3, int p4) {
		JScrollPane scroll = new JScrollPane(view);
		scroll.setHorizontalScrollBarPolicy(31);
		scroll.setBounds(new Rectangle(p1, p2, p3, p4));
		return scroll;
	}
	
	private JTextArea getJTextArea() {
		JTextArea texto = new JTextArea();
		texto.setLineWrap(true);
		texto.setFont(new Font("Arial", 0, 16));
		texto.setWrapStyleWord(true);
		texto.setEditable(false);
		return texto;
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
		if (questoes.size() > 0) {
			Questao qst = questoes.get(qstAtual - 1);
			
			labQuestao.setText((new StringBuilder("Quest\343o ")).append(Atributo.colocaZero(qstAtual, 3))
					.append(" de ").append(Atributo.colocaZero(qstTotal, 3)).toString());
			txaPerg.setText(qst.getPergunta());
			txaPerg.setCaretPosition(0);
			
			// Apresentar o painel correto
			pnObjetivas.setVisible(qst.getTipo() == 'O');
			pnSubjetivas.setVisible(qst.getTipo() == 'S');
			
			if (qst.getTipo() == 'O') {
				txaOpcA.setText(qst.getOpcaoA());
				txaOpcA.setCaretPosition(0);
				txaOpcB.setText(qst.getOpcaoB());
				txaOpcB.setCaretPosition(0);
				txaOpcC.setText(qst.getOpcaoC());
				txaOpcC.setCaretPosition(0);
				txaOpcD.setText(qst.getOpcaoD());
				chkMarcar.setSelected(qst.isMarcar());
				opcoes.clearSelection();
				if (qst.getOpcaoEscolhida() != null && qst.getOpcaoEscolhida().length() > 0 && qst.getTipo() == 'O') {
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
			} else {
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
					"Erro Informa\347\343o", 0);
		}
	}

	private void irQuestao() {
		int questao = qstAtual;
		try {
			if (questao < 1)
				throw new Exception("menor");
			if (questao > qstTotal)
				throw new Exception("maior");
			qstAtual = questao;
			mostrarQuestao();
			edtIrQuestao.setText("");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, (new StringBuilder("N\372mero informado \351 ")).append(e.getMessage())
					.append(" que o total da quest\343o").toString(), "Erro Informa\347\343o", 0);
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
		lista = new JList(questoes.toArray());
		Resumo resumo = new Resumo(lista, tempo);
		if (resumo.isFinalizar())
			finalizar();
		if (resumo.getQstAtual() > -1) {
			qstAtual = resumo.getQstAtual();
			irQuestao();
		}
	}

	private void finalizar() {
		if (JOptionPane.showConfirmDialog(this, "Confirma o T\351rmino do Simulado", "Finalizar?", 0) == 0) {
			parar = false;
			if (VerProcs.SO == 0) {
				try {
					Runtime.getRuntime().exec("explorer.exe");
				} catch (Exception e) {
				}
			}
			new Desempenho(questoes, tempo.getIntHora());
			System.exit(0);
		}
	}

	@Override
	public void run() {
		fraude = new VerProcs();
		fraude.iniciarContagem();
		while (parar) {
			labTempo.setText((new StringBuilder("Tempo Transcorrido: ")).append(tempo.transHora()).toString());
			tempo.reduz();
			try {
				Thread.sleep(1000L);
			} catch (Exception exception) {
			}
			if (!tempo.isMaiorZero()) {
				JOptionPane.showMessageDialog(this, "Tempo Terminado. Programa ser\341 finalizado!");
				new Desempenho(questoes, tempo.getIntHora());
				System.exit(0);
			}
			fraude.procsAtuais();
			if (fraude.isDiferente()) {
				JOptionPane.showMessageDialog(this, "Processo MOFICADO. Prova foi finalizada!");
				JOptionPane.showMessageDialog(this, "Seu RESULTADO é ZERO!");
				System.exit(0);
			}
		}
	}

	public static void main(String args[]) {
		Atributo.carAtributo();
		new SobreSistema();
		new Inicial();
	}

	private JPanel pnObjetivas;
	private JPanel pnSubjetivas;
	private JTextField txSubjetiva;
	private JTextField edtIrQuestao;
	private JTextArea txaPerg;
	private JTextArea txaOpcA;
	private JTextArea txaOpcB;
	private JTextArea txaOpcC;
	private JTextArea txaOpcD;
	private JLabel txaOpcE;
	private JLabel txaOpcF;
	private ButtonGroup opcoes;
	private JRadioButton radOpcA;
	private JRadioButton radOpcB;
	private JRadioButton radOpcC;
	private JRadioButton radOpcD;
	private JRadioButton radOpcE;
	private JRadioButton radOpcF;
	private JLabel labQuestao;
	private JButton imgAnt;
	private JButton imgProx;
	private JCheckBox chkMarcar;
	private JLabel labTempo;
	private JButton butResumo;
	private JButton butFinalizar;
	private JLabel labDetalhe;
	private Tempo tempo;
	private Thread th;
	private int qstAtual;
	private final int qstTotal;
	private List<Questao> questoes;
	private JList<?> lista;
	private boolean parar;
	private VerProcs fraude;
}