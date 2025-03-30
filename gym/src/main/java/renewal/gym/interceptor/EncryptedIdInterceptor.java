package renewal.gym.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import renewal.gym.service.SecureIdEncryptor;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptedIdInterceptor implements HandlerInterceptor {

    private final SecureIdEncryptor secureIdEncryptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("EncryptedIdInterceptor start");

        List<String> Ids = List.of("gymId", "boardId");

        for (String id : Ids) {
            String encryptedId = request.getParameter(id);
            log.debug("EncryptedIdInterceptor id: {} = {}", id, encryptedId);
            if (encryptedId != null) {
                log.debug("DecryptedId: {} = {}", id, secureIdEncryptor.decryptId(encryptedId));
                request.setAttribute(id, secureIdEncryptor.decryptId(encryptedId));
                log.debug("request parameters: {}", request.getParameter(id));
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
