package renewal.gym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import renewal.gym.controller.argument.LoginUserArgumentResolver;
import renewal.gym.controller.argument.PhoneNumberFormatterResolver;
import renewal.gym.converter.EnumToValueConverter;
import renewal.gym.interceptor.*;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/gym/joinSelect", "/gym/join/*", "/gym/login",
                        "/gym/logout", "/gym/duplicationIdCheck",
                        "/css/**", "*/ico", "/error", "/js/**", "/image/**");

        registry.addInterceptor(new RoleCheckInterceptor())
                .order(2)
                .addPathPatterns("/gym/manager/**")
                .excludePathPatterns(
                        "/", "/css/**", "*/ico", "/error", "/js/**", "/image/**"
                );

        registry.addInterceptor(new GymCheckInterceptor())
                .order(3)
                .addPathPatterns("/gym/board/**", "/gym/child/regular");

        registry.addInterceptor(new AuthorityInterceptor())
                .order(3)
                .addPathPatterns("/gym/board", "/gym/manager/**")
                .excludePathPatterns("/gym/manager/manage/event");

        registry.addInterceptor(new ReceiptInterceptor())
                .order(3)
                .addPathPatterns("/payments");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new EnumToValueConverter());
        registry.addFormatterForFieldAnnotation(new PhoneNumberFormatterResolver());
    }
}
