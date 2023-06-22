<template>
  <span class="oname">
    {{ orgname }}
  </span>
</template>
    
<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers('inOrganization')

export default {
  name: 'OrganizationName',  
  computed: {
    ...mapState({
      orgname: state => state.organName
    }),
  },
}
</script>

<style scoped>
.oname{
  position: absolute;
  top: 3rem;
  left: 3rem;
  color: white;
}
</style>