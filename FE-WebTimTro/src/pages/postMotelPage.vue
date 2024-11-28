<template>
  <div>
    <!-- NAVBAR -->
    <div class="hpc-navbar">
      <div class="container">
        <div class="row">
          <navbar />
          <cls-navbarmobile />
        </div>
      </div>
    </div>
    <!-- END NAVBAR -->

    <!-- REVIEW WARD -->
    <div class="col-12">
      <div class="container">
        <div class="widget">
          <form-post></form-post>
        </div>
      </div>
    </div>
    <!-- FOOTER -->
    <div class="card-footer">
      <cls-footer />
    </div>
    <!-- END FOOTER -->
  </div>
</template>

<script>
import Footer from "@/components/PageComponents/homePageComponents/footer.vue";
import navbar from "@/components/PageComponents/homePageComponents/navbar.vue";
import NavbarMobile from "@/components/PageComponents/homePageComponents/navbarMobile.vue";
import formPost from "@/components/PageComponents/postMotelPageComponents/formPost.vue";

export default {
  name: "postMotelPage",
  components: {
    "cls-navbarmobile": NavbarMobile,
    "cls-footer": Footer,
    "form-post": formPost,
    navbar,
  },
};
</script>