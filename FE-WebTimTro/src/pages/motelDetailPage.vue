<template>
  <div class="hpc-navbar">
    <div class="container">
      <div class="row">
        <navbar />
        <NavbarMobileArticle />
      </div>
    </div>
  </div>

  <MainDetails />
  <Footer></Footer>
</template>

<script>
import MainDetails from "@/components/PageComponents/detailsMotelPageComponents/mainDetails.vue";
import NavbarMobileArticle from "@/components/PageComponents/detailsMotelPageComponents/navbarMobileArticle.vue";
import Footer from "@/components/PageComponents/homePageComponents/footer.vue";
import navbar from "@/components/PageComponents/homePageComponents/navbar.vue";

export default {
  name: "MotelDetail",
  components: {
    NavbarMobileArticle,
    MainDetails,
    Footer,
    navbar,
  },
  data() {
    return {};
  },
  mounted() {},
};
</script>

