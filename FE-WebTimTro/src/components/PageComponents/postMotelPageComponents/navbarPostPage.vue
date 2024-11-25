<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light nav-desktop">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto logo">
        <li class="nav-item" @click="toHomePage" style="cursor: pointer">
          <a>
            <img
              src="https://png.pngtree.com/template/20191014/ourmid/pngtree-building-and-construction-logo-design-template-image_317780.jpg"
              width="50"
              height="36"
              alt="mogi"
            />
          </a>
          <span>Trang chủ</span>
        </li>
      </ul>
      <ul class="navbar-nav menu-top">
        <li class="nav-item ml-2">
          <a class="nav-link" @click="toTorenPage">Tìm thuê</a>
        </li>
        <li class="nav-item ml-2">
          <a
            class="nav-link"
            href="https://www.facebook.com/profile.php?id=100017121455155"
            >Liên hệ</a
          >
        </li>
        <li class="nav-item ml-2">
          <a class="nav-link"><i class="far fa-heart"></i></a>
        </li>
        <li class="nav-item ml-2">
          <a class="nav-link"><i class="far fa-bell"></i></a>
        </li>
        <li class="nav-item ml-2">
          <button
            class="btn btn-outline-success my-2 my-sm-0"
            type="submit"
            @click="toPostPage"
          >
            Đăng tin
          </button>
        </li>
      </ul>
      <!-- Đưa các nút Đăng nhập và Đăng ký hoàn toàn về cuối -->
      <div class="ml-auto d-flex">
        <div class="dropdown" v-if="hasToken">
          <button
            class="btn btn-secondary dropdown-toggle"
            type="button"
            id="dropdownMenuButton1"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            Hi {{ userInfo.userName }}
          </button>
          <ul
            class="dropdown-menu dropdown-menu-end"
            aria-labelledby="dropdownMenuButton1"
          >
            <li><a class="dropdown-item" href="#">Thông tin</a></li>
            <li>
              <a
                class="dropdown-item"
                data-bs-toggle="modal"
                data-bs-target="#exampleModal"
                href="#"
                >Đăng xuất</a
              >
            </li>
          </ul>
        </div>
        <button
          class="btn btn-outline-primary mr-2"
          v-else
          @click="toLoginPage"
        >
          Đăng nhập
        </button>
      </div>
      <!-- model -->
      <div
        class="modal fade"
        id="exampleModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Đăng Xuất</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">Bạn có muốn đăng xuất không?</div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-success"
                data-bs-dismiss="modal"
                @click="toLoginPage"
              >
                Đồng ý
              </button>
              <button
                type="button"
                class="btn btn-danger"
                data-bs-dismiss="modal"
              >
                Huỷ
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>
    
  
  <script>
import axios from "axios";
export default {
  name: "Navbar",
  data() {
    return {
      userInfo: {},
    };
  },
  mounted() {
    this.getUserInfo();
  },
  methods: {
    toHomePage() {
      this.$router.push("/");
    },
    toLoginPage() {
      localStorage.removeItem("token");
      this.$router.push("/login");
    },
    toTorenPage() {
      this.$router.push("/search");
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
        } catch (error) {
          console.error("Error fetching user info:", error);
        }
      }
    },
  },

  computed: {
    hasToken() {
      if (localStorage.getItem("token")) {
        return true;
      } else {
        return false;
      }
    },
    noToken() {
      if (localStorage.getItem("token")) {
        return false;
      } else {
        return true;
      }
    },
  },
};
</script>
  
  
   