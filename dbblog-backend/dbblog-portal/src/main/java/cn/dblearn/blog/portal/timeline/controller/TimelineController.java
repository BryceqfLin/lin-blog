package cn.dblearn.blog.portal.timeline.controller;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.entity.timeline.Timeline;
import cn.dblearn.blog.portal.timeline.service.TimelineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * TimeLineController
 *
 * @author bobbi
 * @date 2019/02/24 20:46
 * @email 571002217@qq.com
 * @description
 */
@RestController
@RequestMapping("/timeline")
@Api(value = "时间轴", tags = {"时间轴"})
public class TimelineController {

    @Resource
    private TimelineService timelineService;

    @ApiOperation(value = "【获取时间轴】")
    @GetMapping("")
    public Result listTimeline() {
        List<Timeline> timelineList = timelineService.listTimeLine();

        return Result.sysSuccess(timelineList);
    }
}
