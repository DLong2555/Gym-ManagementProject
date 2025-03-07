package renewal.gym.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
public class TossPaymentsConfig {

    @Value("${payment.toss.test_client_api_key}")
    private String apikey;

    @Value("${payment.toss.test_secrete_api_key}")
    private String secretKey;

    @Value("${payment.toss.success_url}")
    private String successUrl;

    @Value("${payment.toss.fail_url}")
    private String failureUrl;

    public final String url = "https://api.tosspayments.com/v1/payments";
}
