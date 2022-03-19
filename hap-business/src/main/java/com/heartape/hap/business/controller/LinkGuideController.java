package com.heartape.hap.business.controller;

import com.heartape.hap.business.entity.LinkGuide;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.ILinkGuideService;
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
public class LinkGuideController {

    @Autowired
    private ILinkGuideService linkGuideService;

    @GetMapping
    public Result list(@RequestParam Integer page, @RequestParam Integer size) {
        List<LinkGuide> linkGuides = linkGuideService.showList(page, size);
        return Result.success(linkGuides);
    }
}
