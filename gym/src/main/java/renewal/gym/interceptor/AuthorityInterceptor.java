package renewal.gym.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import renewal.gym.constant.LoginSessionConst;
import renewal.gym.dto.LoginUserSession;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("AuthorityInterceptor start");

        HttpSession session = request.getSession();
        LoginUserSession userSession = (LoginUserSession) session.getAttribute(LoginSessionConst.LoginSESSION_KEY);

        if(request.getParameter("gymId") == null){
            return true;
        }

        Long gymId = Long.parseLong(request.getParameter("gymId"));
        //권한이 없음을 알려주는 페이지로 이동
        if(!userSession.getGymIds().contains(gymId)){
            log.info("You are not authorized to access this area.");
            response.sendRedirect("/500");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
