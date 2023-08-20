package uhbp.todolist.common.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class StringEncrypter {

    static final private String HASH_ALGORITHM = "SHA-256";

    @Value("${encrypt.salt}")
    private String tempSalt;

    /**
     * 평문을 해싱하여 변환
     */
    public String doHash(String str) {
        // 받아온 String 에 salt 추가
        str += tempSalt;

        // 해시 알고리즘 시작
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(str.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuilder encryptedStr = new StringBuilder();
            for (byte b : digest) {
                encryptedStr.append(String.format("%02x", b & 0xff));
            }
            return encryptedStr.toString();
        } catch (NoSuchAlgorithmException e) {
            log.info("암호화 실패", e);
            throw new RuntimeException("암호화 실패");
        }
    }

    /**
     * 평문과 암호화된 String 이 일치하는지 여부를 반환
     * @param str 평문
     * @param hashedStr 암호화된 String
     * @return
     */
    public boolean isMatch(String str, String hashedStr) {
        String input = doHash(str);
        return input.equals(hashedStr);
    }
}
