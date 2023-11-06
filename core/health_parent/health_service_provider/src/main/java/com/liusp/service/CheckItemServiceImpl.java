package com.liusp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liusp.dao.CheckItemDao;
import com.liusp.entity.PageResult;
import com.liusp.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cr
 * @date 2023年10月30日 14:04
 * @description  检查项接口的实现类
 */
@Service(interfaceClass =CheckItemService.class ) //声明注解 使用的com.alibaba.dubbo.config包
@Transactional  //开启事务功能
public class CheckItemServiceImpl implements CheckItemService{

    @Autowired //自动注入 springboot框架会自动帮我们创建 该接口的实现类
    CheckItemDao checkItemDao;


    @Override
    public void add(CheckItem checkItem) {
        //调用 dao 数据访问层 接口 实现 sql语句的 添加执行
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //MYbatis 插件完成分页功能

        PageHelper.startPage(currentPage,pageSize); //设置插件 当前页/每页显示条数

        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) {
        //查询当前检查项是否和检查组关联
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count > 0){
            //当前检查项被引用，不能删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
