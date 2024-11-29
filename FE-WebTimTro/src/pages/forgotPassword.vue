<template>
  <div>
    <!-- Loading Spinner -->
    <div v-if="isLoading" class="loading-spinner">
      <div class="spinner"></div>
    </div>

    <div class="bg-gradient-primary">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-xl-10 col-lg-12 col-md-9">
            <div class="card o-hidden border-0 shadow-lg my-5">
              <div class="card-body p-0">
                <div class="row">
                  <div class="col-lg-12">
                    <div class="p-5">
                      <div class="text-center">
                        <h1 class="h4 text-gray-900 mb-4">Quên Mật Khẩu</h1>
                      </div>
                      <form class="user" @submit.prevent="handleForgotPassword">
                        <div class="form-group">
                          <input
                            type="email"
                            class="form-control form-control-user"
                            v-model="email"
                            placeholder="Nhập Email Đăng Ký"
                            required
                          />
                        </div>
                        <button
                          type="submit"
                          class="btn btn-primary btn-user btn-block"
                        >
                          Gửi Yêu Cầu
                        </button>
                      </form>

                      <div class="text-center">
                        <a
                          class="small"
                          @click="toLoginPage"
                          style="cursor: pointer"
                        >
                          Quay Lại Đăng Nhập
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script>
import axios from "axios";
export default {
  name: "ForgotPassword",
  data() {
    return {
      email: "",
      errorMessage: "",
      showModal: false,
      showSuccessModal: false,
      isLoading: false,
    };
  },
  methods: {
    handleForgotPassword() {
      this.isLoading = true; // Hiển thị spinner
      console.log("Spinner bật:", this.isLoading);

      axios
        .post(`http://localhost:8081/forgot-password/${this.email}`)
        .then(() => {
          alert("Đã gửi yêu cầu đặt lại mật khẩu, vui lòng kiểm tra email.");
          this.isLoading = false;
          this.toLoginPage();
        })
        .catch((error) => {
          this.errorMessage = error.response?.data || "Có lỗi xảy ra.";
          console.log("Modal lỗi bật:", this.showModal);
        });
    },

    toLoginPage() {
      this.$router.push("/login");
    },
  },
};
</script>
  
  <style scoped>
/* Loading Spinner Styles */
.loading-spinner {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9999;
}

.spinner {
  border: 4px solid rgba(255, 255, 255, 0.3);
  border-top: 4px solid #fff;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
  