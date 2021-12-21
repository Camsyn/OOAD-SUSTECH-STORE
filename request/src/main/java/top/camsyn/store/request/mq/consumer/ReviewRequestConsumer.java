//package top.camsyn.store.request.mq.consumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import top.camsyn.store.commons.entity.request.Request;
//import top.camsyn.store.request.mq.source.RequestMqConsumerSource;
//import top.camsyn.store.request.service.RequestService;
//
//@Component
//@Slf4j
//public class ReviewRequestConsumer {
//
//    @Autowired
//    RequestService requestService;
//
//    /**
//     * 监听配置中的相关 topic的消息
//     */
//    @Transactional
//    @StreamListener(RequestMqConsumerSource.REVIEW_INPUT)
//    public void onMessage(@Payload Request request) {
//        log.info("审核后处理request， 消息队列异步处理");
//        Integer id = request.getId();
//        Integer state = request.getState();
//        Request req = requestService.getById(id);
//        req.setState(state);
//        requestService.updateById(req);
//    }
//
//}
