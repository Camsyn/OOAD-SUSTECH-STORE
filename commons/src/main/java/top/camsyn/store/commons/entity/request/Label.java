package top.camsyn.store.commons.entity.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName(value = "label", autoResultMap = true)
public class Label {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String labelName;
    Integer frequency;
    private Integer deleted;

}
