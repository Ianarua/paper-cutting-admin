import request from '@/utils/request';

export function fetchList (parentId, params) {
    return request({
        url: '/goodsCategory/list/' + parentId,
        method: 'get',
        params: params
    });
}

export function deleteProductCate (id) {
    return request({
        url: '/goodsCategory/delete/' + id,
        method: 'post'
    });
}

export function createProductCate (data) {
    const newData = {
        parentId: data.categorySuperiorId,
        name: data.goodCategoryName,
        picUlr: data.picUlr
    };
    return request({
        url: '/goodsCategory/create',
        method: 'post',
        data: newData
    });
}

export function updateProductCate (id, data) {
    const newData = {
        parentId: data.categorySuperiorId,
        name: data.goodCategoryName,
        picUlr: data.picUlr
    };
    return request({
        url: '/goodsCategory/update/' + id,
        method: 'post',
        data: newData
    });
}

export function getProductCate (id) {
    return request({
        url: '/goodsCategory/' + id,
        method: 'get'
    });
}

export function updateShowStatus (data) {
    return request({
        url: '/goodsCategory/update/showStatus',
        method: 'post',
        data: data
    });
}

export function updateNavStatus (data) {
    return request({
        url: '/goodsCategory/update/navStatus',
        method: 'post',
        data: data
    });
}

export function fetchListWithChildren () {
    return request({
        url: '/goodsCategory/list/withChildren',
        method: 'get'
    });
}
