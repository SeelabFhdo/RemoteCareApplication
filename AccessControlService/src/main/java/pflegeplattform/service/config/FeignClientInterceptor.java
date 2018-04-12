package pflegeplattform.service.config;

import feign.RequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_TOKEN_TYPE = "Bearer";

  @Override
  public void apply(feign.RequestTemplate requestTemplate) {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();

    if (authentication != null && authentication
        .getDetails() instanceof OAuth2AuthenticationDetails) {
      OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication
          .getDetails();
      requestTemplate.header(AUTHORIZATION_HEADER,
          String.format("%s %s", BEARER_TOKEN_TYPE, details.getTokenValue()));
    }
  }
}