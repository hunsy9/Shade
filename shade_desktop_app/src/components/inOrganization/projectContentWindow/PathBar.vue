<template>
  <span class="frame">
    <img src="@/assets/file-branch.png" width="15" v-if="getSelectedCatl1!=null"/>
    <span class="greyspan">
      {{ getSelectedCatl1 }}
    </span>
    <span class="greyspan">
      {{ getSelectedCatl2 }}
    </span>
  </span>
</template>
    
<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState, mapGetters } = createNamespacedHelpers('inOrganization')

export default {
  name: 'PathBar',
  computed: {
    ...mapState({
      proj: state => state.selected_proj,
      categ_l1: state => state.selected_categ_l1,
      categ_l2: state => state.selected_categ_l2
    }),
    ...mapGetters(['getSelectedCatl1', 'getSelectedCatl2'])
  }
}
</script>

<style scoped>
.frame{
  display: flex;
  align-items: center;
  padding-left: 3rem;
}
span{
  color: var(--ModeTextColor);
  font-size: 1rem;
}
.greyspan{
  margin-left: 0.2rem;
  color: #8E8E8E;
}
@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>