//package top.camsyn.store.gateway.service;
//
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Service
//public class CustomReactiveOAuth2UserService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    @Override
//    public Mono<OAuth2User> loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
//        ParameterizedTypeReference<Map<String,Object>> parameterizedTypeReference= new ParameterizedTypeReference<Map<String,Object>>() {};
//
//        String uri=oAuth2UserRequest.getClientRegistration().getProviderDetails()
//                .getUserInfoEndpoint().getUri()+"?access_token={access_token}";
//
//        Map<String,String> params=new HashMap<>();
//        params.put("access_token",oAuth2UserRequest.getAccessToken().getTokenValue());
//
//        Mono<Map<String, Object>> userAttributes = WebClient.create().get().uri(uri,params)
//                .retrieve().bodyToMono(parameterizedTypeReference);
//
//        return userAttributes.map(attrs ->{
//            Set<GrantedAuthority> authorities=new HashSet<>();
//            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//            return new DefaultOAuth2User(authorities,attrs,"name");
//        });
//    }
//}
