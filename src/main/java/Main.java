import com.dampcake.bencode.Type;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import com.dampcake.bencode.Bencode;

public class Main {
  private static final Gson gson = new Gson();
  private static final Bencode bencode = new Bencode();
  public static void main(String[] args) throws Exception {
    System.err.println("Logs from your program will appear here!");
    String command = args[0];
    switch (command) {
      case "decode":
        String bencodedValue = args[1];
        decode(bencodedValue);
        break;
      case "info":
        String filename = args[1];
        String content = readFile(filename);
        System.out.println("===========================================================================================================");
        System.out.println(content);
        decode(content);
        break;
    }
  }
  private static String readFile(String filename) throws Exception {
    return new String(Files.readAllBytes(Path.of(filename)), StandardCharsets.UTF_8);
  }
  private static void decode(String bencodedValue) {
    Type type = getType(bencodedValue);
    if(type == Type.STRING){
      String decoded = bencode.decode(bencodedValue.getBytes(), Type.STRING);
      System.out.println(gson.toJson(decoded));
    }
    if(type == Type.NUMBER){
      Long number = bencode.decode(bencodedValue.getBytes(), Type.NUMBER);
      System.out.println(gson.toJson(number));
    }
    if(type == Type.LIST){
      List<Object> list = bencode.decode(bencodedValue.getBytes(), Type.LIST);
      System.out.println(gson.toJson(list));
    }
    if(type == Type.DICTIONARY){
      Map<String, Object> dict = bencode.decode(bencodedValue.getBytes(), Type.DICTIONARY);
      System.out.println(gson.toJson(dict));
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
