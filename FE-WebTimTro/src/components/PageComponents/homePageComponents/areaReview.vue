<template>
  <div class="ward-review">
    <h2 class="title">Khu vực nhiều người xem</h2>
    <div class="row ward-review-list" id="area-review">
      <div
        class="col-xs-12 col-sm-12 col-md-12 col-lg-12"
        v-for="(item, index) in areas"
        :key="index"
      >
        <div class="ward-review-item">
          <img :src="item.image" :alt="item.alt" />
          <div class="caption">
            Khám phá các nhà trọ ở
            <span>{{ item.name }}</span>
          </div>
          <a class="link-overlay" @click="toTorenPage(item.name)">
            <span>Xem ngay</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script>
import $ from "jquery";

export default {
  name: "AreaReview",
  data() {
    return {
      areas: [
        {
          image: "img/ho-chi-minh-city.jpg",
          name: "Nam Từ Liêm",
          alt: "",
        },
        { image: "img/hanoi-city.jpg", name: "Bắc Từ Liêm", alt: "" },
        { image: "img/ho-chi-minh-city.jpg", name: "Hồ Tây", alt: "" },
        { image: "img/ho-chi-minh-city.jpg", name: "Hà Đông", alt: "" },
      ],
    };
  },
  methods: {
    toTorenPage(district) {
      this.$router.push({
        path: "/search",
        query: { district: "Quận " + district, province: "Thành phố Hà Nội" },
      });
    },
    initSlick() {
      $("#area-review").slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        dots: false,
        infinite: true,
        arrows: false,
        responsive: [
          {
            breakpoint: 990,
            settings: {
              slidesToShow: 2,
            },
          },
          {
            breakpoint: 630,
            settings: {
              slidesToShow: 1,
            },
          },
        ],
      });
    },
  },
  mounted() {
    this.initSlick();
  },
};
</script>
  
