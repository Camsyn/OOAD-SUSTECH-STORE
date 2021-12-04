package top.camsyn.store.commons.client.callback;

import top.camsyn.store.commons.client.RequestClient;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.model.Result;

public class RequestHystrix implements RequestClient {
    @Override
    public Result<Request> closeRequest(Integer requestId) {
        return null;
    }

    @Override
    public Result<Request> openRequest(Integer requestId) {
        return null;
    }

    @Override
    public Result<Request> getRequest(Integer requestId) {
        return null;
    }


    @Override
    public Result<Request> updateRequestState(Integer requestId, Integer state) {
        return null;
    }

    @Override
    public Result<Request> dropRequest(Integer requestId) {
        return null;
    }

    @Override
    public Result<Boolean> updateRequestForRpc(Request request) {
        return null;
    }
}
