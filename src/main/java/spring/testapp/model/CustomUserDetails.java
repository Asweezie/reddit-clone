package spring.testapp.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Getter
public class CustomUserDetails extends User {
    private Long id;

    public CustomUserDetails(String username, String password, Collection<SimpleGrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }

}
