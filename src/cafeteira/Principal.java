package cafeteira;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import cafeteira.componentes.AnalisaJComponent;
import cafeteira.componentes.CriaComponente;
import cafeteira.uteis.Atributo;
import cafeteira.uteis.TrataArquivo;

/**
 * Janela Pricipal de Trabalho
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class Principal extends JFrame {

  private static final long serialVersionUID = 1L;
  private JPopupMenu popMenu;
  private JMenuItem itmLegenda;
  private JMenuItem itmCorFundo;
  /** CheckBox se a janela eh Modal ou nao */
  private JCheckBox ckbModal;
  /** CheckBox se a janela eh Redimensionavel ou nao */
  private JCheckBox ckbRedimens;
  private JMenuItem itmCriaObj;
  private JMenuItem itmGerar;
  private JMenuItem itmAbrir;
  private JMenuItem itmSalvar;
  private JMenuItem itmLimpar;
  private JMenuItem itmSobre;
  private JMenuItem itmVersao;
  private JMenuItem itmFechar;

  // Variaveis de Controle

  /** Inteiro contendo o objeto que deve ser criado */
  protected int objCriar;
  /** Inteiro contendo o objeto selecionado para criar */
  private int seqObj;
  /** Logico para controlar se a janela de Ajustes dos Objetos ja esta aberta */
  private boolean ajObjeto;
  /** Logico para controlar se a janela de objetos ja esta aberta */
  protected boolean jnObjeto;
  /** Objeto para a janela de Ajustes dos Objetos */
  protected PalhetaObjeto janPalObj;
  /** Objeto para a janela de Criacao dos Objetos */
  private AjustaObjeto janAjusta;
  /** String contendo o Nome da classe para a geracao */
  private String nomJan;
  /** String contendo o Nome do Pacote para a geracao */
  private String nomPac;
  
  /** Cria a janela dos Objetos */
  public Principal() {
    try {
      objCriar = 0;
      seqObj = 0;
      ajObjeto = false;
      jnObjeto = false;
      nomJan = "Classe";
      nomPac = "Pacote";
      mostra();
    } catch (RuntimeException ex) {
      System.out.println("Problemas para executar a Janela Principal");
      System.out.println(ex.getMessage());
    }
  }

  private void mostra() {
    // Acerta os Atributos e Objetos necessarios
    this.setTitle(Atributo.TITULO + " - " + Atributo.CFVERSAO);
    this.getContentPane().setLayout(null);
    this.setResizable(true);
    this.setSize(336, 230);
    this.setLocationRelativeTo(null);

    this.mntMenu();
    // Eventos do Menu
    itmLegenda.addActionListener(e -> acionaColocarLegenda());
    itmCorFundo.addActionListener(e -> acionaCorFundo());
    itmCriaObj.addActionListener(e -> acionaCriarObj());
    itmGerar.addActionListener(e -> acionaGerar());
    itmAbrir.addActionListener(e -> acionaAbrir());
    itmSalvar.addActionListener(e -> acionaSalvar());
    itmLimpar.addActionListener(e -> acionaLimpar());
    itmSobre.addActionListener(e -> acionarSobreFernando());
    itmVersao.addActionListener(e -> acionarSobre());
    itmFechar.addActionListener(e -> acionarFechar());
    // Utilizar o ESC para liberar o Cursor
    KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
    getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
      private static final long serialVersionUID = 100L;
      public void actionPerformed(ActionEvent e) {
        terminarCursor();
      }
    });    
    // Eventos da Janela
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent evt) {
        clkMouse(evt);
      }
    });
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        acionarFechar();
      }
    });
    this.setVisible(true);
  }

  /** Coloca o titulo na Janela */
  private void acionaColocarLegenda() {
    String leg = (String)JOptionPane.showInputDialog(this, "Informe o titulo da Janela:", Atributo.TITULO, 3, null, null, "");
    if (leg != null)
      this.setTitle(leg);
  }

  /** Coloca cor de fundo na Janela */
  private void acionaCorFundo() {
    Color color = this.getBackground();
    color = JColorChooser.showDialog(null, "Escolha a Cor da Janela", color);
    if (color != null)
      this.getContentPane().setBackground(color);
  }

  /** Chamar a janela para criacao dos objetos */
  private void acionaCriarObj() {
    if (!(jnObjeto))
      janPalObj = new PalhetaObjeto(this);
    janPalObj.setVisible(true);
  }

  /** Abrir um projeto salvo do Cafeteira */
  private void acionaAbrir() {
    TrataArquivo arq = new TrataArquivo(this);
    if (arq.abreArquivo()) {
      JOptionPane.showMessageDialog(null, "Arquivo Carregado sem problemas.", Atributo.TITULO, JOptionPane.INFORMATION_MESSAGE);
      if (this.ajObjeto)
        this.janAjusta.carrCbComp();
      int h = this.getHeight();
      int w = this.getWidth();
      this.setSize(1, 1);
      this.setSize(w, h);
    } else
      JOptionPane.showMessageDialog(null, "Erro ao Carregar arquivo. Verique os dados.", Atributo.TITULO, JOptionPane.ERROR_MESSAGE);
  }

  /** Salvar um projeto do Cafeteira */
  private void acionaSalvar() {
    TrataArquivo arq = new TrataArquivo(this);
    if (arq.salvaArquivo()) {
      JOptionPane.showMessageDialog(null, "Arquivo salvo sem problemas.", Atributo.TITULO, JOptionPane.INFORMATION_MESSAGE);
      this.getContentPane().repaint();
    } else
      JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo. Verique os dados.", Atributo.TITULO, JOptionPane.ERROR_MESSAGE);
  }

  /** Remover os objetos da Janela */
  private void acionaLimpar() {
    if (this.ajObjeto) {
      this.janAjusta.aoFechar();
      this.ajObjeto = false;
    }
    this.getContentPane().removeAll();
    this.getContentPane().repaint();
  }

  /** Gerar o codigo da Classe */
  private void acionaGerar() {
    MstGerar gera = new MstGerar(this);
    if (nomJan == null)
      nomJan = "Classe";
    else
      gera.setVisible(true);
  }

  /** Mostrar dados sobre o autor */
  private void acionarSobreFernando() {
    new SobreFernando();
  }

  /** Mostrar dados sobre o Cafeteira */
  private void acionarSobre() {
    new SobreSistema();
  }

  /** Encerrar a aplicacao */
  private void acionarFechar() {
    System.exit(0);
  }
  
  private void terminarCursor() {
    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }

  private void clkMouse(MouseEvent ev) {
    if (getCursor() == Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)) {
      CriaComponente cria = new CriaComponente(this);
      try {
        cria.criarObjeto(ev, objCriar);
        this.refresh();
      } catch (RuntimeException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), Atributo.TITULO, JOptionPane.ERROR_MESSAGE);
      }
    } else if (ev.isMetaDown()) {
      popMenu.show(this, ev.getX(), ev.getY());
    }
  }

  /** Montar o menu */
  private void mntMenu() {
    popMenu = new JPopupMenu();
    itmLegenda = new JMenuItem("Titulo");
    itmCorFundo = new JMenuItem("Cor da Janela");
    JMenu mnuFormato = new JMenu("Formato da Janela");
    ckbModal = new JCheckBox("Modal");
    ckbRedimens = new JCheckBox("Redimensionar");
    itmCriaObj = new JMenuItem("Criar Objetos");
    itmGerar = new JMenuItem("Gerar");
    itmSalvar = new JMenuItem("Salvar Projeto");
    itmAbrir = new JMenuItem("Abrir Projeto");
    itmLimpar = new JMenuItem("Limpar");
    itmSobre = new JMenuItem("Fernando Anselmo");
    itmVersao = new JMenuItem(Atributo.CFVERSAO);
    itmFechar = new JMenuItem("Fechar");
    // Imagens do Menu
    itmLegenda.setIcon(Atributo.getImage("Legenda.gif"));
    itmCorFundo.setIcon(Atributo.getImage("JanCor.gif"));
    mnuFormato.setIcon(Atributo.getImage("Formato.gif"));
    itmCriaObj.setIcon(Atributo.getImage("CriaObj.gif"));
    itmGerar.setIcon(Atributo.getImage("Gerar.gif"));
    itmAbrir.setIcon(Atributo.getImage("Abrir.gif"));
    itmSalvar.setIcon(Atributo.getImage("Salvar.gif"));
    itmLimpar.setIcon(Atributo.getImage("ExcluirTudo.gif"));
    itmSobre.setIcon(Atributo.getImage("Fernando.gif"));
    itmVersao.setIcon(Atributo.getImage("Sobre.gif"));
    itmFechar.setIcon(Atributo.getImage("Fechar.gif"));
    // Montar o Menu
    mnuFormato.add(ckbModal);
    mnuFormato.add(ckbRedimens);
    popMenu.add(itmLegenda);
    popMenu.add(itmCorFundo);
    popMenu.add(mnuFormato);
    popMenu.add(itmCriaObj);
    popMenu.addSeparator();
    popMenu.add(itmGerar);
    popMenu.add(itmAbrir);
    popMenu.add(itmSalvar);
    popMenu.add(itmLimpar);
    popMenu.addSeparator();
    popMenu.add(itmSobre);
    popMenu.add(itmVersao);
    popMenu.addSeparator();
    popMenu.add(itmFechar);
  }

  /** Refazer a janela novamente */
  public void refresh() {
    this.repaint();
  }

  /**
   * Faz o Redimensionamento dos Objetos
   * 
   * @param resizeIncx Inteiro para a posicao a esquerda
   * @param resizeIncy Inteiro para a posicao do topo
   * @param incx       Inteiro para a largura do objeto
   * @param incy       Inteiro para a altura do objeto
   */
  public void resizeSelected(boolean resizeIncx, boolean resizeIncy, int incx, int incy) {
    AnalisaJComponent ajc = new AnalisaJComponent();
    Component[] comps = this.getContentPane().getComponents();
    for (int i = 0; i < comps.length; i++) {
      JComponent cmp = (JComponent) comps[i];
      if (!ajc.resize(cmp))
        continue;
      if (resizeIncx)
        cmp.setSize((cmp.getWidth() + incx), cmp.getHeight());
      if (resizeIncy)
        cmp.setSize(cmp.getWidth(), (cmp.getHeight() + incy));
    }
    this.refresh();
  }
  
  /**
   * Adicionar e obter o sequencial do objeto 
   * @return seqObj
   */
  public int addSeqObj() {
    return seqObj++;
  }

  
  // METODOS get/set
  
  public boolean isAjObjeto() {
    return ajObjeto;
  }

  public void setAjObjeto(boolean ajObjeto) {
    this.ajObjeto = ajObjeto;
  }
  
  public JCheckBox getCkbModal() {
    return ckbModal;
  }

  public JCheckBox getCkbRedimens() {
    return ckbRedimens;
  }
  
  public AjustaObjeto getJanAjusta() {
    return janAjusta;
  }

  public void setJanAjusta(AjustaObjeto janAjusta) {
    this.janAjusta = janAjusta;
  }
  
  public String getNomJan() {
    return nomJan;
  }

  public void setNomJan(String nomJan) {
    this.nomJan = nomJan;
  }

  public String getNomPac() {
    return nomPac;
  }

  public void setNomPac(String nomPac) {
    this.nomPac = nomPac;
  }

  /**
   * Metodo pricipal para iniciar a aplicacao
   * 
   * @param args Argumentos passados apenas para compor o metodo "main" (nao
   *             necessarios)
   */
  public static void main(String [] args) {
    Principal janela = new Principal();
    janela.acionarSobre();
  }

}