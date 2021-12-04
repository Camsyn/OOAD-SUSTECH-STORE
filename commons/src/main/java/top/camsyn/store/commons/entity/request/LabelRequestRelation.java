package top.camsyn.store.commons.entity.request;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("request_label_relation")
public class LabelRequestRelation {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    Integer requestId;
    Integer labelId;
    private Integer deleted;

    public LabelRequestRelation(Integer requestId, Integer labelId) {
        this.requestId = requestId;
        this.labelId = labelId;
    }
}
