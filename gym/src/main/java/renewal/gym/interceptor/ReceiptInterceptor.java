package renewal.gym.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import renewal.gym.constant.LoginSessionConst;
import renewal.gym.dto.LoginUserSession;

import java.util.Objects;

public class ReceiptInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        LoginUserSession userSession = (LoginUserSession) session.getAttribute(LoginSessionConst.LoginSESSION_KEY);

        Long id = null;
        if(request.getParameter("userId") == null) id = userSession.getId();
        else id = Long.parseLong(request.getParameter("userId"));

        //권한이 없음을 알려주는 페이지로 이동
        if(!Objects.equals(userSession.getId(), id)){
            response.sendRedirect("/500");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
