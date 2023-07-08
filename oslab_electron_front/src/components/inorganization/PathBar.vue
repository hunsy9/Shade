<template>
  <span class="frame">
    <span>
      {{ proj }} 
    </span>
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
  line-height: 620%;
  padding-left: 3rem;
}
span{
  color: white;
  font-size: 1rem;
}
.greyspan{
  color: #8E8E8E;
}
</style>