package renewal.gym.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import renewal.gym.constant.LoginSessionConst;
import renewal.gym.dto.LoginUserSession;

@Slf4j
public class GymCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("GymCheckInterceptor start");

        HttpSession session = request.getSession();
        LoginUserSession userSession = (LoginUserSession) session.getAttribute(LoginSessionConst.LoginSESSION_KEY);

        if(userSession.getGymIds().isEmpty()){
            response.sendRedirect("/gym/search");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
