/**
 * Copyright (C), 2000-2024, XXX有限公司
 * FileName: GoodsCategoryServiceImpl
 * Author: 22932
 * Date: 2024/4/19 12:11:05
 * Description:
 * <p>
 * History:
 * <author> 作者姓名
 * <time> 修改时间
 * <version> 版本号
 * <desc> 版本描述
 */
package com.iyaovo.paper.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.iyaovo.paper.admin.domain.dto.GoodsCategoryParam;
import com.iyaovo.paper.admin.domain.dto.GoodsCategoryWithChildrenItem;
import com.iyaovo.paper.admin.domain.entity.GoodsCategory;
import com.iyaovo.paper.admin.mapper.GoodsCategoryMapper;
import com.iyaovo.paper.admin.mapper.GoodsInfoMapper;
import com.iyaovo.paper.admin.service.IGoodsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GoodsCategoryServiceImpl
 * @Description: java类描述
 * @Author: 22932
 * @Date: 2024/4/19 12:11:05
 */
@Service
@RequiredArgsConstructor
public class GoodsCategoryServiceImpl implements IGoodsCategoryService {

   private final GoodsCategoryMapper goodsCategoryMapper;

   private final GoodsInfoMapper goodsInfoMapper;

   @Override
   public int create(GoodsCategoryParam goodsCategoryParam) {
      return goodsCategoryMapper.insert(new GoodsCategory(null,goodsCategoryParam.getName(),goodsCategoryParam.getPicUrl(),goodsCategoryParam.getParentId()));
   }

   @Override
   public int delete(Integer goodsCategoryId) {
      return goodsCategoryMapper.deleteById(goodsCategoryId);
   }

   @Override
   public int update(Integer id, GoodsCategoryParam goodsCategoryParam) {
      return goodsCategoryMapper.updateById(new GoodsCategory(id, goodsCategoryParam.getName(),goodsCategoryParam.getPicUrl(),goodsCategoryParam.getParentId()));
   }

   @Override
   public List<GoodsCategory> getList(Long parentId, Integer pageSize, Integer pageNum) {
      PageHelper.startPage(pageNum,pageSize);
      QueryWrapper<GoodsCategory> goodsCategoryQueryWrapper = new QueryWrapper<GoodsCategory>();
      goodsCategoryQueryWrapper.eq("category_superior_id",parentId);
      List<GoodsCategory> goodsCategories = goodsCategoryMapper.selectList(goodsCategoryQueryWrapper);
      goodsCategories.forEach(goodsCategory -> {
         goodsCategory.setGoodsNumber(getGoodsNumber(goodsCategory.getGoodsCategoryId(),goodsCategory.getCategorySuperiorId()));
      });
      return goodsCategories;
   }

   private Long getGoodsNumber(Integer categoryId,Integer parentId){
      final long[] number = {0L};
      if(parentId == 0){
         QueryWrapper queryCategoryWrapper = new QueryWrapper<>();
         queryCategoryWrapper.eq("category_superior_id",categoryId);
         List<GoodsCategory> goodsCategories = goodsCategoryMapper.selectList(queryCategoryWrapper);
         goodsCategories.forEach(goodsCategory -> {
            QueryWrapper queryGoodsNumberWrapper = new QueryWrapper<>();
            queryGoodsNumberWrapper.eq("goods_category_id",goodsCategory.getGoodsCategoryId());
            number[0] += goodsInfoMapper.selectCount(queryGoodsNumberWrapper);
         });
         return number[0];
      }else{
         QueryWrapper queryGoodsNumberWrapper = new QueryWrapper<>();
         queryGoodsNumberWrapper.eq("goods_category_id",categoryId);
         return goodsInfoMapper.selectCount(queryGoodsNumberWrapper);
      }
   }

   @Override
   public List<GoodsCategoryWithChildrenItem> listWithChildren() {
      QueryWrapper<GoodsCategory> goodsCategoryQueryWrapper = new QueryWrapper<GoodsCategory>();
      goodsCategoryQueryWrapper.eq("category_superior_id",0);
      List<GoodsCategory> goodsCategories = goodsCategoryMapper.selectList(goodsCategoryQueryWrapper);
      List<GoodsCategoryWithChildrenItem> goodsCategoryWithChildrenItems = new ArrayList<>();
      goodsCategories.forEach(goodsCategory -> {
         QueryWrapper<GoodsCategory> goodsCategoryChildrenQueryWrapper = new QueryWrapper<GoodsCategory>();
         goodsCategoryChildrenQueryWrapper.eq("category_superior_id",goodsCategory.getGoodsCategoryId());
         GoodsCategoryWithChildrenItem goodsCategoryWithChildrenItem = new GoodsCategoryWithChildrenItem(goodsCategory.getGoodsCategoryId(),
                 goodsCategory.getGoodCategoryName(),goodsCategory.getCategorySuperiorId(),goodsCategoryMapper.selectList(goodsCategoryChildrenQueryWrapper));
         goodsCategoryWithChildrenItems.add(goodsCategoryWithChildrenItem);
      });
      return goodsCategoryWithChildrenItems;
   }

   @Override
   public GoodsCategory getOneGoodsCategory(Integer goodsCategoryId) {
      return goodsCategoryMapper.selectById(goodsCategoryId);
   }
}

