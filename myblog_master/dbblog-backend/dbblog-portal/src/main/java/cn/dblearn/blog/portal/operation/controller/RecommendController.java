package cn.dblearn.blog.portal.operation.controller;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.constants.RedisCacheNames;
import cn.dblearn.blog.entity.operation.vo.RecommendVO;
import cn.dblearn.blog.portal.operation.service.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Recommend
 *
 * @author bobbi
 * @date 2019/02/22 13:40
 * @email 571002217@qq.com
 * @description
 */
@RestController("recommendPortalController")
@CacheConfig(cacheNames = RedisCacheNames.RECOMMEND)
@RequestMapping("/operation")
@Api(value = "推荐浏览", tags = {"推荐浏览"})
public class RecommendController {

    @Resource
    private RecommendService recommendService;

    @ApiOperation(value = "【推荐列表】")
    @RequestMapping("/recommends")
    @Cacheable(key = "'RECOMMEND'")
    public Result listRecommend() {
        List<RecommendVO> recommendList = recommendService.listRecommendVo();
        return Result.ok().put("recommendList",recommendList);
    }

    @ApiOperation(value = "【热门推荐】")
    @RequestMapping("/hotReads")
    @Cacheable(key = "'HOTREAD'")
    public Result listHotRead () {
        List<RecommendVO> hotReadList = recommendService.listHotRead();
        return Result.ok().put("hotReadList",hotReadList);
    }
}
