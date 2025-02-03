import com.google.gson.Gson;

import java.util.Arrays;
// import com.dampcake.bencode.Bencode; - available if you need it!

public class Main {
  private static final Gson gson = new Gson();

  public static void main(String[] args) throws Exception {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!");
    
    String command = args[0];
    if("decode".equals(command)) {
      //  Uncomment this block to pass the first stage
        String bencodedValue = args[1];
        String decoded;
        try {
          decoded = decodeBencode(bencodedValue.toCharArray());
        } catch(RuntimeException e) {
          System.out.println(e.getMessage());
          return;
        }
        System.out.println(gson.toJson(decoded));

    } else {
      System.out.println("Unknown command: " + command);
    }

  }

  static String decodeBencode(char[] bencodedString) {
    StringBuilder lengthSB = new StringBuilder();
    if (Character.isDigit(bencodedString[0])) {
      int firstColonIndex = 0;
      for(int i = 0; i < bencodedString.length; i++) {
        if(bencodedString[i] == ':') {
          firstColonIndex = i;
          break;
        }
        lengthSB.append(bencodedString[i]);
      }
      int length = Integer.parseInt(lengthSB.toString());
      return new String(Arrays.copyOfRange(bencodedString,firstColonIndex+1,firstColonIndex+1+length));
    } else {
      throw new RuntimeException("Only strings are supported at the moment");
    }
  }
  
}
