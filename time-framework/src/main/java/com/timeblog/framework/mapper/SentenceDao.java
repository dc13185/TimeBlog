package com.timeblog.framework.mapper;


import com.timeblog.business.domain.Sentence;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Sentence)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-09 18:02:30
 */
public interface SentenceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param sentenceId 主键
     * @return 实例对象
     */
    Sentence queryById(Integer sentenceId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Sentence> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param createDate 时间
     * @return 对象列表
     */
    List<Sentence> queryByDate(String createDate);

    /**
     * 新增数据
     *
     * @param Sentence 实例对象
     * @return 影响行数
     */
    int insert(Sentence Sentence);

    void insertBatch(List<Sentence> sentences);

    /**
     * 修改数据
     *
     * @param Sentence 实例对象
     * @return 影响行数
     */
    int update(Sentence Sentence);

    /**
     * 通过主键删除数据
     *
     * @param sentenceId 主键
     * @return 影响行数
     */
    int deleteById(Integer sentenceId);


    /**
     * 根据时间删除
     *
     * @param date 时间字符串
     * @return 影响行数
     */
    int deleteByCreateDate(String date);

}
