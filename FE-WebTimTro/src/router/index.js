import HomePages from '@/pages/homePage.vue';
import TorenPage from '@/pages/torenPage.vue';
import { createRouter, createWebHistory } from 'vue-router'
import LoginPage from '@/pages/loginPage.vue';
import SignupPage from '@/pages/RegisterPage.vue';
import inforAccount from '@/pages/accountPage/inforAccount.vue';
import MotelDetailPage from '@/pages/motelDetailPage.vue';
import postMotelPage from '@/pages/accountPage/postMotelPage.vue';
import editMotel from '@/pages/accountPage/editMotel.vue';
import ManageMotel from '@/pages/accountPage/manageMotel.vue';
import ChangePassword from '@/pages/accountPage/changePassword.vue';
import ApprovePostMotel from '@/pages/adminPage/approvePostMotel.vue';
import UnApprovePostMotel from '@/pages/adminPage/unApprovePostMotel.vue';
import ForgotPassword from '@/pages/forgotPassword.vue';
import ResetPasswordPage from '@/pages/resetPasswordPage.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", name: "HomePage", component: HomePages },
    { path: "/search", name: "TorenPage", component: TorenPage },
    { path: "/login", name: "LoginPage", component: LoginPage },
    { path: "/register", name: "SignupPage", component: SignupPage },
    { path: "/account/thong-tin-tai-khoan", name: "UserProfile", component: inforAccount },
    { path: "/account/dang-tin", name: "PostPage", component: postMotelPage },
    { path: "/account/quan-ly-tro", name: "ManageMotel", component: ManageMotel },
    { path: "/account/doi-mat-khau", name: "ChangePassword", component: ChangePassword },
    { path: "/account/edit-motel/:id", name: "EditPage", component: editMotel },
    { path: "/admin/duyet-bai-dang", name: "ApprovePage", component: UnApprovePostMotel },
    { path: "/admin/bai-dang-da-duyet", name: "UnApprovePage", component: ApprovePostMotel },
    { path: "/forgot-password", name: "ForgotPassword", component: ForgotPassword },
    {
      path: '/motel/:slug-:id',
      name: 'motelDetail',
      component: MotelDetailPage
    },
    {
      path: "/reset-password",
      name: "ResetPassword",
      component: ResetPasswordPage
    },

  ],
});

export default router
