package cafeteira;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import cafeteira.componentes.CafButton;
import cafeteira.componentes.CafCheckBox;
import cafeteira.componentes.CafComboBox;
import cafeteira.componentes.CafLabel;
import cafeteira.componentes.CafList;
import cafeteira.componentes.CafPasswordField;
import cafeteira.componentes.CafRadioButton;
import cafeteira.componentes.CafTextArea;
import cafeteira.componentes.CafTextField;
import cafeteira.uteis.Atributo;

/**
 * Formul�rio com todos os objetos que podem ser gerados
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class PalhetaObjeto extends JFrame {

  private static final long serialVersionUID = 4L;
  private Principal frmModi;

  /**
   * Cria a janela dos Objetos
   * 
   * @param recModi Janela principal
   */
  public PalhetaObjeto(Principal recModi) {
    try {
      frmModi = recModi;
      mostra();
      frmModi.jnObjeto = true;
    } catch (RuntimeException ex) {
      ex.printStackTrace();
    }
  }

  private void mostra() {
    this.getContentPane().setLayout(new FlowLayout());
    this.setSize(760, 90);
    this.setTitle("Click no Objeto a criar");

    this.add(criarBotao("JTextField", CafTextField.NUMERO));
    this.add(criarBotao("JPasswordField", CafPasswordField.NUMERO));
    this.add(criarBotao("JButton", CafButton.NUMERO));
    this.add(criarBotao("JList", CafList.NUMERO));
    this.add(criarBotao("JTextArea", CafTextArea.NUMERO));
    this.add(criarBotao("JRadioButton", CafRadioButton.NUMERO));
    this.add(criarBotao("JCheckBox", CafCheckBox.NUMERO));
    this.add(criarBotao("JLabel", CafLabel.NUMERO));
    this.add(criarBotao("JComboBox", CafComboBox.NUMERO));

    JButton btNormal = new JButton("", Atributo.getImage("Cursor.gif"));
    btNormal.setToolTipText("Termina");
    btNormal.setPreferredSize(new Dimension(70, 40));
    this.getContentPane().add(btNormal, null);
    btNormal.addActionListener(e -> termina());

    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        aoFechar();
      }
    });
  }
  
  private JButton criarBotao(String nome, final int num) {
    JButton btn = new JButton("", Atributo.getImage(nome + ".gif"));
    btn.setPreferredSize(new Dimension(70, 40));
    btn.setToolTipText(nome);
    btn.addActionListener(e -> criaObj(num));
    return btn;
  }

  private void termina() {
    frmModi.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }

  private void criaObj(int tipObj) {
    frmModi.objCriar = tipObj;
    frmModi.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
  }

  private void aoFechar() {
    this.termina();
    frmModi.jnObjeto = false;
    this.dispose();
  }
}