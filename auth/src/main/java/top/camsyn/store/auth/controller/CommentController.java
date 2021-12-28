package top.camsyn.store.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.auth.service.impl.UserCommentService;
import top.camsyn.store.commons.entity.chat.Comment;
import top.camsyn.store.commons.entity.user.UserComment;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.commons.model.UserDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    UserCommentService userCommentService;

    @PutMapping("/like")
    public Result<UserComment> likeComment(@RequestParam("commentId") Integer commentId){
        log.info("点赞： {}", commentId);
        final UserComment comment = userCommentService.getById(commentId);
        comment.setLike_(comment.getLike_()+1);
        return Result.succeed(comment);
    }

    @PutMapping("/unlike")
    public Result<UserComment> unlikeComment(@RequestParam("commentId") Integer commentId){
        log.info("取消点赞： {}", commentId);
        final UserComment comment = userCommentService.getById(commentId);
        final Integer like = comment.getLike_();
        if (like>0)
            comment.setLike_(like -1);
        return Result.succeed(comment);
    }

    @GetMapping("/getPage")
    public Result<List<UserComment>> getUserComments(@RequestParam("sid") Integer toSid,
                                                     @RequestParam("page") Integer page,
                                                     @RequestParam("limit") Integer limit,
                                                     @RequestParam("sort") Boolean sortByTime) {
        log.info("获取对用户的评论： {}", toSid);
        return Result.succeed(userCommentService.getUSerCommentPage(toSid, page, limit, sortByTime));
    }

    @PostMapping("/push")
    public Result<UserComment> pushComment(@RequestBody UserComment comment){
        comment.setLike_(0);
        log.info("成功发送评论， {}",comment);
        final int sid = UaaHelper.getLoginSid();
        comment.setFromSid(sid);
        userCommentService.save(comment);
        return Result.succeed(comment);
    }
    @DeleteMapping("/delete")
    public Result<UserComment> deleteComment(@RequestParam("commentId") Integer commentId){
        log.info("欲删除评论， {}",commentId);
        final UserComment comment = userCommentService.getById(commentId);
        UaaHelper.assertAdmin(comment.getFromSid());
        userCommentService.removeById(commentId);
        log.info("删除评论成功， {}",commentId);
        return Result.succeed(comment);
    }



}
