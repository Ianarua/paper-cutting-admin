/**
 * Copyright (C), 2000-2024, XXX有限公司
 * FileName: GoodsInfoServiceImpl
 * Author: 22932
 * Date: 2024/4/13 19:30:56
 * Description:
 * <p>
 * History:
 * <author> 作者姓名
 * <time> 修改时间
 * <version> 版本号
 * <desc> 版本描述
 */
package com.iyaovo.paper.admin.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.iyaovo.paper.admin.domain.dto.GoodsInfoParam;
import com.iyaovo.paper.admin.domain.dto.GoodsInfoQueryParam;
import com.iyaovo.paper.admin.domain.entity.GoodsCategory;
import com.iyaovo.paper.admin.domain.entity.GoodsInfo;
import com.iyaovo.paper.admin.domain.vo.GoodsInfoVo;
import com.iyaovo.paper.admin.mapper.GoodsCategoryMapper;
import com.iyaovo.paper.admin.mapper.GoodsInfoMapper;
import com.iyaovo.paper.admin.mapper.ShopInfoMapper;
import com.iyaovo.paper.admin.service.IGoodsInfoService;
import com.iyaovo.paper.common.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: GoodsInfoServiceImpl
 * @Description: java类描述
 * @Author: 22932
 * @Date: 2024/4/13 19:30:56
 */
@Service
@RequiredArgsConstructor
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements IGoodsInfoService {

   private final GoodsInfoMapper goodsInfoMapper;

   private final ShopInfoMapper shopInfoMapper;

   private final GoodsCategoryMapper goodsCategoryMapper;

   @Override
   public GoodsInfoVo getOneGoods(Integer goodsId) {
      List<GoodsInfo> goods = new ArrayList<>();
      GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
      goods.add(goodsInfo);
      return goodsInfoToGoodsInfoVo(goods).get(0);
   }

   @Override
   public int create(GoodsInfoParam goodsInfoParam) {
      return goodsInfoMapper.insert(new GoodsInfo(null,goodsInfoParam.getGoodsCategoryId(),
              goodsInfoParam.getShopId(),goodsInfoParam.getName(),goodsInfoParam.getIntroduction(),
              goodsInfoParam.getLogo(),goodsInfoParam.getPrice(),goodsInfoParam.getPromotionPrice(),
              0,goodsInfoParam.getTotalNumber()));
   }

   @Override
   public int update(Integer id ,GoodsInfoParam goodsInfoParam) {
      return goodsInfoMapper.updateById(new GoodsInfo(id,goodsInfoParam.getGoodsCategoryId(),
              goodsInfoParam.getShopId(),goodsInfoParam.getName(),goodsInfoParam.getIntroduction(),
              goodsInfoParam.getLogo(),goodsInfoParam.getPrice(),goodsInfoParam.getPromotionPrice(),
              0,goodsInfoParam.getTotalNumber()));
   }

   @Override
   public void deleteGoods(Integer goodsId) {
      goodsInfoMapper.deleteById(goodsId);
   }

   @Override
   public List<GoodsInfoVo> list(String keyword, Integer goodsCategoryId, Integer shopId, Integer pageSize, Integer pageNum) {
      PageHelper.startPage(pageNum,pageSize);
      QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<GoodsInfo>();
      if(!StrUtil.hasBlank(keyword)){
         goodsInfoQueryWrapper.like("goods_name",keyword);
      }
      if(goodsCategoryId != null){
         goodsInfoQueryWrapper.eq("goods_category_id",goodsCategoryId);
      }
      if(shopId != null){
         goodsInfoQueryWrapper.eq("shop_id",shopId);
      }
      return goodsInfoToGoodsInfoVo(goodsInfoMapper.selectList(goodsInfoQueryWrapper));
   }


   @Override
   public List<GoodsInfoVo> list(String keyWord) {
      System.out.println("service");
      QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<GoodsInfo>();
      if(!StrUtil.hasBlank(keyWord)){
         goodsInfoQueryWrapper.like("goods_name",keyWord);
      }
      return goodsInfoToGoodsInfoVo(goodsInfoMapper.selectList(goodsInfoQueryWrapper));
   }




   private List<GoodsInfoVo> goodsInfoToGoodsInfoVo(List<GoodsInfo> goodsInfoList){
      List<GoodsInfoVo> goodsInfoVoList = new ArrayList<GoodsInfoVo>();
      goodsInfoList.forEach(goodsInfo ->{
         //entity转为vo
         GoodsInfoVo goodsInfoVo = new GoodsInfoVo(goodsInfo.getGoodsId(),goodsInfo.getGoodsName(),goodsInfo.getGoodsIntroduction(), goodsInfo.getPicUrl(), goodsInfo.getPrice(),
                 goodsInfo.getPromotionPrice(),goodsInfo.getSoldNumber(),goodsInfo.getTotalNumber(),null,null);
         //把店铺信息封装到vo
         goodsInfoVo.setShopInfo(shopInfoMapper.selectById(goodsInfo.getShopId()));
         QueryWrapper<GoodsCategory> categorySecondQueryWrapper = new QueryWrapper<GoodsCategory>();
         categorySecondQueryWrapper.eq("goods_category_id",goodsInfo.getGoodsCategoryId());
         GoodsCategory goodsSecondCategory = goodsCategoryMapper.selectOne(categorySecondQueryWrapper);
         QueryWrapper<GoodsCategory> categoryFirstQueryWrapper = new QueryWrapper<GoodsCategory>();
         categoryFirstQueryWrapper.eq("goods_category_id",goodsSecondCategory.getCategorySuperiorId());
         GoodsCategory goodsFirstCategory = goodsCategoryMapper.selectOne(categoryFirstQueryWrapper);
         //类别信息封装到vo
         Map<Integer,String> map = new HashMap<Integer,String>();
         map.put(Constants.FIRST_CATEGORY,goodsFirstCategory.getGoodCategoryName());
         map.put(Constants.SECOND_CATEGORY,goodsSecondCategory.getGoodCategoryName());
         goodsInfoVo.setCategoryMap(map);
         goodsInfoVoList.add(goodsInfoVo);
      });
      return goodsInfoVoList;
   }

}

