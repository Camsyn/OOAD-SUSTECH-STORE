package top.camsyn.store.commons.client.callback;


import top.camsyn.store.commons.client.ReviewClient;
import top.camsyn.store.review.domain.ReviewLog;
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
    public boolean autoReviewUser(String t_id){
        return false;
    }

    @Override
    public boolean autoReviewRequest(String t_id) {
        return false;
    }

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
    public List<ReviewLog> selectReportRecord(String i_id) {
        return null;
    }

}
