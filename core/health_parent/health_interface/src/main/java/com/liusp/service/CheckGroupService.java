package com.liusp.service;

import com.liusp.entity.PageResult;
import com.liusp.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);


    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void delete(Integer id);

    List<CheckGroup> findAll();
}
