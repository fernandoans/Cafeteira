package cafeteira;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import cafeteira.componentes.AnalisaJComponent;
import cafeteira.uteis.CafeteiraException;

/**
 * Janela de propriedades dos objetos
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class AjustaObjeto extends JFrame {

  private static final long serialVersionUID = 2L;
  // Refer�ncia
  private Principal frmModi;
  private boolean clickCheck = true;
  // Objetos da Janela
  private JComponent cmpAtual;
  private JTextField proEsquerda;
  private JTextField proTopo;
  private JTextField proLargura;
  private JTextField proAltura;
  private JTextField proLegenda;
  private JTextField proNome;
  private JTextField proSugestao;
  private JCheckBox evento1;
  private JCheckBox evento2;
  private JCheckBox evento4;
  private JCheckBox evento8;
  private JCheckBox evento16;
  private JComboBox<String> cbComponente;

  /**
   * Cria e organiza os objetos na janela de propriedade dos objetos
   * 
   * @param cmp     Nome do componente
   * @param recModi Janela Principal
   */
  public AjustaObjeto(JComponent cmp, Principal recModi) {
    try {
      frmModi = recModi;
      cmpAtual = cmp;
      mostra();
    } catch (CafeteiraException ex) {
      ex.printStackTrace();
    }
  }

  private void mostra() {
    this.setTitle("Ajusta Objeto");
    this.setResizable(true);
    // Objeto das Abas
    JTabbedPane abas = new JTabbedPane();

    // 1a Aba - Propriedades
    JPanel panPropriedade = new JPanel(new GridLayout(7, 2, 5, 5));
    panPropriedade.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    panPropriedade.add(new JLabel("Esquerda:"));
    proEsquerda = new JTextField("");
    proEsquerda.addActionListener(e -> setAjustes());
    panPropriedade.add(proEsquerda);
    panPropriedade.add(new JLabel("Topo:"));
    proTopo = new JTextField("");
    proTopo.addActionListener(e -> setAjustes());
    panPropriedade.add(proTopo);
    panPropriedade.add(new JLabel("Largura:"));
    proLargura = new JTextField("");
    proLargura.addActionListener(e -> setAjustes());
    panPropriedade.add(proLargura);
    panPropriedade.add(new JLabel("Altura:"));
    proAltura = new JTextField("");
    proAltura.addActionListener(e -> setAjustes());
    panPropriedade.add(proAltura);
    panPropriedade.add(new JLabel("Legenda"));
    proLegenda = new JTextField("");
    proLegenda.addActionListener(e -> setAjustes());
    panPropriedade.add(proLegenda, null);
    panPropriedade.add(new JLabel("Nome:"));
    proNome = new JTextField("");
    proNome.addActionListener(e -> setNomComp());
    panPropriedade.add(proNome);
    panPropriedade.add(new JLabel("Sugest\343o:"));
    proSugestao = new JTextField("");
    proSugestao.addActionListener(e -> setAjustes());
    panPropriedade.add(proSugestao, null);
    abas.addTab("Propriedades", panPropriedade);

    // 2a Aba - Eventos
    JPanel panEvento = new JPanel(new GridLayout(5, 1, 5, 5));
    panEvento.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    evento1 = new JCheckBox("ao Pressionar", false);
    evento1.addItemListener(e -> setEventos(1, (e.getStateChange() == ItemEvent.SELECTED)));
    panEvento.add(evento1);
    evento2 = new JCheckBox("ao Clicar", false);
    evento2.addItemListener(e -> setEventos(2, (e.getStateChange() == ItemEvent.SELECTED)));
    panEvento.add(evento2);
    evento4 = new JCheckBox("ao Acionar", false);
    evento4.addItemListener(e -> setEventos(4, (e.getStateChange() == ItemEvent.SELECTED)));
    panEvento.add(evento4);
    evento8 = new JCheckBox("ao Ganhar Foco", false);
    evento8.addItemListener(e -> setEventos(8, (e.getStateChange() == ItemEvent.SELECTED)));
    panEvento.add(evento8);
    evento16 = new JCheckBox("ao Perder Foco", false);
    evento16.addItemListener(e -> setEventos(16, (e.getStateChange() == ItemEvent.SELECTED)));
    panEvento.add(evento16);
    abas.addTab("Eventos", panEvento);

    this.add(abas, BorderLayout.CENTER);

    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        aoFechar();
      }
    });
    getAjustes();
    getEventos();
    // Carrega Combo - Tem que ser a ultima
    cbComponente = new JComboBox<>();
    carrCbComp();
  }

  /**
   * Coloca o componente recebido como padrao na janela
   * 
   * @param cmp Componente
   */
  public void setComponente(JComponent cmp) {
    cmpAtual = cmp;
    cbComponente.setSelectedItem(cmpAtual.getName());
    getAjustes();
    getEventos();
  }

  /** Carrega o JComboBox de componentes */
  public void carrCbComp() {
    if (cbComponente != null) {
      this.getContentPane().remove(cbComponente);
    }
    Component[] comps = frmModi.getContentPane().getComponents();
    String[] nomComps = new String[comps.length];
    JComponent cmp;
    for (int i = 0; i < comps.length; i++) {
      cmp = (JComponent) comps[i];
      nomComps[i] = cmp.getName();
    }
    // Cria Combo
    cbComponente = new JComboBox<>(nomComps);
    this.add(cbComponente, BorderLayout.NORTH);
    cbComponente.addItemListener(e -> setComponenteCombo());
    this.pack();
    this.setSize(232, 292);
    cbComponente.setSelectedItem(cmpAtual.getName());
  }

  private void setComponenteCombo() {
    if (!(cmpAtual.getName().equals(cbComponente.getSelectedItem()))) {
      Component[] comps = frmModi.getContentPane().getComponents();
      String nomComp = "";
      for (int i = 0; i < comps.length; i++) {
        nomComp = comps[i].getName();
        if (nomComp.equals(cbComponente.getSelectedItem())) {
          cmpAtual = (JComponent) comps[i];
          break;
        }
      }
      getAjustes();
      getEventos();
    }
  }

  private void getAjustes() {
    AnalisaJComponent cmpAnalisado = new AnalisaJComponent();
    proEsquerda.setText("" + cmpAtual.getX());
    proTopo.setText("" + cmpAtual.getY());
    proLargura.setText("" + cmpAtual.getWidth());
    proAltura.setText("" + cmpAtual.getHeight());
    proLegenda.setText(cmpAnalisado.getText(cmpAtual));
    proNome.setText(cmpAtual.getName());
    proSugestao.setText(cmpAtual.getToolTipText());
  }

  private void setAjustes() {
    AnalisaJComponent cmpAnalisado = new AnalisaJComponent();
    cmpAtual.setBounds(Integer.parseInt(proEsquerda.getText()), Integer.parseInt(proTopo.getText()),
        Integer.parseInt(proLargura.getText()), Integer.parseInt(proAltura.getText()));
    cmpAnalisado.setText(cmpAtual, proLegenda.getText());
    cmpAtual.setToolTipText(proSugestao.getText());
  }

  private void setNomComp() {
    cmpAtual.setName(proNome.getText());
    carrCbComp();
  }

  private void getEventos() {
    AnalisaJComponent cmpAnalisado = new AnalisaJComponent();
    clickCheck = false;
    int numEvento = cmpAnalisado.getNumEvento(cmpAtual);
    evento16.setSelected(numEvento - 16 >= 0);
    if (evento16.isSelected()) {
      numEvento -= 16;
    }
    evento8.setSelected(numEvento - 8 >= 0);
    if (evento8.isSelected()) {
      numEvento -= 8;
    }
    evento4.setSelected(numEvento - 4 >= 0);
    if (evento4.isSelected()) {
      numEvento -= 4;
    }
    evento2.setSelected(numEvento - 2 >= 0);
    if (evento2.isSelected()) {
      numEvento -= 2;
    }
    evento1.setSelected(numEvento - 1 >= 0);
    clickCheck = true;
  }

  private void setEventos(int num, boolean selec) {
    if (clickCheck) {
      AnalisaJComponent cmpAnalisado = new AnalisaJComponent();
      int numEvento = cmpAnalisado.getNumEvento(cmpAtual);
      cmpAnalisado.setNumEvento(cmpAtual, (selec) ? numEvento + num : numEvento - num);
    }
  }

  /** Encerra a janela */
  public void aoFechar() {
    frmModi.setAjObjeto(false);
    dispose();
  }
}