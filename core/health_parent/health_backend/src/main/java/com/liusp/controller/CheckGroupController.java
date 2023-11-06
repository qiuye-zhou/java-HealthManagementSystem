package com.liusp.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liusp.constant.MessageConstant;
import com.liusp.entity.PageResult;
import com.liusp.entity.QueryPageBean;
import com.liusp.entity.Result;
import com.liusp.pojo.CheckGroup;
import com.liusp.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cr
 * @date 2023年10月31日 10:14
 * @description  检查组 控制层
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {


    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){

        try {
            checkGroupService.add(checkGroup,checkitemIds);
        }catch (Exception e){ //Exception e 代表 捕获的异常
            e.printStackTrace();//打印异常信息
            //返回给前端异常信息
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }


    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务层方法
        PageResult pageResult = checkGroupService.findPage(queryPageBean.getCurrentPage()
                ,queryPageBean.getPageSize()
                ,queryPageBean.getQueryString());

        return pageResult;
    }


    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        try {
            //根据检查组id 查询一条检查组信息
            CheckGroup checkGroup =  checkGroupService.findById(id);
            //返回前端 响应数据
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId.do")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try {
            //根据检查组id 查询一条检查组信息
            List<Integer>  ids=  checkGroupService.findCheckItemIdsByCheckGroupId(id);
            //返回前端 响应数据
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,ids);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }



    @RequestMapping("/edit.do")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            //调用修改业务
            checkGroupService.edit(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    //删除
    @RequestMapping("/delete.do")
    public Result delete(Integer id){

        try {
            //调用修改业务
            checkGroupService.delete(id);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);

    }

    //查询所有检查组
    @RequestMapping("/findAll.do")
    public Result findAll(){
        try {
            //调用修改业务
            List<CheckGroup> list = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
