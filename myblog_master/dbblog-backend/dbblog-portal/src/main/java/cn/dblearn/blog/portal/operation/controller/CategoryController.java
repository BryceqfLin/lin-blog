package cn.dblearn.blog.portal.operation.controller;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.constants.RedisCacheNames;
import cn.dblearn.blog.entity.operation.Category;
import cn.dblearn.blog.portal.operation.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * CategoryController
 *
 * @author bobbi
 * @date 2019/02/19 15:28
 * @email 571002217@qq.com
 * @description
 */
@RestController("categoryPortalController")
@CacheConfig(cacheNames = RedisCacheNames.CATEGORY)
@RequestMapping("/operation")
@Api(value = "分类列表", tags = {"分类列表"})
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation(value = "【全部分类】")
    @GetMapping("/categories")
    @Cacheable
    public Result listCategory(@RequestParam Map<String,Object> params) {
        List<Category> categoryList = categoryService.listCategory(params);
        return Result.ok().put("categoryList",categoryList);
    }

}
