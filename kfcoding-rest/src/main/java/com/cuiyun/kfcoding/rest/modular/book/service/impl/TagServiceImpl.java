package com.cuiyun.kfcoding.rest.modular.book.service.impl;

import com.cuiyun.kfcoding.rest.modular.book.model.Tag;
import com.cuiyun.kfcoding.rest.modular.book.dao.TagMapper;
import com.cuiyun.kfcoding.rest.modular.book.service.ITagService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maple123
 * @since 2018-06-07
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
