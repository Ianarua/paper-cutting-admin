/**
 * Copyright (C), 2000-2024, XXX有限公司
 * FileName: ShopInfoServiceImpl
 * Author: 22932
 * Date: 2024/4/13 19:11:52
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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyaovo.paper.admin.domain.dto.ShopInfoParam;
import com.iyaovo.paper.admin.domain.entity.GoodsInfo;
import com.iyaovo.paper.admin.domain.entity.ShopInfo;
import com.iyaovo.paper.admin.domain.entity.UmsAdminShopRelation;
import com.iyaovo.paper.admin.mapper.GoodsInfoMapper;
import com.iyaovo.paper.admin.mapper.ShopInfoMapper;
import com.iyaovo.paper.admin.mapper.UmsAdminShopRelationMapper;
import com.iyaovo.paper.admin.service.IShopInfoService;
import com.iyaovo.paper.admin.service.UmsAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ShopInfoServiceImpl
 * @Description: java类描述
 * @Author: 22932
 * @Date: 2024/4/13 19:11:52
 */
@Service
@RequiredArgsConstructor
public class ShopInfoServiceImpl extends ServiceImpl<ShopInfoMapper, ShopInfo> implements IShopInfoService {

   private final ShopInfoMapper shopInfoMapper;
   private final GoodsInfoMapper goodsInfoMapper;

   private final UmsAdminService umsAdminService;
   private final UmsAdminShopRelationMapper umsAdminShopRelationMapper;

   @Override
   public List<GoodsInfo> showGoodsByShopId(Integer shopId, Integer pageNum, Integer pageSize) {
//      PageHelper.startPage(pageNum,pageSize);
      QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<GoodsInfo>();
      goodsInfoQueryWrapper.eq("shop_id", shopId);
      return goodsInfoMapper.selectList(goodsInfoQueryWrapper);
   }

   @Override
   public ShopInfo showShopInfoByGoodsId(Integer goodsId) {
      QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<GoodsInfo>();
      GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
      QueryWrapper<ShopInfo> shopInfoQueryWrapper = new QueryWrapper<ShopInfo>();
      shopInfoQueryWrapper.eq("shop_id",goodsInfo.getShopId());
      return shopInfoMapper.selectOne(shopInfoQueryWrapper);
   }

   @Override
   public List<ShopInfo> listAllShop() {
      return shopInfoMapper.selectList(null);
   }

   @Override
   public int createShop(ShopInfoParam shopInfoParam) {
<<<<<<< Updated upstream
      System.out.println(shopInfoParam.getShopName());
      int insert = shopInfoMapper.insert(new ShopInfo(null, shopInfoParam.getShopName(), shopInfoParam.getPicUrl()));
      QueryWrapper<ShopInfo> shopInfoQueryWrapper = new QueryWrapper<ShopInfo>();
      shopInfoQueryWrapper.eq("shop_name",shopInfoParam.getShopName());
      umsAdminShopRelationMapper.insert(new UmsAdminShopRelation(null,umsAdminService.getUmsAdmin().getId(),shopInfoMapper.selectOne(shopInfoQueryWrapper).getShopId()));
      return insert;
=======
      return shopInfoMapper.insert(new ShopInfo(null, shopInfoParam.getShopName(), shopInfoParam.getPicUrl()));
>>>>>>> Stashed changes
   }

   @Override
   public int updateShop(Integer id, ShopInfoParam shopInfoParam) {
      return shopInfoMapper.updateById(new ShopInfo(id,shopInfoParam.getShopName(), shopInfoParam.getPicUrl()));
   }


   @Override
   public int deleteShop(Integer id) {
      return shopInfoMapper.deleteById(id);
   }

   @Override
   public int deleteShop(List<Integer> ids) {
      return shopInfoMapper.deleteBatchIds(ids);
   }

   @Override
   public Page<ShopInfo> listShop(String keyword, Integer pageNum, Integer pageSize) {
      Page<ShopInfo> page = new Page<>(pageNum, pageSize);
      QueryWrapper<ShopInfo> shopInfoQueryWrapper = new QueryWrapper<ShopInfo>();
      if(!StrUtil.hasBlank(keyword)){
         shopInfoQueryWrapper.like("shop_name",keyword);
      }
      return shopInfoMapper.selectPage(page,shopInfoQueryWrapper);
   }

   @Override
   public ShopInfo getOneShop(Integer shopId) {
      return shopInfoMapper.selectById(shopId);
   }

}

