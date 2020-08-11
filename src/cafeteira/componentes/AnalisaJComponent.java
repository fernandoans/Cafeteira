package cafeteira.componentes;

import java.util.StringTokenizer;

import javax.swing.JComponent;

/**
 * Analisa cada componente separadamente
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public class AnalisaJComponent {

  private final String[] classes = new String[] { "JTextField", "JPasswordField", "JButton", "JList", "JTextArea",
      "JRadioButton", "JCheckBox", "JLabel", "JComboBox" };
  private final String espaco4 = "\n    ";

  /**
   * Obtem o nome completo da classe e devolve apenas o nome da classe
   * 
   * @param classe Nome completo da classe
   * @return Nome da classe simples
   */
  public String getClasse(String classe) {
    StringTokenizer strTok = new StringTokenizer(classe, ".");
    if (strTok.countTokens() == 2) {
      strTok.nextToken();
      classe = strTok.nextToken();
    }
    return classe;
  }

  /**
   * Obtem o nome do componente e devolve a linha deste para salvar no arquivo do
   * tipo: classe:nome:posX:posY:posW:posH:text:hint:numEvt
   * 
   * @param cmp Componente
   * @return Linha formatada para salvar no arquivo
   */
  public String getAnaComponente(JComponent cmp) {
    String hint = " ";
    int posX = cmp.getX();
    int posY = cmp.getY();
    int posW = cmp.getWidth();
    int posH = cmp.getHeight();
    if (cmp.getToolTipText() != null)
      hint = "".equals(cmp.getToolTipText()) ? " " : cmp.getToolTipText();
    return this.getNumComp(cmp) + "|" + cmp.getName() + "|" + posX + "|" + posY + "|" + posW + "|" + posH + "|"
        + ("".equals(this.getText(cmp)) ? " " : getText(cmp)) + "|" + hint + "|" + this.getNumEvento(cmp);
  }

  /**
   * Obtem o nome do componente e devolve a linha deste para gerar no arquivo do
   * tipo: private JClasse nome;
   * 
   * @param cmp Componente
   * @return Linha completa para formar o bloco 1
   */
  public String getCriaComponente(JComponent cmp) {
    return "\n  private " + cmp.toString() + " " + cmp.getName() + ";";
  }

  /**
   * Obtem o nome do componente e devolve as linhas de propriedades deste para
   * gerar no arquivo do tipo: nome = new JClasse("text");
   * nome.setToolTipText("hint"); nome.setBounds(new
   * Rectangle(PosX,PosY,PosW,PosH)); this.getContentPane().add(nome, null);
   * 
   * @param cmp Componente
   * @return Linha completa para formar o bloco 3
   */
  public String getPropComponente(JComponent cmp) {
    String nome = cmp.getName();
    String classe = cmp.toString();
    String txtObj = espaco4 + nome + " = new " + classe + "("
        + ((this.getText(cmp).equals("")) ? "" : "\"" + getText(cmp) + "\"") + ");";
    int posX = cmp.getX();
    int posY = cmp.getY();
    int posW = cmp.getWidth();
    int posH = cmp.getHeight();
    if (cmp.getToolTipText() != null) {
      if (!(cmp.getToolTipText().trim().equals(""))) {
        txtObj += espaco4 + nome + ".setToolTipText(\"" + cmp.getToolTipText() + "\");";
      }
    }
    txtObj += espaco4 + nome + ".setBounds(new Rectangle(" + posX + ", " + posY + ", " + posW + ", " + posH + "));";
    txtObj += espaco4 + "this.getContentPane().add(" + nome + ", null);";
    return txtObj;
  }

  private String devTexto(CafTextField jTxF) {
    return jTxF.getText();
  }

  private String devTexto(CafPasswordField jPwF) {
    char[] senha = jPwF.getPassword();
    StringBuilder retTexto = new StringBuilder("");
    for (int i = 0; i < senha.length; i++) {
      retTexto.append(senha[i]);
    }
    return retTexto.toString();
  }

  private String devTexto(CafButton jBut) {
    return jBut.getText();
  }

  private String devTexto(CafList jLst) {
    return jLst.getText();
  }
  
  private String devTexto(CafTextArea jTxA) {
    return jTxA.getText();
  }

  private String devTexto(CafRadioButton jRdB) {
    return jRdB.getText();
  }

  private String devTexto(CafCheckBox jCkB) {
    return jCkB.getText();
  }

  private String devTexto(CafLabel jLab) {
    return jLab.getText();
  }

  private String devTexto(CafComboBox jCom) {
    return jCom.getText();
  }

  private void setTexto(CafTextField jTxF, String conteudo) {
    jTxF.setText(conteudo);
  }

  private void setTexto(CafPasswordField jPwF, String conteudo) {
    jPwF.setText(conteudo);
  }

  private void setTexto(CafButton jBut, String conteudo) {
    jBut.setText(conteudo);
  }

  private void setTexto(CafTextArea jTxA, String conteudo) {
    jTxA.setText(conteudo);
  }

  private void setTexto(CafRadioButton jRdB, String conteudo) {
    jRdB.setText(conteudo);
  }

  private void setTexto(CafCheckBox jCkB, String conteudo) {
    jCkB.setText(conteudo);
  }

  private void setTexto(CafLabel jLab, String conteudo) {
    jLab.setText(conteudo);
  }

  private void setTexto(CafList jLst, String conteudo) {
    jLst.setText(conteudo);
  }

  private void setTexto(CafComboBox jCmb, String conteudo) {
    jCmb.setText(conteudo);
  }

  /**
   * Devolve as linhas com o codigo da criacao dos eventos do componente
   * selecionado
   * 
   * @param cmp Componente
   * @return String contendo os eventos selecionados deste componente
   */
  public String getCriaEvento(JComponent cmp) {
    String txtObj = "";
    int numEvento = this.getNumEvento(cmp);
    if ((numEvento - 16) >= 0) { // evento PerdaFoco
      numEvento -= 16;
      txtObj += retornarEvento(cmp.getName(), "addFocusListener", "FocusAdapter", "focusLost", "FocusEvent");
    }
    if ((numEvento - 8) >= 0) { // evento GanhoFoco
      numEvento -= 8;
      txtObj += retornarEvento(cmp.getName(), "addFocusListener", "FocusAdapter", "focusGained", "FocusEvent");
    }
    if ((numEvento - 4) >= 0) { // evento aoAcionar
      numEvento -= 4;
      txtObj += retornarEvento(cmp.getName(), "addActionListener", "ActionListener", "actionPerformed", "ActionEvent");
    }
    if ((numEvento - 2) >= 0) { // evento aoClicar
      numEvento -= 2;
      txtObj += retornarEvento(cmp.getName(), "addMouseListener", "MouseAdapter", "mouseClicked", "MouseEvent");
    }
    if ((numEvento - 1) >= 0) { // evento aoPressionar
      txtObj += retornarEvento(cmp.getName(), "addKeyListener", "KeyAdapter", "keyPressed", "KeyEvent");
    }
    return txtObj;
  }

  private String retornarEvento(String nome, String listener, String adaptador, String metodo, String chave) {
    return espaco4 + nome + "." + listener + "(new " + adaptador + "() {" + espaco4 + "  @Override" + espaco4
        + "  public void " + metodo + "(" + chave + " e) {" + espaco4 + "  // Chamada a um metodo" + espaco4 + "  }"
        + espaco4 + "});";
  }

  // GETS/SETS
  
  private void setNumEvento(CafTextField jTxF, int num) {
    jTxF.setNumEvento(num);
  }

  private void setNumEvento(CafPasswordField jPwF, int num) {
    jPwF.setNumEvento(num);
  }

  private void setNumEvento(CafButton jBut, int num) {
    jBut.setNumEvento(num);
  }

  private void setNumEvento(CafList jLst, int num) {
    jLst.setNumEvento(num);
  }

  private void setNumEvento(CafTextArea jTxA, int num) {
    jTxA.setNumEvento(num);
  }

  private void setNumEvento(CafRadioButton jRdB, int num) {
    jRdB.setNumEvento(num);
  }

  private void setNumEvento(CafCheckBox jCkB, int num) {
    jCkB.setNumEvento(num);
  }

  private void setNumEvento(CafLabel jLab, int num) {
    jLab.setNumEvento(num);
  }

  private void setNumEvento(CafComboBox jCbx, int num) {
    jCbx.setNumEvento(num);
  }

  private int getNumEvento(CafTextField jTxF) {
    return jTxF.getNumEvento();
  }

  private int getNumEvento(CafPasswordField jPwF) {
    return jPwF.getNumEvento();
  }

  private int getNumEvento(CafList jLst) {
    return jLst.getNumEvento();
  }

  private int getNumEvento(CafButton jBut) {
    return jBut.getNumEvento();
  }

  private int getNumEvento(CafTextArea jTxA) {
    return jTxA.getNumEvento();
  }

  private int getNumEvento(CafRadioButton jRdB) {
    return jRdB.getNumEvento();
  }

  private int getNumEvento(CafCheckBox jCkB) {
    return jCkB.getNumEvento();
  }

  private int getNumEvento(CafLabel jLab) {
    return jLab.getNumEvento();
  }

  private int getNumEvento(CafComboBox jCbx) {
    return jCbx.getNumEvento();
  }

  // VERIFICACAO DOS COMPONENTES
  
  /**
   * Verifica se o componente foi redimensionado
   * 
   * @param cmp Componente
   * @return Logico
   */
  public boolean resize(JComponent cmp) {
    if (classes[CafTextField.NUMERO].equals(cmp.toString())) {
      return (((CafTextField) cmp).getResizing());
    } else if (classes[CafPasswordField.NUMERO].equals(cmp.toString())) {
      return (((CafPasswordField) cmp).getResizing());
    } else if (classes[CafButton.NUMERO].equals(cmp.toString())) {
      return (((CafButton) cmp).getResizing());
    } else if (classes[CafList.NUMERO].equals(cmp.toString())) {
      return (((CafList) cmp).getResizing());
    } else if (classes[CafTextArea.NUMERO].equals(cmp.toString())) {
      return (((CafTextArea) cmp).getResizing());
    } else if (classes[CafRadioButton.NUMERO].equals(cmp.toString())) {
      return (((CafRadioButton) cmp).getResizing());
    } else if (classes[CafCheckBox.NUMERO].equals(cmp.toString())) {
      return (((CafCheckBox) cmp).getResizing());
    } else if (classes[CafLabel.NUMERO].equals(cmp.toString())) {
      return (((CafLabel) cmp).getResizing());
    } else if (classes[CafComboBox.NUMERO].equals(cmp.toString())) {
      return (((CafComboBox) cmp).getResizing());
    }
    return false;
  }

  /**
   * Devolve (se tiver) a propriedade Text do Componente
   * 
   * @param cmp Componente
   * @return String contendo o "text" do componente
   */
  public String getText(JComponent cmp) {
    if (classes[CafTextField.NUMERO].equals(cmp.toString())) {
      return this.devTexto((CafTextField) cmp);
    } else if (classes[CafPasswordField.NUMERO].equals(cmp.toString())) {
      return this.devTexto((CafPasswordField) cmp);
    } else if (classes[CafButton.NUMERO].equals(cmp.toString())) {
      return this.devTexto((CafButton) cmp);
    } else if (classes[CafList.NUMERO].equals(cmp.toString())) {
      return this.devTexto((CafList) cmp);
    } else if (classes[CafTextArea.NUMERO].equals(cmp.toString())) {
      return this.devTexto((CafTextArea) cmp);
    } else if (classes[CafRadioButton.NUMERO].equals(cmp.toString())) {
      return this.devTexto((CafRadioButton) cmp);
    } else if (classes[CafCheckBox.NUMERO].equals(cmp.toString())) {
      return this.devTexto((CafCheckBox) cmp);
    } else if (classes[CafLabel.NUMERO].equals(cmp.toString())) {
      return this.devTexto((CafLabel) cmp);
    } else if (classes[CafComboBox.NUMERO].equals(cmp.toString())) {
      return this.devTexto((CafComboBox) cmp);
    }
    return "";
  }

  /**
   * Envia (se puder) o valor para a propriedade Text do Componente
   * 
   * @param cmp      Componente
   * @param conteudo String contendo a propriedade "text" para o componente
   */
  public void setText(JComponent cmp, String conteudo) {
    if (classes[CafTextField.NUMERO].equals(cmp.toString())) {
      this.setTexto((CafTextField) cmp, conteudo);
    } else if (classes[CafPasswordField.NUMERO].equals(cmp.toString())) {
      this.setTexto((CafPasswordField) cmp, conteudo);
    } else if (classes[CafButton.NUMERO].equals(cmp.toString())) {
      this.setTexto((CafButton) cmp, conteudo);
    } else if (classes[CafList.NUMERO].equals(cmp.toString())) {
      this.setTexto((CafList) cmp, conteudo);
    } else if (classes[CafTextArea.NUMERO].equals(cmp.toString())) {
      this.setTexto((CafTextArea) cmp, conteudo);
    } else if (classes[CafRadioButton.NUMERO].equals(cmp.toString())) {
      this.setTexto((CafRadioButton) cmp, conteudo);
    } else if (classes[CafCheckBox.NUMERO].equals(cmp.toString())) {
      this.setTexto((CafCheckBox) cmp, conteudo);
    } else if (classes[CafLabel.NUMERO].equals(cmp.toString())) {
      this.setTexto((CafLabel) cmp, conteudo);
    } else if (classes[CafComboBox.NUMERO].equals(cmp.toString())) {
      this.setTexto((CafComboBox) cmp, conteudo);
    }
  }

  /**
   * Envia o numero do Evento para o componente
   * 
   * @param cmp Componente
   * @param num inteiro para selecionar os eventos
   */
  public void setNumEvento(JComponent cmp, int num) {
    if (classes[CafTextField.NUMERO].equals(cmp.toString())) {
      this.setNumEvento((CafTextField) cmp, num);
    } else if (classes[CafPasswordField.NUMERO].equals(cmp.toString())) {
      this.setNumEvento((CafPasswordField) cmp, num);
    } else if (classes[CafButton.NUMERO].equals(cmp.toString())) {
      this.setNumEvento((CafButton) cmp, num);
    } else if (classes[CafList.NUMERO].equals(cmp.toString())) {
      this.setNumEvento((CafList) cmp, num);
    } else if (classes[CafTextArea.NUMERO].equals(cmp.toString())) {
      this.setNumEvento((CafTextArea) cmp, num);
    } else if (classes[CafRadioButton.NUMERO].equals(cmp.toString())) {
      this.setNumEvento((CafRadioButton) cmp, num);
    } else if (classes[CafCheckBox.NUMERO].equals(cmp.toString())) {
      this.setNumEvento((CafCheckBox) cmp, num);
    } else if (classes[CafLabel.NUMERO].equals(cmp.toString())) {
      this.setNumEvento((CafLabel) cmp, num);
    } else if (classes[CafComboBox.NUMERO].equals(cmp.toString())) {
      this.setNumEvento((CafComboBox) cmp, num);
    }
  }

  /**
   * Obtem o numero do Evento do componente
   * 
   * @param cmp Componente
   * @return Inteiro contendo os eventos selecionados
   */
  public int getNumEvento(JComponent cmp) {
    if (classes[CafTextField.NUMERO].equals(cmp.toString())) {
      return this.getNumEvento((CafTextField) cmp);
    } else if (classes[CafPasswordField.NUMERO].equals(cmp.toString())) {
      return this.getNumEvento((CafPasswordField) cmp);
    } else if (classes[CafButton.NUMERO].equals(cmp.toString())) {
      return this.getNumEvento((CafButton) cmp);
    } else if (classes[CafList.NUMERO].equals(cmp.toString())) {
      return this.getNumEvento((CafList) cmp);
    } else if (classes[CafTextArea.NUMERO].equals(cmp.toString())) {
      return this.getNumEvento((CafTextArea) cmp);
    } else if (classes[CafRadioButton.NUMERO].equals(cmp.toString())) {
      return this.getNumEvento((CafRadioButton) cmp);
    } else if (classes[CafCheckBox.NUMERO].equals(cmp.toString())) {
      return this.getNumEvento((CafCheckBox) cmp);
    } else if (classes[CafLabel.NUMERO].equals(cmp.toString())) {
      return this.getNumEvento((CafLabel) cmp);
    } else if (classes[CafComboBox.NUMERO].equals(cmp.toString())) {
      return this.getNumEvento((CafComboBox) cmp);
    }
    return 0;
  }

  /**
   * Obtem o numero do componente
   * 
   * @param cmp Componente
   * @return Inteiro contendo o numero
   */
  public int getNumComp(JComponent cmp) {
    if (classes[CafTextField.NUMERO].equals(cmp.toString())) {
      return CafTextField.NUMERO;
    } else if (classes[CafPasswordField.NUMERO].equals(cmp.toString())) {
      return CafPasswordField.NUMERO;
    } else if (classes[CafButton.NUMERO].equals(cmp.toString())) {
      return CafButton.NUMERO;
    } else if (classes[CafList.NUMERO].equals(cmp.toString())) {
      return CafList.NUMERO;
    } else if (classes[CafTextArea.NUMERO].equals(cmp.toString())) {
      return CafTextArea.NUMERO;
    } else if (classes[CafRadioButton.NUMERO].equals(cmp.toString())) {
      return CafRadioButton.NUMERO;
    } else if (classes[CafCheckBox.NUMERO].equals(cmp.toString())) {
      return CafCheckBox.NUMERO;
    } else if (classes[CafLabel.NUMERO].equals(cmp.toString())) {
      return CafLabel.NUMERO;
    } else if (classes[CafComboBox.NUMERO].equals(cmp.toString())) {
      return CafComboBox.NUMERO;
    }
    return -1;
  }
}