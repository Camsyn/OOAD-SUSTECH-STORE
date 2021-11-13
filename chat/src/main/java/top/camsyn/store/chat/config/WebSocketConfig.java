package top.camsyn.store.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Chen_Kunqiu
 *
 * 对于 Web Socket 的使用，可以先通过 Spring Ja 配置文件。在这 文件中， 先新建 ServerEndpointE porter 对象
 * 它可以定义 WebSocket服务器的端点, 这样客户端就能请求服务器的断点
 *
 */
@Configuration
public class WebSocketConfig {
    /**
     * 有了这 Bean 可以使用 ServerEndpoint 一个端点服务类。在这 站点服务类中，还
     * 可以定义 WebSocket 的打开、关闭、错误和发送消息的方法。
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
