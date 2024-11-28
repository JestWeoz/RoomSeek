<template>
  <div>
    <!-- Breadcrumb -->
    <div class="container">
      <div class="row">
        <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <a href="/search">Tìm Thuê</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">
              {{ motel?.title || "Đang tải..." }}
            </li>
          </ol>
        </nav>
      </div>
    </div>

    <!-- Page Content -->
    <div v-if="motel" class="container">
      <div class="row">
        <!-- Main Content -->
        <div class="col-lg-8">
          <div class="main">
            <!-- Gallery -->
            <div
              id="carouselExampleDark"
              class="carousel carousel-dark slide"
              data-bs-ride="carousel"
            >
              <div class="carousel-indicators">
                <button
                  v-for="(file, index) in motel.filesDTO"
                  :key="index"
                  type="button"
                  :data-bs-target="'#carouselExampleDark'"
                  :data-bs-slide-to="index"
                  :class="{ active: index === 0 }"
                  :aria-current="index === 0"
                ></button>
              </div>
              <div class="carousel-inner">
                <div
                  v-for="(file, index) in motel.filesDTO"
                  :key="index"
                  class="carousel-item"
                  :class="{ active: index === 0 }"
                >
                  <img
                    :src="file.fileUrl"
                    class="d-block w-100 carousel-image"
                    alt="Image"
                  />
                </div>
              </div>
              <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselExampleDark"
                data-bs-slide="prev"
              >
                <span
                  class="carousel-control-prev-icon"
                  aria-hidden="true"
                ></span>
                <span class="visually-hidden">Previous</span>
              </button>
              <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselExampleDark"
                data-bs-slide="next"
              >
                <span
                  class="carousel-control-next-icon"
                  aria-hidden="true"
                ></span>
                <span class="visually-hidden">Next</span>
              </button>
            </div>

            <!-- Article Header -->
            <div class="header-article my-4">
              <h1 class="fw-bold">{{ motel.title }}</h1>
              <p class="text-muted">{{ motel.address }}</p>
              <div class="price-info my-5">
                <h2 class="text-danger fw-bold">
                  {{ formatVND(motel.price) }}
                </h2>
              </div>
            </div>

            <!-- Property Info -->
            <div class="property-info">
              <h2 class="fw-bold">Thông tin chính</h2>
              <div class="row g-3">
                <ul class="col-6 list-unstyled">
                  <li><strong>Giá:</strong> {{ formatVND(motel.price) }}</li>
                  <li>
                    <strong>Diện tích sử dụng:</strong> {{ motel.area }} m<sup
                      >2</sup
                    >
                  </li>
                  <li>
                    <strong>Ngày đăng:</strong> {{ formatDate(motel.createAt) }}
                  </li>
                  <li><strong>Mã BĐS:</strong> {{ motel.id }}</li>
                </ul>
                <ul class="col-6 list-unstyled">
                  <li>
                    <strong>Số Người Tối Đa:</strong> {{ motel.maxPeople }}
                  </li>
                  <li><strong>Nội Thất:</strong> {{ motel.interior }}</li>
                  <li><strong>Dạng nhà trọ:</strong> {{ motel.type }}</li>
                </ul>
              </div>
            </div>

            <!-- Description -->
            <div class="prop-info-content mt-4">
              <h2 class="fw-bold">Mô tả chi tiết</h2>
              <p>{{ motel.detail }}</p>
            </div>
          </div>
        </div>

        <!-- Sidebar -->
        <div class="col-lg-4">
          <div class="side-bar">
            <div class="agent-info bg-light p-3 rounded">
              <img
                src="https://mogi.vn/content/images/avatar.png"
                alt="Agent Avatar"
                class="img-thumbnail rounded-circle mb-3"
                style="width: 80px; height: 80px"
              />
              <h5>{{ motel.owner?.fullName }}</h5>
              <p class="text-muted mb-1">
                Số Điện Thoại {{ motel.owner?.phone }}
              </p>
              <a
                :href="`https://zalo.me/${motel.owner?.phone}`"
                target="_blank"
                class="btn btn-primary btn-sm d-block my-2"
              >
                Liên Hệ Qua Zalo
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Similar Properties -->
    <div class="SimilarUrl bg-light py-4">
      <div class="container">
        <h2 class="fw-bold mb-4">Các phòng trọ gần đó</h2>
        <div class="row g-3">
          <div
            v-for="(nearbyMotel, index) in motels"
            :key="index"
            class="col-6 col-md-4 col-lg-3"
          >
            <div class="card">
              <!-- Hình ảnh -->
              <img
                v-if="nearbyMotel.filesDTO?.length > 0"
                :src="nearbyMotel.filesDTO[0].fileUrl"
                class="card-img-top"
                alt="Property"
              />
              <img
                v-else
                src="https://via.placeholder.com/300x200?text=No+Image"
                class="card-img-top"
                alt="Property"
              />

              <!-- Nội dung -->
              <div class="card-body">
                <h5 class="card-title text-danger">
                  {{ formatVND(nearbyMotel.price) }}
                </h5>
                <p class="card-text">
                  <a
                    :href="`/motel/${nearbyMotel.title.replace(/ /g, '-')}-${
                      nearbyMotel.id
                    }`"
                  >
                    {{ nearbyMotel.title }}
                  </a>
                </p>
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
import { removeVietnameseTones } from "@/utils/removeVnTone";
export default {
  name: "Article",
  data() {
    return {
      motels: [],
      motel: [], // Lưu dữ liệu từ API
    };
  },
  methods: {
    formatDate(date) {
      if (!date) return null;

      const options = { day: "2-digit", month: "2-digit", year: "numeric" };
      return new Date(date).toLocaleDateString("vi-VN", options);
    },
    formatVND(amount) {
      if (typeof amount !== "number") {
        return "Invalid input";
      }
      return amount.toLocaleString("vi-VN", { style: "decimal" }) + " VND";
    },
    async fetchMotelData(id) {
      try {
        const response = await axios.get(`http://localhost:8081/motel/${id}`);
        this.motel = response.data;
        console.log(this.motel);
      } catch (error) {
        console.error("Error fetching motel data:", error);
      }
    },
    async fetchMotelInThisDistrict() {
      if (!this.motel.district) {
        console.error(
          "District chưa có giá trị. Đợi dữ liệu tải từ fetchMotelData."
        );
        return;
      }

      try {
        const response = await axios.get("http://localhost:8081/search", {
          params: {
            district: this.motel.district,
          },
        });

        // Lọc bỏ nhà trọ hiện tại
        this.motels = response.data.filter(
          (nearbyMotel) => nearbyMotel.id !== this.motel.id
        );

        console.log("Motels gần đó:", this.motels);
      } catch (error) {
        console.error("Error fetching motel data:", error);
      }
    },
  },
  mounted() {
    const motelId = this.$route.params.id.split("-").pop(); // Lấy ID từ URL

    if (motelId) {
      this.fetchMotelData(motelId).then(() => {
        // Sau khi fetch dữ liệu phòng trọ, gọi fetchMotelInThisDistrict
        this.fetchMotelInThisDistrict();
      });
    } else {
      console.error("Motel ID không hợp lệ");
    }
  },
};
</script>
<style scoped>
.card-img-top {
  object-fit: cover; /* Giữ tỷ lệ hình ảnh */
  width: 100%; /* Hình ảnh chiếm toàn bộ chiều ngang */
  height: 200px; /* Chiều cao cố định */
  border-radius: 4px; /* Tùy chọn: bo tròn góc nếu cần */
}

.carousel-image {
  max-height: 400px; /* Giới hạn chiều cao ảnh */
  object-fit: cover; /* Căn chỉnh ảnh để phù hợp với khung */
  object-position: cover; /* Căn giữa nội dung ảnh */
}
</style>
