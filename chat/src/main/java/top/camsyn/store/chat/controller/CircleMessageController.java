package top.camsyn.store.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.chat.service.CircleMessageService;
import top.camsyn.store.chat.service.CommentService;
import top.camsyn.store.commons.client.ReviewClient;
import top.camsyn.store.commons.entity.chat.CircleMessage;
import top.camsyn.store.commons.entity.chat.Comment;
import top.camsyn.store.commons.entity.user.UserComment;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("circle")
public class CircleMessageController {
    @Autowired
    CircleMessageService circleMessageService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReviewClient reviewClient;

    @GetMapping("/get")
    public Result<CircleMessage> getCircleMessage(Integer cmId) {
        log.info("获取圈子消息");
        return Result.succeed(circleMessageService.getById(cmId));
    }

    @PostMapping("/post")
    public Result<CircleMessage> publishCircleMessage(@RequestBody CircleMessage message) {
        log.info("publishCircleMessage. message: {}", message);
        try {
            message.setSendId(UaaHelper.getLoginSid());
            circleMessageService.save(message);
            log.info("publishCircleMessage成功");
            return Result.succeed(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("非法sid");
            return Result.failed("非法sid");
        }
    }

    @PostMapping("/comment/post")
    public Result<Comment> publishCircleComment(@RequestBody Comment comment) {
        log.info("publishCircleComment. comment: {}", comment);
        try {
            comment.setSendId(UaaHelper.getLoginSid());
            commentService.save(comment);
            log.info("publishCircleComment成功");
            return Result.succeed(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("非法sid");
            return Result.failed("非法sid");
        }
    }

    @DeleteMapping("/delete")
    public Result<CircleMessage> deleteCircleMessage(@RequestParam("id") Integer id) {
        log.info("deleteCircleMessage. id: {}", id);
        CircleMessage cm = circleMessageService.getById(id);
        if (cm == null) {
            log.info("该动态不存在或已删除");
            return Result.failed("该动态不存在或已删除");
        }
        if (cm.getSendId() != UaaHelper.getLoginSid()) {
            log.info("无法删除不属于你的动态");
            return Result.failed("无法删除不属于你的动态");
        }
        boolean isRemoved = circleMessageService.removeById(id);
        if (isRemoved) log.info("成功删除");
        else log.info("未知原因，无法删除");
        return isRemoved ? Result.succeed(cm, "成功删除") : Result.failed("未知原因，无法删除");
    }

    @DeleteMapping("/comment/delete")
    public Result<Comment> deleteYourComment(@RequestParam("id") Integer id) {
        log.info("deleteComment. id: {}", id);
        Comment comment = commentService.getById(id);

        if (comment == null) {
            log.info("该动态评论不存在或已删除");
            return Result.failed("该动态评论不存在或已删除");
        }
        if (comment.getSendId() != UaaHelper.getLoginSid()) {
            log.info("无法删除不属于你的动态评论");
            return Result.failed("无法删除不属于你的动态评论");
        }
        boolean isRemoved = circleMessageService.removeById(id);
        if (isRemoved) log.info("成功删除");
        else log.info("未知原因，无法删除");
        return isRemoved ? Result.succeed(comment, "成功删除") : Result.failed("未知原因，无法删除");
    }


    @GetMapping("/all/getLatest")
    public Result<List<CircleMessage>> getLatestMessages(@RequestParam("count") Integer count) {
        log.info("请求全局最新动态， size：{}", count);
        return Result.succeed(circleMessageService.getLatestMessage(count + 1));
    }

    @GetMapping("/all/getElse")
    public Result<List<CircleMessage>> getElseMessage(@RequestParam("count") Integer count,
                                                      @RequestParam("before") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime timeBefore) {
        log.info("getElseMessage， count: {}, before: {}", count, timeBefore);
        return Result.succeed(circleMessageService.getLatestMessage(count, timeBefore));
    }
    @GetMapping("/all/page")
    public Result<List<CircleMessage>> getLatestMessagePage(@RequestParam("page") Integer page,
                                                           @RequestParam("limit") Integer limit) {
        log.info("getLatestMessagePage， page: {}, limit: {}",page, limit);
        return Result.succeed(circleMessageService.getLatestMessagePage(page, limit));
    }
    @GetMapping("/sid/page")
    public Result<List<CircleMessage>> getMessagePageBySid(@RequestParam("sid") Integer sid,
                                                           @RequestParam("page") Integer page,
                                                           @RequestParam("limit") Integer limit) {
        log.info("getMessagePageBySid，sid: {}, page: {}, limit: {}", sid, page, limit);
        return Result.succeed(circleMessageService.getMessagePageBySid(sid, page, limit));
    }
    @GetMapping("/sid/getLatest")
    public Result<List<CircleMessage>> getMessageBySid(@RequestParam("sid") Integer sid,
                                                       @RequestParam("count") Integer count
    ) {
        log.info("getMessageBySid，sid: {} , count: {}", sid, count);
        return Result.succeed(circleMessageService.getMessageBySid(sid, count));
    }


    @GetMapping("/sid/getElse")
    public Result<List<CircleMessage>> getMessageBySid(@RequestParam("sid") Integer sid,
                                                       @RequestParam("count") Integer count,
                                                       @RequestParam("before") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                               LocalDateTime timeBefore) {
        log.info("getMessageBySid，sid: {}, count: {}, before: {}", sid, count, timeBefore);
        return Result.succeed(circleMessageService.getMessageBySid(sid, count, timeBefore));
    }





    @GetMapping("/comment/sid/getLatest")
    public Result<List<Comment>> getLatestCommentBySid(@RequestParam("sid") Integer sid,
                                                       @RequestParam("count") Integer count) {
        log.info("getLatestCommentBySid，sid: {}. size：{}", sid, count);
        return Result.succeed(commentService.getCommentBySid(sid, count + 1));
    }

    @GetMapping("/comment/sid/getElse")
    public Result<List<Comment>> getLatestCommentBySid(@RequestParam("sid") Integer sid,
                                                       @RequestParam("count") Integer count,
                                                       @RequestParam("before") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                               LocalDateTime timeBefore) {
        log.info("getLatestCommentBySid，sid: {}. size：{}, before: {}", sid, count, timeBefore);
        return Result.succeed(commentService.getCommentBySid(sid, count + 1, timeBefore));
    }


    @GetMapping("/comment/cmId/getLatest")
    public Result<List<Comment>> getLatestComment(@RequestParam("cmId") Integer cmId,
                                                  @RequestParam("count") Integer count) {
        log.info("getLatestComment，cmId: {}. size：{}", cmId, count);
        return Result.succeed(commentService.getLatestComment(cmId, count + 1));
    }

    @GetMapping("/comment/cmId/getElse")
    public Result<List<Comment>> getLatestComment(@RequestParam("cmId") Integer cmId,
                                                  @RequestParam("count") Integer count,
                                                  @RequestParam("before") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                          LocalDateTime timeBefore) {
        log.info("getLatestComment，cmId: {}. size：{}, before: {}", cmId, count, timeBefore);
        return Result.succeed(commentService.getLatestComment(cmId, count + 1, timeBefore));
    }


    @PutMapping("/comment/like")
    public Result<Comment> likeComment(@RequestParam("commentId") Integer commentId) {
        log.info("点赞： {}", commentId);
        final Comment comment = commentService.getById(commentId);
        comment.setLike_(comment.getLike_() + 1);
        return Result.succeed(comment);
    }

    @PutMapping("/comment/unlike")
    public Result<Comment> unlikeComment(@RequestParam("commentId") Integer commentId) {
        log.info("取消点赞： {}", commentId);
        final Comment comment = commentService.getById(commentId);
        final Integer like = comment.getLike_();
        if (like > 0)
            comment.setLike_(like - 1);
        return Result.succeed(comment);
    }

    @GetMapping("/comment/getPage")
    public Result<List<Comment>> getUserComments(@RequestParam("sid") Integer cmId,
                                                 @RequestParam("page") Integer page,
                                                 @RequestParam("limit") Integer limit,
                                                 @RequestParam("sort") Boolean sortByTime) {
        log.info("获取对动态的评论： {}", cmId);
        return Result.succeed(commentService.getUSerCommentPage(cmId, page, limit, sortByTime));
    }

}
