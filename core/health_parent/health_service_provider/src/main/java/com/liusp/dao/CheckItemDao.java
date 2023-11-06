package com.liusp.dao;

import com.github.pagehelper.Page;
import com.liusp.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);

    long findCountByCheckItemId(Integer id);

    void deleteById(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

    long findCountByCheckGroupId(Integer id);
}
