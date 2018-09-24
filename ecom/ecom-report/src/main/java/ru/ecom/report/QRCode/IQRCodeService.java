package ru.ecom.report.QRCode;

/**
 * Created by Milamesher on 21.09.2018.
 */
public interface IQRCodeService {
    String generateQRCodeImageBase64(String QR_text, int QR_w, int QR_h, String QR_TYPE);
    Boolean createInsertQRCode(String QR_text,int QR_w, int QR_h, String QR_TYPE,String template,String ext,String replacesource);
}