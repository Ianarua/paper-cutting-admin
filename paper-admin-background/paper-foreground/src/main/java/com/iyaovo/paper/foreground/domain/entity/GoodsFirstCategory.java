/**
 * Copyright (C), 2000-2024, XXX有限公司
 * FileName: GoodsFirstCategory
 * Author: 22932
 * Date: 2024/4/12 17:39:58
 * Description:
 * <p>
 * History:
 * <author> 作者姓名
 * <time> 修改时间
 * <version> 版本号
 * <desc> 版本描述
 */
package com.iyaovo.paper.foreground.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: GoodsFirstCategory
 * @Description: java类描述
 * @Author: 22932
 * @Date: 2024/4/12 17:39:58
 */
@Schema(description = "商品第一类别")
@Slf4j
@Data
@AllArgsConstructor
@TableName("goods_first_category")
public class GoodsFirstCategory {

   /**
    * 商品第一类别id
    */
   @TableId(value = "goods_first_category_id",type = IdType.AUTO)
   private Integer goodsFirstCategoryId;

   /**
    * 商品第一类别名称
    */
   @TableField("goods_first_category_name")
   private String goodsFirstCategoryName;
}

