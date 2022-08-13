package com.zml.wiki.controller;

import com.zml.wiki.req.EbookQueryReq;
import com.zml.wiki.req.EbookSaveReq;
import com.zml.wiki.resp.CommonResp;
import com.zml.wiki.resp.EbookResp;
import com.zml.wiki.resp.PageResp;
import com.zml.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;
    @GetMapping ("/list")
    public CommonResp list(@Valid EbookQueryReq req){
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping  ("/save")
    public CommonResp save(@RequestBody EbookSaveReq req){
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);

        return resp;
    }
    @DeleteMapping  ("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);

        return resp;
    }
}
