<template>
    <img src="@/assets/refresh.png" @click="Refetch">
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

<style scoped>
img{
  right: 1rem;
  bottom: 1rem;
  width: 1.2rem;
  height: 1.2rem;
  position: fixed;
  cursor: pointer;
}
.content:hover{
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  background-color: white;
}
</style>