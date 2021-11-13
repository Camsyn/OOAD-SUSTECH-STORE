package top.camsyn.store.chat.service;

import org.springframework.stereotype.Service;
import top.camsyn.store.chat.entity.ChatRecord;
import top.camsyn.store.chat.mapper.ChatRecordMapper;
import top.camsyn.store.commons.entity.Role;
import top.camsyn.store.commons.mapper.RoleMapper;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;

@Service
public class ChatRecordService extends SuperServiceImpl<ChatRecordMapper, ChatRecord>{
}
