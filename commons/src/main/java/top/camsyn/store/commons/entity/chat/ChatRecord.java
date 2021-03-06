package top.camsyn.store.commons.entity.chat;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "chat_record")
public class ChatRecord {
    // （生成）
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    // （生成）
    int sendId;
    int recvId;
    String content;
    int type;

    // （生成）
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime sendTime;
    // （生成）
    LocalDateTime recvTime;
    // （生成）
    boolean isRead;

    @Override
    public String toString(){
        return JSON.toJSONString(this,true);
    }
}

//@EqualsAndHashCode(callSuper = true)
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Builder
//public class Account extends Model<top.camsyn.store.commons.entity.auth.Account> {
//    @TableId()
//    private Integer sid;
//    @TableField(value = "pwd")
//    private String password;
//    private String email;
//
//
//    @TableField(fill = FieldFill.INSERT)
//    private Date createTime;
//
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private Date updateTime;
//}
