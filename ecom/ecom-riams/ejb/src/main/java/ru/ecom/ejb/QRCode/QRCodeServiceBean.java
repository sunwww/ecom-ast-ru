package ru.ecom.ejb.QRCode;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import sun.misc.BASE64Encoder;
import java.awt.image.BufferedImage;
import java.io.*;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.navigation.TextNavigation;
import org.odftoolkit.simple.common.navigation.TextSelection;
import java.net.URI;

/**
 * Created by Milamesher on 14.09.2018.
 * Для работы с QR-кодами.
 */
@Stateless
@Local(IQRCodeService.class)
@Remote(IQRCodeService.class)
public class QRCodeServiceBean implements IQRCodeService {
    private String QR_CODE_filename="tmpFileForQRCode";
    //Метод возвращает qr-код с текстом, размерами QR_w и QR_h. default QR_TYPE PNG
    public String generateQRCodeImageBase64(String QR_text, int QR_w, int QR_h, String QR_TYPE) {
        if (QR_TYPE==null || QR_TYPE.equals("")) QR_TYPE="PNG";
        String QR_CODE_IMAGE_PATH = QR_CODE_filename+"."+QR_TYPE;
        String base64=null;
        try {
            MatrixToImageWriter.writeToStream(new QRCodeWriter().encode(QR_text, BarcodeFormat.QR_CODE, QR_w, QR_h),QR_TYPE,new FileOutputStream(QR_CODE_IMAGE_PATH));
            base64 = encodeToString(ImageIO.read(new File(QR_CODE_IMAGE_PATH)), QR_TYPE);
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
       return base64;
    }
    //Milamesher #120 14092018  метод получения строки в base64 из файла с изображением
    private String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            imageString = new BASE64Encoder().encode(bos.toByteArray());
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
    //Milamesher #120 19092018 метод вставки qr-кода в файл
    public Boolean createInsertQRCode(String QR_text,int QR_w, int QR_h, String QR_TYPE,String template,String replacesource) {
        Boolean flag=true;
        /*if (QR_w==0) QR_w=300;
        if (QR_h==0) QR_h=300;
        if (replacesource==null || replacesource.equals("")) replacesource="replacesource";*/
        if (QR_TYPE==null || QR_TYPE.equals("")) QR_TYPE="PNG";
        String QR_CODE_IMAGE_PATH = QR_CODE_filename+"."+QR_TYPE;
        try {
            MatrixToImageWriter.writeToStream(new QRCodeWriter().encode(QR_text, BarcodeFormat.QR_CODE, QR_w, QR_h),QR_TYPE,new FileOutputStream(QR_CODE_IMAGE_PATH));
            File file = new File(QR_CODE_IMAGE_PATH);
            if (file.exists()) {
                try {
                    flag=putQRImage(file.toURI(),template,replacesource);
                }
                catch (Exception e) {
                    e.printStackTrace(); flag=false;
                }
            }
            //Files.deleteIfExists(Paths.get(QR_CODE_IMAGE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace(); flag=false;
        } catch (IOException e) {
            e.printStackTrace(); flag=false;
        }  catch (Exception e) {
            e.printStackTrace(); flag=false;
        }
        return flag;
    }
    //Milamesher #120 130092018  метод замены кодового слова на QR-код
    private Boolean putQRImage(URI uri,String template, String replacesource) {
        try {
            TextDocument textdoc=(TextDocument)TextDocument.loadDocument(template);
            TextNavigation search = new TextNavigation(replacesource, textdoc);
            while (search.hasNext()) {
                TextSelection item= (TextSelection) search.nextSelection();
                item.replaceWith(uri);
            }
            textdoc.save(template);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}