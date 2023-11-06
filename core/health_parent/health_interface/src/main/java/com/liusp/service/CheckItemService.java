package com.liusp.service;

import com.liusp.entity.PageResult;
import com.liusp.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    void add(CheckItem checkItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
