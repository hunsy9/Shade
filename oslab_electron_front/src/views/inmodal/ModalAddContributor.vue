<template>
  <div>

  </div>
  <ModalPrivileges/>
</template>

<script>
import ModalPrivileges from '@/components/inorganization/modal/contributor/ModalPrivileges.vue'

export default {
  name: 'ModalAddContributor',
  data() {
    return {
      addContributorEmail: '',
    }
  },
  components: {
    ModalPrivileges,
  }
}
</script>