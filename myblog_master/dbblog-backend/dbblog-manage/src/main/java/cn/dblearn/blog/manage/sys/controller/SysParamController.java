package cn.dblearn.blog.manage.sys.controller;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.base.AbstractController;
import cn.dblearn.blog.common.util.PageUtils;
import cn.dblearn.blog.common.validator.ValidatorUtils;
import cn.dblearn.blog.entity.sys.SysParam;
import cn.dblearn.blog.manage.sys.service.SysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统参数 前端控制器
 * </p>
 *
 * @author bobbi
 * @since 2018-12-28
 */
@RestController
@Slf4j
@RequestMapping("/admin/sys/param")
@Api(value = "参数管理", tags = {"参数管理"})
public class SysParamController extends AbstractController {
    @Autowired
    private SysParamService paramService;

    @ApiOperation(value = "【参数列表】")
    @GetMapping("/list")
    @RequiresPermissions("sys:param:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = paramService.queryPage(params);

        return Result.ok().put("page", page);
    }

    @ApiOperation(value = "【获取所有参数】")
    @GetMapping("/all")
    public Result listAll(){
        List<SysParam> sysParamList = paramService.list(null);
        return Result.ok().put("sysParamList",sysParamList);
    }

    @ApiOperation(value = "【参数详情】")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:param:info")
    public Result info(@PathVariable("id") String id){
       SysParam param = paramService.getById(id);

        return Result.ok().put("param", param);
    }

    @ApiOperation(value = "【保存参数】")
    @PostMapping("/save")
    @RequiresPermissions("sys:param:save")
    public Result save(@RequestBody SysParam param){
        ValidatorUtils.validateEntity(param);
        paramService.save(param);

        return Result.ok();
    }

    @ApiOperation(value = "【修改参数】")
    @PutMapping("/update")
    @RequiresPermissions("sys:param:update")
    public Result update(@RequestBody SysParam param){
        ValidatorUtils.validateEntity(param);
        paramService.updateById(param);
        return Result.ok();
    }

    @ApiOperation(value = "【删除参数】")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:param:delete")
    public Result delete(@RequestBody String[] ids){
        paramService.removeByIds(Arrays.asList(ids));

        return Result.ok();
    }
}
