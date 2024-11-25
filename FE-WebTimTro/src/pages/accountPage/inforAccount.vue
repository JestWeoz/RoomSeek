<template>
  <div class="hpc-navbar">
    <div class="container">
      <div class="row">
        <navbarInforAccount></navbarInforAccount>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-1"></div>
    <div class="col-md-2">
      <!-- Sidebar -->
      <div class="widget" style="background-color: #ecececc4">
        <ul class="list-group">
          <li class="list-group-item active">Thông tin Tài Khoản</li>
          <li
            class="list-group-item"
            style="cursor: pointer"
            @click="toManageMotelPage"
          >
            Quản lý trọ đã đăng
          </li>
          <li
            class="list-group-item"
            @click="toPostPage"
            style="cursor: pointer"
          >
            Đăng Tin
          </li>
          <li class="list-group-item" style="cursor: pointer">
            Quản lý đánh giá trợ
          </li>
        </ul>
      </div>
    </div>

    <div class="col-md-8">
      <!-- Main Content -->
      <div class="widget" style="background-color: #ecececc4">
        <h3 class="mb-4">THÔNG TIN TÀI KHOẢN</h3>
        <p>
          Cập nhật thông tin của bạn và tìm hiểu các thông tin này được sử dụng
          ra sao.
        </p>

        <form @submit.prevent="updateInfo">
          <div class="mb-3">
            <label class="form-label">Họ tên</label>
            <input
              type="text"
              v-model="name"
              class="form-control"
              placeholder="Nhập Tên"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Số Điện Thoại</label>
            <input
              type="text"
              v-model="phoneNumber"
              class="form-control"
              placeholder="Nhập SĐT"
            />
          </div>

          <button type="submit" class="btn btn-primary">Cập nhật</button>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import navbarInforAccount from "@/components/PageComponents/accountPageComponents/navbarInforAccount.vue";
import axios from "axios";
export default {
  name: "InforAccount",
  components: {
    navbarInforAccount,
  },
  mounted() {
    this.getUserInfo();
  },
  data() {
    return {
      userInfo: [],
      phoneNumber: null,
      name: "",
    };
  },

  methods: {
    toManageMotelPage() {
      this.$router.push("/account/quan-ly-tro");
    },
    toPostPage() {
      this.$router.push("/account/dang-tin");
    },
    async getUserInfo() {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const response = await axios.get("http://localhost:8081/get-info", {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          this.userInfo = response.data;
          this.phoneNumber = this.phoneNumber;
        } catch (error) {
          console.error("Error fetching user info:", error);
        }
      }
    },
    async updateUserInfo() {
      
    }

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
