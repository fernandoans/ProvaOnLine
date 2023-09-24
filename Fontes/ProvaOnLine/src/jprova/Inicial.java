package jprova;

// Importa os pacotes necessarios
import java.util.List;
import java.awt.event.*;
import javax.swing.*;

import jprova.banco.TratarArquivo;

import java.awt.Rectangle;
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
		super((new StringBuilder(String.valueOf(Atributo.titulo)))
			.append(" - " + Atributo.CFVERSAO).toString());
		th = new Thread(this);
		qstAtual = 1;
		parar = true;
		tempo = new Tempo(Atributo.tempo);
		questoes = (new TratarArquivo()).obterDados(Atributo.totQuestao);
		qstTotal = questoes.size();
		mostrar();
	}

	public final void mostrar() {
		getContentPane().setLayout(null);
		setSize(850, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		// Titulo - Parte 1
		labQuestao = new JLabel("Quest\343o 999 de 200");
		labQuestao.setBounds(new Rectangle(10, 10, 140, 21));
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
		objeto0 = new JLabel("Anterior");
		objeto0.setBounds(new Rectangle(20, 80, 60, 13));
		getContentPane().add(objeto0, null);

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
		objeto1 = new JLabel("Pr\363ximo");
		objeto1.setBounds(new Rectangle(90, 80, 57, 13));
		getContentPane().add(objeto1, null);

		// Titulo - Parte 2
		objeto2 = new JLabel("Ir Para:");
		objeto2.setBounds(new Rectangle(160, 49, 57, 13));
		getContentPane().add(objeto2, null);

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
		labTempo = new JLabel("Tempo Transcorrido: HH:MM:SS");
		labTempo.setBounds(new Rectangle(600, 10, 250, 21));
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

		// Area das Perguntas
		objeto5 = new JLabel("Pergunta:");
		objeto5.setBounds(new Rectangle(10, 110, 90, 30));
		getContentPane().add(objeto5, null);
		
		txaPerg = new JTextArea();
		txaPerg.setLineWrap(true);
		txaPerg.setFont(new Font("Arial", 0, 16));
		txaPerg.setWrapStyleWord(true);
		txaPerg.setEditable(false);
		JScrollPane spPerg = new JScrollPane(txaPerg);
		spPerg.setHorizontalScrollBarPolicy(31);
		spPerg.setBounds(new Rectangle(110, 110, 720, 100));
		getContentPane().add(spPerg, null);
		
		// Area das Respostas
		txaOpcA = new JTextArea();
		txaOpcA.setLineWrap(true);
		txaOpcA.setFont(new Font("Arial", 0, 16));
		txaOpcA.setWrapStyleWord(true);
		txaOpcA.setEditable(false);
		JScrollPane spOpcA = new JScrollPane(txaOpcA);
		spOpcA.setHorizontalScrollBarPolicy(31);
		spOpcA.setBounds(new Rectangle(110, 220, 720, 50));
		getContentPane().add(spOpcA, null);
		
		txaOpcB = new JTextArea();
		txaOpcB.setLineWrap(true);
		txaOpcB.setFont(new Font("Arial", 0, 16));
		txaOpcB.setWrapStyleWord(true);
		txaOpcB.setEditable(false);
		JScrollPane spOpcB = new JScrollPane(txaOpcB);
		spOpcB.setHorizontalScrollBarPolicy(31);
		spOpcB.setBounds(new Rectangle(110, 280, 720, 50));
		getContentPane().add(spOpcB, null);
		
		txaOpcC = new JTextArea();
		txaOpcC.setLineWrap(true);
		txaOpcC.setFont(new Font("Arial", 0, 16));
		txaOpcC.setWrapStyleWord(true);
		txaOpcC.setEditable(false);
		JScrollPane spOpcC = new JScrollPane(txaOpcC);
		spOpcC.setHorizontalScrollBarPolicy(31);
		spOpcC.setBounds(new Rectangle(110, 340, 720, 50));
		getContentPane().add(spOpcC, null);
		
		txaOpcD = new JTextArea();
		txaOpcD.setLineWrap(true);
		txaOpcD.setFont(new Font("Arial", 0, 16));
		txaOpcD.setWrapStyleWord(true);
		txaOpcD.setEditable(false);
		JScrollPane spOpcD = new JScrollPane(txaOpcD);
		spOpcD.setHorizontalScrollBarPolicy(31);
		spOpcD.setBounds(new Rectangle(110, 400, 720, 50));
		getContentPane().add(spOpcD, null);
		
		txaOpcE = new JLabel();
		txaOpcE.setFont(new Font("Arial", 0, 16));
		txaOpcE.setText("Todas as questões acima.");
		txaOpcE.setBounds(new Rectangle(110, 460, 720, 20));
		getContentPane().add(txaOpcE, null);
		
		txaOpcF = new JLabel();
		txaOpcF.setFont(new Font("Arial", 0, 16));
		txaOpcF.setText("Nenhuma das questões acima.");
		txaOpcF.setBounds(new Rectangle(110, 490, 720, 20));
		getContentPane().add(txaOpcF, null);
		
		// Botões de Respostas
		opcoes = new ButtonGroup();
		radOpcA = new JRadioButton("Op\347\343o A:");
		opcoes.add(radOpcA);
		radOpcA.setBounds(new Rectangle(10, 220, 90, 30));
		getContentPane().add(radOpcA, null);
		radOpcA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao('A');
			}
		});
		radOpcB = new JRadioButton("Op\347\343o B:");
		opcoes.add(radOpcB);
		radOpcB.setBounds(new Rectangle(10, 280, 90, 30));
		getContentPane().add(radOpcB, null);
		radOpcB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao('B');
			}
		});
		radOpcC = new JRadioButton("Op\347\343o C:");
		opcoes.add(radOpcC);
		radOpcC.setBounds(new Rectangle(10, 340, 90, 30));
		getContentPane().add(radOpcC, null);
		radOpcC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao('C');
			}
		});
		radOpcD = new JRadioButton("Op\347\343o D:");
		opcoes.add(radOpcD);
		radOpcD.setBounds(new Rectangle(10, 400, 90, 30));
		getContentPane().add(radOpcD, null);
		radOpcD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao('D');
			}
		});
		radOpcE = new JRadioButton("Op\347\343o E:");
		opcoes.add(radOpcE);
		radOpcE.setBounds(new Rectangle(10, 460, 90, 20));
		getContentPane().add(radOpcE, null);
		radOpcE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao('E');
			}
		});
		radOpcF = new JRadioButton("Op\347\343o F:");
		opcoes.add(radOpcF);
		radOpcF.setBounds(new Rectangle(10, 490, 90, 20));
		getContentPane().add(radOpcF, null);
		radOpcF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarOpcao('F');
			}
		});
		
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

		labDetalhe = new JLabel(Atributo.COPYRIGHT);
		labDetalhe.setBounds(new Rectangle(320, 535, 300, 30));
		getContentPane().add(labDetalhe, null);
		mostrarQuestao();
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
			labQuestao.setText((new StringBuilder("Quest\343o "))
					.append(Atributo.colocaZero(qstAtual, 3)).append(" de ")
					.append(Atributo.colocaZero(qstTotal, 3)).toString());
			txaPerg.setText(qst.getPergunta());
			txaPerg.setCaretPosition(0);
			txaOpcA.setText(qst.getOpcaoA());
			txaOpcA.setCaretPosition(0);
			txaOpcB.setText(qst.getOpcaoB());
			txaOpcB.setCaretPosition(0);
			txaOpcC.setText(qst.getOpcaoC());
			txaOpcC.setCaretPosition(0);
			txaOpcD.setText(qst.getOpcaoD());
			chkMarcar.setSelected(qst.isMarcar());
			opcoes.clearSelection();
			switch (qst.getOpcaoEscolhida()) {
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
			}
		}
	}

	private void pularQuestao() {
		try {
			qstAtual = Integer.parseInt(edtIrQuestao.getText());
			irQuestao();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Informar um N\372mero de Quest\343o v\341lido",
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
			JOptionPane.showMessageDialog(this, (new StringBuilder(
					"N\372mero informado \351 ")).append(e.getMessage())
					.append(" que o total da quest\343o").toString(),
					"Erro Informa\347\343o", 0);
		}
	}

	private void marcarOpcao(char opc) {
		questoes.get(qstAtual - 1).setOpcaoEscolhida(opc);
	}

	private void marcarQuestao() {
		questoes.get(qstAtual - 1)
				.setMarcar(chkMarcar.isSelected());
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
		if (JOptionPane.showConfirmDialog(this,
				"Confirma o T\351rmino do Simulado", "Finalizar?", 0) == 0) {
			parar = false;
			new Desempenho(questoes, tempo.getIntHora());
			System.exit(0);
		}
	}

	@Override
	public void run() {
		fraude = new VerProcs();
		fraude.iniciarContagem();
		while (parar) {
			labTempo.setText((new StringBuilder("Tempo Transcorrido: "))
					.append(tempo.transHora()).toString());
			tempo.reduz();
			try {
				Thread.sleep(1000L);
			} catch (Exception exception) {
			}
			if (!tempo.isMaiorZero()) {
				JOptionPane.showMessageDialog(this,
						"Tempo Terminado. Programa ser\341 finalizado!");
				new Desempenho(questoes, tempo.getIntHora());
				System.exit(0);
			}
			fraude.procsAtuais();
	        if (fraude.isDiferente()) {
				JOptionPane.showMessageDialog(this,
						"Processo MOFICADO. Prova foi finalizada!");
				JOptionPane.showMessageDialog(this,
						"Seu RESULTADO é ZERO!");
	            System.exit(0);
	        }		
		}
	}

	public static void main(String args[]) {
		Atributo.carAtributo();
		new SobreSistema();
		new Inicial();
	}

	private JLabel objeto0;
	private JLabel objeto1;
	private JLabel objeto2;
	private JLabel objeto5;
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
