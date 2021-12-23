package top.camsyn.store.commons.entity.review;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "r_log", autoResultMap = true)
@ToString
public class ReviewLog {
    // 主键
    private int r_id;
    // 举报者
    private int initiator;
    //被举报对象
    private int target;
    // 描述
    private String description;
    // 最后处理时间
    private Date time;
    // 状态
    private int state;//0: 审核中  1: 过审  2: 不过审  3: 申诉审核中 4: 申诉失败，确认不过审
    // 被举报对象类别
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
