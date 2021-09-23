package ua.kharkiv.epam.shchehlov.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurePassword {

    private static final String HASHING = "SHA-256";
    private static final String SALT = "DfgGH2";

    private SecurePassword() {
        // to prevent object creation
    }

    public static String getHashing() {
        return HASHING;
    }

    /**
     * Method to encrypt password using SHA algorithms with salt.
     *
     * @param passwordToHash not encrypted password,
     *                       salt
     * @return String.
     */
    public static String getSecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(HASHING);
            md.update(SALT.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            generatedPassword = bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Method for convert bytes array to hex code.
     *
     * @param bytes array of bytes which need to convert.
     * @return String.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    /**
     * Method to generate salt to make password even more secure.
     *
     * @return byte array.
     */
    public static String getSalt() {
        byte[] salt = new byte[16];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytesToHex(salt);
    }

}
