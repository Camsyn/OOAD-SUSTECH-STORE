package top.camsyn.store.commons.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class CsrfSameSiteFilter implements Filter {
    static Set<String> allowedOrigins = new HashSet<>();
    final String sameSiteAttribute = "; SameSite=None";
    final String secureAttribute = "; Secure";
    static {
        allowedOrigins.add("http://localhost:8081");
        allowedOrigins.add("http://camsyn.top");
        allowedOrigins.add("http://camsyn.cn");
        allowedOrigins.add("https://localhost:8081");
        allowedOrigins.add("https://camsyn.top");
        allowedOrigins.add("https://camsyn.cn");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) res;

        final Collection<String> cookies = response.getHeaders(HttpHeaders.SET_COOKIE);
        if (cookies == null || cookies.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        cookies
            .stream()
            .filter(StringUtils::isNotBlank)
            .map(header -> {
                if (header.toLowerCase().contains("samesite")) {
                    return header;
                } else {
                    return header.concat(sameSiteAttribute);
                }
            })
            .map(header -> {
                if (header.toLowerCase().contains("secure")) {
                    return header;
                } else {
                    return header.concat(secureAttribute);
                }
            })
            .forEach(finalHeader -> response.setHeader(HttpHeaders.SET_COOKIE, finalHeader));
        chain.doFilter(request, response);

    }


}