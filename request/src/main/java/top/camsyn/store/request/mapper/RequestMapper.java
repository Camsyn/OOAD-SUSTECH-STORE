package top.camsyn.store.request.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.bouncycastle.cert.ocsp.Req;
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
            "    inner join request_label_relation rlr on r.id = rlr.request_id\n" +
            "    inner join label l on rlr.label_id = l.id\n" +
            "    <where>\n" +
            "        (\n" +
            "        ( r.exact_price between ${s.priceFrom} and ${s.priceTo} )\n" +
            "        or\n" +
            "        ( r.float_price_from &gt;= ${s.priceFrom} and r.float_price_to &lt;= ${s.priceTo} )\n" +
            "        )\n" +
            "        <choose>\n" +
            "            <when test='s.isAmbiguous = false'>\n" +
            "                <choose>\n" +
            "                    <when test='s.type=0' >\n" +
            "                        and r.title = #{s.queryStr}\n" +
            "                    </when>\n" +
            "                    <when test='s.type=1' >\n" +
            "                        and ( r.title = #{s.queryStr} or r.desc = #{s.queryStr} )\n" +
            "                    </when>\n" +
            "                    <when test='s.type=2' >\n" +
            "                        and l.label_name = #{s.queryStr}\n" +
            "                    </when>\n" +
            "                    <when test='s.type=3' >\n" +
            "                        and ( r.title = #{s.queryStr} or r.desc = #{s.queryStr} or l.label_name = #{s.queryStr})\n" +
            "                    </when>\n" +
            "                </choose>\n" +
            "            </when>\n" +
            "            <otherwise>\n" +
            "                <choose>\n" +
            "                    <when test='s.type=0' >\n" +
            "                        and r.title regexp #{s.queryStr}\n" +
            "                    </when>\n" +
            "                    <when test='s.type=1' >\n" +
            "                        and ( r.title regexp #{s.queryStr} or r.desc regexp #{s.queryStr} )\n" +
            "                    </when>\n" +
            "                    <when test='s.type=2' >\n" +
            "                        and l.label_name regexp #{s.queryStr}\n" +
            "                    </when>\n" +
            "                    <when test='s.type=3' >\n" +
            "                        and ( r.title regexp #{s.queryStr} or r.desc regexp #{s.queryStr} or l.label_name regexp #{s.queryStr})\n" +
            "                    </when>\n" +
            "                </choose>\n" +
            "            </otherwise>\n" +
            "        </choose>\n" +
            "        \n" +
            "        <if test='s.labels != null and s.label.isEmpty() != true'>\n" +
            "            and\n" +
            "            l.label in\n" +
            "            <foreach collection=\"s.labels\" item=\"label\" open=\"(\" close=\")\" separator=\",\" index=\"i\">\n" +
            "                #{label}\n" +
            "            </foreach>\n" +
            "        </if>\n" +
            "        \n" +
            "        <choose>\n" +
            "            <when test='s.isRandom == true'>\n" +
            "                order by rand()\n" +
            "            </when>\n" +
            "            <otherwise>\n" +
            "                <choose>\n" +
            "                    <when test='s.firstOder != null and s.firstOder.trim() != &quot;&quot;'>\n" +
            "                        order by ${s.firstOder}\n" +
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
