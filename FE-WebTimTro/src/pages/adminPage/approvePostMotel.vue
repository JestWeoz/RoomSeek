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
          <li
            class="list-group-item"
            @click="toUnApprovedMotelPage"
            style="cursor: pointer"
          >
            Tin đang chờ duyệt
          </li>
          <li class="list-group-item active" style="cursor: pointer">
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
          <h3 class="m-0">DANH SÁCH TIN ĐÃ DUYỆT</h3>
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
        <button class="btn btn-success" @click="approveSelected">Gỡ Tin</button>
      </div>
    </div>
  </div>

  <!-- Modal -->
  <div v-if="showSuccessModal" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Thông báo</h5>
          <button
            type="button"
            class="close"
            @click="closeModal"
            aria-label="Close"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <p>Gỡ tin thành công!</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="closeModal">
            OK
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Navbar from "@/components/PageComponents/homePageComponents/navbar.vue";
import { removeVietnameseTones } from "@/utils/removeVnTone";

export default {
  name: "ApprovePage",
  components: {
    Navbar,
  },
  data() {
    return {
      motels: [],
      showConfirm: false,
      showSuccessModal: false, // Trạng thái hiển thị modal
      selectAll: false,
      selectedMotels: [],
      status: 0,
    };
  },
  mounted() {
    this.getMotels();
  },
  methods: {
    toggleAllCheckboxes() {
      if (this.selectAll) {
        this.selectedMotels = this.motels.map((motel) => motel.id);
      } else {
        this.selectedMotels = [];
      }
    },
    toDetailsPage(id, title) {
      const newTitle = removeVietnameseTones(title);
      const slug = newTitle
        .toLowerCase()
        .replace(/ /g, "-")
        .replace(/[^\w-]+/g, "");
      this.$router.push(`/motel/${slug}-${id}`);
    },
    toUnApprovedMotelPage() {
      this.$router.push("/admin/duyet-bai-dang");
    },
    async getMotels() {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const response = await axios.get(
            "http://localhost:8081/approved-motels",
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
    closeModal() {
      this.showSuccessModal = false;
      window.location.reload();
    },
    async approveSelected() {
      const formData = new FormData();
      formData.append("id", this.selectedMotels);
      formData.append("status", this.status);
      const token = localStorage.getItem("token");
      if (token) {
        try {
          await axios.post("http://localhost:8081/admin/set-status", formData, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          // Hiện modal sau khi duyệt thành công
          this.showSuccessModal = true;
        } catch (error) {
          console.error("Lỗi duyệt bài:", error);
        }
      }
    },
  },
};
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-dialog {
  background: #fff;
  border-radius: 5px;
  max-width: 500px;
  width: 100%;
}
</style>
