package top.camsyn.store.commons.client.callback;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.camsyn.store.commons.client.ReviewClient;
import top.camsyn.store.commons.entity.review.ReviewLog;
import java.util.List;


public class ReviewHystrix implements ReviewClient {

    @Override
    public List<ReviewLog> selectAll() {
        return null;
    }

    @Override
    public List<ReviewLog> selectPassed() {
        return null;
    }

    @Override
    public List<ReviewLog> selectDeciding(){
        return null;
    }

    @Override
    public List<ReviewLog> selectFailed(){
        return null;
    }

    @Override
    public ReviewLog selectByRid(String R_id){
        return null;
    }

    @Override
    public String updateByRid(String R_id, String operate){
        return null;
    }

    @Override
    public String autoReviewUser(String t_id){
        return null;
    }

    @Override
    public String autoReviewRequest(String t_id) {
        return null;
    }

    @Override
    public String autoReviewChat(String t_id){return null;}

    @Override
    public String autoReviewCircle(String t_id){return null;}

    @Override
    public String autoReviewComment(String t_id){return null;}

    @Override
    public List<ReviewLog> selectReportsAboutUser(String t_id) {
        return null;
    }

    @Override
    public List<ReviewLog> selectReportsAboutRequest(String t_id) {
        return null;
    }

    @Override
    public List<ReviewLog> selectReportsAboutOrder(String t_id) {
        return null;
    }

    @Override
    public List<ReviewLog> selectReportsAboutChat(String t_id) {
        return null;
    }

    @Override
    public List<ReviewLog> selectReportsAboutCircle(String t_id) {
        return null;
    }

    @Override
    public List<ReviewLog> selectReportsAboutComment(String t_id) {
        return null;
    }

    @Override
    public List<ReviewLog> selectReportRecord(String i_id) {
        return null;
    }

}
