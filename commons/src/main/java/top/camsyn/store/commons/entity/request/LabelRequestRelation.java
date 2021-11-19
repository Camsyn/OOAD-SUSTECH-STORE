package top.camsyn.store.commons.entity.request;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("request_label_relation")
public class LabelRequestRelation {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String labelName;
    Integer frequency;
    private Integer deleted;
}
