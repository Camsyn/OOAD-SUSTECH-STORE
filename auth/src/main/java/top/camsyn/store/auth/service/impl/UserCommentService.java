package top.camsyn.store.auth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import top.camsyn.store.auth.mapper.UserCommentMapper;
import top.camsyn.store.auth.service.IUserService;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.entity.user.UserComment;
import top.camsyn.store.commons.mapper.UserMapper;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;

import java.util.List;

@Service
public class UserCommentService extends SuperServiceImpl<UserCommentMapper, UserComment> {
    public List<UserComment> getUSerCommentPage(Integer toSid, Integer page, Integer limit, Boolean isSortByTime) {
        return lambdaQuery().eq(UserComment::getToSid, toSid)
                .orderByDesc(isSortByTime ? UserComment::getCreateTime : UserComment::getLike_)
                .page(new Page<>(page, limit)).getRecords();
    }
}
