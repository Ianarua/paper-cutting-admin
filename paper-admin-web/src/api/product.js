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

export function updateDeleteStatus (params) {
    return request({
        url: '/goods/update/deleteStatus',
        method: 'post',
        params: params
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
    return request({
        url: '/goods/create',
        method: 'post',
        data: data
    });
}

export function updateProduct (id, data) {
    return request({
        url: '/goods/update/' + id,
        method: 'post',
        data: data
    });
}

export function getProduct (id) {
    return request({
        url: '/goods/updateInfo/' + id,
        method: 'get'
    });
}

