package com.iyaovo.paper.admin.controller;

import com.iyaovo.paper.admin.domain.dto.GoodsInfoParam;
import com.iyaovo.paper.admin.domain.dto.GoodsInfoQueryParam;
import com.iyaovo.paper.admin.domain.vo.GoodsInfoVo;
import com.iyaovo.paper.admin.service.IGoodsInfoService;
import com.iyaovo.paper.common.api.CommonPage;
import com.iyaovo.paper.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品管理Controller
 * Created by macro on 2018/4/26.
 */
@Controller
@Tag(name =  "商品管理")
@RequestMapping("/goods")
public class GoodsController {
//    @Autowired
//    private PmsProductService productService;

    @Autowired
    private IGoodsInfoService iGoodsInfoService;

    @Operation(summary = "创建商品")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody GoodsInfoParam goodsInfoParam) {
        int count = iGoodsInfoService.create(goodsInfoParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }


    @Operation(summary = "更新商品")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Integer id, @RequestBody GoodsInfoParam goodsInfoParam) {
        int count = iGoodsInfoService.update(id, goodsInfoParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @Operation(summary = "查询商品")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<GoodsInfoVo>> getList( @RequestParam(value = "keyword",required = false)String keyword,
                                                          @RequestParam(value = "goodsCategoryId",required = false) Integer goodsCategoryId,
                                                          @RequestParam(value = "shopId",required = false) Integer shopId,
                                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        System.out.println(keyword);
        System.out.println(goodsCategoryId);
        List<GoodsInfoVo> goodsInfoVoList = iGoodsInfoService.list(keyword,goodsCategoryId,shopId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(goodsInfoVoList));
    }


    @Operation(summary = "根据商品名称模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<GoodsInfoVo>> getList(String keyword) {
        System.out.println("controller");
        List<GoodsInfoVo> goodsInfoVoList = iGoodsInfoService.list(keyword);
        return CommonResult.success(goodsInfoVoList);
    }




}
