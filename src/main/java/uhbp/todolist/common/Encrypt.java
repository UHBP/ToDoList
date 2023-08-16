package uhbp.todolist.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class Encrypt {

    /**
     * 평문을 MD5로 해싱하여 변환
     *
     * @param str
     * @return
     */
    public String doHash(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuilder encryptedStr = new StringBuilder();
            for (byte b : digest) {
                encryptedStr.append(String.format("%02x", b & 0xff));
            }
            return encryptedStr.toString();
        } catch (NoSuchAlgorithmException e) {
            log.info("암호화 실패", e);
            return "";
        }
    }

    /**
     * 평문(str) 과 hashedStr 이 일치하는지 비교
     *
     * @param str
     * @param hashedStr
     * @return
     */
    public boolean isMatch(String str, String hashedStr) {
        String input = doHash(str);
        return input.equals(hashedStr);
    }
}
