package cafeteira.uteis;

public class CafeteiraException extends RuntimeException {

  private static final long serialVersionUID = 100L;

  public CafeteiraException(String msg) {
    super(msg);
  }
}
