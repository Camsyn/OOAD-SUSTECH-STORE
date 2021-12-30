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
    private Integer id;
    // 举报者
    private Integer initiator;
    //被举报对象
    private Integer target;
    // 描述
    private String description;
    // 最后处理时间
    private Date time;
    // 状态
    private Integer state;//0: 审核中  1: 过审  2: 不过审  3: 申诉审核中 4: 申诉失败，确认不过审
    // 被举报对象类别
    private Integer category;//0: 用户  1: 请求  2: 订单  3: 聊天记录  4: 动态  5: 评论

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInitiator() {
        return initiator;
    }

    public void setInitiator(Integer initiator) {
        this.initiator = initiator;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ReviewLog{" +
                "id=" + id +
                ", initiator=" + initiator +
                ", target=" + target +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", state=" + state +
                ", category=" + category +
                '}';
    }
}
