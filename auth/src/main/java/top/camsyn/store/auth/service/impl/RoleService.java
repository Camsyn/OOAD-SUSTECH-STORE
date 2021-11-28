package top.camsyn.store.auth.service.impl;

import org.springframework.stereotype.Service;
import top.camsyn.store.auth.mapper.RoleMapper;
import top.camsyn.store.auth.service.IRoleService;
import top.camsyn.store.auth.model.Role;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;


@Service
public class RoleService extends SuperServiceImpl<RoleMapper, Role> implements IRoleService {

}
