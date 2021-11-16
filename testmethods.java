import java.io.*;
import java.util.Base64;

public class testmethods {
public static String EncodeBase64(byte[] b)throws IOException{
    return Base64.getEncoder().encodeToString(b);

}
public static byte[] DecodeBase64(String fileString){
      return Base64.getDecoder().decode(fileString);
}
    public static void main(String[] args)throws IOException {

        File myFile = new File("C:\\Users\\xbato\\Desktop\\T3.mkv");
        File myCopy = new File ("C:\\Users\\xbato\\Desktop\\myCopy.mkv");

        FileInputStream fis = new FileInputStream(myFile);
        byte[] partToSend = new byte[1024];
        byte[] partRcv = new byte[1024];
        FileOutputStream fos = new FileOutputStream(myCopy);
        String Encoded;
        long current = 0l;
        long FileSize = myFile.length();

        while(current <= FileSize) {
            fis.read(partToSend);
            Encoded = EncodeBase64(partToSend);

            partRcv = DecodeBase64(Encoded);
            fos.write(partRcv);
            fos.flush();
            current += 1024l;
        }
        fos.close();
        System.out.print("FIN");


    }

}