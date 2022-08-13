package com.zml.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zml.wiki.domain.Ebook;
import com.zml.wiki.domain.EbookExample;
import com.zml.wiki.mapper.EbookMapper;
import com.zml.wiki.req.EbookQueryReq;
import com.zml.wiki.req.EbookSaveReq;
import com.zml.wiki.resp.EbookResp;
import com.zml.wiki.resp.PageResp;
import com.zml.wiki.util.CopyUtil;
import com.zml.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
//    @Autowired
    private EbookMapper ebookMapper;
    @Resource
//    @Autowired
    private SnowFlake snowFlake;

    public PageResp<EbookResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        //动态sql
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");//分为左匹配和右匹配，这里为任意匹配
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<EbookResp> respList = new ArrayList<>();

//列表复制
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
        //        for (Ebook ebook : ebookList) {
////            EbookResp ebookResp = new EbookResp();
////            BeanUtils.copyProperties(ebook,ebookResp);
        //对象复制
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//
//        }
        PageResp<EbookResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    //保存
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if(ObjectUtils.isEmpty(req.getId())){
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);

        }else{

            ebookMapper.updateByPrimaryKey(ebook);
        }

    }

    public void delete(Long id){
        ebookMapper.deleteByPrimaryKey(id);

    }

}
