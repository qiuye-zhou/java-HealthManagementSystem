package com.liusp.dao;

import com.github.pagehelper.Page;
import com.liusp.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Object> map);

    Page<CheckGroup> selectByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(Integer id);

    long findSetMealCountByCheckGroupId(Integer id);

    void delete(Integer id);

    List<CheckGroup> findAll();
}
