package top.camsyn.store.request.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
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
     * 搜索类型：0或者null: 不搜索   1: 标题    2: 标题+简介   3: 标签    4: 全局
     */
    @Builder.Default
    public Integer searchStrategy = 0;

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
    @Builder.Default
    public Boolean isAmbiguous=true;


    /**
     * 检索的价格区间
     */
    @Builder.Default
    public Double priceFrom = 0.0;
    @Builder.Default
    public Double priceTo = Double.MAX_VALUE;

    /**
     *  含义参见 Request类, 为List是因为可以多个type同时搜索, 当为空或者null时表示不做限制
     */
    public List<Integer> types;
    public List<Integer> tradeTypes;
    public List<Integer> publishers;

    /**
     * 无： 展示所有   0: 仅仅展示开启的请求    1: 展示关闭的请求  (无法展示隐藏的、审核的、封禁的请求)
     */
    public Integer openState;

    /**
     * 0: 按更新时间， 1: 按发布时间（通过审核的时间）  2: 按请求创建时间
     */
    public Integer timeState;
    /**
     * 搜索的时间区间
     */
    public LocalDateTime after;
    public LocalDateTime before;

//    public boolean isFirstOrderAsc;
}

