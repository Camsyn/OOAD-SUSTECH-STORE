package top.camsyn.store.review.service;

import top.camsyn.store.commons.client.RequestClient;
import top.camsyn.store.commons.client.UserClient;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.helper.UaaHelper;

import top.camsyn.store.review.Mapper.ReviewLogMapper;
import top.camsyn.store.review.domain.ReviewLog;
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
            }
        }
        rL.setCategory(category);
        reviewLogMapper.insert(rL);
        return "Report " + t_id_s + " success!";
    }

    public boolean isPassed(int t_id, int category){
        if(category==0){
            User user = userClient.getUser(t_id).getData();
            if(reviewLogMapper.review(user.getDescription())==0 && reviewLogMapper.review(user.getNickname())==0){
                return true;
            }
        }else{
            Request request = requestClient.getRequest(t_id).getData();
            if(reviewLogMapper.review(request.getDescription())==0){
                return true;
            }
        }
//        String s = "hello world";
//        if(category==0) {
//            switch (t_id) {
//                case 1:
//                    s = "你好";
//                    break;
//                case 2:
//                    s = "nt";
//                    break;
//                case 3:
//                    s = "臭屌丝";
//                    break;
//                case 4:
//                    s = "死妈玩意";
//                    break;
//                default:
//                    s = "傻狗";
//            }
//        }else{
//            switch (t_id) {
//                case 1:
//                    s = "傻卵";
//                    break;
//                case 2:
//                    s = "那么好";
//                    break;
//                case 3:
//                    s = "我的";
//                    break;
//                case 4:
//                    s = "诶嘿";
//                    break;
//                default:
//                    s = "共产党";
//            }
//        }
//        if(reviewLogMapper.review(s)==0){
//            return true;
//        }
        return false;
    }

    public String updateByRid(String R_id_s, String operate){
        int R_id = Integer.parseInt(R_id_s);
        ReviewLog rL = reviewLogMapper.selectByR_id(R_id);
        if (rL.getState()==3 && operate.equals("0")){
            rL.setState(4);
        }
        else if(operate.equals("1")){
            rL.setState(1);
        }else{
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
