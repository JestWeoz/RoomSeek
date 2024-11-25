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
        <div v-for="room in rooms" :key="room.id" class="card mb-3">
          <div class="card-body">
            <h5 class="card-title">{{ room.title }}</h5>
            <p class="card-text">{{ room.description }}</p>
            <p class="card-text">
              <small class="text-muted">Giá: {{ room.price }} VNĐ</small>
            </p>
            <button class="btn btn-warning me-2" @click="editRoom(room.id)">
              Sửa
            </button>
            <button class="btn btn-danger" @click="deleteRoom(room.id)">
              Xoá
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
    
    <script>
import navbarInforAccount from "@/components/PageComponents/accountPageComponents/navbarInforAccount.vue";
export default {
  name: "ManageMotel",
  components: {
    navbarInforAccount,
  },
  methods: {
    toInforAccount() {
      this.$router.push("/account/thong-tin-tai-khoan");
    },
    toPostPage() {
      this.$router.push("/account/dang-tin");
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
    