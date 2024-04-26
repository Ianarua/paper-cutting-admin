import request from '@/utils/request';

export function fetchList (params) {
    return request({
        url: '/goods/list',
        method: 'get',
        params: params
    });
}

export function fetchSimpleList (params) {
    return request({
        url: '/goods/simpleList',
        method: 'get',
        params: params
    });
}

export function updateDelete (data) {
    console.log('data', data);
    return request({
        url: '/goods/delete',
        method: 'post',
        data: data
    });
}

export function updateNewStatus (params) {
    return request({
        url: '/goods/update/newStatus',
        method: 'post',
        params: params
    });
}

export function updateRecommendStatus (params) {
    return request({
        url: '/goods/update/recommendStatus',
        method: 'post',
        params: params
    });
}

export function updatePublishStatus (params) {
    return request({
        url: '/goods/update/publishStatus',
        method: 'post',
        params: params
    });
}

export function createProduct (data) {
    console.log('data------', data);
    const goodsInfoParam = {
        name: data.name,
        logo: data.logo,
        introduction: data.introduction,
        goodsCategoryId: data.goodsCategoryId,
        shopId: data.shopId,
        price: data.price,
        promotionPrice: data.promotionPrice,
        totalNumber: data.totalNumber
    };
    return request({
        url: '/goods/create',
        method: 'post',
        data: goodsInfoParam
    });
}

export function updateProduct (id, data) {
    console.log('data------', data);
    const goodsInfoParam = {
        name: data.name,
        logo: data.logo,
        introduction: data.introduction,
        goodsCategoryId: data.goodsCategoryId,
        shopId: data.shopId,
        price: data.price,
        promotionPrice: data.promotionPrice,
        totalNumber: data.totalNumber
    };
    return request({
        url: '/goods/update/' + id,
        method: 'post',
        data: goodsInfoParam
    });
}

export function getProduct (id) {
    return request({
        url: '/goods/' + id,
        method: 'get'
    });
}

