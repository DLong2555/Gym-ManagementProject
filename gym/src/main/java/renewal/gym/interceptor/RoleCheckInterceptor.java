package renewal.gym.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import renewal.gym.constant.LoginSessionConst;
import renewal.gym.domain.Role;
import renewal.gym.dto.LoginUserSession;

@Slf4j
public class RoleCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("RoleCheckInterceptor preHandle");

        HttpSession session = request.getSession();
        LoginUserSession userSession = (LoginUserSession) session.getAttribute(LoginSessionConst.LoginSESSION_KEY);

        //권한이 없음을 알려주는 페이지로 이동
        if(!userSession.getRole().equals(Role.MANAGER)){
            response.sendRedirect("/500");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
