package top.camsyn.store.review.service;

import top.camsyn.store.commons.client.*;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.entity.chat.CircleMessage;
import top.camsyn.store.commons.entity.chat.Comment;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.helper.UaaHelper;

import top.camsyn.store.review.Mapper.ReviewLogMapper;
import top.camsyn.store.commons.entity.review.ReviewLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewLogService {

    @Autowired
    private ReviewLogMapper reviewLogMapper;

    @Autowired
    RequestClient requestClient;

    @Autowired
    UserClient userClient;

    @Autowired
    OrderClient orderClient;

    @Autowired
    ChatClient chatClient;



    public List<ReviewLog> selectAll(){
        return reviewLogMapper.selectAll();
    }

    public List<ReviewLog> selectReportRecord(String i_id_s){
        int i_id = Integer.parseInt(i_id_s);
        if(i_id==-1){
            i_id = UaaHelper.getLoginSid();
//            i_id = 1;
        }
        return reviewLogMapper.selectReportRecord(i_id);
    }

    public List<ReviewLog> selectByState(String state_s){
        int state = Integer.parseInt(state_s);
        return reviewLogMapper.selectByState(state);
    }

    public List<ReviewLog> selectByTC(String t_id_s, String category_s){
        int t_id = Integer.parseInt(t_id_s);
        int category = Integer.parseInt(category_s);
        return reviewLogMapper.selectByTC(t_id, category);
    }

    public List<ReviewLog> selectSendingByTC(String t_id_s, String category_s){
        int t_id = Integer.parseInt(t_id_s);
        int category = Integer.parseInt(category_s);
        return reviewLogMapper.selectSendingByTC(t_id, category);
    }

    public ReviewLog selectByRid(String R_id_s){
        int R_id = Integer.parseInt(R_id_s);
        return reviewLogMapper.selectByR_id(R_id);
    }

    public String report(String t_id_s, String desc, int category){
//        int i_id = 1;
        int i_id = UaaHelper.getLoginSid();
        int t_id = Integer.parseInt(t_id_s);
        ReviewLog rL = new ReviewLog();
        rL.setInitiator(i_id);
        rL.setTarget(t_id);
        if(desc==null || desc.equals("")){
            desc = "该用户没有填写描述";
        }
        rL.setDescription(desc);
        rL.setTime(new Date());
        if(category==2){
            rL.setState(0);
        }
        else{
            if (isPassed(t_id, category)){
                rL.setState(1);
            }else{
                rL.setState(2);
                if(category==0){
                    User user = userClient.getUser(t_id).getData();
                    user.setCredit(user.getCredit()-1);
                }else if(category==1){
                    Request request = requestClient.getRequest(t_id).getData();
                    User user = userClient.getUser(request.getPusher()).getData();
                    user.setCredit(user.getCredit()-1);
                }else if(category==3){
                    ChatRecord chatRecord = chatClient.getChatRecord(t_id).getData();
                    User user = userClient.getUser(chatRecord.getSendId()).getData();
                    user.setCredit(user.getCredit()-1);
                }else if(category==4){
                    CircleMessage circleMessage = chatClient.getCircleMessage(t_id).getData();
                    User user = userClient.getUser(circleMessage.getSendId()).getData();
                    user.setCredit(user.getCredit()-1);
                }else if(category==5){
                    Comment comment = chatClient.getComment(t_id).getData();
                    User user = userClient.getUser(comment.getSendId()).getData();
                    user.setCredit(user.getCredit()-1);
                }
            }
        }
        rL.setCategory(category);
        reviewLogMapper.insert(rL);
        return "Report " + t_id_s + " success!";
    }

    public String autoReview(String t_id_s, int category){
        int i_id = -1;
        int t_id = Integer.parseInt(t_id_s);
        ReviewLog rL = new ReviewLog();
        rL.setInitiator(i_id);
        rL.setTarget(t_id);
        String desc = "";
        rL.setDescription(desc);
        rL.setTime(new Date());
        if(category==2){
            rL.setState(0);
            orderClient.reviewOrder(t_id);
        }
        else{
            if (isPassed(t_id, category)){
                rL.setState(1);
            }else{
                rL.setState(2);
                desc = "存在敏感词汇";
//                if(category==0){
//                    User user = userClient.getUser(t_id).getData();
//                    user.setCredit(user.getCredit()-1);
//                }else if(category==1){
//                    Request request = requestClient.getRequest(t_id).getData();
//                    User user = userClient.getUser(request.getPusher()).getData();
//                    user.setCredit(user.getCredit()-1);
//                }else if(category==3){
//                    ChatRecord chatRecord = chatClient.getChatRecord(t_id).getData();
//                    User user = userClient.getUser(chatRecord.getSendId()).getData();
//                    user.setCredit(user.getCredit()-1);
//                }else if(category==4){
//                    CircleMessage circleMessage = chatClient.getCircleMessage(t_id).getData();
//                    User user = userClient.getUser(circleMessage.getSendId()).getData();
//                    user.setCredit(user.getCredit()-1);
//                }else if(category==5){
//                    Comment comment = chatClient.getComment(t_id).getData();
//                    User user = userClient.getUser(comment.getSendId()).getData();
//                    user.setCredit(user.getCredit()-1);
//                }
            }
        }
        rL.setCategory(category);
        reviewLogMapper.insert(rL);
        return "Auto review " + t_id_s + " success!";
    }

    public boolean isPassed(int t_id, int category){
        if(category==0){
            User user = userClient.getUser(t_id).getData();
            if(reviewLogMapper.review(user.getNickname()+user.getDescription())==0){
                return true;
            }
        }else if(category==1){
            Request request = requestClient.getRequest(t_id).getData();
            if(reviewLogMapper.review(request.getDesc_()+request.getTitle())==0) {
                requestClient.updateRequestState(t_id,2);
                return true;
            }else{
                requestClient.updateRequestState(t_id,5);
            }
        }else if(category==3){
            ChatRecord chatRecord = chatClient.getChatRecord(t_id).getData();
            if(reviewLogMapper.review(chatRecord.getContent())==0) {
                return true;
            }else{
                chatClient.deleteChatRecord(chatRecord.getId());
            }
        }else if(category==4){
            CircleMessage circleMessage = chatClient.getCircleMessage(t_id).getData();
            if(reviewLogMapper.review(circleMessage.getTopic()+circleMessage.getContent())==0) {
                return true;
            }else{
                chatClient.deleteCircleMessage(circleMessage.getId());
            }
        }else if(category==5){
            Comment comment = chatClient.getComment(t_id).getData();
            if(reviewLogMapper.review(comment.getContent())==0) {
                return true;
            }else{
                chatClient.deleteComment(comment.getId());
            }
        }
        return false;
    }

    public String updateByRid(String R_id_s, String operate){
        int R_id = Integer.parseInt(R_id_s);
        ReviewLog rL = reviewLogMapper.selectByR_id(R_id);
        int t_id = rL.getTarget();
        int category = rL.getCategory();
        if (rL.getState()==3 && operate.equals("0")){
            rL.setState(4);
            if(category==1){
                requestClient.updateRequestState(t_id,5);
            }else if(category==2){
                orderClient.terminateOrder(t_id);
            }else if(category==3){
                chatClient.deleteChatRecord(t_id);
            }else if(category==4){
                chatClient.deleteCircleMessage(t_id);
            }else if(category==5){
                chatClient.deleteComment(t_id);
            }
        }
        else if(operate.equals("1")){
            if(rL.getState()==3 || rL.getState()==2){
                if(category==0){
                    User user = userClient.getUser(t_id).getData();
                    user.setCredit(user.getCredit()+1);
                }else if(category==1){
                    Request request = requestClient.getRequest(t_id).getData();
                    User user = userClient.getUser(request.getPusher()).getData();
                    user.setCredit(user.getCredit()+1);
                    requestClient.updateRequestState(t_id,2);
                }else if(category==2){
                    TradeRecord tradeRecord = orderClient.restoreOrder(t_id).getData();
                    User user = userClient.getUser(tradeRecord.getPusher()).getData();
                    user.setCredit(user.getCredit()+1);
                }else if(category==3){
                    ChatRecord chatRecord = chatClient.getChatRecord(t_id).getData();
                    User user = userClient.getUser(chatRecord.getSendId()).getData();
                    user.setCredit(user.getCredit()+2);
                }else if(category==4){
                    CircleMessage circleMessage = chatClient.getCircleMessage(t_id).getData();
                    User user = userClient.getUser(circleMessage.getSendId()).getData();
                    user.setCredit(user.getCredit()+2);
                }else if(category==5){
                    Comment comment = chatClient.getComment(t_id).getData();
                    User user = userClient.getUser(comment.getSendId()).getData();
                    user.setCredit(user.getCredit()+2);
                }
            }
            rL.setState(1);
        }else{
            if(rL.getState()==0 || rL.getState()==1){
                if(category==0){
                    User user = userClient.getUser(t_id).getData();
                    user.setCredit(user.getCredit()-1);
                }else if(category==1){
                    Request request = requestClient.getRequest(t_id).getData();
                    User user = userClient.getUser(request.getPusher()).getData();
                    user.setCredit(user.getCredit()-1);
                    requestClient.updateRequestState(t_id,5);
                }else if(category==2){
                    TradeRecord tradeRecord = orderClient.terminateOrder(t_id).getData();
                    User user = userClient.getUser(tradeRecord.getPusher()).getData();
                    user.setCredit(user.getCredit()-1);
                }else if(category==3){
                    ChatRecord chatRecord = chatClient.getChatRecord(t_id).getData();
                    User user = userClient.getUser(chatRecord.getSendId()).getData();
                    user.setCredit(user.getCredit()-1);
                    chatClient.deleteChatRecord(t_id);
                }else if(category==4){
                    CircleMessage circleMessage = chatClient.getCircleMessage(t_id).getData();
                    User user = userClient.getUser(circleMessage.getSendId()).getData();
                    user.setCredit(user.getCredit()-1);
                    chatClient.deleteCircleMessage(t_id);
                }else if(category==5){
                    Comment comment = chatClient.getComment(t_id).getData();
                    User user = userClient.getUser(comment.getSendId()).getData();
                    user.setCredit(user.getCredit()-1);
                    chatClient.deleteComment(t_id);
                }
            }
            rL.setState(2);
        }
        rL.setTime(new Date());
        reviewLogMapper.update(rL);
        return "Update " + R_id_s + " success!";
    }

    public String argue(String R_id_s, String desc){
        int R_id = Integer.parseInt(R_id_s);
        ReviewLog rL = reviewLogMapper.selectByR_id(R_id);
        if(rL.getState()==4){
            return "Argue is not allowed!";
        }
        rL.setState(3);
        if(desc!=null && !desc.equals("")){
            rL.setDescription("REPORT: "+rL.getDescription() + " 0000 ARGUE: "+desc);
        }
        rL.setTime(new Date());
        reviewLogMapper.update(rL);
        return "Argue " + R_id_s + " success!";
    }
}
