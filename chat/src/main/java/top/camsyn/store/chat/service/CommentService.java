package top.camsyn.store.chat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import top.camsyn.store.chat.mapper.CommentMapper;
import top.camsyn.store.commons.entity.chat.Comment;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService extends SuperServiceImpl<CommentMapper,Comment>{

    public List<Comment> getCommentBySid(int sid, int count){
        return lambdaQuery().eq(Comment::getSendId, sid)
                .orderByDesc(Comment::getSendTime)
                .last("limit " + count).list();
    }
    public List<Comment> getCommentBySid(int sid, int count, LocalDateTime before){
        return lambdaQuery().eq(Comment::getSendId, sid)
                .lt(Comment::getSendTime, before)
                .orderByDesc(Comment::getSendTime)
                .last("limit " + count).list();
    }
    public List<Comment> getLatestComment(int cmId, int count){
        return lambdaQuery().eq(Comment::getCmId, cmId)
                .orderByDesc(Comment::getSendTime)
                .last("limit " + count).list();
    }
    public List<Comment> getLatestComment(int cmId, int count, LocalDateTime before){
        return lambdaQuery().eq(Comment::getCmId, cmId)
                .lt(Comment::getSendTime, before)
                .orderByDesc(Comment::getSendTime)
                .last("limit " + count).list();
    }

    public List<Comment> getUSerCommentPage(Integer cmId, Integer page, Integer limit, Boolean sortByTime) {
        return lambdaQuery().eq(Comment::getCmId, cmId)
                .orderByDesc(sortByTime ? Comment::getSendTime : Comment::getLike_)
                .page(new Page<>(page, limit)).getRecords();
    }
}