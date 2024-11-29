<template>
  <fieldset class="border p-3">
    <legend class="w-auto px-2">Nhập thông tin nhà của bạn</legend>

    <!-- Tên toà nhà -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="title"
        placeholder="Nhập Tên Toà Nhà"
        v-model="formData.title"
        required
      />
      <label for="title">Nhập tiêu đề</label>
    </div>

    <!-- Số nhà -->
    <div class="form-floating mb-3">
      <input
        type="number"
        class="form-control"
        id="houseNumber"
        placeholder="Số nhà"
        v-model="formData.houseNumber"
        required
      />
      <label for="houseNumber">Số nhà</label>
    </div>

    <!-- Tỉnh/Thành Phố, Quận/Huyện, Phường/Xã, Đường -->
    <div class="container">
      <div class="row">
        <div class="col-6 col-sm-3">
          <select
            class="form-select mb-3"
            v-model="selectedLocations.province"
            @change="onProvinceChange"
            required
          >
            <option value="" selected disabled>Chọn tỉnh thành</option>
            <option
              v-for="province in provinces"
              :key="province.code"
              :value="province"
            >
              {{ province.name }}
            </option>
          </select>
        </div>
        <div class="col-6 col-sm-3">
          <select
            class="form-select mb-3"
            v-model="selectedLocations.district"
            @change="onDistrictChange"
            :disabled="!districts.length"
            required
          >
            <option value="" disabled selected>Chọn quận huyện</option>
            <option
              v-for="district in districts"
              :key="district.code"
              :value="district"
            >
              {{ district.name }}
            </option>
          </select>
        </div>
        <div class="col-6 col-sm-3">
          <select
            class="form-select mb-3"
            v-model="selectedLocations.ward"
            :disabled="!wards.length"
            required
          >
            <option value="" disabled selected>Chọn phường xã</option>
            <option v-for="ward in wards" :key="ward.code" :value="ward">
              {{ ward.name }}
            </option>
          </select>
        </div>
        <div class="col-6 col-sm-3">
          <input
            type="text"
            class="form-control mb-3"
            placeholder="Đường"
            v-model="formData.street"
            required
          />
        </div>
      </div>
    </div>

    <!-- Diện tích -->
    <div class="form-floating mb-3">
      <input
        type="number"
        class="form-control"
        id="area"
        placeholder="Diện tích"
        v-model="formData.area"
        required
      />
      <label for="area">Diện tích (m²)</label>
    </div>

    <!-- Giá thuê -->
    <div class="form-floating mb-3">
      <input
        type="number"
        class="form-control"
        id="price"
        placeholder="Giá thuê"
        v-model="formData.price"
        required
      />
      <label for="price">Giá thuê (VND)</label>
    </div>

    <!-- Nội thất -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="interior"
        placeholder="Nội thất"
        v-model="formData.interior"
        required
      />
      <label for="interior">Nội thất</label>
    </div>

    <!-- Loại hình -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="type"
        placeholder="Loại hình"
        v-model="formData.type"
        required
      />
      <label for="type">Loại hình</label>
    </div>

    <!-- Mô tả chi tiết -->
    <div class="form-floating mb-3">
      <textarea
        class="form-control"
        id="detail"
        placeholder="Mô tả chi tiết"
        v-model="formData.detail"
        required
      ></textarea>
      <label for="detail">Mô tả chi tiết</label>
    </div>

    <!-- Số người tối đa -->
    <div class="form-floating mb-3">
      <input
        type="number"
        class="form-control"
        id="maxPeople"
        placeholder="Số người tối đa"
        v-model="formData.maxPeople"
        required
      />
      <label for="maxPeople">Số người tối đa</label>
    </div>

    <!-- Phần thêm ảnh -->
    <div class="mb-3">
      <uploadImage @update:files="handleUploadedFiles" />
    </div>

    <!-- Các nút đồng ý và huỷ -->
    <div class="d-flex justify-content-end">
      <button type="button" class="btn btn-success me-2" @click="submitForm">
        Đồng ý
      </button>
      <button type="button" class="btn btn-secondary" @click="resetForm">
        Huỷ
      </button>
    </div>
    <!-- Vòng xoay và thông báo thành công -->
    <div v-if="loading || success" class="simple-modal-overlay">
      <div class="simple-modal-box">
        <div v-if="loading" class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Đang xử lý...</span>
        </div>
        <div v-if="success" class="text-success">
          <!-- Thêm phần hình ảnh dấu tick -->
          <img
            src="/img/check-circle-fill.svg"
            alt="Success"
            class="success-icon"
          />
          <p class="success-text">Đăng tin thành công</p>
        </div>
      </div>
    </div>
  </fieldset>
</template>

<script>
import axios from "axios";
import uploadImage from "./uploadImage.vue";

export default {
  components: {
    uploadImage,
  },
  data() {
    return {
      provinces: [],
      districts: [],
      wards: [],
      selectedLocations: {
        province: "",
        district: "",
        ward: "",
      },
      formData: {
        title: null,
        type: null,
        houseNumber: null,
        street: null,
        area: null,
        price: null,
        interior: null,
        maxPeople: null,
        detail: null,
      },
      uploadedImages: [],
      loading: false, // Trạng thái chờ
      success: false, // Trạng thái thành công
    };
  },
  created() {
    this.fetchProvinces();
  },
  methods: {
    handleUploadedFiles(files) {
      this.uploadedImages = files;
    },
    async fetchProvinces() {
      try {
        const response = await axios.get(
          "https://provinces.open-api.vn/api/?depth=1"
        );
        this.provinces = response.data;
      } catch (error) {
        console.error("Error fetching provinces:", error);
      }
    },
    async onProvinceChange() {
      this.selectedLocations.district = "";
      this.selectedLocations.ward = "";
      this.districts = [];
      this.wards = [];

      if (this.selectedLocations.province) {
        try {
          const response = await axios.get(
            `https://provinces.open-api.vn/api/p/${this.selectedLocations.province.code}?depth=2`
          );
          this.districts = response.data.districts;
        } catch (error) {
          console.error("Error fetching districts:", error);
        }
      }
    },
    async onDistrictChange() {
      this.selectedLocations.ward = "";
      this.wards = [];

      if (this.selectedLocations.district) {
        try {
          const response = await axios.get(
            `https://provinces.open-api.vn/api/d/${this.selectedLocations.district.code}?depth=2`
          );
          this.wards = response.data.wards;
        } catch (error) {
          console.error("Error fetching wards:", error);
        }
      }
    },
    resetForm() {
      this.formData = {
        title: null,
        type: null,
        houseNumber: null,
        street: null,
        area: null,
        price: null,
        interior: null,
        maxPeople: null,
        detail: null,
      };
      this.selectedLocations = {
        province: "",
        district: "",
        ward: "",
      };
      this.uploadedImages = [];
      window.location.reload();
    },
    async submitForm() {
      // Reset trạng thái
      this.success = false;

      // Validation
      if (!this.validateForm()) {
        alert("Vui lòng điền đầy đủ thông tin!");
        return;
      }

      const formData = new FormData();

      // Append location data
      formData.append("province", this.selectedLocations.province.name);
      formData.append("district", this.selectedLocations.district.name);
      formData.append("ward", this.selectedLocations.ward.name);

      // Append form data
      Object.keys(this.formData).forEach((key) => {
        formData.append(key, this.formData[key]);
      });

      // Append images
      this.uploadedImages.forEach((file, index) => {
        formData.append(`files[${index}]`, file);
      });
      console.log(formData);
      const token = localStorage.getItem("token");

      try {
        this.loading = true; // Hiển thị vòng xoay
        const response = await axios.post(
          "http://localhost:8081/create",
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: `Bearer ${token}`,
            },
          }
        );
        this.success = true; // Hiển thị dấu tích thành công
        this.resetForm();
      } catch (error) {
        console.error("Error:", error);
        alert(error.response?.data?.message || "Đã xảy ra lỗi khi tạo mới!");
      } finally {
        this.loading = false; // Ẩn vòng xoay
        setTimeout(() => (this.success = false), 5000); // Ẩn dấu tích sau 3s
      }
    },
    validateForm() {
      const requiredFields = Object.values(this.formData);
      const locationFields = Object.values(this.selectedLocations);

      return (
        requiredFields.every((field) => field != null && field !== "") &&
        locationFields.every((field) => field !== "") &&
        this.uploadedImages.length > 0
      );
    },
  },
};
</script>

<style scoped>
.border {
  border: 5px solid #ced4da;
  border-radius: 5px;
}
.legend {
  font-weight: bold;
}
.simple-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* Nền mờ */
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1050;
}

.simple-modal-box {
  width: 200px; /* Hình vuông */
  height: 200px;
  background: white;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  text-align: center;
}

.spinner-border {
  width: 3rem;
  height: 3rem;
}

.success-text {
  font-size: 1.2rem;
  font-weight: bold;
  color: green;
  margin: 0;
}
.success-icon {
  width: 50px; /* Điều chỉnh kích thước biểu tượng dấu tick */
  height: 50px;
  margin-bottom: 10px; /* Khoảng cách giữa hình ảnh và văn bản */
}
</style>