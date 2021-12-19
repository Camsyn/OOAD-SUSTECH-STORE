package top.camsyn.store.review.domain;

import java.util.Date;

public class ReviewLog {
    private int r_id;
    private int initiator;
    private int target;
    private String description;
    private Date time;
    private int state;//0: 审核中  1: 过审  2: 不过审  3: 申诉审核中 4: 申诉失败，确认不过审
    private int category;//0: 用户  1: 请求  2: 订单

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getInitiator() {
        return initiator;
    }

    public void setInitiator(int initiator) {
        this.initiator = initiator;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ReviewLog{" +
                "r_id=" + r_id +
                ", initiator=" + initiator +
                ", target=" + target +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", state=" + state +
                ", category=" + category +
                '}';
    }
}
