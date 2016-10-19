import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class UtfCode {

  private static final String OLD   = "Cp1252";
  private static final String NEW   = "UTF-8";
  private static final String DELIM = "=============================================";

  public static void main(String[] args) {
    UtfCode u = new UtfCode();
    u.doTheThing();
  }

  // You can try to set the environment variable called
  // ANT_OPTS (or JAVA_TOOL_OPTIONS) to -Dfile.encoding=UTF8
  public void doTheThing() {
    try {
      String[] propsList = { "file.encoding", "ibm.system.encoding", "os.encoding",
          "sun.jnu.encoding", "ibm.system.encoding" };

      PrintStream out = new PrintStream(System.out, true, NEW);
      out.println(DELIM);
      out.println("Everything is set to " + OLD + ", System out set to " + NEW);

      for (String s : propsList) {
        System.setProperty(s, OLD);
      }

      out.println("施华洛世奇");
      out.println("World");
      System.out.println("Now using standard out:");
      System.out.println("施华洛世奇");
      out.println(DELIM);
    } catch (UnsupportedEncodingException meh) {
      meh.printStackTrace();
    }
  }

}
