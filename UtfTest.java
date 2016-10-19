import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class UtfTest {
  private static final String CHARSET = "UTF-8";

  public static void main(String args[]) {

    try{
      String path = "/Users/benpoole/Desktop/";
      File f = new File(path);
      f.mkdirs();

      StringBuffer buff = new StringBuffer(
      "Here we go, some charset testing. I'm going to send some extended Latin, Cuneiform, Runic, "
      + "Cyrillic, Brahmi, Japanese and Chinese stuff. Oh yes...\n");
      buff
      .append("\u3091 \u0506 \u3082 \u307D \u2030 \u0E58 \u00AB \u00E0 \u00E7 \u00F8 \u304C \uFB46 \u1200E \u16A1");

      Writer writer = new OutputStreamWriter(new FileOutputStream(path + "output.txt", false),
      CHARSET);
      writer.write(buff.toString(), 0, buff.length());
      writer.close();
    }catch(Exception e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }

}
