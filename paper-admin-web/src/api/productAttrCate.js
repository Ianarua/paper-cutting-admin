import request from '@/utils/request';

export function fetchList (params) {
    return request({
        url: '/goodsCategory/category/list',
        method: 'get',
        params: params
    });
}

export function createProductAttrCate (data) {
    return request({
        url: '/goodsCategory/category/create',
        method: 'post',
        data: data
    });
}

export function deleteProductAttrCate (id) {
    return request({
        url: '/goodsCategory/category/delete/' + id,
        method: 'get'
    });
}

export function updateProductAttrCate (id, data) {
    return request({
        url: '/goodsCategory/category/update/' + id,
        method: 'post',
        data: data
    });
}

export function fetchListWithAttr () {
    return request({
        url: '/goodsCategory/category/list/withAttr',
        method: 'get'
    });
}
