<template>
  <!-- NAVBAR -->
  <div class="hpc-navbar">
    <div class="container">
      <div class="row">
        <cls-navbar />
        <cls-navbarmobile />
      </div>
    </div>
  </div>
  <!-- END NAVBAR -->

  <!-- BANNER HOME SEARCH -->
  <div class="row">
    <div class="bg-page-content">
      <cls-search />
    </div>
  </div>
  <!-- END BANNER HOME SEARCH -->

  <!-- REVIEW WARD -->
  <div class="col-12">
    <div class="container">
      <!-- REVIEW WARD -->
      <area-review />
      <!-- END REVIEW WARD -->

      <!-- PROPERTY LIST -->
      <cls-property-listing />
      <!-- END PROPERTY LIST -->

      <!-- BANNER CONTENT -->
      <cls-banner />
      <!-- END BANNER CONTENT -->
      <propertyHCM></propertyHCM>
      <!-- PROPERTY LIST -->
      <!-- END PROPERTY LIST -->
    </div>
  </div>
  <!-- FOOTER -->
  <cls-footer />
  <!-- END FOOTER -->
</template>
  
  <script>
import search from "@/components/PageComponents/homePageComponents/search.vue";
import areaReview from "@/components/PageComponents/homePageComponents/areaReview.vue";
import banner from "@/components/PageComponents/homePageComponents/banner.vue";
import footer from "@/components/PageComponents/homePageComponents/footer.vue";
import navbar from "@/components/PageComponents/homePageComponents/navbar.vue";
import navbarMobile from "@/components/PageComponents/homePageComponents/navbarMobile.vue";
import propertyListing from "@/components/PageComponents/homePageComponents/propertyListingHN.vue";
import propertyListingHCM from "@/components/PageComponents/homePageComponents/propertyListingHCM.vue";
export default {
  name: "homePages",
  components: {
    "cls-search": search,
    "cls-navbar": navbar,
    "cls-navbarmobile": navbarMobile,
    "area-review": areaReview,
    "cls-property-listing": propertyListing,
    "cls-footer": footer,
    "cls-banner": banner,
    propertyHCM: propertyListingHCM,
  },
  data() {
    return {};
  },
  mounted() {
    // Bạn có thể thêm các script cần thiết ở đây
  },
};
</script>