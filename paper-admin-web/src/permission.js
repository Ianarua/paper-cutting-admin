import router from './router';
import store from './store';
import NProgress from 'nprogress'; // Progress 进度条
import 'nprogress/nprogress.css';// Progress 进度条样式
import { Message } from 'element-ui';
import { getToken } from '@/utils/auth'; // 验权

const whiteList = ['/login']; // 不重定向白名单
router.beforeEach((to, from, next) => {
    NProgress.start();
    const token = getToken();
    if (token) {
        if (to.path === '/login') {
            next({ path: '/' });
            NProgress.done();
        } else {
            if (store.getters.roles.length === 0) {
                store.dispatch('GetInfo').then(res => {
                    let menus = res.data.menus;
                    let username = res.data.username;
                    store.dispatch('GenerateRoutes', { menus, username }).then(() => {
                        router.addRoutes(store.getters.addRouters);
                        next({ ...to, replace: true });
                    });
                }).catch((err) => {
                    store.dispatch('FedLogOut').then(() => {
                        Message.error(err || 'Verification failed, please login again');
                        next({ path: '/' });
                    });
                });
            } else {
                next();
            }
        }
    } else {
        // 如果访问的是注册页面，则直接放行，不重定向到登录页面
        if (to.path === '/register') {
            next();
            NProgress.done();
        } else {
            if (whiteList.indexOf(to.path) !== -1) {
                next();
            } else {
                next('/login');
                NProgress.done();
            }
        }
    }
});


router.afterEach(() => {
    NProgress.done(); // 结束Progress
});
