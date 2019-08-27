import javax.crypto.Cipher;

public class VerifyAmazonCorrettoCryptoProvider {
  private static void verify() throws Exception {
    String name = Cipher.getInstance("AES/GCM/NoPadding").getProvider().getName();

    System.out.println("provider: " + name);
  }

  public static void main(String[] args) throws Exception {
    verify();
  }
}
