package org.xdove.ctcloud.ivm.entity;

import lombok.Data;

import java.util.List;

/**
 * 检测规则
 * @author Wszl
 * @date 2021年7月31日
 */
@Data
public class KitchenUpdateTaskAnalysisRule {
    /** 检测类型 "mouse"老鼠,"phone"打电 */
    private List<String> types;
    /** 检测区域 */
    private List<Integer> area;
}
