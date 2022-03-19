package com.heartape.hap.business.controller;

import com.heartape.hap.business.entity.ro.LabelRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public Result create(@RequestBody LabelRO label) {
        labelService.create(label);
        return Result.success();
    }
}
