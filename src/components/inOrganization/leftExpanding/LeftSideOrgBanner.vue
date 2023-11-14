<template>
  <div class="shadeIconWrapper">
    <img class="icon" src="@/assets/shadeIcon.png" width="60" height="60"/>
    <div class="orgName">{{org_name}}</div>
  </div>

</template>
<script>
import {createNamespacedHelpers} from "vuex";

const { mapState } = createNamespacedHelpers('inOrganization')

export default {
  name: 'LeftSideOrgBanner',
  computed: {
    ...mapState({
      org_name: state => state.organName.toUpperCase()
    }),
  },
}
</script>

<style scoped>
.shadeIconWrapper{
  text-align: center;
  width: 4rem;
  height: 7rem;
  border-radius: 0.5rem;
  margin-top: 7rem;
  margin-left: auto;
  margin-right: auto;
}
.orgName{
  text-align: center;
  color: var(--ModeTextColor);
  font-weight: 3;
  margin-top: 1.5rem;
}
</style>