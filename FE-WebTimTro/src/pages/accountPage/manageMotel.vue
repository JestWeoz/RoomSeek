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
    <!-- Sidebar -->
    <div class="col-md-2">
      <div class="widget" style="background-color: #ecececc4">
        <ul class="list-group">
          <li
            class="list-group-item"
            @click="toInforAccount"
            style="cursor: pointer"
          >
            Thông tin Tài Khoản
          </li>
          <li class="list-group-item active">Quản lý trọ đã đăng</li>
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
    <!-- Main Content -->
    <div class="col-md-8">
      <div class="widget" style="background-color: #ecececc4">
        <h3 class="mb-4">DANH SÁCH TRỌ ĐÃ ĐĂNG</h3>
        <div v-for="motel in motels" :key="motel.id" class="card mb-3">
          <div class="card-body">
            <h5 class="card-title">{{ motel.title }}</h5>
            <p class="card-text">{{ motel.description }}</p>
            <p class="card-text">
              <small class="text-muted">Giá: {{ motel.price }} VNĐ</small>
            </p>
            <button class="btn btn-warning me-2" @click="editmotel(motel.id)">
              Sửa
            </button>
            <button class="btn btn-danger" @click="showDeleteModal(motel.id)">
              Xoá
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div
    v-if="showConfirm"
    class="modal"
    style="display: block; background-color: rgba(0, 0, 0, 0.5)"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Xác nhận xoá</h5>
          <button type="button" class="btn-close" @click="closeModal"></button>
        </div>
        <div class="modal-body">
          <p>Bạn có chắc chắn muốn xoá bài đăng này không?</p>
        </div>
        <div class="modal-footer">
          <button class="btn btn-danger" @click="confirmDelete">Đồng ý</button>
          <button class="btn btn-secondary" @click="closeModal">Huỷ</button>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script>
import axios from "axios";
import navbarInforAccount from "@/components/PageComponents/accountPageComponents/navbarInforAccount.vue";
export default {
  name: "ManageMotel",
  components: {
    navbarInforAccount,
  },
  data() {
    return {
      motels: [],
      showConfirm: false,
    };
  },
  mounted() {
    this.getMotels();
  },
  methods: {
    toInforAccount() {
      this.$router.push("/account/thong-tin-tai-khoan");
    },
    toPostPage() {
      this.$router.push("/account/dang-tin");
    },
    async getMotels() {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const response = await axios.get(
            "http://localhost:8081/account/motels",
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
          this.motels = response.data;
        } catch (error) {
          console.error("Error get motels :", error);
        }
      }
    },
    showDeleteModal(id) {
      this.motelToDelete = id;
      this.showConfirm = true;
    },
    closeModal() {
      this.showConfirm = false;
      this.motelToDelete = null;
    },
    async confirmDelete() {
      if (this.motelToDelete) {
        try {
          await axios.delete(
            `http://localhost:8081/delete/${this.motelToDelete}`
          );
          this.motels = this.motels.filter(
            (motel) => motel.id !== this.motelToDelete
          );
        } catch (error) {
          console.error("Error delete motel: ", error);
        } finally {
          this.closeModal();
        }
      }
    },
    editmotel(id) {
      this.$router.push(`/account/edit-motel/${id}`);
    },
  },
};
</script>
  
  <style scoped>
.card {
  border: 1px solid #ddd;
}
.card-title {
  font-size: 1.25rem;
  color: #0056b3;
}
.card-text {
  font-size: 1rem;
  color: #333;
}
button {
  cursor: pointer;
}
</style>
  