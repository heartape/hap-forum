package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.LabelBO;
import com.heartape.hap.business.entity.bo.SimpleLabelBO;
import com.heartape.hap.business.entity.ro.LabelRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/business/label")
public class LabelController {

    @Autowired
    private ILabelService labelService;

    @GetMapping("/list")
    public Result list(@RequestParam(required = false) String name, @RequestParam Integer page, @RequestParam Integer size) {
        PageInfo<SimpleLabelBO> labelBO = labelService.list(name, page, size);
        return Result.success(labelBO);
    }

    @GetMapping
    public Result detail(@RequestParam Long labelId) {
        LabelBO labelBO = labelService.detail(labelId);
        return Result.success(labelBO);
    }

    /**
     * 拆分到后台管理模块
     */
    @PostMapping
    public Result create(@RequestBody LabelRO label) {
        labelService.create(label);
        return Result.success();
    }
}
