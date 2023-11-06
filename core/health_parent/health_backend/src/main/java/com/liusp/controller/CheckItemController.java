package com.liusp.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liusp.constant.MessageConstant;
import com.liusp.entity.PageResult;
import com.liusp.entity.QueryPageBean;
import com.liusp.entity.Result;
import com.liusp.pojo.CheckItem;
import com.liusp.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cr
 * @date 2023年10月30日 10:26
 * @description 处理检查项控制层类
 */
@RestController // spring框架会帮我们创建这个类的对象  返回对象 会直接转为json格式
@RequestMapping("/checkitem")
public class CheckItemController {

    //声明要使用的接口
    @Reference
    private CheckItemService checkItemService;

    //编写 新增处理方法
//    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckItem checkItem){
        //TODO  调用业务方法 完成 添加检查项到数据库
        try { //尝试运行代码 如果出现异常 会被catch捕获
            checkItemService.add(checkItem);
        }catch (Exception e){ //Exception e 代表 捕获的异常
            e.printStackTrace();//打印异常信息
            //返回给前端异常信息
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS,checkItem);
    }

    //分页查询方法
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        System.out.println(queryPageBean.getCurrentPage());
        //调用业务层方法
        PageResult pageResult = checkItemService.findPage(queryPageBean.getCurrentPage()
                ,queryPageBean.getPageSize()
                ,queryPageBean.getQueryString());

        return pageResult;
    }


    //删除方法
//    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("/delete.do")
    public Result delete(Integer id){

        try {
            checkItemService.delete(id);
        }catch (Exception e){ //Exception e 代表 捕获的异常
            e.printStackTrace();//打印异常信息
            //返回给前端异常信息
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    //通过主键id查询检查项
    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        try {
            CheckItem checkItem =  checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){ //Exception e 代表 捕获的异常
            e.printStackTrace();//打印异常信息
            //返回给前端异常信息
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }


    //进行修改编辑
    @RequestMapping("/edit.do")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
        }catch (Exception e){ //Exception e 代表 捕获的异常
            e.printStackTrace();//打印异常信息
            //返回给前端异常信息
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    //查询所有检查项
    @RequestMapping("/findAll.do")
    public Result findAll(){
        List<CheckItem> list=checkItemService.findAll();

        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }
}
