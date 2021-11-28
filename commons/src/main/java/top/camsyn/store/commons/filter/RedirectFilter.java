package top.camsyn.store.commons.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import top.camsyn.store.commons.props.CommonsProperties;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@Order(1)
@ConditionalOnMissingClass({"top.camsyn.store.gateway.GatewayApplication","top.camsyn.store.auth.AuthApplication"})
public class RedirectFilter implements Filter {

    @Autowired
    CommonsProperties commonsProperties;

    static Pattern login = Pattern.compile("^\\s*https?:\\/\\/[^?]*(?=login)");
    static Pattern authorize = Pattern.compile("(?<=redirect_uri=).*?(?=&)");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        String location = response.getHeader(HttpHeaders.LOCATION);
        log.info("origin location: {}",location);

        if (location==null){
            chain.doFilter(request, response);
            return;
        }
        String newLocation="";
        Matcher m1 = login.matcher(location);
        if (m1.find()) {
            newLocation = m1.replaceFirst(commonsProperties.getGatewayIp() + "/" + commonsProperties.getAppName() + "/");
            response.setHeader(HttpHeaders.LOCATION, newLocation);
            log.info("new location: {}",newLocation);

        } else {
            Matcher m2 = authorize.matcher(location);
            if (m2.find()) {
                String redirectUri = m2.group(0);
                Matcher m3 = login.matcher(redirectUri);
                if (m3.find()) {
                    String s = m3.replaceFirst(commonsProperties.getGatewayIp() + "/" + commonsProperties.getAppName() + "/");
                    newLocation = m2.replaceFirst(s);
                    response.setHeader(HttpHeaders.LOCATION, newLocation);
                    log.info("new location: {}",newLocation);

                }
            }
        }

//        Pattern
        chain.doFilter(request, resp);
    }

    public static void main(String[] args) {
        Matcher matcher = authorize.matcher("http://localhost:8000/auth/oauth/authorize?client_id=store&redirect_uri=http://120.77.145.246:8001/login&response_type=code&state=Ml7BzD");
        if (matcher.find()) {
            String redirectUri = matcher.group(0);
            System.out.println(redirectUri);
            Matcher m1 = login.matcher(redirectUri);
            if (m1.find()) {
                String s = m1.replaceFirst("Gatewat:8000" + "/" + "auth/");
                System.out.println(matcher.replaceFirst(s));
            }

        }
    }
}
