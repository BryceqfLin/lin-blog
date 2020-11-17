package cn.dblearn.blog.manage.article.controller;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.constants.RedisCacheNames;
import cn.dblearn.blog.common.enums.ModuleEnum;
import cn.dblearn.blog.common.mq.annotation.RefreshEsMqSender;
import cn.dblearn.blog.common.util.PageUtils;
import cn.dblearn.blog.common.validator.ValidatorUtils;
import cn.dblearn.blog.entity.article.Article;
import cn.dblearn.blog.entity.article.dto.ArticleDTO;
import cn.dblearn.blog.manage.article.service.ArticleService;
import cn.dblearn.blog.manage.operation.service.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * ArticleAdminController
 *
 * @author bobbi
 * @date 2018/11/20 20:25
 * @email 571002217@qq.com
 * @description
 */
@RestController
@RequestMapping("/admin/article")
@CacheConfig(cacheNames ={RedisCacheNames.RECOMMEND,RedisCacheNames.TAG,RedisCacheNames.ARTICLE,RedisCacheNames.TIMELINE})
@Api(value = "文章管理", tags = {"文章管理"})
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private RecommendService recommendService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @ApiOperation(value = "【分页查询文章列表】")
    @GetMapping("/list")
    @RequiresPermissions("article:list")
    public Result listArticle(@RequestParam Map<String, Object> params) {
        PageUtils page = articleService.queryPage(params);
        return Result.ok().put("page",page);
    }

    @ApiOperation(value = "【文章详情】")
    @GetMapping("/info/{articleId}")
    @RequiresPermissions("article:list")
    public Result info(@PathVariable Integer articleId) {
        ArticleDTO article = articleService.getArticle(articleId);
        return Result.ok().put("article",article);
    }

    @ApiOperation(value = "【新增文章】")
    @PostMapping("/save")
    @RequiresPermissions("article:save")
    @CacheEvict(allEntries = true)
    @RefreshEsMqSender(sender = "dbblog-manage-saveArticle")
    public Result saveArticle(@RequestBody ArticleDTO article){
        ValidatorUtils.validateEntity(article);
        articleService.saveArticle(article);
        return Result.ok();
    }

    @ApiOperation(value = "【更新文章】")
    @PutMapping("/update")
    @RequiresPermissions("article:update")
    @CacheEvict(allEntries = true)
    @RefreshEsMqSender(sender = "dbblog-manage-updateArticle")
    public Result updateArticle(@RequestBody ArticleDTO article){
        ValidatorUtils.validateEntity(article);
        articleService.updateArticle(article);
        return Result.ok();
    }

    @ApiOperation(value = "【更新状态】")
    @PutMapping("/update/status")
    @RequiresPermissions("article:update")
    @CacheEvict(allEntries = true)
    @RefreshEsMqSender(sender = "dbblog-manage-updateStatus")
    public Result updateStatus(@RequestBody Article article) {
        articleService.updateById(article);
        return Result.ok();
    }

    @ApiOperation(value = "【删除文章】")
    @DeleteMapping("/delete")
    @RequiresPermissions("article:delete")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    @RefreshEsMqSender(sender = "dbblog-manage-deleteArticle")
    public Result deleteBatch(@RequestBody Integer[] articleIds) {
        recommendService.deleteBatchByLinkId(articleIds, ModuleEnum.ARTICLE.getValue());
        articleService.deleteBatch(articleIds);
        return Result.ok();
    }

    @ApiOperation(value = "【缓存刷新】")
    @DeleteMapping("/cache/refresh")
    @RequiresPermissions("article:cache:refresh")
    public Result flush() {
        Set<String> keys = redisTemplate.keys(RedisCacheNames.PROFIX+"*");
        redisTemplate.delete(keys);
        return Result.ok();
    }

}
