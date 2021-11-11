//package top.camsyn.store.gateway.component;
//
//import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
//import org.springframework.security.oauth2.client.endpoint.ReactiveOAuth2AccessTokenResponseClient;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
//import org.springframework.security.oauth2.core.web.reactive.function.OAuth2BodyExtractors;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Component
//public class CustomReactiveAccessTokenResponseClient implements ReactiveOAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
//
//    @Override
//    public Mono<OAuth2AccessTokenResponse> getTokenResponse(OAuth2AuthorizationCodeGrantRequest oAuth2AuthorizationCodeGrantRequest) {
//
//        ClientRegistration clientRegistration=oAuth2AuthorizationCodeGrantRequest.getClientRegistration();
//        return Mono.defer(() -> WebClient.create().post().uri(clientRegistration.getProviderDetails().getTokenUri(),new Object[0])
//                .body(createRequestBody(oAuth2AuthorizationCodeGrantRequest))
//                .exchange().flatMap(response -> response.body(OAuth2BodyExtractors.oauth2AccessTokenResponse()))
//        );
//    }
//
//    private BodyInserters.FormInserter<String> createRequestBody(OAuth2AuthorizationCodeGrantRequest oAuth2AuthorizationCodeGrantRequest){
//        ClientRegistration clientRegistration=oAuth2AuthorizationCodeGrantRequest.getClientRegistration();
//
//        return BodyInserters.fromFormData("grant_type","authorization_code")
//                .with("client_id",clientRegistration.getClientId())
//                .with("client_secret",clientRegistration.getClientSecret())
//                .with("redirect_uri",oAuth2AuthorizationCodeGrantRequest.getAuthorizationExchange().getAuthorizationRequest().getRedirectUri())
//                .with("code", oAuth2AuthorizationCodeGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
//    }
//}
