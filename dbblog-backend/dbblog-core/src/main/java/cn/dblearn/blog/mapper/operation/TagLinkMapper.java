package cn.dblearn.blog.mapper.operation;

import cn.dblearn.blog.entity.operation.TagLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文章标签多对多维护表 Mapper 接口
 * </p>
 *
 * @author bobbi
 * @since 2019-01-07
 */
@Mapper
@Repository
public interface TagLinkMapper extends BaseMapper<TagLink> {

}
