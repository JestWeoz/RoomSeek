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
          <li
            class="list-group-item"
            style="cursor: pointer"
            @click="toInfoPage"
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
          <li
            class="list-group-item"
            @click="toPostPage"
            style="cursor: pointer"
          >
            Đăng Tin
          </li>
          <li class="list-group-item active" style="cursor: pointer">
            Đổi mật khẩu
          </li>
        </ul>
      </div>
    </div>

    <div class="col-md-8">
      <!-- Main Content -->
      <div class="widget" style="background-color: #ecececc4">
        <h3 class="mb-4">ĐỔI MẬT KHẨU</h3>
        <p>Cập nhật thông tin của bạn</p>

        <form @submit.prevent="updateInfo">
          <div class="mb-3">
            <label class="form-label">Mật khẩu cũ</label>
            <input
              type="password"
              v-model="oldPassword"
              class="form-control"
              placeholder="Nhập mật khẩu cũ"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Mật khẩu mới</label>
            <input
              type="password"
              v-model="newPassword"
              class="form-control"
              placeholder="Nhập mật khẩu mới"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Nhập lại mật khẩu</label>
            <input
              type="password"
              v-model="confirmPassword"
              class="form-control"
              placeholder="Nhập lại mật khẩu mới"
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
          <p>Cập nhật mật khẩu thành công</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="closeModal">
            Đóng
          </button>
        </div>
      </div>
    </div>
  </div>
  <!-- Modal lỗi -->
  <div
    v-if="showErrorModal"
    class="modal"
    tabindex="-1"
    style="display: block; background-color: rgba(0, 0, 0, 0.5)"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Thông báo lỗi</h5>
          <button
            type="button"
            class="btn-close"
            @click="closeErrorModal"
          ></button>
        </div>
        <div class="modal-body">
          <p>{{ errorMessage }}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" @click="closeErrorModal">
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
  name: "ChangePassword",
  components: {
    navbarInforAccount,
    Navbar,
  },
  data() {
    return {
      oldPassword: "",
      newPassword: "",
      confirmPassword: "",
      showModal: false,
      showErrorModal: false,
      errorMessage: "",
    };
  },

  methods: {
    toInfoPage() {
      this.$router.push("/account/thong-tin-tai-khoan");
    },
    toManageMotelPage() {
      this.$router.push("/account/quan-ly-tro");
    },
    toPostPage() {
      this.$router.push("/account/dang-tin");
    },

    async submitForm() {
      const formData = new FormData();
      formData.append("oldPassword", this.oldPassword);
      formData.append("newPassword", this.newPassword);
      formData.append("confirmPassword", this.confirmPassword);
      const token = localStorage.getItem("token");
      if (token) {
        try {
          await axios.post("http://localhost:8081/changePassword", formData, {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: `Bearer ${token}`,
            },
          });
          this.showModal = true;
        } catch (error) {
          this.errorMessage =
            error.response?.data?.message ||
            "Mật khẩu cũ hoặc mật khẩu nhập lại của bạn không đúng";
          this.showErrorModal = true;
        }
      }
    },
    closeModal() {
      this.showModal = false; // Đóng modal
      window.location.reload();
    },
    closeErrorModal() {
      this.showErrorModal = false;
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
  