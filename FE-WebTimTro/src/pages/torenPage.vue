<template>
  <!-- NAVBAR -->
  <div class="hpc-navbar">
    <div class="container">
      <div class="row">
        <navbar />
        <cls-navbarmobile />
      </div>
    </div>
  </div>

  <!-- SEARCH NAVBAR -->
  <cls-search-navbar />

  <!-- PAGE CONTENT -->
  <div class="mg-page-content">
    <div class="container">
      <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
          <div id="page-header">
            <h1 class="page-title">
              Thuê phòng trọ 2024 giá rẻ tại Việt Nam, giá thuê mới nhất
            </h1>
          </div>
          <div class="property-list">
            <cls-result-list />
            <cls-props />
            <cls-pagination />
          </div>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
          <!-- <cls-banner-widget /> -->
          <cls-kinds-of-house />
        </div>
      </div>
    </div>
  </div>
  <!-- FOOTER -->
  <cls-footer />
</template>
  
  <script>
import Footer from "@/components/PageComponents/homePageComponents/footer.vue";
import navbarMobileVue from "@/components/PageComponents/homePageComponents/navbarMobile.vue";
import BannerWidget from "@/components/PageComponents/torenPageComponents/bannerWidget.vue";
import CityArea from "@/components/PageComponents/torenPageComponents/cityArea.vue";
import KindsOfHouse from "@/components/PageComponents/torenPageComponents/fillerSearch.vue";
import NavbarToren from "@/components/PageComponents/torenPageComponents/navbarToren.vue";
import Pagination from "@/components/PageComponents/torenPageComponents/pagination.vue";
import Props from "@/components/PageComponents/torenPageComponents/props.vue";
import ResultList from "@/components/PageComponents/torenPageComponents/resultList.vue";
import SearchNavbar from "@/components/PageComponents/torenPageComponents/searchNavbar.vue";
import navbar from "@/components/PageComponents/homePageComponents/navbar.vue";

export default {
  name: "torenPage",
  components: {
    clsFooter: Footer,
    clsNavbar: NavbarToren,
    clsNavbarmobile: navbarMobileVue,
    clsSearchNavbar: SearchNavbar,
    clsResultList: ResultList,
    clsProps: Props,
    clsPagination: Pagination,
    clsBannerWidget: BannerWidget,
    clsKindsOfHouse: KindsOfHouse,
    clsCityArea: CityArea,
    navbar,
  },
  data() {
    return {
      message: "vooc code toi it thoi cac bo F12 =)))",
    };
  },
};
</script>
  
  
  