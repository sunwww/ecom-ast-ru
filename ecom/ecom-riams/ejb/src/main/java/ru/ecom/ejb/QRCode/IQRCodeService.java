package ru.ecom.ejb.QRCode;

/**
 * Created by Milamesher on 14.09.2018.
 */
public interface IQRCodeService {
    String generateQRCodeImageBase64(String QR_text, int QR_w, int QR_h, String QR_TYPE);
}