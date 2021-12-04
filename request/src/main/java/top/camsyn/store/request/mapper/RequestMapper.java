package top.camsyn.store.request.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.request.dto.SearchDto;

import java.util.List;

@Mapper
public interface RequestMapper extends BaseMapper<Request> {


    /**
     * 联表的分页查询
     */
    @Select("select r.*, l.label_name\n" +
            "from request r\n" +
            "         inner join request_label_relation rlr on r.id = rlr.request_id\n" +
            "         inner join label l on rlr.label_id = l.id"+
            "         ${ew.customSqlSegment}"
    )
    List<Request> pageOfRequestByLabel(@Param("page")IPage<Request> page,
                                       @Param(Constants.WRAPPER) Wrapper<Request> queryWrapper);

  /**
     * 联表的分页查询
     */
    @Select("select r.*\n" +
            "from request r\n" +
            "         inner join request_label_relation rlr on r.id = rlr.request_id\n" +
            "         inner join label l on rlr.label_id = l.id"+
            "         ${ew.customSqlSegment}"
    )
    List<Request> getRequestByLabel(@Param(Constants.WRAPPER) Wrapper<Request> queryWrapper);


    /**
     * 搜索的动态sql实现
     * @param page 分页
     * @param searchDto 搜索凭据
     * @return 搜索的结果
     */
    @Select("<script>\n" +
            "    select r.* from request r\n" +
            "    left join request_label_relation rlr on r.id = rlr.request_id\n" +
            "    left join label l on rlr.label_id = l.id\n" +
            "    <where>\n" +
            "        (\n" +
            "        ( r.exact_price &gt;= ${s.priceFrom} and  r.exact_price &lt;= ${s.priceTo} )\n" +
            "        or\n" +
            "        ( r.float_price_from &gt;= ${s.priceFrom} and r.float_price_to &lt;= ${s.priceTo} )\n" +
            "        )\n" +
            "        <choose>\n" +
            "            <when test='s.isAmbiguous = false'>\n" +
            "                <choose>\n" +
            "                    <when test='s.searchStrategy==1' >\n" +
            "                        and r.title = #{s.queryStr}\n" +
            "                    </when>\n" +
            "                    <when test='s.searchStrategy==2' >\n" +
            "                        and ( r.title = #{s.queryStr} or r.desc = #{s.queryStr} )\n" +
            "                    </when>\n" +
            "                    <when test='s.searchStrategy==3' >\n" +
            "                        and l.label_name = #{s.queryStr}\n" +
            "                    </when>\n" +
            "                    <when test='s.searchStrategy==4' >\n" +
            "                        and ( r.title = #{s.queryStr} or r.desc = #{s.queryStr} or l.label_name = #{s.queryStr})\n" +
            "                    </when>\n" +
            "                    <otherwise></otherwise>\n" +
            "                </choose>\n" +
            "            </when>\n" +
            "            <otherwise>\n" +
            "                <choose>\n" +
            "                    <when test='s.searchStrategy==1' >\n" +
            "                        and r.title regexp #{s.queryStr}\n" +
            "                    </when>\n" +
            "                    <when test='s.searchStrategy==2' >\n" +
            "                        and ( r.title regexp #{s.queryStr} or r.desc regexp #{s.queryStr} )\n" +
            "                    </when>\n" +
            "                    <when test='s.searchStrategy==3' >\n" +
            "                        and l.label_name regexp #{s.queryStr}\n" +
            "                    </when>\n" +
            "                    <when test='s.searchStrategy==4' >\n" +
            "                        and ( r.title regexp #{s.queryStr} or r.desc regexp #{s.queryStr} or l.label_name regexp #{s.queryStr})\n" +
            "                    </when>\n" +
            "                    <otherwise></otherwise>\n" +
            "                </choose>\n" +
            "            </otherwise>\n" +
            "        </choose>\n" +
            "        \n" +
            "        <if test='s.labels != null and s.labels.isEmpty() != true'>\n" +
            "            and\n" +
            "            l.label_name in\n" +
            "            <foreach collection=\"s.labels\" item=\"label\" open=\"(\" close=\")\" separator=\",\" index=\"i\">\n" +
            "                #{label}\n" +
            "            </foreach>\n" +
            "        </if>\n" +
            "        \n" +
            "        <if test='s.types != null and s.types.isEmpty() != true'>\n" +
            "            and\n" +
            "            r.type in\n" +
            "            <foreach collection=\"s.types\" item=\"type\" open=\"(\" close=\")\" separator=\",\" index=\"i\">\n" +
            "                ${type}\n" +
            "            </foreach>\n" +
            "        </if>\n" +
            "        \n" +
            "        <if test='s.tradeTypes != null and s.tradeTypes.isEmpty() != true'>\n" +
            "            and\n" +
            "            r.type in\n" +
            "            <foreach collection=\"s.tradeTypes\" item=\"tradeType\" open=\"(\" close=\")\" separator=\",\" index=\"i\">\n" +
            "                ${tradeType}\n" +
            "            </foreach>\n" +
            "        </if>\n" +
            "        \n" +
            "        <if test='s.publishers != null and s.publishers.isEmpty() != true'>\n" +
            "            and\n" +
            "            r.type in\n" +
            "            <foreach collection=\"s.publishers\" item=\"publisher\" open=\"(\" close=\")\" separator=\",\" index=\"i\">\n" +
            "                ${publisher}\n" +
            "            </foreach>\n" +
            "        </if>\n" +
            "        \n" +
            "        <if test='s.openState != null and (s.openState=0 or s.openState=1)'>\n" +
            "            and\n" +
            "            r.state = \n" +
            "               <choose>\n" +
            "                   <when test='s.openState==0'>3</when>\n" +
            "                   <when test='s.openState==1'>2</when>\n" +
            "               </choose>" +
            "        </if>\n" +
            "        \n" +
            "        <if test='s.after != null'>\n" +
            "            and\n" +
            "            <choose>\n" +
            "                <when test='s.timeState==0'>r.update_time</when>\n" +
            "                <when test='s.timeState==1'>r.publish_time</when>\n" +
            "                <otherwise>r.create_time</otherwise>\n" +
            "            </choose>" +
            "            &gt;= #{s.after}" +
            "        </if>\n" +
            "        \n" +
            "        <if test='s.before != null'>\n" +
            "            and\n" +
            "            <choose>\n" +
            "                <when test='s.timeState==0'>r.update_time</when>\n" +
            "                <when test='s.timeState==1'>r.publish_time</when>\n" +
            "                <otherwise>r.create_time</otherwise>\n" +
            "            </choose>" +
            "            &lt;= #{s.before}" +
            "        </if>\n" +
            "        \n" +
            "        <choose>\n" +
            "            <when test='s.isRandom == true'>\n" +
            "                order by rand()\n" +
            "            </when>\n" +
            "            <otherwise>\n" +
            "                <choose>\n" +
            "                    <when test='s.firstOrder != null and s.firstOrder.trim() != &quot;&quot;'>\n" +
            "                        order by ${s.firstOrder}\n" +
            "                        <if test='s.isFirstOrderAsc != null and s.isFirstOrderAsc != true'>\n" +
            "                            desc\n" +
            "                        </if>\n" +
            "                        <if test='s.secondOrder != null and s.secondOrder.trim() != &quot;&quot;'>\n" +
            "                            , ${s.secondOrder}\n" +
            "                            <if test='s.isSecondOrderAsc != null and s.isSecondOrderAsc != true'>\n" +
            "                                desc\n" +
            "                            </if>\n" +
            "                            <if test='s.thirdOrder != null and s.thirdOrder.trim() != &quot;&quot;'>\n" +
            "                                , ${s.thirdOrder}\n" +
            "                                <if test='s.isThirdOrderAsc != null and s.isThirdOrderAsc != true'>\n" +
            "                                    desc\n" +
            "                                </if>\n" +
            "                            </if>\n" +
            "                        </if>\n" +
            "                    </when>\n" +
            "                    <otherwise>\n" +
            "                        order by r.update_time desc\n" +
            "                    </otherwise>\n" +
            "                </choose>\n" +
            "            </otherwise>\n" +
            "        </choose>\n" +
            "\n" +
            "    </where>\n" +
            "</script>")
    IPage<Request> search(@Param("page")IPage<Request> page, @Param("s") SearchDto searchDto);

}
