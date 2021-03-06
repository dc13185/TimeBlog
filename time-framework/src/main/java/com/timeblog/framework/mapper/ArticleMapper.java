package com.timeblog.framework.mapper;

import com.timeblog.business.domain.Article;
import com.timeblog.business.domain.PageDomain;
import com.timeblog.business.domain.QueryArticleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lmm
 * @Classname ArticleMapper
 * @Description 文章接口
 * @Date 2020/2/9 10:28
 * @Version V1.0
 */
public interface ArticleMapper {


    /**
     * 通过ID查询单条数据
     *
     * @param articleId 主键
     * @return 实例对象
     */
    Article queryById(Integer articleId);

    /**
     * 通过ID查询单条数据和对应标签
     *
     * @param articleId 主键
     * @return 实例对象
     */
    Article queryArticleAndLabelsById(Integer articleId);


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Article> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Article> queryAllByTypeAndLimit(@Param("articleTypeId") int articleTypeId,@Param("offset") int offset, @Param("limit") int limit);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param pageDomain 查询所有
     * @return 对象列表
     */
    List<Article> queryAll(PageDomain pageDomain);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param pageDomain 查询所有已经发布了的
     * @return 对象列表
     */
    List<Article> queryAllFormal(QueryArticleEntity queryArticleEntity);

    /**
     * 新增数据
     *
     * @param Article 实例对象
     * @return 影响行数
     */
    int insert(Article Article);

    /**
     * 修改数据
     *
     * @param Article 实例对象
     * @return 影响行数
     */
    int update(Article Article);

    /**
     * 通过主键删除数据
     *
     * @param articleId 主键
     * @return 影响行数
     */
    int deleteById(Integer articleId);


    int queryCount();

    int deleteByArticleIds(List<String> articleIdList);
}

