package com.heartape.hap.business.service.impl;

import com.heartape.hap.business.entity.Article;
import com.heartape.hap.business.mapper.ArticleMapper;
import com.heartape.hap.business.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heartape
 * @since 2022-03-12
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
