<template>
    <button @click="Refetch">
        Refetch
    </button>
</template>

<script>
import {RefetchState} from "@/store/inOrganization";
import { mapActions, mapMutations } from "vuex";
import { mapGetters } from "vuex";

export default {
  name: 'RefetchBtn',
  props: {
    RefetchState: RefetchState,
  },
  computed: {
  },
  methods: {
    ...mapActions('login', ['tryGetOrg']),
    ...mapMutations('organizationInfo', ['setOrgInfo']),
    ...mapGetters('login', ['getUserID']),

    async Refetch() {
      const user_id = this.getUserID()
      const org_data = await this.tryGetOrg(user_id)
      switch (this.RefetchState) {
        case RefetchState.BEFOREORG:
          if (!org_data) {
            console.log("org를 가져오지 못했습니다.")
            return 
          }
          this.setOrgInfo(org_data)
          break
        default:
          break
      }
    }
  }
}
</script>
