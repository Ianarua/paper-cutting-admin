import request from '@/utils/request';

export function fetchList (cid, params) {
    return request({
        url: '/goodsCategory/list/' + cid,
        method: 'get',
        params: params
    });
}

export function deleteProductAttr (data) {
    return request({
        url: '/goodsCategory/delete',
        method: 'post',
        data: data
    });
}

export function createProductAttr (data) {
    return request({
        url: '/goodsCategory/create',
        method: 'post',
        data: data
    });
}

export function updateProductAttr (id, data) {
    return request({
        url: '/goodsCategory/update/' + id,
        method: 'post',
        data: data
    });
}

export function getProductAttr (id) {
    return request({
        url: '/goodsCategory/' + id,
        method: 'get'
    });
}

export function getProductAttrInfo (productCategoryId) {
    return request({
        url: '/goodsCategory/attrInfo/' + productCategoryId,
        method: 'get'
    });
}
