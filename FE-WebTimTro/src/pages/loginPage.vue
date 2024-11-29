<template>
  <div>
    <!-- Modal -->
    <div
      class="modal"
      v-if="showModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content slide-down">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Lỗi Đăng Nhập</h5>
            <button
              class="close"
              type="button"
              @click="closeModal"
              aria-label="Close"
            >
              <span>×</span>
            </button>
          </div>
          <div class="modal-body">
            <p>{{ errorMessage }}</p>
          </div>
          <div class="modal-footer">
            <button class="btn btn-danger" type="button" @click="closeModal">
              OK
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="bg-gradient-primary">
      <div class="container">
        <!-- Outer Row -->
        <div class="row justify-content-center">
          <div class="col-xl-10 col-lg-12 col-md-9">
            <div class="card o-hidden border-0 shadow-lg my-5">
              <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                  <div class="col-lg-12">
                    <div class="p-5">
                      <div class="text-center">
                        <h1 class="h4 text-gray-900 mb-4">Đăng Nhập</h1>
                      </div>
                      <form class="user" @submit.prevent="handleLogin">
                        <div class="form-group">
                          <input
                            type="text"
                            class="form-control form-control-user"
                            v-model="userName"
                            placeholder="Tên Đăng Nhập"
                            required
                          />
                        </div>
                        <div class="form-group">
                          <input
                            type="password"
                            class="form-control form-control-user"
                            v-model="password"
                            placeholder="Mật Khẩu"
                            required
                          />
                        </div>
                        <div class="form-group">
                          <div class="custom-control custom-checkbox small">
                            <input
                              type="checkbox"
                              class="custom-control-input"
                              id="customCheck"
                            />
                            <label
                              class="custom-control-label"
                              for="customCheck"
                              >Lưu Đăng Nhập</label
                            >
                          </div>
                        </div>
                        <button
                          type="submit"
                          class="btn btn-primary btn-user btn-block"
                        >
                          Đăng Nhập
                        </button>
                        <hr />
                        <button
                          type="button"
                          class="btn btn-google btn-user btn-block"
                          @click="loginWithGoogle"
                        >
                          <i
                            class="fab fa-google fa-fw"
                            style="color: #db4437"
                          ></i>
                          Đăng nhập bằng Google
                        </button>
                        <button
                          type="button"
                          class="btn btn-facebook btn-user btn-block"
                          @click="loginWithFacebook"
                        >
                          <i
                            class="fab fa-facebook-f fa-fw"
                            style="color: #4267b2"
                          ></i>
                          Đăng nhập bằng Facebook
                        </button>
                      </form>
                      <hr />
                      <div class="text-center">
                        <a
                          class="small"
                          @click="toForgotPasswordPage"
                          style="cursor: pointer"
                          >Quên Mật Khẩu?</a
                        >
                      </div>
                      <div class="text-center">
                        <a
                          class="small"
                          style="cursor: pointer"
                          @click="toRegisterPage"
                          >Tạo Tài Khoản Mới!</a
                        >
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
  name: "Login",
  data() {
    return {
      userName: "",
      password: "",
      errorMessage: "",
      showModal: false,
    };
  },
  methods: {
    handleLogin() {
      const data = {
        userName: this.userName,
        password: this.password,
      };

      axios
        .post("http://localhost:8081/login", data, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          localStorage.setItem("token", response.data);
          this.$router.push("/");
        })
        .catch((error) => {
          this.errorMessage = error.response.data;
          this.showModal = true;
        });
    },
    toForgotPasswordPage() {
      this.$router.push("/forgot-password");
    },
    closeModal() {
      this.showModal = false;
    },
    loginWithGoogle() {
      console.log("Logging in with Google");
    },
    loginWithFacebook() {
      console.log("Logging in with Facebook");
    },
    toRegisterPage() {
      this.$router.push("/register");
    },
  },
};
</script>
  
  <style scoped>
.bg-gradient-primary {
  background-color: #641e1eff;
  background-image: #000000;
  background-size: contain;
}

.bg-login-image {
  background: url("/img/buildingLogo.jpg");
  background-position: center;
  background-size: contain;
}

html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
}

.container {
  min-height: 100vh;
}

/* Modal styles */
.modal {
  position: fixed;
  z-index: 1050;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  display: flex;
  align-items: flex-start;
  justify-content: center;
  animation: fadeIn 0.5s;
}

.modal-dialog {
  max-width: 500px;
  width: 100%;
  margin-top: 20px;
}

.modal-content {
  border-radius: 0.5rem;
  transform: translateY(-50px);

  animation: slideDown 0.5s forwards;
}
.btn-google {
  background-color: #db4437;
  color: #ffffff;
  border: none;
}
.btn-google:hover {
  background-color: #c23321;
}

.btn-facebook {
  background-color: #4267b2;
  color: #ffffff;
  border: none;
}
.btn-facebook:hover {
  background-color: #365899;
}

@keyframes slideDown {
  from {
    transform: translateY(-50px);
  }

  to {
    transform: translateY(0);
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}
</style>
<style scoped>
</style>


