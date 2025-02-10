package renewal.gym.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import renewal.gym.constant.LoginSessionConst;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("로그인 체크 인터셉터 동작 : {}", request.getRequestURI());
        String uri = request.getRequestURI();

        HttpSession session = request.getSession();

        if(session == null || session.getAttribute(LoginSessionConst.LoginSESSION_KEY) == null) {
            response.sendRedirect("/gym/login?redirect=" + uri);
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("로그인 체크 인터셉터 통과 리다이렉트 : {}", request.getRequestURI());

        if(ex != null) {
            log.info("error = ", ex);
        }
    }
}
