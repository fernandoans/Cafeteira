package cafeteira.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JTextArea;

import cafeteira.Principal;

/**
 * Versao para o cafeteira do JTextArea
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class CafTextArea extends JTextArea {

  private static final long serialVersionUID = 14L;
  public static final int NUMERO = 4;
  private boolean selected;
  private boolean resizing;
  private int selectRec;
  private static Cursor defCursor = new Cursor(0);
  private int resizex;
  private int resizey;
  private boolean resizeIncx;
  private boolean resizeIncy;
  private Principal frmModi;
  private int numEvento = 0;

  /**
   * Construtor da classe
   * 
   * @param titulo  String contendo o "text" para o CafTextArea
   * @param recModi Janela principal
   */
  public CafTextArea(String titulo, Principal recModi) {
    frmModi = recModi;
    this.setText(titulo);
    selected = false;
    resizing = true;
    selectRec = 6;

    resizex = 0;
    resizey = 0;
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        aoClicarMouse();
      }
      @Override
      public void mousePressed(MouseEvent evt) {
        aoPressionarMouse();
      }
    });
    this.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent evt) {
        aoArrastarMouse(evt);
      }
      @Override
      public void mouseMoved(MouseEvent evt) {
        aoMoverMouse(evt);
      }
    });
  }

  /**
   * Desenhar o objeto corretamente
   * 
   * @param g Objeto Graphics
   */
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    int wpixelsTMP = this.getWidth();
    int hpixelsTMP = this.getHeight();
    if (selected) {
      int side = selectRec;
      g.setColor(Color.black);
      g.fillRect(0, 0, side, side);
      g.fillRect(wpixelsTMP - side, 0, side, side);
      g.fillRect(0, hpixelsTMP - side, side, side);
      g.fillRect(wpixelsTMP - side, hpixelsTMP - side, side, side);
    }
  }

  /**
   * Modifica o redimensionamento do componente
   * 
   * @param val logico informando o redimensionamento
   */
  public void setResizing(boolean val) {
    resizing = val;
  }

  /**
   * Obtem o tamanho do componente
   * 
   * @return logico contendo o redimensionamento
   */
  public boolean getResizing() {
    return resizing;
  }

  private void aoMoverMouse(MouseEvent e) {
    int cx = e.getX();
    int cy = e.getY();
    if (!resizing && selected) {
      resizex = cx;
      resizey = cy;
      resizeIncx = true;
      resizeIncy = true;
      if ((double) cx > this.getWidth() - (double) selectRec && (double) cy > this.getHeight() - (double) selectRec) {
        setCursor(new Cursor(5));
        resizing = true;
        return;
      }
      if ((double) cx > this.getWidth() - (double) selectRec) {
        setCursor(new Cursor(11));
        resizing = true;
        resizeIncy = false;
        return;
      }
      if ((double) cy > this.getHeight() - (double) selectRec) {
        setCursor(new Cursor(9));
        resizing = true;
        resizeIncx = false;
        return;
      }
    }
    if (resizing) {
      setCursor(defCursor);
      resizing = false;
    }
  }

  private void aoClicarMouse() {
    selected = !selected;
    frmModi.refresh();
  }

  private void aoArrastarMouse(MouseEvent e) {
    if (resizing) {
      frmModi.resizeSelected(resizeIncx, resizeIncy, e.getX() - resizex, e.getY() - resizey);
      if (resizeIncx)
        resizex = e.getX();
      if (resizeIncy)
        resizey = e.getY();
    }
  }

  private void aoPressionarMouse() {
    if (resizing)
      selected = true;
    frmModi.refresh();
  }

  /**
   * Modifica o numero de eventos que este componente possui
   * 
   * @param newNumEvento Inteiro contendo o numero de evento
   */
  public void setNumEvento(int newNumEvento) {
    numEvento = newNumEvento;
  }

  /**
   * Obtem os eventos que este componente possui
   * 
   * @return Inteiro contendo o numero de eventos
   */
  public int getNumEvento() {
    return numEvento;
  }

  @Override
  public String toString() {
    return "JTextArea";
  }
}