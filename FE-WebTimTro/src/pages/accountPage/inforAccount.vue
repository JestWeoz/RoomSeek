<template>
  <div class="hpc-navbar">
    <div class="container">
      <div class="row">
        <Navbar></Navbar>
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
          <li
            class="list-group-item"
            style="cursor: pointer"
            @click="toChangePasswordPage"
          >
            Đổi mật khẩu
          </li>
        </ul>
      </div>
    </div>

    <div class="col-md-8">
      <!-- Main Content -->
      <div class="widget" style="background-color: #ecececc4">
        <h3 class="mb-4">THÔNG TIN TÀI KHOẢN</h3>
        <p>Cập nhật thông tin của bạn</p>

        <form @submit.prevent="updateInfo">
          <div class="mb-3">
            <label class="form-label">Họ tên</label>
            <input
              type="text"
              v-model="userInfo.fullName"
              class="form-control"
              placeholder="Nhập Tên"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Số Điện Thoại</label>
            <input
              type="text"
              v-model="userInfo.phoneNumber"
              class="form-control"
              placeholder="Nhập SĐT"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Email</label>
            <input
              type="text"
              v-model="userInfo.email"
              class="form-control"
              placeholder="Nhập Email"
            />
          </div>

          <button type="submit" class="btn btn-primary" @click="submitForm()">
            Cập nhật
          </button>
        </form>
      </div>
    </div>
  </div>
  <!-- Modal -->
  <div
    v-if="showModal"
    class="modal"
    tabindex="-1"
    style="display: block; background-color: rgba(0, 0, 0, 0.5)"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Thông báo</h5>
          <button type="button" class="btn-close" @click="closeModal"></button>
        </div>
        <div class="modal-body">
          <p>Cập nhật thông tin thành công!</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="closeModal">
            Đóng
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import navbarInforAccount from "@/components/PageComponents/accountPageComponents/navbarInforAccount.vue";
import Navbar from "@/components/PageComponents/homePageComponents/navbar.vue";
import axios from "axios";
export default {
  name: "InforAccount",
  components: {
    navbarInforAccount,
    Navbar,
  },
  created() {
    this.getUserInfo();
  },
  data() {
    return {
      userInfo: [],
      showModal: false,
    };
  },

  methods: {
    toManageMotelPage() {
      this.$router.push("/account/quan-ly-tro");
    },
    toPostPage() {
      this.$router.push("/account/dang-tin");
    },
    toChangePasswordPage() {
      this.$router.push("/account/doi-mat-khau");
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
        } catch (error) {
          console.error("Error fetching user info:", error);
        }
      }
    },
    async updateUserInfo() {},
    async submitForm() {
      const formData = new FormData();
      formData.append("fullName", this.userInfo.fullName);
      formData.append("phoneNumber", this.userInfo.phoneNumber);
      formData.append("email", this.userInfo.email);
      const token = localStorage.getItem("token");
      if (token) {
        try {
          await axios.post(
            "http://localhost:8081/account/user-info",
            formData,
            {
              headers: {
                "Content-Type": "multipart/form-data",
                Authorization: `Bearer ${token}`,
              },
            }
          );
          this.showModal = true;
        } catch (error) {
          console.error("Error fetching user info:", error);
        }
      }
    },
    closeModal() {
      this.showModal = false; // Đóng modal
      window.location.reload();
    },
  },
};
</script>
<style scoped>
h3 {
  color: #57bee7;
  font-size: xx-large;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-dialog {
  max-width: 500px;
}
</style>
