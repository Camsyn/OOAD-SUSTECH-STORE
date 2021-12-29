package top.camsyn.store.commons.entity.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "label", autoResultMap = true)
public class Label {
    /**
     * 生成
     */
    @TableId(value = "label_name",type = IdType.AUTO)
    String labelName;
    /**
     * 生成
     */
    Integer pullFrequency;
    /**
     * 生成
     */
    Integer pushFrequency;
    private Integer deleted;

}
