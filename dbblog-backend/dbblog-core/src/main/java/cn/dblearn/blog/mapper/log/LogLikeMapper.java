package cn.dblearn.blog.mapper.log;

import cn.dblearn.blog.entity.log.LogLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 阅读日志 Mapper 接口
 * </p>
 *
 * @author bobbi
 * @since 2019-02-15
 */
@Mapper
@Repository
public interface LogLikeMapper extends BaseMapper<LogLike> {

}