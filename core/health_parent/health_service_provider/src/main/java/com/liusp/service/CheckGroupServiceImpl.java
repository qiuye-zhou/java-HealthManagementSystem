package com.liusp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liusp.dao.CheckGroupDao;
import com.liusp.dao.CheckItemDao;
import com.liusp.entity.PageResult;
import com.liusp.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cr
 * @date 2023年10月31日 10:21
 * @description
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{

    @Autowired
    CheckGroupDao checkGroupDao;
    @Autowired
    CheckItemDao checkItemDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.将检查组的基本信息 存入 t_checkgroup 表中
        checkGroupDao.add(checkGroup);
        //2.将检查组和 检查项的关联关系 保存在 t_checkgroup_checkitem 表中

        //insert into t_checkgroup_checkitem (checkgroup_id , checkitem_id )
        // values (#{checkgroup_id},#{ checkitem_id})    5   28 29 30 31 32
        setCheckGroupAndCheckItem(checkGroup, checkitemIds);

    }

    private void setCheckGroupAndCheckItem(CheckGroup checkGroup, Integer[] checkitemIds) {
        for (Integer id : checkitemIds) {
            Map<String,Object> map = new HashMap<>();
            map.put("checkgroup_id", checkGroup.getId());
            map.put("checkitem_id",id);
            checkGroupDao.setCheckGroupAndCheckItem(map);
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize); //设置插件 当前页/每页显示条数

        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setCheckGroupAndCheckItem(checkGroup,checkitemIds);
        //更新检查组基本信息
        checkGroupDao.edit(checkGroup);
    }

    @Override
    public void delete(Integer id) {
    /*    Boolean flag = false; //默认没有与这两个表进行关联
        //1. 判断当前的检查组是否与检查项进行了关联
        long count = checkItemDao.findCountByCheckGroupId(id);
        if(count>0){
            flag =true;
        }
        //2. 判断当前检查组是否与 套餐进行了关联
        long count1= checkGroupDao.findSetMealCountByCheckGroupId(id);
        if(count1>0){
            flag =true;
        }
        if(flag){ //与其他表 有关联关系 不能删除
            throw new RuntimeException("该检查组被关联,不能删除!");
        }

        //3.如果都没有 直接删除
        checkGroupDao.delete(id);*/


        //1.判断该检查组是否与 套餐有关联关系 如果有不能删除
        long count= checkGroupDao.findSetMealCountByCheckGroupId(id);
        if(count>0){
            throw new RuntimeException("该检查组被关联,不能删除!");
        }

        //2.将该检查组的关联关系删除 t_checkgroup_checkitem
        checkGroupDao.deleteAssociation(id);

        //3.删除检查组 t_checkgroup
        checkGroupDao.delete(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
