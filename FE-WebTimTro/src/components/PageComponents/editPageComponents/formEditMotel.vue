<template>
  <fieldset class="border p-3">
    <legend class="w-auto px-2">Thông tin trọ của bạn</legend>

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
            v-model="selectedLocations.province.name"
            @change="onProvinceChange(selectedLocations.province.name)"
            required
          >
            <option value="" selected disabled>Chọn tỉnh thành</option>
            <option
              v-for="province in provinces"
              :key="province.code"
              :value="province.name"
            >
              {{ province.name }}
            </option>
          </select>
        </div>
        <div class="col-6 col-sm-3">
          <select
            class="form-select mb-3"
            v-model="selectedLocations.district.name"
            @change="onDistrictChange(selectedLocations.district.name)"
            :disabled="!districts.length"
            required
          >
            <option value="" disabled selected>Chọn quận huyện</option>
            <option
              v-for="district in districts"
              :key="district.code"
              :value="district.name"
            >
              {{ district.name }}
            </option>
          </select>
        </div>
        <div class="col-6 col-sm-3">
          <select
            class="form-select mb-3"
            v-model="selectedLocations.ward.name"
            :disabled="!wards.length"
            required
          >
            <option value="" disabled selected>Chọn phường xã</option>
            <option v-for="ward in wards" :key="ward.code" :value="ward.name">
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
    <!-- phần ảnh cũ -->
    <div class="mb-3 row">
      <label for="uploadFile" class="col-3 col-form-label"
        >Hình ảnh đã lưu</label
      >
      <div class="col-12">
        <!-- Preview container -->
        <div class="mt-3 d-flex flex-wrap gap-3">
          <div
            v-for="(preview, index) in Images"
            :key="index"
            class="position-relative preview-container"
            style="width: 150px"
          >
            <img
              :src="preview.fileUrl"
              :alt="'Preview ' + (index + 1)"
              class="img-fluid rounded"
              style="height: 150px; object-fit: cover; width: 100%"
            />
            <button class="remove-button" @click="handleDeleteImg(preview.id)">
              ×
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Phần thêm ảnh -->
    <div class="mb-3">
      <uploadImageEdit @update:files="handleUploadedFiles" />
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
          
        </div>
      </div>
    </div>
  </fieldset>
</template>
  
  <script>
import axios from "axios";

import uploadImageEdit from "./uploadImageEdit.vue";

export default {
  components: {
    uploadImageEdit,
  },
  created() {
    const motelId = this.$route.params.id;
    this.fetchMotelById(motelId);
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
        id: null,
        userId: null,
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
      district: "",
      province: "",
      listIdDelete: [],
      uploadedImages: [],
      Images: [],
      loading: false, // Trạng thái chờ
      success: false, // Trạng thái thành công
    };
  },

  methods: {
    handleUploadedFiles(files) {
      this.uploadedImages = files;
    },

    async onProvinceChange(province) {
      let code;
      for (let item of this.provinces) {
        if (item.name == province) {
          code = item.code;
          break;
        }
      }
      try {
        const response = await axios.get(
          `https://provinces.open-api.vn/api/p/${code}?depth=2`
        );
        this.districts = response.data.districts;
      } catch (error) {
        console.error("Error fetching districts:", error);
        this.districts = [];
      }
    },
    async onDistrictChange(ward) {
      let code;
      for (let item of this.districts) {
        if (item.name == ward) {
          code = item.code;
        }
      }

      try {
        const response = await axios.get(
          `https://provinces.open-api.vn/api/d/${code}?depth=2`
        );
        this.wards = response.data.wards;
      } catch (error) {
        console.error("Error fetching wards:", error);
      }
    },

    async fetchMotelById(id) {
      try {
        const token = localStorage.getItem("token"); // Lấy token nếu có
        const response = await axios.get(`http://localhost:8081/motel/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        try {
          const response = await axios.get(
            "https://provinces.open-api.vn/api/?depth=1"
          );
          this.provinces = response.data;
        } catch (error) {
          console.error("Error fetching provinces:", error);
        }

        const motelData = response.data;
        console.log(motelData);
        // Gán dữ liệu vào formData và selectedLocations

        this.formData = {
          userId: motelData.owner.id || "",
          id: motelData.id || "",
          title: motelData.title || "",
          type: motelData.type || "",
          houseNumber: motelData.houseNumber || "",
          street: motelData.street || "",
          area: motelData.area || "",
          price: motelData.price || "",
          interior: motelData.interior || "",
          maxPeople: motelData.maxPeople || "",
          detail: motelData.detail || "",
        };

        this.selectedLocations = {
          province: { name: motelData.province } || "",
          district: { name: motelData.district } || "",
          ward: { name: motelData.ward } || "",
        };

        // Nếu có ảnh, bạn cần xử lý ảnh upload nếu muốn hiển thị
        let code;
        for (let item of this.provinces) {
          if (item.name == motelData.province) {
            code = item.code;
            break;
          }
        }
        try {
          const response = await axios.get(
            `https://provinces.open-api.vn/api/p/${code}?depth=2`
          );
          this.districts = response.data.districts;
        } catch (error) {
          console.error("Error fetching districts:", error);
          this.districts = [];
        }
        this.Images = motelData.filesDTO || [];
        this.onProvinceChange(motelData.province);
        this.onDistrictChange(motelData.district);
      } catch (error) {
        console.error("Error fetching motel data:", error);
        alert("Không thể tải thông tin của trọ!");
      }
    },
    async handleDeleteImg(fileId) {
      this.listIdDelete.push(fileId);
      this.Images = this.Images.filter((preview) => preview.id !== fileId);
    },
    async submitForm() {
      // Reset trạng thái
      this.success = false;

      const formEdit = new FormData();

      // Append location data
      formEdit.append("province", this.selectedLocations.province.name);
      formEdit.append("district", this.selectedLocations.district.name);
      formEdit.append("ward", this.selectedLocations.ward.name);
      formEdit.append("listIdDelete", this.listIdDelete);
      // Append form data
      Object.keys(this.formData).forEach((key) => {
        formEdit.append(key, this.formData[key]);
      });

      // Append images
      this.uploadedImages.forEach((file, index) => {
        formEdit.append(`files[${index}]`, file);
      });

      const token = localStorage.getItem("token");

      try {
        this.loading = true; // Hiển thị vòng xoay
        await axios.post("http://localhost:8081/editMotel", formEdit, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${token}`,
          },
        });
        this.success = true; // Hiển thị dấu tích thành công
        // this.resetForm();
      } catch (error) {
        console.error("Error:", error);
        alert(error.response?.data?.message || "Đã xảy ra lỗi khi sửa bài!");
      } finally {
        this.loading = false; // Ẩn vòng xoay
        setTimeout(() => (this.success = false), 3000); // Ẩn dấu tích sau 3s
      }
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
.remove-button {
  position: absolute;
  top: -8px;
  right: -8px;
  background-color: #ff4444;
  color: white;
  border: none;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  line-height: 20px;
  text-align: center;
  cursor: pointer;
  padding: 0;
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