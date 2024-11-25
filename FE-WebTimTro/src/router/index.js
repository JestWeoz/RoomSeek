import HomePages from '@/pages/homePage.vue';
import TorenPage from '@/pages/torenPage.vue';
import { createRouter, createWebHistory } from 'vue-router'
import LoginPage from '@/pages/loginPage.vue';
import SignupPage from '@/pages/SignupPage.vue';
import PostMotelPage from '@/pages/postMotelPage.vue';
import inforAccount from '@/pages/accountPage/inforAccount.vue';
import MotelDetailPage from '@/pages/motelDetailPage.vue';
import postMotelPage from '@/pages/accountPage/postMotelPage.vue';

import ManageMotel from '@/pages/accountPage/manageMotel.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", name: "HomePage", component: HomePages },
    { path: "/search", name: "TorenPage", component: TorenPage },
    { path: "/login", name: "LoginPage", component: LoginPage },
    { path: "/register", name: "SignupPage", component: SignupPage },
    { path: "/postMotel", name: "PostMotelPage", component: PostMotelPage },
    { path: "/account/thong-tin-tai-khoan", name: "UserProfile", component: inforAccount },
    { path: "/account/dang-tin", name: "PostPage", component: postMotelPage },
    { path: "/account/quan-ly-tro", name: "ManageMotel", component: ManageMotel },
    {
      path: '/motel/:slug-:id',
      name: 'motelDetail',
      component: MotelDetailPage
    },

  ],
});

export default router
