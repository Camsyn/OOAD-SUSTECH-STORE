package top.camsyn.store.request.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchDto implements Serializable {
    public static final long serialVersionUID = -2084416068307485742L;

    /**
     * 搜索关键字
     */
    public String queryStr;
    /**
     * 当前页数
     */
    public Integer page;
    /**
     * 每页显示数
     */
    public Integer limit;
    /**
     * 搜索类型：-1: 不搜索   0: 标题    1: 标题+简介   2: 标签    3: 全局
     */
    public Integer type;

    /**
     * label 限制
     */
    public List<String> labels;


    /**
     * 排序依据, 是否正序
     */
    public String firstOrder;
    public Boolean isFirstOrderAsc;
    public String secondOrder;
    public Boolean isSecondOrderAsc;
    public String thirdOrder;
    public Boolean isThirdOrderAsc;

    /**
     * 是否随机返回
     */
    public Boolean isRandom;

    /**
     * 是否模糊匹配(默认开启, 通过整正则匹配)
     */
    public Boolean isAmbiguous=true;


    /**
     * 检索的价格区间
     */
    public Double priceFrom = Double.MAX_VALUE;
    public Double priceTo = 0.0;


//    public boolean isFirstOrderAsc;
}

