package cn.dblearn.blog.portal.operation.controller;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.constants.RedisCacheNames;
import cn.dblearn.blog.entity.operation.vo.TagVO;
import cn.dblearn.blog.portal.operation.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * TagController
 *
 * @author bobbi
 * @date 2019/02/22 16:34
 * @email 571002217@qq.com
 * @description
 */
@RestController("tagPortalController")
@CacheConfig(cacheNames = RedisCacheNames.TAG)
@RequestMapping("/operation")
@Api(value = "标签浏览", tags = {"标签浏览"})
public class TagController {

    @Resource
    private TagService tagService;

    @ApiOperation(value = "【全部标签】")
    @GetMapping("/tags")
    @Cacheable
    public Result listTag() {
        List<TagVO> tagList = tagService.listTagVo();
        return Result.ok().put("tagList",tagList);
    }

}
