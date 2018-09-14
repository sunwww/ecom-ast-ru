package ru.ecom.ejb.QRCode;

import ru.ecom.api.IApiService;

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

/**
 * Created by Milamesher on 14.09.2018.
 * Для работы с QR-кодами.
 */
@Stateless
@Local(IApiService.class)
@Remote(IApiService.class)
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
}