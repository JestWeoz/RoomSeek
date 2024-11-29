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
                        <h1 class="h4 text-gray-900 mb-4">Đặt Lại Mật Khẩu</h1>
                      </div>
                      <form class="user" @submit.prevent="handleResetPassword">
                        <div class="form-group">
                          <input
                            type="password"
                            class="form-control form-control-user"
                            v-model="newPassword"
                            placeholder="Nhập mật khẩu mới"
                            required
                          />
                        </div>
                        <button
                          type="submit"
                          class="btn btn-primary btn-user btn-block"
                        >
                          Xác Nhận
                        </button>
                      </form>
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
  name: "ResetPassword",
  data() {
    return {
      newPassword: "",
      token: this.$route.query.token, // Lấy token từ URL
      isLoading: false,
    };
  },
  methods: {
    handleResetPassword() {
      const data = new URLSearchParams();
      data.append("token", this.token);
      data.append("newPassword", this.newPassword);

      axios
        .post("http://localhost:8081/reset-password", data, {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
        })
        .then(() => {
          this.isLoading = true;
          alert("Đặt lại mật khẩu mới thành công");
          this.$router.push("/login");
        })
        .catch((error) => {
          this.success = false;
          this.message = error.response?.data || "Có lỗi xảy ra!";
        });
    },
  },
};
</script>
  
  <style scoped>
.reset-password-page {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.card-title {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-control {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.btn {
  display: block;
  width: 100%;
  padding: 10px;
  background: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn:hover {
  background: #0056b3;
}

.success {
  color: green;
  text-align: center;
  margin-top: 10px;
}

.error {
  color: red;
  text-align: center;
  margin-top: 10px;
}
</style>
  