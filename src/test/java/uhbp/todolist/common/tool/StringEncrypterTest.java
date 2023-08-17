package uhbp.todolist.common.tool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class StringEncrypterTest {

    StringEncrypter encrypter = new StringEncrypter();

    @Test
    void isMatch() {
        String inputStr = "pw";
        String hashedStr = encrypter.doHash(inputStr);

        Assertions.assertTrue(encrypter.isMatch(inputStr, hashedStr));
    }
}