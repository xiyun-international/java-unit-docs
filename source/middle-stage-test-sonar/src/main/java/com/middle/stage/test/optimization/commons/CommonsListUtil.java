package com.middle.stage.test.optimization.commons;


import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @author zyq
 */
@Data
public class CommonsListUtil<T> {

    /**
     * 输入参数-新数据集合
     */
    private List<T> newObjectList;

    /**
     * 输入参数-旧数据集合
     */
    private List<T> oldObjectList;

    /**
     * 新集合求出的差集
     */
    private List<T> tmpNewObjectList;
    /**
     * 旧集合求出的差集
     */
    private List<T> tmpOldObjectList;
    /**
     * 新集合求出的交集
     */
    private List<T> tmpIntersectNewObjectList;
    /**
     * 旧集合求出的交集
     */
    private List<T> tmpIntersectOldObjectList;

    public CommonsListUtil(List<T> newObjectList, List<T> oldObjectList) {
        this.newObjectList = newObjectList;
        this.oldObjectList = oldObjectList;
    }

    public CommonsListUtil invoke() {

        //复制原集合
        tmpNewObjectList = Lists.newArrayList();
        tmpNewObjectList.addAll(newObjectList);
        tmpOldObjectList = Lists.newArrayList();
        tmpOldObjectList.addAll(oldObjectList);
        tmpIntersectNewObjectList = Lists.newArrayList();
        tmpIntersectNewObjectList.addAll(newObjectList);
        tmpIntersectOldObjectList = Lists.newArrayList();
        tmpIntersectOldObjectList.addAll(oldObjectList);

        //求差集操作（对象必须重写hashCode 和equals方法）
        tmpNewObjectList.removeAll(oldObjectList);
        tmpOldObjectList.removeAll(newObjectList);

        //求交集操作（对象必须重写hashCode 和equals方法）
        tmpIntersectNewObjectList.retainAll(oldObjectList);
        tmpIntersectOldObjectList.retainAll(newObjectList);
        return this;
    }
}