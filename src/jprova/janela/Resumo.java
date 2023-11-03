package jprova.janela;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jprova.util.Tempo;

/**
 * Mostrar a tela Resumo
 * @author Fernando
 */
@SuppressWarnings("serial")
public class Resumo extends JDialog implements Runnable {

    private JList<?> lista;
    private JLabel labTempo;
    private JButton butResumo;
    private JButton butFinalizar;

    // Atributos usados
    private boolean finalizar = false;
    private boolean parar = true;
    private Tempo tempo;
    private Thread th = new Thread(this);
    private int qstAtual = -1;
    
    public Resumo(JList<?> lst, Tempo tempo) {
        this.tempo = tempo;
        this.lista = lst;
        mostrar();
    }

    public final void mostrar() {
        this.setTitle("Resumo");
        this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(new Color(238, 238, 238));
        this.setSize(810, 440);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setModal(true);
        
        labTempo = MntComponents.getJLabel("Tempo Transcorrido: HH:MM:SS", 560, 10, 250, 21);
        this.getContentPane().add(labTempo, null);
        
        // Miolo
        this.getContentPane().add(MntComponents.getJLabel("Nº", 18, 40, 55, 13), null);
        this.getContentPane().add(MntComponents.getJLabel("Pergunta", 75, 40, 80, 13), null);
        this.getContentPane().add(MntComponents.getJLabel("Opção Escolhida", 375, 40, 130, 13), null);
        this.getContentPane().add(MntComponents.getJLabel("Revisão", 720, 40, 80, 13), null);
        
        lista.setFont(new Font("Courier New", Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(lista);
        sp.setBounds(new Rectangle(10, 60, 780, 300));
        this.getContentPane().add(sp, null);
        lista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                if (evt.getValueIsAdjusting()) {
                    return;
                }
                qstAtual = evt.getFirstIndex() + 1;
            }
        });

        // Botoes
        butResumo = MntComponents.getJButtonTxt("Voltar", 10, 370, 100, 30,
        	new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                aoFechar();
	            }
	        });
        this.getContentPane().add(butResumo, null);

        butFinalizar = MntComponents.getJButtonTxt("Finalizar", 687, 370, 100, 30, 
        	new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                finalizar = true;
	                aoFechar();
	            }
	        });
        this.getContentPane().add(butFinalizar, null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                aoFechar();
            }
        });
        th.start();
        this.setVisible(true);
    }

    public int getQstAtual() {
        return qstAtual;
    }

    public boolean isFinalizar() {
        return finalizar;
    }
    
    @SuppressWarnings("static-access")
	@Override
    public void run() {
        while (parar && tempo.isMaiorZero()) {
            labTempo.setText("Tempo Transcorrido: " + tempo.transHora());
            try { th.sleep(1000); } catch (Exception e) { }
        }
    }

    private void aoFechar() {
        this.parar = false;
        this.dispose();
    }
}