package com.heartape.hap.utils;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqlUtils {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 获取批量执行mapper
     * 非线程安全
     * @param clazz mapper类,不要从spring容器获取其代理类
     */
    public <T> T batchMapper(Class<T> clazz) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        return sqlSession.getMapper(clazz);
    }

    /**
     * 获取批量执行mapper
     * 线程安全
     * @param clazz mapper类,不要从spring容器获取其代理类
     */
    public <T> T safeBatchMapper(Class<T> clazz) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        return sqlSessionTemplate.getMapper(clazz);
    }

    /**
     * 将分页信息装配进PageInfo
     */
    public <T> PageInfo<T> assemblePageInfo(List<T> list, long total, int pageNum, int pageSize) {
        PageInfo<T> pageInfo = PageInfo.of(list);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(total);
        int pages = (int)total / pageSize + 1;
        pageInfo.setPages(pages);
        pageInfo.setIsFirstPage(pageNum == 1);
        pageInfo.setIsLastPage(pageNum == pages);
        int prePage = 0;
        boolean hasPreviousPage = false;
        if (pageNum > 0 && pageNum <= pages) {
            prePage = pageNum - 1;
            hasPreviousPage = true;
        }
        pageInfo.setPrePage(prePage);
        pageInfo.setHasPreviousPage(hasPreviousPage);
        int nextPage = 0;
        boolean hasNextPage = false;
        if (pageNum > 0 && pageNum < pages) {
            nextPage = pageNum + 1;
            hasNextPage = true;
        }
        pageInfo.setNextPage(nextPage);
        pageInfo.setHasNextPage(hasNextPage);
        return pageInfo;
    }
}
