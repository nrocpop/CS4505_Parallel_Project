import java.io.*;
import java.util.Base64;

public class testmethods {
    //public static void testing1(){
//    FileInputStream fi = null;
//    File f = new File("C:\\Users\\Alex\\Desktop\\Donut1440.png");
//    String encoded = EncodeBase64(f);
//
//    fi = new FileInputStream(f);
//    ByteArrayOutputStream bout = new ByteArrayOutputStream();
//    byte[] buffer = new byte[4096];
//    while(fi.read(buffer) != -1){
//        bout.write(buffer,0,buffer.length);
//    }
//    byte[] bytesOut = bout.toByteArray();
//    File newFile = new File("C:\\Users\\Alex\\Desktop\\marscopy.png");
//    FileOutputStream fos = new FileOutputStream(newFile);
//    fos.write(bytesOut);
//    fos.flush();
//    fos.close();
//}

    public static String EncodeBase64(File f)throws IOException{
    FileInputStream fis = new FileInputStream(f);
    byte[] fileBytes =new byte[(int)f.length()];
    fis.read(fileBytes);
    String encodedFile = Base64.getEncoder().encodeToString(fileBytes);
    return encodedFile;
}


public static byte[] DecodeBase64(String fileString){
       byte[] decoded = Base64.getDecoder().decode(fileString);
       return decoded;

}
    public static void main(String[] args)throws IOException {
        File myFile = new File("C:\\Users\\Alex\\Desktop\\Donut1440.png");
       String encoded = EncodeBase64(myFile);
       System.out.print(encoded);
       byte[] arr = DecodeBase64(encoded);
        File newFile = new File("C:\\Users\\Alex\\Desktop\\marscopy.png");
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(arr);
        fos.flush();
        fos.close();


    }

}