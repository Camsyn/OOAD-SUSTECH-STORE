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
    /**
     * 生成
     */
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String labelName;
    /**
     * 生成
     */
    Integer frequency;
    private Integer deleted;

}
