package spring.testapp.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import spring.testapp.model.CustomUserDetails;

@Component
public class SecurityUtility {

    public CustomUserDetails getCurrentUserDetails() throws IllegalStateException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is not authenticated or is anonymous
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken
                || "anonymousUser".equals(authentication.getPrincipal().toString())) {
            throw new IllegalStateException("User is not logged in");
        }

        // Ensure the principal is an instance of CustomUserDetails
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalStateException("Principal is not of expected type CustomUserDetails");
        }

        return (CustomUserDetails) authentication.getPrincipal();
    }
}
