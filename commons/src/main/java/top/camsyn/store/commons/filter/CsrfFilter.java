package top.camsyn.store.commons.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class CsrfFilter implements Filter {
    static Set<String> allowedOrigins = new HashSet<>();

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

        String setCookie = response.getHeader("Set-Cookie");
        if (setCookie!=null){
            response.setHeader("Set-Cookie", setCookie+"SameSite=None;");
//            response.setHeader("Set-Cookie", "SameSite=None; Secure");
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}