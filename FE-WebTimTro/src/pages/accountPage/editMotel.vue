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
            class="list-group-item active"
            style="cursor: pointer"
            @click="toManageMotelPage"
          >
            Quản lý trọ đã đăng
          </li>
          <li class="list-group-item" style="cursor: pointer">Đăng Tin</li>
          <li class="list-group-item" style="cursor: pointer">
            Quản lý đánh giá trợ
          </li>
        </ul>
      </div>
    </div>

    <div class="col-md-8">
      <!-- Main Content -->
      <div class="widget">
        <formEditMotel></formEditMotel>
      </div>
    </div>
  </div>
</template>
<script>
import formEditMotel from "@/components/PageComponents/postMotelPageComponents/formEditMotel.vue";
import navbarInforAccount from "@/components/PageComponents/accountPageComponents/navbarInforAccount.vue";
export default {
  name: "EditPage",
  components: {
    navbarInforAccount,
    formEditMotel,
  },
  methods: {
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
