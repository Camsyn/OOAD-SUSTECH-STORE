package top.camsyn.store.request.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchDto implements Serializable {
    private static final long serialVersionUID = -2084416068307485742L;

    /**
     * 搜索关键字
     */
    private String queryStr;
    /**
     * 当前页数
     */
    private Integer page;
    /**
     * 每页显示数
     */
    private Integer limit;
    /**
     * 搜索类型：-1: 不搜索   0: 标题    1: 标题+简介   2: 标签    3: 全局
     */
    private Integer type;

    /**
     * label 限制
     */
    private List<String> labels;


    private String firstOrder;
    private boolean isFirstOrderAsc;
    private String secondOrder;
//    private boolean isFirstOrderAsc;
}

