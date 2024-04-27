<template>
    <div style="margin-top: 50px">
        <el-form :model="value" :rules="rules" ref="productInfoForm" label-width="120px" class="form-inner-container"
                 size="small">
            <el-form-item label="商品分类：" prop="goodsCategoryId">
                <el-cascader
                    v-model="goodsCategoryId"
                    :options="productCateOptions"
                />
            </el-form-item>
            <el-form-item label="商品名称：" prop="name">
                <el-input v-model="value.name"/>
            </el-form-item>
            <!-- <el-form-item label="副标题：" prop="subTitle">-->
            <!--            <el-input v-model="value.subTitle"></el-input>-->
            <!-- </el-form-item>-->
            <el-form-item label="商品品牌：" prop="shopId">
                <el-select
                    v-model="value.shopId"
                    @change="handleBrandChange"
                    placeholder="请选择品牌"
                >
                    <el-option
                        v-for="item in brandOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="商品介绍：" prop="introduction">
                <el-input
                    :autoSize="true"
                    v-model="value.introduction"
                    type="textarea"
                    placeholder="请输入内容"
                />
            </el-form-item>
            <el-form-item label="商品相册：" prop="logo">
                <multi-upload v-model="value.logo"/>
            </el-form-item>
            <!--            <el-form-item label="商品货号：">-->
            <!--                <el-input v-model="value.productSn"></el-input>-->
            <!--            </el-form-item>-->
            <el-form-item label="商品售价：" prop="promotionPrice">
                <el-input v-model.number="value.promotionPrice"/>
            </el-form-item>
            <el-form-item label="市场价：" prop="price">
                <el-input v-model.number="value.price"/>
            </el-form-item>
            <el-form-item label="商品库存：" prop="totalNumber">
                <el-input v-model.number="value.totalNumber"/>
            </el-form-item>
            <!--            <el-form-item label="计量单位：">-->
            <!--                <el-input v-model="value.unit"></el-input>-->
            <!--            </el-form-item>-->
            <!--            <el-form-item label="商品重量：">-->
            <!--                <el-input v-model="value.weight" style="width: 300px"></el-input>-->
            <!--                <span style="margin-left: 20px">克</span>-->
            <!--            </el-form-item>-->
            <!--            <el-form-item label="排序">-->
            <!--                <el-input v-model="value.sort"/>-->
            <!--            </el-form-item>-->
            <el-form-item style="text-align: center">
                <el-button type="primary" size="medium" @click="handleNext('productInfoForm')">
                    提交
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import { fetchListWithChildren } from '@/api/productCate';
import { fetchList as fetchBrandList } from '@/api/brand';
import { getProduct } from '@/api/product';
import MultiUpload from '../../../../components/Upload/multiUpload.vue';

export default {
    name: 'ProductInfoDetail',
    components: {
        MultiUpload
    },
    props: {
        value: Object,
        isEdit: {
            type: Boolean,
            default: false
        }
    },
    data () {
        return {
            hasEditCreated: false,
            //选中商品分类的值
            goodsCategoryId: [],
            productCateOptions: [],
            brandOptions: [],
            rules: {
                name: [
                    { required: true, message: '请输入商品名称', trigger: 'blur' },
                    { min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur' }
                ],
                // subTitle: [{ required: true, message: '请输入商品副标题', trigger: 'blur' }],
                goodsCategoryId: [{ required: true, message: '请选择商品分类', trigger: 'blur' }],
                shopId: [{ required: true, message: '请选择商品品牌', trigger: 'blur' }],
                promotionPrice: [
                    { required: true, message: '请输入商品售价', trigger: 'blur' },
                    { type: 'number', message: '并不是一个数字' }
                ],
                price: [
                    { required: true, message: '请输入市场价', trigger: 'blur' },
                    { type: 'number', message: '并不是一个数字' }
                ],
                totalNumber: [
                    { required: true, message: '请输入商品库存', trigger: 'blur' },
                    { type: 'number', message: '并不是一个数字' }
                ]


                // requiredProp: [{ required: true, message: '该项为必填项', trigger: 'blur' }]
            }
        };
    },
    created () {
        this.getProductCateList();
        this.getBrandList();
    },
    mounted () {
        console.log('value', this.value);
    },
    computed: {
        //商品的编号
        goodsId () {
            return this.value.goodsId;
        },
        //商品的主图和画册图片
        logo: {
            get: function () {
                let logo = [];
                if (this.value.logo === undefined || this.value.logo == null || this.value.logo === '') {
                    return logo;
                }
                logo.push(this.value.logo);
                if (this.value.albumPics === undefined || this.value.albumPics == null || this.value.albumPics === '') {
                    return logo;
                }
                let albumPics = this.value.albumPics.split(',');
                for (let i = 0; i < albumPics.length; i++) {
                    logo.push(albumPics[i]);
                }
                return logo;
            },
            set: function (newValue) {
                if (newValue == null || newValue.length === 0) {
                    this.value.logo = null;
                    this.value.albumPics = null;
                } else {
                    this.value.logo = newValue[0];
                    this.value.albumPics = '';
                    if (newValue.length > 1) {
                        for (let i = 1; i < newValue.length; i++) {
                            this.value.albumPics += newValue[i];
                            if (i !== newValue.length - 1) {
                                this.value.albumPics += ',';
                            }
                        }
                    }
                }
            }
        }
    },
    watch: {
        goodsId: function (newValue) {
            if (!this.isEdit) return;
            if (this.hasEditCreated) return;
            if (newValue === undefined || newValue == null || newValue === 0) return;
            this.handleEditCreated();
        },
        goodsCategoryId: function (newValue) {
            if (newValue != null && newValue.length === 2) {
                this.value.goodsCategoryId = newValue[1];
                this.value.goodsCategoryName = this.getCateNameById(this.value.goodsCategoryId);
            } else {
                this.value.goodsCategoryId = null;
                this.value.goodsCategoryName = null;
            }
        }
    },
    methods: {
        // 处理编辑逻辑
        handleEditCreated () {
            console.log(123);
            if (this.value.goodsCategoryId !== null) {
                this.goodsCategoryId.push(this.value.superiorCategoryId);
                this.goodsCategoryId.push(this.value.goodsCategoryId);
            }
            this.hasEditCreated = true;
        },

        getProductCateList () {
            fetchListWithChildren().then(response => {
                let list = response.data;
                this.productCateOptions = [];
                for (let i = 0; i < list.length; i++) {
                    let children = [];
                    if (list[i].children != null && list[i].children.length > 0) {
                        for (let j = 0; j < list[i].children.length; j++) {
                            children.push(
                                {
                                    label: list[i].children[j].goodCategoryName,
                                    value: list[i].children[j].goodsCategoryId
                                }
                            );
                        }
                    }
                    this.productCateOptions.push(
                        {
                            label: list[i].goodCategoryName,
                            value: list[i].goodsCategoryId,
                            children: children
                        }
                    );
                }
            });
        },
        getBrandList () {
            fetchBrandList({ pageNum: 1, pageSize: 100 }).then(response => {
                this.brandOptions = [];
                let brandList = response.data.list;
                for (let i = 0; i < brandList.length; i++) {
                    this.brandOptions.push(
                        {
                            label: brandList[i].shopName,
                            value: brandList[i].shopId
                        }
                    );
                }
            });
        },
        getCateNameById (id) {
            let name = null;
            for (let i = 0; i < this.productCateOptions.length; i++) {
                for (let j = 0; j < this.productCateOptions[i].children.length; j++) {
                    if (this.productCateOptions[i].children[j].value === id) {
                        name = this.productCateOptions[i].children[j].label;
                        return name;
                    }
                }
            }
            return name;
        },
        handleNext (formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$emit('finishCommit', this.isEdit);
                } else {
                    this.$message({
                        message: '验证失败',
                        type: 'error',
                        duration: 1000
                    });
                    return false;
                }
            });
        },
        handleBrandChange (val) {
            let brandName = '';
            for (let i = 0; i < this.brandOptions.length; i++) {
                if (this.brandOptions[i].value === val) {
                    brandName = this.brandOptions[i].label;
                    break;
                }
            }
            this.value.brandName = brandName;
        }

    }
};
</script>

<style scoped>
</style>
