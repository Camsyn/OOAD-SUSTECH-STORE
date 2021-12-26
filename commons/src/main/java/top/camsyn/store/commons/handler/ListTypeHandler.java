package top.camsyn.store.commons.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.*;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理jsonArray字符串为pojoList
 *
 * @author zj
 * @date 2020/1/8
 */
//支持的java对象
@MappedTypes(value = {List.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
@Slf4j
public class ListTypeHandler<T extends Object> implements TypeHandler<List<T>> {

    private List<T> getListByJsonArrayString(String content) {
        if (StringUtils.isEmpty(content)) {
            return new ArrayList<>();
        }
        log.info("db-json 2 List: {}", content);
        return JSON.parseObject(content, new TypeReference<ArrayList<T>>(){});
    }

    /**
     * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型
     *
     * <PRE>
     * PreparedStatement pstmt = con.prepareStatement("UPDATE EMPLOYEES
     * SET SALARY = ? WHERE ID = ?");
     * pstmt.setBigDecimal(1, 153833.00)
     * pstmt.setInt(2, 110592)
     * </PRE>
     *
     * @param preparedStatement An object that represents a precompiled SQL statement
     * @param i                 当前参数的位置
     * @param t                 当前参数的Java对象
     * @param jdbcType          当前参数的数据库类型
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<T> t, JdbcType jdbcType) throws SQLException {
        if (CollectionUtils.isEmpty(t)) {
            preparedStatement.setString(i, null);
        } else {
            preparedStatement.setString(i, JSON.toJSONString(t));
        }
    }

    @Override
    public List<T> getResult(ResultSet resultSet, String s) throws SQLException {
        return getListByJsonArrayString(resultSet.getString(s));
    }

    @Override
    public List<T> getResult(ResultSet resultSet, int i) throws SQLException {
        return getListByJsonArrayString(resultSet.getString(i));
    }

    @Override
    public List<T> getResult(CallableStatement callableStatement, int i) throws SQLException {
        return getListByJsonArrayString(callableStatement.getString(i));
    }
}

