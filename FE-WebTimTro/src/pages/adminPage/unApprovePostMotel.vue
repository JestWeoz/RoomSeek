<template>
  <div class="hpc-navbar">
    <div class="container">
      <div class="row">
        <navbar />
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-1"></div>
    <!-- Sidebar -->
    <div class="col-md-2">
      <div class="widget" style="background-color: #ecececc4">
        <ul class="list-group">
          <li class="list-group-item active">Tin đang chờ duyệt</li>
          <li
            class="list-group-item"
            style="cursor: pointer"
            @click="toApprovedMotelPage"
          >
            Tin đã duyệt
          </li>
        </ul>
      </div>
    </div>
    <!-- Main Content -->
    <div class="col-md-8">
      <div class="widget" style="background-color: #ecececc4">
        <!-- Hàng chứa tiêu đề và checkbox "Chọn tất cả" -->
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h3 class="m-0">DANH SÁCH TIN ĐANG CHỜ DUYỆT</h3>
          <div class="form-check">
            <input
              type="checkbox"
              id="select-all"
              class="form-check-input"
              v-model="selectAll"
              @change="toggleAllCheckboxes"
            />
            <label for="select-all" class="form-check-label">Chọn tất cả</label>
          </div>
        </div>

        <div
          v-for="motel in motels"
          :key="motel.id"
          class="card mb-3 position-relative"
        >
          <!-- Checkbox trên góc phải của từng card -->
          <div class="form-check position-absolute top-0 end-0 m-2">
            <input
              type="checkbox"
              :id="'motel-' + motel.id"
              class="form-check-input"
              v-model="selectedMotels"
              :value="motel.id"
            />
            <label for="">chọn</label>
          </div>
          <div class="card-body">
            <h5
              class="card-title"
              @click="toDetailsPage(motel.id, motel.title)"
              style="cursor: pointer"
            >
              {{ motel.title }}
            </h5>
            <p class="card-text">{{ motel.detail }}</p>
            <p class="card-text">{{ motel.address }}</p>
            <p class="card-text">
              <small class="text-muted">Giá: {{ motel.price }} VNĐ</small>
            </p>
          </div>
        </div>
        <button class="btn btn-success" @click="approveSelected">
          Duyệt Tin
        </button>
      </div>
    </div>
  </div>
</template>





      
      <script>
import axios from "axios";

import Navbar from "@/components/PageComponents/homePageComponents/navbar.vue";
import { removeVietnameseTones } from "@/utils/removeVnTone";
export default {
  name: "UnApprovePage",
  components: {
    Navbar,
  },
  data() {
    return {
      motels: [],
      showConfirm: false,
      selectAll: false,
      selectedMotels: [],
      status: 1,
    };
  },
  mounted() {
    this.getMotels();
  },
  methods: {
    toDetailsPage(id, title) {
      const newTitle = removeVietnameseTones(title);
      const slug = newTitle
        .toLowerCase()
        .replace(/ /g, "-") // Thay khoảng trắng bằng dấu '-'
        .replace(/[^\w-]+/g, "");
      this.$router.push(`/motel/${slug}-${id}`);
    },
    toggleAllCheckboxes() {
      if (this.selectAll) {
        this.selectedMotels = this.motels.map((motel) => motel.id);
      } else {
        this.selectedMotels = [];
      }
    },
    toApprovedMotelPage() {
      this.$router.push("/admin/bai-dang-da-duyet");
    },
    async getMotels() {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const response = await axios.get(
            "http://localhost:8081/unapproved-motels",
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
    async approveSelected() {
      const formData = new FormData();
      formData.append("id", this.selectedMotels);
      formData.append("status", this.status);
      const token = localStorage.getItem("token");
      console.log(formData);
      if (token) {
        try {
          await axios.post("http://localhost:8081/admin/set-status", formData, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
        } catch (error) {
          console.error("loi");
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
  position: relative; /* Để định vị checkbox bên trong card */
}
.card-title {
  font-size: 1.25rem;
  color: #0056b3;
}
.card-text {
  font-size: 1rem;
  color: #333;
}
.position-absolute {
  position: absolute !important;
}
.top-0 {
  top: 0 !important;
}
.end-0 {
  right: 0 !important;
}
.m-2 {
  margin: 0.5rem !important;
}
button {
  cursor: pointer;
}
</style>

      