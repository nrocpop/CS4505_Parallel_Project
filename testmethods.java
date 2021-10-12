import java.io.*;

public class testmethods {

    public static void main(String[] args)throws IOException {
        FileInputStream fi = null;
        File f = new File("C:\\Users\\xbato\\Desktop\\Class Folders\\test\\mars.jpg");
        fi = new FileInputStream(f);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[256];
        //ByteArrayInputStream bin = new ByteArrayInputStream(buffer);

        while(fi.read(buffer) != -1){
            bout.write(buffer,0,256);
        }
        byte[] bytesOut = bout.toByteArray();
        File newFile = new File("C:\\Users\\xbato\\Desktop\\marscopy.png");
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(bytesOut);
        fos.flush();
        fos.close();

    }

}