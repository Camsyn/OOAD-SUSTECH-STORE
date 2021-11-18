package top.camsyn.store.request.service;

import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.mapper.AccountMapper;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;
import top.camsyn.store.request.mapper.RequestMapper;

@Service
public class RequestService extends SuperServiceImpl<RequestMapper, Request>  {
}
