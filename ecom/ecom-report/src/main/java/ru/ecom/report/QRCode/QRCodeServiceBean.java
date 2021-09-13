package ru.ecom.report.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import org.odftoolkit.simple.Document;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.navigation.TextNavigation;
import org.odftoolkit.simple.common.navigation.TextSelection;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import sun.misc.BASE64Encoder;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Milamesher on 21.09.2018.
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
        catch (Exception e) {
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
    public Boolean createInsertQRCode(String QR_text,int QR_w, int QR_h, String QR_TYPE,String template,String ext,String replacesource) {
        Boolean flag = false;
        if (QR_text!=null && !QR_text.equals("") && QR_w!=0 && QR_h!=0 && template!=null && !template.equals("") && ext!=null && !ext.equals("")
                && replacesource!=null && !replacesource.equals("")) {
            flag = true;
            if (QR_TYPE == null || QR_TYPE.equals("")) QR_TYPE = "PNG";
            String QR_CODE_IMAGE_PATH = template.replace(ext, "") + "." + QR_TYPE;
            try {
                MatrixToImageWriter.writeToStream(new QRCodeWriter().encode(QR_text, BarcodeFormat.QR_CODE, QR_w, QR_h), QR_TYPE, new FileOutputStream(QR_CODE_IMAGE_PATH));
                File file = new File(QR_CODE_IMAGE_PATH);
                if (file.exists()) {
                    flag = putQRImage(file.toURI(), template, replacesource);
                }
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
    //Milamesher #120 130092018  метод замены кодового слова на QR-код
    private Boolean putQRImage(URI uri,String template, String replacesource) {
        try {
            Document  textdoc=null;
            if (template.endsWith(".odt")) {
                textdoc = TextDocument.loadDocument(template);
                TextNavigation search = new TextNavigation(replacesource, textdoc);
                if ( uri != null) {
                    while (search.hasNext()) {
                        TextSelection item = (TextSelection) search.nextSelection();
                        try {
                            if (item != null) item.replaceWith(uri);
                        }
                        catch(NullPointerException e) {}
                    }
                }
            }
            //Milamesher #120 19112018 работа с .ods файлом
            else if (template.endsWith(".ods")) {
                textdoc = SpreadsheetDocument.loadDocument(template);
                if (!textdoc.getTableList().isEmpty()) {
                    Table t = textdoc.getTableList().get(0);
                    boolean flag = false;
                    for (int i = 0; i < t.getRowCount(); i++) {
                        for (int j = 0; i < t.getRowByIndex(i).getCellCount(); j++) {
                            Cell c = t.getCellByPosition(i, j);
                            if (c.getDisplayText().contains(replacesource)) {
                                c.setImage(uri);
                                flag = true;
                                break;
                            }
                            if (flag) break;
                        }
                    }
                }
            }
            if (textdoc!=null) textdoc.save(template);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}