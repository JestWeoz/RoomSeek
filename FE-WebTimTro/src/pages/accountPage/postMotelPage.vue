<template>
  <div class="hpc-navbar">
    <div class="container">
      <div class="row">
        <navbarInforAccount />
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-1"></div>
    <div class="col-md-2">
      <!-- Sidebar -->
      <div class="widget" style="background-color: #ecececc4">
        <ul class="list-group">
          <li
            class="list-group-item"
            @click="toInforAccount"
            style="cursor: pointer"
          >
            Thông tin Tài Khoản
          </li>
          <li
            class="list-group-item"
            style="cursor: pointer"
            @click="toManageMotelPage"
          >
            Quản lý trọ đã đăng
          </li>
          <li class="list-group-item active" style="cursor: pointer">
            Đăng Tin
          </li>
          <li
            class="list-group-item"
            style="cursor: pointer"
            @click="toChangePasswordPage"
          >
            Đổi Mật Khẩu
          </li>
        </ul>
      </div>
    </div>

    <div class="col-md-8">
      <!-- Main Content -->
      <div class="widget">
        <formPost></formPost>
      </div>
    </div>
  </div>
</template>
<script>
import formPost from "@/components/PageComponents/postMotelPageComponents/formPost.vue";
import navbarInforAccount from "@/components/PageComponents/accountPageComponents/navbarInforAccount.vue";
export default {
  name: "PostPage",
  components: {
    navbarInforAccount,
    formPost,
  },
  methods: {
    toChangePasswordPage() {
      this.$router.push("/account/doi-mat-khau");
    },
    toManageMotelPage() {
      this.$router.push("/account/quan-ly-tro");
    },
    toInforAccount() {
      this.$router.push("/account/thong-tin-tai-khoan");
    },
  },
};
</script>
<style scoped>
h3 {
  color: #57bee7;
  font-size: xx-large;
}

button {
  background-color: #0056b3;
  border-color: #0056b3;
}
</style>
