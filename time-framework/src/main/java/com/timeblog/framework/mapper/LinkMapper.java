package com.timeblog.framework.mapper;

import com.timeblog.business.domain.Link;
import com.timeblog.business.domain.PageDomain;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author clw
 * @Classname LinkMapper
 * @Description 友链接口
 * @Date 2020/3/12 12:23
 * @Version V1.0
 */
public interface LinkMapper {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param pageDomain 实例对象
     * @return 对象列表
     */
    List<Link> queryAlls(PageDomain pageDomain);

    List<Link> queryAll();

    int insert(Link link);

    int updateStatus(Link link);

    Link queryById(Integer linkId);

    int deleteLinkByIds(List<String> linkIdList);
}
