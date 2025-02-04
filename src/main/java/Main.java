import com.dampcake.bencode.Type;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import com.dampcake.bencode.Bencode;

public class Main {
  private static final Gson gson = new Gson();
  private static final Bencode bencode = new Bencode();
  public static void main(String[] args) throws Exception {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!");
    String command = args[0];
    String bencodedValue = args[1];
    switch (command) {
      case "decode":
        Type type = getType(bencodedValue);
        if(type == Type.STRING){
          String decoded = bencode.decode(bencodedValue.getBytes(), Type.STRING);
          System.out.println(gson.toJson(decoded));
        }
        if(type == Type.NUMBER){
          Long number = bencode.decode(bencodedValue.getBytes(), Type.NUMBER);
          System.out.println(gson.toJson(number));
        }
        break;
    }
  }
  public static Type getType(String bencodedValue) {
    char []c = bencodedValue.toCharArray();
    if(Character.isDigit(c[0])) {
      return Type.STRING;
    }else if(c[0]=='i'){
      return Type.NUMBER;
    }else if(c[0]=='l'){
      return Type.LIST;
    }else if(c[0]=='d'){
      return Type.DICTIONARY;
    }else{
      return Type.UNKNOWN;
    }
  }
}
