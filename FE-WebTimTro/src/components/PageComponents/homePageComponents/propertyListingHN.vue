<template>
  <div class="property-listing">
    <h2 class="title">Cho thuê tại Hà Nội</h2>
    <div class="row property-list">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
        <ul class="property-items">
          <li
            class="property-item"
            v-for="(property, index) in properties"
            :key="index"
          >
            <img
              class="lozad"
              :src="
                property.imageUrl ||
                'https://via.placeholder.com/300x200?text=No+Image'
              "
              alt=""
              data-loaded="true"
            />
            <h2 class="prop-title">
              <a
                class="link-overlay"
                @click="toDetailsPage(property.id, property.title)"
                >{{ property.title || property.detail }}</a
              >
            </h2>
            <ul class="prop-attr">
              <li>
                <i class="fa fa-chart-area"></i
                >{{ property.area || "N/A" }} m<sup>2</sup>
              </li>
            </ul>
            <div class="price">
              {{ formatVND(property.price) || "Liên hệ" }}
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { removeVietnameseTones } from "@/utils/removeVnTone";
export default {
  name: "PropertyListing",
  data() {
    return {
      properties: [], // Danh sách sẽ được lấy từ API
    };
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
    formatVND(amount) {
      if (typeof amount !== "number") {
        return "Invalid input";
      }
      return amount.toLocaleString("vi-VN", { style: "decimal" }) + " VND";
    },
    async fetchProperties() {
      try {
        const response = await axios.get("http://localhost:8081/search", {
          params: {
            province: "Hà Nội",
          },
        });

        // Chuẩn hóa dữ liệu từ API
        this.properties = response.data.map((property) => ({
          id: property.id,
          title: property.title,
          imageUrl: property.filesDTO?.[0]?.fileUrl || "",
          area: property.area || "N/A",
          bedrooms: property.bedrooms || "N/A",
          bathrooms: property.bathrooms || "N/A",
          price: property.price || "Liên hệ",
        }));
      } catch (error) {
        console.error("Error fetching properties:", error);
      }
    },
  },
  mounted() {
    this.fetchProperties(); // Tự động gọi API khi component được mount
  },
};
</script>

<style scoped>
</style>

