package cafeteira.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import cafeteira.AjustaObjeto;
import cafeteira.Principal;
import cafeteira.uteis.CafeteiraException;

/**
 * Cria os componentes na Janela Principal
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class CriaComponente {
  // Referencia
  private Principal frmModi;
  // cursor
  private Cursor cursorAtual;
  // do componente
  private Component cmpMove;
  private JComponent cmpSelecionado;
  private Rectangle oldCaixaMov;
  private int moveStartX;
  private int moveStartY;
  // do Menu do Objeto
  private JPopupMenu popMenu;
  private JMenuItem itmPropriedade;
  private JMenuItem itmExclui;
  private AnalisaJComponent ajc;

  /**
   * Construtor da classe
   * 
   * @param recModi Janela principal
   */
  public CriaComponente(Principal recModi) {
    frmModi = recModi;
    cursorAtual = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    cmpMove = null;
    cmpSelecionado = null;
    popMenu = new JPopupMenu();
    itmPropriedade = new JMenuItem("Propriedades");
    itmExclui = new JMenuItem("Exclui");
    ajc = new AnalisaJComponent();
  }

  /**
   * Cria determinado objeto em uma posicao determinada pelo mouse 1 - Cria
   * CafTextField 2 - Cria CafPasswordField 3 - CafButton 4 - CafList 5 -
   * CafTextArea 6 - CafRadioButton 7 - CafCheckBox 8 - CafLabel 9 - CafComboBox
   * 
   * @param e        objeto MouseEvent contendo a posicao do mouse
   * @param objCriar Inteiro contendo o numero do objeto
   */
  public void criarObjeto(MouseEvent e, int objCriar) {
    switch (objCriar) {
    case CafTextField.NUMERO: // CafTextField
      CafTextField jTextField1 = new CafTextField("", frmModi);
      jTextField1.setBounds(new Rectangle(e.getX() - 15, e.getY() - 25, 100, 21));
      insEvento(jTextField1);
      frmModi.getContentPane().add(jTextField1, null);
      break;
    case CafPasswordField.NUMERO: // CafPasswordField
      CafPasswordField jPasswordField1 = new CafPasswordField("", frmModi);
      jPasswordField1.setBounds(new Rectangle(e.getX() - 15, e.getY() - 25, 100, 21));
      insEvento(jPasswordField1);
      frmModi.getContentPane().add(jPasswordField1, null);
      break;
    case CafButton.NUMERO: // CafButton
      CafButton jButton1 = new CafButton("Button", frmModi);
      jButton1.setBounds(new Rectangle(e.getX() - 15, e.getY() - 25, 100, 30));
      insEvento(jButton1);
      frmModi.getContentPane().add(jButton1, null);
      break;
    case CafList.NUMERO: // CafList
      CafList jList1 = new CafList(frmModi);
      jList1.setBounds(new Rectangle(e.getX() - 15, e.getY() - 25, 130, 160));
      insEvento(jList1);
      frmModi.getContentPane().add(jList1, null);
      break;
    case CafTextArea.NUMERO: // CafTextArea
      CafTextArea jTextArea1 = new CafTextArea("", frmModi);
      jTextArea1.setBounds(new Rectangle(e.getX() - 15, e.getY() - 25, 130, 80));
      insEvento(jTextArea1);
      frmModi.getContentPane().add(jTextArea1, null);
      break;
    case CafRadioButton.NUMERO: // CafRadioButton
      CafRadioButton jRadioButton1 = new CafRadioButton("RadioButton", frmModi);
      jRadioButton1.setBounds(new Rectangle(e.getX() - 15, e.getY() - 25, 100, 30));
      insEvento(jRadioButton1);
      frmModi.getContentPane().add(jRadioButton1, null);
      break;
    case CafCheckBox.NUMERO: // CafCheckBox
      CafCheckBox jCheckBox1 = new CafCheckBox("CheckBox", frmModi);
      jCheckBox1.setBounds(new Rectangle(e.getX() - 15, e.getY() - 25, 100, 30));
      insEvento(jCheckBox1);
      frmModi.getContentPane().add(jCheckBox1, null);
      break;
    case CafLabel.NUMERO: // CafLabel
      CafLabel jLabel1 = new CafLabel("Label", frmModi);
      jLabel1.setBounds(new Rectangle(e.getX() - 15, e.getY() - 25, 57, 13));
      insEvento(jLabel1);
      frmModi.getContentPane().add(jLabel1, null);
      break;
    case CafComboBox.NUMERO: // CafComboBox
      CafComboBox jComboBox1 = new CafComboBox(frmModi);
      jComboBox1.setBounds(new Rectangle(e.getX() - 15, e.getY() - 25, 57, 20));
      insEvento(jComboBox1);
      frmModi.getContentPane().add(jComboBox1, null);
      break;
    default: // Erro
      throw new CafeteiraException("Classe não Reconhecida");
    }
    if (frmModi.isAjObjeto()) {
      frmModi.getJanAjusta().carrCbComp();
    }
    frmModi.repaint();
  }

  /**
   * Cria determinado objeto a partir de uma informacao salva 1 - Cria
   * CafTextField 2 - Cria CafPasswordField 3 - CafButton 4 - CafList 5 -
   * CafTextArea 6 - CafRadioButton 7 - CafCheckBox 8 - CafLabel 9 - CafComboBox
   * 
   * @param objCriar Inteiro contendo o numero do objeto
   * @param nome     String contendo o nome
   * @param posX     Inteiro para a posicao a esquerda
   * @param posY     Inteiro para a posicao do topo
   * @param posW     Inteiro para a largura
   * @param posH     Inteiro para a altura
   * @param texto    String contendo o "text" do objeto
   * @param hint     String contendo a "sugestao" do objeto
   * @param nEvt     Inteiro contendo os eventos
   */
  public void criaObjetoDoArquivo(int objCriar, String nome, int posX, int posY, int posW, int posH, String texto,
      String hint, int nEvt) {
    switch (objCriar) {
    case CafTextField.NUMERO: // CafTextField
      CafTextField jTextField1 = new CafTextField("", frmModi);
      jTextField1.setBounds(new Rectangle(posX, posY, posW, posH));
      insEvento(jTextField1, nome, texto, hint, nEvt);
      frmModi.getContentPane().add(jTextField1, null);
      break;
    case CafPasswordField.NUMERO: // CafPasswordField
      CafPasswordField jPasswordField1 = new CafPasswordField("", frmModi);
      jPasswordField1.setBounds(new Rectangle(posX, posY, posW, posH));
      insEvento(jPasswordField1, nome, texto, hint, nEvt);
      frmModi.getContentPane().add(jPasswordField1, null);
      break;
    case CafButton.NUMERO: // CafButton
      CafButton jButton1 = new CafButton("Button", frmModi);
      jButton1.setBounds(new Rectangle(posX, posY, posW, posH));
      insEvento(jButton1, nome, texto, hint, nEvt);
      frmModi.getContentPane().add(jButton1, null);
      break;
    case CafList.NUMERO: // CafList
      CafList jList1 = new CafList(frmModi);
      jList1.setBounds(new Rectangle(posX, posY, posW, posH));
      insEvento(jList1, nome, texto, hint, nEvt);
      frmModi.getContentPane().add(jList1, null);
      break;
    case CafTextArea.NUMERO: // CafTextArea
      CafTextArea jTextArea1 = new CafTextArea("", frmModi);
      jTextArea1.setBounds(new Rectangle(posX, posY, posW, posH));
      insEvento(jTextArea1, nome, texto, hint, nEvt);
      frmModi.getContentPane().add(jTextArea1, null);
      break;
    case CafRadioButton.NUMERO: // CafRadioButton
      CafRadioButton jRadioButton1 = new CafRadioButton("RadioButton", frmModi);
      jRadioButton1.setBounds(new Rectangle(posX, posY, posW, posH));
      insEvento(jRadioButton1, nome, texto, hint, nEvt);
      frmModi.getContentPane().add(jRadioButton1, null);
      break;
    case CafCheckBox.NUMERO: // CafCheckBox
      CafCheckBox jCheckBox1 = new CafCheckBox("CheckBox", frmModi);
      jCheckBox1.setBounds(new Rectangle(posX, posY, posW, posH));
      insEvento(jCheckBox1, nome, texto, hint, nEvt);
      frmModi.getContentPane().add(jCheckBox1, null);
      break;
    case CafLabel.NUMERO: // CafLabel
      CafLabel jLabel1 = new CafLabel("Label", frmModi);
      jLabel1.setBounds(new Rectangle(posX, posY, posW, posH));
      insEvento(jLabel1, nome, texto, hint, nEvt);
      frmModi.getContentPane().add(jLabel1, null);
      break;
    case CafComboBox.NUMERO: // CafComboBox
      CafComboBox jComboBox1 = new CafComboBox(frmModi);
      jComboBox1.setBounds(new Rectangle(posX, posY, posW, posH));
      insEvento(jComboBox1, nome, texto, hint, nEvt);
      frmModi.getContentPane().add(jComboBox1, null);
      break;
    default: 
      throw new CafeteiraException("Erro na Criação do Objeto");
    }
  }

  private void insEvento(JComponent cmp) {
    cmp.setName("objeto" + frmModi.addSeqObj());
    // Menu do Objeto
    popMenu.add(itmPropriedade);
    popMenu.add(itmExclui);
    // Eventos do Menu do Objeto
    itmPropriedade.addActionListener(e -> propObjeto());
    itmExclui.addActionListener(e -> excObjeto());
    // Eventos de Mouse
    cmp.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        aoClicarMouse(evt);
      }
      @Override
      public void mousePressed(MouseEvent evt) {
        aoPressionarMouse(evt);
      }
      @Override
      public void mouseReleased(MouseEvent evt) {
        aoSoltarMouse(evt);
      }
    });
    cmp.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent evt) {
        aoArrastarMouse(evt);
      }
      @Override
      public void mouseMoved(MouseEvent evt) {
        aoMoverMouse(evt);
      }
    });
    cmpSelecionado = cmp;
  }

  private void insEvento(JComponent cmp, String nome, String texto, String hint, int nEvt) {
    cmp.setName(nome);
    cmp.setToolTipText(hint);
    // Coloca as propriedades especiais
    AnalisaJComponent cmpAnalisado = new AnalisaJComponent();
    cmpAnalisado.setText(cmp, texto);
    cmpAnalisado.setNumEvento(cmp, nEvt);
    // Menu do Objeto
    popMenu.add(itmPropriedade);
    popMenu.add(itmExclui);
    // Eventos do Menu do Objeto
    itmPropriedade.addActionListener(e -> propObjeto());
    itmExclui.addActionListener(e -> excObjeto());
    // Eventos de Mouse
    cmp.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        aoClicarMouse(evt);
      }
      @Override
      public void mousePressed(MouseEvent evt) {
        aoPressionarMouse(evt);
      }
      @Override
      public void mouseReleased(MouseEvent evt) {
        aoSoltarMouse(evt);
      }
    });
    cmp.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent evt) {
        aoArrastarMouse(evt);
      }
      @Override
      public void mouseMoved(MouseEvent evt) {
        aoMoverMouse(evt);
      }
    });
    cmpSelecionado = cmp;
  }

  private void propObjeto() {
    if (cmpSelecionado != null) {
      if (frmModi.isAjObjeto()) {
        frmModi.getJanAjusta().setComponente(cmpSelecionado);
      } else {
        frmModi.setJanAjusta(new AjustaObjeto(cmpSelecionado, frmModi));
        frmModi.setAjObjeto(true);
      }
      frmModi.getJanAjusta().setVisible(true);
    }
  }

  private void excObjeto() {
    if (cmpSelecionado != null) {
      frmModi.remove(cmpSelecionado);
      if (frmModi.isAjObjeto()) {
        frmModi.getJanAjusta().aoFechar();
        frmModi.setAjObjeto(false);
      }
      frmModi.repaint();
    }
  }

  private boolean naoResize() {
    return (!ajc.resize(cmpSelecionado));
  }

  private void aoClicarMouse(MouseEvent evt) {
    if (naoResize())
      if (evt.isMetaDown())
        popMenu.show(evt.getComponent(), evt.getX(), evt.getY());
  }

  private void aoPressionarMouse(MouseEvent evt) {
    Component cmp = evt.getComponent();
    if (cmp != null) {
      cursorAtual = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
      frmModi.setCursor(cursorAtual);
      if (naoResize())
        startMove(cmp, evt.getX(), evt.getY());
    }
  }

  private void aoSoltarMouse(MouseEvent evt) {
    int x = evt.getX();
    int y = evt.getY();
    Component cmp = evt.getComponent();
    if (naoResize()) {
      if (cmp != null) {
        deleteCaixaMov();
        finishMove(x, y);
      }
    }
    cursorAtual = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    frmModi.setCursor(cursorAtual);
    frmModi.repaint();
  }

  private synchronized void aoArrastarMouse(MouseEvent evt) {
    int x = evt.getX();
    int y = evt.getY();
    Component cmp = evt.getComponent();
    if (cmp != null)
      if (naoResize())
        drawCaixaMov(getMoveBox(x + 2, y + 23));
  }

  private synchronized void aoMoverMouse(MouseEvent evt) {
    if (naoResize()) {
      if (cursorAtual == Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)) {
        Component cmp = evt.getComponent();
        if (cmp != null)
          startMove(cmp, evt.getX(), evt.getY());
      }
    }
  }

  private void drawCaixaMov(Rectangle box) {
    Graphics g = frmModi.getGraphics();
    if (g != null) {
      deleteCaixaMov();
      g.setColor(Color.red);
      g.drawRect(box.x, box.y, box.width, box.height);
      oldCaixaMov = box;
    }
  }

  private void deleteCaixaMov() {
    if (oldCaixaMov != null) {
      Graphics g = frmModi.getGraphics();
      if (g != null) {
        g.setColor(frmModi.getBackground());
        g.drawRect(oldCaixaMov.x, oldCaixaMov.y, oldCaixaMov.width, oldCaixaMov.height);
        oldCaixaMov = null;
      }
    }
  }

  private void startMove(Component child, int x, int y) {
    cmpMove = child;
    moveStartX = x;
    moveStartY = y;
  }

  private Rectangle getMoveBox(int mx, int my) {
    int x = cmpMove.getLocation().x;
    int y = cmpMove.getLocation().y;
    int w = cmpMove.getSize().width;
    int h = cmpMove.getSize().height;
    x = x + mx - moveStartX;
    y = y + my - moveStartY;
    return new Rectangle(x, y, w, h);
  }

  private void finishMove(int mx, int my) {
    Rectangle box = getMoveBox(mx, my);
    cmpMove.setBounds(box.x, box.y, box.width, box.height);
    deleteCaixaMov();
    cmpMove = null;
  }
}