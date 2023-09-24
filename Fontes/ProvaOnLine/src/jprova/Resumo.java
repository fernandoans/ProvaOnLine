package jprova;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Mostrar a tela Resumo
 * @author Fernando
 */
@SuppressWarnings("serial")
public class Resumo extends JDialog implements Runnable {

    private JLabel objeto0;
    private JLabel objeto1;
    private JLabel objeto2;
    private JLabel objeto3;
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
        this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(new Color(238, 238, 238));
        this.setSize(810, 440);
        this.setLocationRelativeTo(null);
        this.setTitle("Resumo");
        this.setResizable(false);
        this.setModal(true);
        
        labTempo = new JLabel("Tempo Transcorrido: HH:MM:SS");
        labTempo.setBounds(new Rectangle(560, 10, 250, 21));
        this.getContentPane().add(labTempo, null);
        
        // Miolo
        objeto0 = new JLabel("Nº");
        objeto0.setBounds(new Rectangle(18, 40, 55, 13));
        this.getContentPane().add(objeto0, null);
        objeto1 = new JLabel("Pergunta");
        objeto1.setBounds(new Rectangle(75, 40, 80, 13));
        this.getContentPane().add(objeto1, null);
        objeto2 = new JLabel("Opção Escolhida");
        objeto2.setBounds(new Rectangle(375, 40, 130, 13));
        this.getContentPane().add(objeto2, null);
        objeto3 = new JLabel("Revisão");
        objeto3.setBounds(new Rectangle(720, 40, 80, 13));
        this.getContentPane().add(objeto3, null);
        
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
        butResumo = new JButton("Voltar");
        butResumo.setBounds(new Rectangle(10, 370, 100, 30));
        this.getContentPane().add(butResumo, null);
        butResumo.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aoFechar();
            }
        });
        butFinalizar = new JButton("Finalizar");
        butFinalizar.setBounds(new Rectangle(687, 370, 100, 30));
        this.getContentPane().add(butFinalizar, null);
        butFinalizar.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizar = true;
                aoFechar();
            }
        });

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