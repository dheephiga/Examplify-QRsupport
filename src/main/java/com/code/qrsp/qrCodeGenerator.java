package com.code.qrsp;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;



public class qrCodeGenerator {

    public static byte[] generateQRCode(String id) {
        String filePath = "QRCode.png";
        String charset = "UTF-8";
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        try {
            String key = KeyProvider.generateVerificationKey(id);

            Map<String, String> qrCodeDataMap = Map.of(
                    "Name", id,
                    "Key", key
            );

            String jsonString = new JSONObject(qrCodeDataMap).toString();

            createQRCode(jsonString, filePath, charset, hintMap, 500, 500);

            BufferedImage image = ImageIO.read(new File(filePath));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
            return null;
        }
    }

    private static void createQRCode(String qrCodeData, String filePath, String charset,
                                     Map<EncodeHintType, Object> hintMap,
                                     int qrCodeHeight, int qrCodeWidth) throws WriterException, IOException {

        BitMatrix matrix = new QRCodeWriter().encode(
                new String(qrCodeData.getBytes(charset), charset),
                BarcodeFormat.QR_CODE,
                qrCodeWidth,
                qrCodeHeight,
                hintMap
        );

        MatrixToImageWriter.writeToPath(
                matrix,
                filePath.substring(filePath.lastIndexOf('.') + 1),
                FileSystems.getDefault().getPath(filePath)
        );
    }
}
