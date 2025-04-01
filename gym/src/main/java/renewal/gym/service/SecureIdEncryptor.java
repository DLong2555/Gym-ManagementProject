package renewal.gym.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class SecureIdEncryptor {

    @Value("${encryptor.secret.algorithm}")
    private String algorithm;

    @Value("${encryptor.secret.key}")
    private String mySecretKey;

    public String encryptId(Long id) {
        try{
            SecretKeySpec keySpec = new SecretKeySpec(mySecretKey.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(id.toString().getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        }catch (Exception e){
            throw new RuntimeException("Error while encrypting id ", e);
        }
    }

    public Long decryptId(String encryptedId) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(mySecretKey.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedId));
            return Long.parseLong((new String(decrypted)));

        }catch (Exception e){
            throw new RuntimeException("Error while decrypting id ", e);
        }

    }
}
