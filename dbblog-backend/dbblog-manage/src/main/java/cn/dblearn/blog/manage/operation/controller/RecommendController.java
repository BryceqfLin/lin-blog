package cn.dblearn.blog.manage.operation.controller;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.base.AbstractController;
import cn.dblearn.blog.common.constants.RedisCacheNames;
import cn.dblearn.blog.common.util.PageUtils;
import cn.dblearn.blog.common.validator.ValidatorUtils;
import cn.dblearn.blog.entity.operation.Recommend;
import cn.dblearn.blog.entity.operation.vo.RecommendVO;
import cn.dblearn.blog.manage.operation.service.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 推荐 前端控制器
 * </p>
 *
 * @author bobbi
 * @since 2019-02-22
 */
@RestController
@RequestMapping("/admin/operation/recommend")
@CacheConfig(cacheNames = RedisCacheNames.RECOMMEND)
@Api(value = "推荐管理", tags = {"推荐管理"})
public class RecommendController extends AbstractController {
    @Resource
    private RecommendService recommendService;

    @ApiOperation(value = "【分页获取推荐列表】")
    @GetMapping("/list")
    @RequiresPermissions("operation:recommend:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = recommendService.queryPage(params);

        return Result.sysSuccess(page);
    }

    @ApiOperation(value = "【获取所有推荐列表】")
    @GetMapping("/select")
    @RequiresPermissions("operation:recommend:list")
    public Result select(@ApiParam(value = "推荐类型") @RequestParam(required = false) String type,
                         @ApiParam(value = "文章标题") @RequestParam(required = false) String name) {
        List<RecommendVO> recommendList = recommendService.listSelect(type,name);
        return Result.sysSuccess(recommendList);
    }

    @ApiOperation(value = "【推荐详情】")
    @GetMapping("/info/{id}")
    @RequiresPermissions("operation:recommend:info")
    public Result info(@PathVariable("id") String id) {
        Recommend recommend = recommendService.getById(id);

        return Result.sysSuccess(recommend);
    }

    @ApiOperation(value = "【保存推荐】")
    @PostMapping("/save")
    @RequiresPermissions("operation:recommend:save")
    @CacheEvict(allEntries = true)
    public Result save(@RequestBody Recommend recommend) {
        ValidatorUtils.validateEntity(recommend);
        recommendService.save(recommend);

        return Result.sysSuccess();
    }

    @ApiOperation(value = "【修改推荐】")
    @PutMapping("/update")
    @RequiresPermissions("operation:recommend:update")
    @CacheEvict(allEntries = true)
    public Result update(@RequestBody Recommend recommend) {
        ValidatorUtils.validateEntity(recommend);
        recommendService.updateById(recommend);
        return Result.sysSuccess();
    }

    @ApiOperation(value = "【推荐置顶】")
    @PutMapping("/top/{id}")
    @RequiresPermissions("operation:recommend:update")
    @CacheEvict(allEntries = true)
    public Result updateTop(@PathVariable Integer id) {
        recommendService.updateTop(id);
        return Result.sysSuccess();
    }

    @ApiOperation(value = "【删除推荐】")
    @DeleteMapping("/delete")
    @RequiresPermissions("operation:recommend:delete")
    @CacheEvict(allEntries = true)
    public Result delete(@RequestBody String[] ids) {
        recommendService.removeByIds(Arrays.asList(ids));

        return Result.sysSuccess();
    }
}
