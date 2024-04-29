<template>
    <div>
        <el-card class="login-form-layout">
            <el-form
                :model="admin"
                ref="registerForm"
                size="small"
                :rules="registerRules"
            >
                <div style="text-align: center">
                    <svg-icon icon-class="login-mall" style="width: 36px; height: 36px; color: #409EFF"></svg-icon>
                </div>
                <h2 class="login-title color-main">注册</h2>
                <el-form-item label="帐号：" prop="username">
                    <el-input v-model="admin.username"/>
                </el-form-item>
                <el-form-item label="姓名：">
                    <el-input v-model="admin.nickName"/>
                </el-form-item>
                <el-form-item label="邮箱：">
                    <el-input v-model="admin.email"/>
                </el-form-item>
                <el-form-item label="密码：" prop="password">
                    <el-input v-model="admin.password" type="password"/>
                </el-form-item>
                <el-form-item label="备注：">
                    <el-input
                        v-model="admin.note"
                        type="textarea"
                        :rows="2"
                    />
                </el-form-item>
                <el-form-item label="品牌名称：" prop="shopName">
                    <el-input v-model="admin.shopName"/>
                </el-form-item>
                <!--            <el-form-item label="品牌首字母：">-->
                <!--                <el-input v-model="brand.firstLetter"></el-input>-->
                <!--            </el-form-item>-->
                <el-form-item label="品牌LOGO：" prop="picUrl">
                    <single-upload v-model="admin.picUrl"/>
                </el-form-item>
                <el-form-item style="margin-bottom: 60px;text-align: center">
                    <el-button
                        style="width: 45%"
                        type="primary"
                        :loading="loading"
                        @click.native.prevent="handleRegister"
                    >
                        点击注册
                    </el-button>
                    <el-button style="width: 45%" type="primary" @click.native.prevent="handleToLogin">
                        返回登录
                    </el-button>
                </el-form-item>
            </el-form>
            <!--            <el-form autoComplete="on"-->
            <!--                     :model="loginForm"-->
            <!--                     :rules="loginRules"-->
            <!--                     ref="loginForm"-->
            <!--                     label-position="left">-->
            <!--                <div style="text-align: center">-->
            <!--                    <svg-icon icon-class="login-mall" style="width: 56px;height: 56px;color: #409EFF"/>-->
            <!--                </div>-->
            <!--                <h2 class="login-title color-main">mall-admin-web</h2>-->
            <!--                <el-form-item prop="username">-->
            <!--                    <el-input name="username"-->
            <!--                              type="text"-->
            <!--                              v-model="loginForm.username"-->
            <!--                              autoComplete="on"-->
            <!--                              placeholder="请输入用户名"-->
            <!--                    >-->
            <!--                       <span slot="prefix">-->
            <!--                           <svg-icon icon-class="user" class="color-main"/>-->
            <!--                       </span>-->
            <!--                    </el-input>-->
            <!--                </el-form-item>-->
            <!--                <el-form-item prop="password">-->
            <!--                    <el-input name="password"-->
            <!--                              :type="pwdType"-->
            <!--                              @keyup.enter.native="handleLogin"-->
            <!--                              v-model="loginForm.password"-->
            <!--                              autoComplete="on"-->
            <!--                              placeholder="请输入密码"-->
            <!--                    >-->
            <!--                        <span slot="prefix">-->
            <!--                            <svg-icon icon-class="password" class="color-main"/>-->
            <!--                        </span>-->
            <!--                        <span slot="suffix" @click="showPwd">-->
            <!--                            <svg-icon icon-class="eye" class="color-main"></svg-icon>-->
            <!--                        </span>-->
            <!--                    </el-input>-->
            <!--                </el-form-item>-->

            <!--            </el-form>-->
        </el-card>
        <img :src="login_center_bg" class="login-center-layout"/>
    </div>
</template>

<script>
import { isvalidUsername } from '@/utils/validate';
import login_center_bg from '@/assets/images/login_center_bg.png';
import { registerUser } from '@/api/login';
import { createBrand } from '@/api/brand';
import SingleUpload from '@/components/Upload/singleUpload';
import { setCookie } from '@/utils/support';

const defaultAdmin = {
    // id: null,
    username: null,
    password: null,
    nickName: null,
    email: null,
    note: null,
    shopName: null,
    picUrl: null
};
export default {
    name: 'register',
    components: { SingleUpload },
    data () {
        const validateUsername = (rule, value, callback) => {
            if (!isvalidUsername(value)) {
                callback(new Error('请输入正确的用户名'));
            } else {
                callback();
            }
        };
        const validatePass = (rule, value, callback) => {
            if (value.length < 3) {
                callback(new Error('密码不能小于3位'));
            } else {
                callback();
            }
        };
        return {
            registerForm: {
                username: '',
                password: '',
                // id: 0,
                nickName: '',
                email: '',
                note: ''
            },
            registerRules: {
                username: [{ required: true, trigger: 'blur', message: '请输入账号!' }],
                password: [{ required: true, trigger: 'blur', message: '请输入密码!' }],
                shopName: [
                    { required: true, message: '请输入品牌名称', trigger: 'blur' },
                    { min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur' }
                ]
                // picUrl: [
                //     { required: true, message: '请输入品牌logo', trigger: 'blur' }
                // ],
            },
            login: {
                username: '',
                password: ''
            },
            admin: Object.assign({}, defaultAdmin),
            loading: false,
            pwdType: 'password',
            login_center_bg,
            dialogVisible: false,
            supportDialogVisible: false
        };
    },
    // created () {
    //     this.loginForm.username = getCookie('username');
    //     this.loginForm.password = getCookie('password');
    //     if (this.loginForm.username === undefined || this.loginForm.username == null || this.loginForm.username === '') {
    //         this.loginForm.username = 'admin';
    //     }
    //     if (this.loginForm.password === undefined || this.loginForm.password == null) {
    //         this.loginForm.password = '';
    //     }
    // },
    methods: {
        showPwd () {
            if (this.pwdType === 'password') {
                this.pwdType = '';
            } else {
                this.pwdType = 'password';
            }
        },
        handleToLogin () {
            this.$router.push({ path: '/' });
            // this.$refs.registerForm.validate(valid => {
            //     if (valid) {
            //         // let isSupport = getSupport();
            //         // if(isSupport===undefined||isSupport==null){
            //         //   this.dialogVisible =true;
            //         //   return;
            //         // }
            //         this.loading = true;
            //         this.$store.dispatch('Login', this.registerForm).then(() => {
            //             this.loading = false;
            //             setCookie('username', this.registerForm.username, 15);
            //             setCookie('password', this.registerForm.password, 15);
            //             this.$router.push({ path: '/' });
            //         }).catch(() => {
            //             this.loading = false;
            //         });
            //     } else {
            //         console.log('参数验证不合法！');
            //         return false;
            //     }
            // });
        },
        handleRegister () {
            // this.dialogVisible = true;
            this.$refs.registerForm.validate(async valid => {
                if (valid) {
                    this.loading = true;
                    // registerUser(this.admin).then(res => {
                    //     this.login.username = this.admin.username;
                    //     this.login.password = this.admin.password;
                    //     this.$store.dispatch('Login', this.login).then(() => {
                    //         this.loading = false;
                    //         setCookie('username', this.login.username, 15);
                    //         setCookie('password', this.login.password, 15);
                    //         createBrand({
                    //             shopName: this.admin.shopName,
                    //             picUrl: this.admin.picUrl
                    //         }).then(res => {
                    //             this.$message({
                    //                 message: '注册成功！',
                    //                 type: 'success'
                    //             });
                    //             this.$router.push({ path: '/' });
                    //         });
                    //     }).catch(() => {
                    //         this.loading = false;
                    //     });
                    // });

                    await registerUser(this.admin);
                    this.login.username = this.admin.username;
                    this.login.password = this.admin.password;
                    await this.$store.dispatch('Login', {
                        username: this.login.username,
                        password: this.login.password
                    });
                    this.loading = false;
                    setCookie('username', this.login.username, 15);
                    setCookie('password', this.login.password, 15);
                    await createBrand({ shopName: this.admin.shopName, picUrl: this.admin.picUrl });
                    this.$message({
                        message: '注册成功！',
                        type: 'success'
                    });
                    this.loading = false;
                    this.$router.push({ path: '/' });

                } else {
                    console.log('参数验证不合法！');
                    return false;
                }
            });
        }
    }
};
</script>

<style scoped>
.login-form-layout {
    position: absolute;
    left: 0;
    right: 0;
    width: 560px;
    margin: 100px auto;
    border-top: 10px solid #409EFF;
}

.login-title {
    text-align: center;
}

.login-center-layout {
    background: #409EFF;
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
    margin-top: 200px;
}
</style>
