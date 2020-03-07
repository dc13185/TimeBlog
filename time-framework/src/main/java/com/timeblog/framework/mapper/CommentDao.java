package com.timeblog.framework.mapper;

import com.timeblog.business.domain.Comment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Comment)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-05 21:35:10
 */
public interface CommentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    Comment queryById(Integer commentId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Comment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param Comment 实例对象
     * @return 对象列表
     */
    List<Comment> queryAll(Comment Comment);

    /**
     * 根据文章ID查评论
     *
     * @param articleId 文章id
     * @return 对象列表
     */
    List<Comment> queryCommentByArticleId(Integer articleId);



    /**
     * 新增数据
     *
     * @param Comment 实例对象
     * @return 影响行数
     */
    int insert(Comment Comment);

    /**
     * 修改数据
     *
     * @param Comment 实例对象
     * @return 影响行数
     */
    int update(Comment Comment);

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 影响行数
     */
    int deleteById(Integer commentId);

}
