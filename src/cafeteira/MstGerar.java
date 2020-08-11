package cafeteira;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import cafeteira.componentes.AnalisaJComponent;
import cafeteira.uteis.Atributo;

/**
 * Mostra a geracao do Codigo
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class MstGerar extends JDialog {

  private static final long serialVersionUID = 3L;
  // Referencia
  private Principal frmModi;
  // Objetos da Janela
  private JTabbedPane abas = new JTabbedPane();
  private JTextArea txaClasse = new JTextArea();
  private JTextArea txaObjeto = new JTextArea();
  private JScrollPane scrollClasse = new JScrollPane();
  private JScrollPane scrollObjeto = new JScrollPane();
  private JButton btCopiar = new JButton("Classe");
  private JButton btSelecionar = new JButton("Objetos");

  /**
   * Realiza a geracao da classe
   * 
   * @param recModi Formulario Principal
   */
  public MstGerar(Principal recModi) {
    try {
      frmModi = recModi;
      mostra();
    } catch (RuntimeException ex) {
      ex.printStackTrace();
    }
  }

  private void mostra() {
    this.setTitle(Atributo.TITULO + " - " + Atributo.CFVERSAO);
    Font fntCourrier = new Font("Courier New", 0, 11);
    this.setSize(652, 507);
    this.setResizable(false);
    this.setModal(true);

    // 1a Aba
    txaClasse.setFont(fntCourrier);
    scrollClasse.getViewport().add(txaClasse);
    abas.addTab("Codigo da Classe", scrollClasse);
    // 2a Aba
    txaObjeto.setFont(fntCourrier);
    scrollObjeto.getViewport().add(txaObjeto);
    abas.addTab("Codigo Interno", scrollObjeto);

    JPanel pnBotoes = new JPanel();
    pnBotoes.add(btCopiar, null);
    btCopiar.addActionListener(e -> acaoCopiaClasse());
    pnBotoes.add(btSelecionar, null);
    btSelecionar.addActionListener(e -> acaoCopiaObjeto());

    this.add(abas, BorderLayout.CENTER);
    this.add(pnBotoes, BorderLayout.SOUTH);

    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        aoFechar();
      }
    });
    geraClasse();
  }

  private void geraClasse() {
    frmModi.setNomJan((String)JOptionPane.showInputDialog(this, "Informe o Nome da Classe", Atributo.TITULO, 3, null, null, frmModi.getNomJan()));
    if (frmModi.getNomJan() != null) {
      frmModi.setNomPac((String)JOptionPane.showInputDialog(this, "Informe o Nome do Pacote (ou deixe em branco)", Atributo.TITULO, 3, null, null, frmModi.getNomPac()));
      if (frmModi.getNomPac() == null)
        frmModi.setNomPac("");
      if (frmModi.getNomPac().trim().equals("")) {
        txaClasse.setText("// Importa os pacotes necessarios\n");
      } else {
        txaClasse.setText("package " + frmModi.getNomPac() + ";" + "\n\n// Importa os pacotes necessarios\n");
      }
      // ***
      final String espacoPadrao = "\n  }\n";
      txaClasse.append("import java.awt.*;" 
          + "\nimport java.awt.event.*;" 
          + "\nimport javax.swing.*;" 
          + "\n" 
          + "\n/** " + frmModi.getNomJan() + ((frmModi.getNomPac().equals("")) ? "" : "\n * Projeto..: " + frmModi.getNomPac())
          + "\n * [ coloque aqui a descricao da classe ]" 
          + "\n * @author Fernando Anselmo em " + (new SimpleDateFormat("MMM - yyyy")).format(new Date()) 
          + "\n * @version 1.0" 
          + "\n */" 
          + "\npublic class " + frmModi.getNomJan() + " extends " + ((frmModi.getCkbModal().isSelected()) ? "JDialog" : "JFrame") + " {" 
          + "\n"
          + "\n  // Bloco 1 - Dados dos Objetos da Janela" 
          + "\n" 
          + "\n  public " + frmModi.getNomJan() + "() {"
          + "\n    // Bloco 2 - Dados da Criacao da Janela" + "\n"
          + "\n    // Bloco 3 - Dados da Criacao dos Controles na Janela" + "\n"
          + "\n    this.addWindowListener(new WindowAdapter() {"
          + "\n      @Override" 
          + "\n      public void windowClosing(WindowEvent e) {" 
          + "\n        aoFechar();"
          + "\n      }" 
          + "\n    });" 
          + espacoPadrao 
          + "\n  private void aoFechar() {"
          + "\n    System.exit(0);" 
          + espacoPadrao
          + "\n  // Insira aqui seus metodos para os eventos" 
          + "\n"
          + "\n  public static void main(String args[]) {" 
          + "\n    new " + frmModi.getNomJan() + "();" 
          + espacoPadrao + "}");
      geraObjeto();
    }
  }

  private void geraObjeto() {
    AnalisaJComponent cmpAnalisado = new AnalisaJComponent();
    txaObjeto.setText("  // Bloco 1 - Objetos da Janela");
    Component[] comps = frmModi.getContentPane().getComponents();
    for (int i = 0; i < comps.length; i++) {
      txaObjeto.append(cmpAnalisado.getCriaComponente((JComponent) comps[i]));
    }
    // ***
    txaObjeto.append("\n\n    // Bloco 2 - Definicao dos dados da Janela");
    txaObjeto.append("\n    this.getContentPane().setLayout(null);");
    txaObjeto.append("\n    this.getContentPane().setBackground(new Color("
        + frmModi.getContentPane().getBackground().getRed() + ", " + frmModi.getContentPane().getBackground().getGreen()
        + ", " + frmModi.getContentPane().getBackground().getBlue() + "));");
    txaObjeto.append("\n    this.setSize(" + frmModi.getWidth() + ", " + frmModi.getHeight() + ");");
    txaObjeto.append("\n    this.setLocation(" + frmModi.getX() + ", " + frmModi.getY() + ");");
    txaObjeto.append("\n    this.setTitle(\"" + frmModi.getTitle() + "\");");
    txaObjeto.append("\n    this.setResizable(" + ((frmModi.getCkbRedimens().isSelected()) ? "true" : "false") + ");");
    txaObjeto.append(((frmModi.getCkbModal().isSelected()) ? "\n        this.setModal(true);" : ""));
    // ***
    txaObjeto.append("\n\n    // Bloco 3 - Criacao dos Objetos na Janela");
    for (int i = 0; i < comps.length; i++) {
      txaObjeto.append(cmpAnalisado.getPropComponente((JComponent) comps[i]));
      txaObjeto.append(cmpAnalisado.getCriaEvento((JComponent) comps[i]));
    }
    txaObjeto.append("\n    this.visible(true);");
  }

  private void acaoCopiaClasse() {
    StringSelection contents = new StringSelection(txaClasse.getText());
    Clipboard clipboard = getToolkit().getSystemClipboard();
    clipboard.setContents(contents, null);
  }

  private void acaoCopiaObjeto() {
    StringSelection contents = new StringSelection(txaObjeto.getText());
    Clipboard clipboard = getToolkit().getSystemClipboard();
    clipboard.setContents(contents, null);
  }

  private void aoFechar() {
    dispose();
  }
}