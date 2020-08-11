package cafeteira.uteis;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Versao atual e metodos gerais
 * 
 * @author Fernando Anselmo
 * @version 1.0
 */
public final class Atributo {

  /** Versao atual */
  public static final String CFVERSAO = "Versao 2.0";
  public static final String TITULO = "Cafeteira";
  
  private Atributo() {
    // Evitar de se criar a classe
  }
  
  /**
   * Devolve um objeto ImageIcon com determinada imagem
   * 
   * @param s String contendo o nome da imagem
   * @return Objeto ImageIcon
   */
  public static ImageIcon getImage(String s) {
    URL url = getResource("cafeteira/images/" + s);
    if (url != null)
      return new ImageIcon(url);
    else
      return null;
  }

  private static URL getResource(String s) {
    return ClassLoader.getSystemResource(s);
  }
}