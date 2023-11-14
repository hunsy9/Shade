<template>
  <button class="button" @click="$emit('openModalAddServer', 'Add New Server')" v-if="categ_l2 && categ_l1 && proj">
    Add New Server
  </button>
</template>
    
<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers('inOrganization')

export default {
  name: 'NewServerButton',
  computed: {
    ...mapState({
      proj: state => state.selected_proj,
      categ_l1: state => state.selected_categ_l1,
      categ_l2: state => state.selected_categ_l2
    }),
  }
}
</script>

<style scoped>
.button{
  position: absolute;
  //top: 7%;
    top: 5rem;
  right: 7%;
  color: var(--ModeTextColor);
  display: block;
  margin: 0 auto;
  background-color: var(--ModeBtnColor);
  padding-left: 1.8rem;
  padding-right: 1.8rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
  animation: fade-in 1s linear;
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