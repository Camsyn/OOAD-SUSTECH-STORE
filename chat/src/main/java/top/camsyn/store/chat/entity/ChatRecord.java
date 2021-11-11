package top.camsyn.store.chat.entity;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@TableName(value = "chat_record")
public class ChatRecord {
    int id;
    int sendId;
    int recvId;
    String content;
    int type;

    LocalDateTime sendTime;
    LocalDateTime recvTime;
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
//public class Account extends Model<top.camsyn.store.commons.entity.Account> {
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
