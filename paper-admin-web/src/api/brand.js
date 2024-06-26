import request from '@/utils/request';

export function fetchList (params) {
    return request({
        url: '/shop/list',
        method: 'get',
        params: params
    });
}

export function createBrand (data) {
    return request({
        url: '/shop/create',
        method: 'post',
        data: data
    });
}

export function updateShowStatus (data) {
    return request({
        url: '/shop/update/showStatus',
        method: 'post',
        data: data
    });
}

export function updateFactoryStatus (data) {
    return request({
        url: '/shop/update/factoryStatus',
        method: 'post',
        data: data
    });
}

export function deleteBrand (id) {
    return request({
        url: '/shop/delete/' + id,
        method: 'get'
    });
}

export function getBrand (id) {
    return request({
        url: '/shop/' + id,
        method: 'get'
    });
}

export function updateBrand (id, data) {
    return request({
        url: '/shop/update/' + id,
        method: 'post',
        data: data
    });
}

