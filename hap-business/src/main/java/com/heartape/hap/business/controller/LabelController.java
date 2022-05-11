package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.LabelBO;
import com.heartape.hap.business.entity.bo.SimpleLabelBO;
import com.heartape.hap.business.entity.ro.LabelChildRO;
import com.heartape.hap.business.entity.ro.LabelRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.ILabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "标签")
public class LabelController {

    @Autowired
    private ILabelService labelService;

    @GetMapping("/list")
    @ApiOperation("标签搜索")
    public Result list(@RequestParam(required = false) String name, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<SimpleLabelBO> labelBO = labelService.list(name, pageNum, pageSize);
        return Result.success(labelBO);
    }

    @GetMapping
    @ApiOperation("标签详情")
    public Result detail(@RequestParam Long labelId) {
        LabelBO labelBO = labelService.detail(labelId);
        return Result.success(labelBO);
    }

    /**
     * todo:拆分到后台管理模块
     */
    @PostMapping
    @ApiOperation("创建标签")
    public Result create(@RequestBody LabelRO label) {
        labelService.create(label);
        return Result.success();
    }

    /**
     * todo:拆分到后台管理模块
     */
    @PostMapping("/child")
    @ApiOperation("创建子标签")
    public Result createChild(@RequestBody LabelChildRO labelChildRO) {
        labelService.createChild(labelChildRO);
        return Result.success();
    }
}
