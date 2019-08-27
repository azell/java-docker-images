import javax.crypto.Cipher;

public class Test {
  private static void check() throws Exception {
    String name = Cipher.getInstance("AES/GCM/NoPadding").getProvider().getName();

    System.out.println("provider: " + name);
  }

  public static void main(String[] args) throws Exception {
    check();
  }
}
