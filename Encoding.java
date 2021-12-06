import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class Encoding {

    public static String EncodeBase64(File f)throws IOException {
        FileInputStream fis = new FileInputStream(f);
        byte[] fileBytes =new byte[(int)f.length()];
        fis.read(fileBytes);
        String encodedFile = Base64.getEncoder().encodeToString(fileBytes);
        return encodedFile;
    }

    public static String EncodeBase64(byte[] b)throws IOException{
        return Base64.getEncoder().encodeToString(b);
    }
    //Decodes a Base64 string to a byte array
    public static byte[] DecodeBase64(String fileString){
        return Base64.getDecoder().decode(fileString);
    }
}
