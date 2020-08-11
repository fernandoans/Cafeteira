package cafeteira;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafeteira.uteis.Atributo;

/**
 * Dados do Software
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class SobreSistema extends JDialog {

  private static final long serialVersionUID = 6L;

  /** Cria a janela dos dados */
  public SobreSistema() {
    this.setTitle(Atributo.TITULO + " - " + Atributo.CFVERSAO);
    this.setBackground(new Color(255, 255, 255));
    this.getContentPane().setLayout(new BorderLayout(1, 0));
    this.setSize(380, 420);
    this.setModal(true);
    this.setResizable(false);
    this.setLocationRelativeTo(null);

    JLabel lbSplash = new JLabel();
    lbSplash.setIcon(Atributo.getImage("logo.jpg"));
    
    JPanel pnLicenca = new JPanel();
    pnLicenca.add(new JLabel("2020 - Fernando Anselmo - Licen\347a GPL"));
    pnLicenca.setBackground(new Color(255, 255, 255));
    
    this.add(lbSplash, BorderLayout.CENTER);
    this.add(pnLicenca, BorderLayout.SOUTH);

    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent evt) {
        aoFechar();
      }
      @Override
      public void windowActivated(WindowEvent e) {
        iniciarEspera();
      }
    });
    this.setUndecorated(true);
    this.setVisible(true);
  }

  public void aoFechar() {
    dispose();
  }
  
  public void iniciarEspera() {
    try {
      Thread.sleep(3000L);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    aoFechar();
  }
}