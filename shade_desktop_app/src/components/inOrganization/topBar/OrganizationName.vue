<template>
  <span class="oname" :class="{'oname-shifted': shiftState == true}">
    {{ orgname }}
  </span>
</template>
<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers('inOrganization')

export default {
  name: 'OrganizationName',
  props:{
    shiftState: Boolean
  },
  computed: {
    ...mapState({
      orgname: state => state.organName.toUpperCase()+"_organization"
    }),
  },
}
</script>

<style scoped>
.oname{
  position: absolute;
  top: 0.7rem;
  left: 3rem;
  font-size: 0.85rem;
  color: var(--ModeTextColor);
}
.oname-shifted{
  position: absolute;
  top: 0.7rem;
  left: 6rem;
  font-size: 0.85rem;
  color: var(--ModeTextColor);
}
</style>