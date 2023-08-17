package uhbp.todolist.common.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class StringEncrypter {

    static final private String HASH_ALGORITHM = "SHA-256";

    //TODO 구현용 임시 salt. 실제 서비스 시 감추어야 함
    private String tempSalt = "salt";

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
     * 평문(str) 과 hashedStr 이 일치하는지 비교
     */
    public boolean isMatch(String str, String hashedStr) {
        String input = doHash(str);
        return input.equals(hashedStr);
    }
}
