package cafeteira;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafeteira.uteis.Atributo;

/**
 * Dados do Autor
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class SobreFernando extends JFrame {

  private static final long serialVersionUID = 5L;

  /** Cria a janela dos dados */
  public SobreFernando() {
    this.setTitle("Sobre Fernando Anselmo");
    this.setSize(590, 345);
    this.setLocationRelativeTo(null);

    JPanel pnEsquerda = new JPanel();
    JLabel lbFoto = new JLabel();
    lbFoto.setIcon(Atributo.getImage("Fernando.jpg"));
    pnEsquerda.add(lbFoto);

    JPanel pnDireita = new JPanel(new GridLayout(10, 1));
    pnDireita.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    pnDireita.add(new JLabel("Fernando Anselmo"));
    pnDireita.add(new JLabel("Projetos: https://fernandoans.github.io/"));
    pnDireita.add(new JLabel("Email: fernando.anselmo74@gmail.com"));
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    });
    
    this.setLayout(new GridLayout(1, 2));
    this.add(pnEsquerda);
    this.add(pnDireita);
    this.setVisible(true);
  }
}