package cafeteira.uteis;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.swing.JComponent;

import cafeteira.Principal;
import cafeteira.componentes.AnalisaJComponent;
import cafeteira.componentes.CriaComponente;

/**
 * Realiza o tratamento com arquivos do Projeto
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class TrataArquivo {

  // Referencia
  private Principal frmModi;

  /**
   * Construtor inicial
   * 
   * @param recModi Janela Principal
   */
  public TrataArquivo(Principal recModi) {
    frmModi = recModi;
  }

  /**
   * Abre o arquivo do Projeto
   * 
   * @return sucesso ou fracasso
   */
  public boolean abreArquivo() {
    boolean ret = false;
    FileDialog dig = new FileDialog(frmModi, "Abrir Arquivo", FileDialog.LOAD);
    dig.setDirectory("");
    dig.setFile("nome.caf");
    dig.setVisible(true);
    String nomArq = dig.getDirectory() + dig.getFile();
    try {
      if (new File(nomArq).exists()) {
        BufferedReader arqEntrada = new BufferedReader(new FileReader(nomArq));
        String linMnt = "";
        if ((linMnt = arqEntrada.readLine()) != null) {
          separaClasse(linMnt);
          while ((linMnt = arqEntrada.readLine()) != null)
            separaLinha(linMnt);
        }
        arqEntrada.close();
      }
      ret = true;
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return ret;
  }

  /**
   * Salva o arquivo do Projeto
   * 
   * @return sucesso ou fracasso
   */
  public boolean salvaArquivo() {
    boolean ret = false;
    FileDialog dig = new FileDialog(frmModi, "Salvar Arquivo", FileDialog.SAVE);
    dig.setDirectory("");
    dig.setFile("nome.caf");
    dig.setVisible(true);
    String nomArq = dig.getDirectory() + dig.getFile();
    try {
      PrintWriter arqSaida = new PrintWriter(new FileWriter(nomArq));
      TextArea saida = geraSaida();
      arqSaida.print(saida.getText());
      arqSaida.close();
      ret = true;
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return ret;
  }

  private void separaClasse(String lin) {
    StringTokenizer strTok = new StringTokenizer(lin, "|");
    if (strTok.countTokens() == 9) {
      frmModi.setNomJan(strTok.nextToken());
      frmModi.setNomPac(strTok.nextToken());
      int posX = Integer.parseInt(strTok.nextToken());
      int posY = Integer.parseInt(strTok.nextToken());
      frmModi.setSize(posX, posY);
      int posLT = Integer.parseInt(strTok.nextToken());
      int posLH = Integer.parseInt(strTok.nextToken());
      frmModi.setLocation(posLT, posLH);
      frmModi.setTitle(strTok.nextToken());
      int ckModal = Integer.parseInt(strTok.nextToken());
      frmModi.getCkbModal().setSelected((ckModal == 1));
      int ckRedimens = Integer.parseInt(strTok.nextToken());
      frmModi.getCkbRedimens().setSelected((ckRedimens == 1));
    }
  }

  private void separaLinha(String lin) {
    StringTokenizer strTok = new StringTokenizer(lin, "|");
    if (strTok.countTokens() == 9) {
      int objCriar = Integer.parseInt(strTok.nextToken());
      String nome = strTok.nextToken();
      int posX = Integer.parseInt(strTok.nextToken());
      int posY = Integer.parseInt(strTok.nextToken());
      int posW = Integer.parseInt(strTok.nextToken());
      int posH = Integer.parseInt(strTok.nextToken());
      String texto = strTok.nextToken().trim();
      String hint = strTok.nextToken().trim();
      int nEvt = Integer.parseInt(strTok.nextToken());
      // Cria o objeto na Janela
      CriaComponente cria = new CriaComponente(frmModi);
      cria.criaObjetoDoArquivo(objCriar, nome, posX, posY, posW, posH, texto, hint, nEvt);
    }
  }

  private TextArea geraSaida() {
    TextArea ret = new TextArea();
    ret.append(frmModi.getNomJan() + "|" + frmModi.getNomPac() + "|" + frmModi.getWidth() + "|" + frmModi.getHeight() + "|"
        + frmModi.getX() + "|" + frmModi.getY() + "|" + frmModi.getTitle() + "|"
        + (frmModi.getCkbModal().isSelected() ? "1" : "0") + "|" + (frmModi.getCkbRedimens().isSelected() ? "1" : "0") + "|"
        + '\n');
    AnalisaJComponent cmpAnalisado = new AnalisaJComponent();
    Component[] comps = frmModi.getContentPane().getComponents();
    for (int i = 0; i < comps.length; i++) {
      ret.append(cmpAnalisado.getAnaComponente((JComponent) comps[i]) + '\n');
    }
    return ret;
  }
}