package com.heartape.hap.business.controller;

import com.heartape.hap.business.entity.bo.LinkGuideBO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.ILinkGuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/business/link-guide")
@Api(tags = "导航链接")
public class LinkGuideController {

    @Autowired
    private ILinkGuideService linkGuideService;

    @GetMapping
    @ApiOperation("导航链接列表")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        List<LinkGuideBO> linkGuides = linkGuideService.showList(pageNum, pageSize);
        return Result.success(linkGuides);
    }

    /**
     * todo:后续抽离到后台模块
     */
    @PostMapping
    @ApiOperation("创建导航链接")
    public Result create() {
        linkGuideService.create();
        return Result.success();
    }
}
