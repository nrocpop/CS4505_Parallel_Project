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

//    public static String EncodeBase64(File f)throws IOException{
//    FileInputStream fis = new FileInputStream(f);
//    byte[] fileBytes =new byte[(int)f.length()];
//    fis.read(fileBytes);
//    String encodedFile = Base64.getEncoder().encodeToString(fileBytes);
//    return encodedFile;
//}

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
        Long current = 0l;
        Long FileSize = myFile.length();

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