package cn.dblearn.blog.portal.operation.controller;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.constants.RedisCacheNames;
import cn.dblearn.blog.entity.operation.Link;
import cn.dblearn.blog.portal.operation.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * LinkController
 *
 * @author bobbi
 * @date 2019/02/21 17:09
 * @email 571002217@qq.com
 * @description
 */
@RequestMapping("/operation")
@CacheConfig(cacheNames = RedisCacheNames.LINK)
@RestController("LinkPortalController")
@Api(value = "链接列表", tags = {"链接列表"})
public class LinkController {

    @Resource
    private LinkService linkService;

    @ApiOperation(value = "【全部链接】")
    @RequestMapping("/links")
    @Cacheable
    public Result listLink() {
        List<Link> linkList = linkService.listLink();
        return Result.ok().put("linkList",linkList);
    }
}
