<template>
  <div class="cont" :class="{click:isClick && mode == 2}" @click="inContributors">
    <img src="@/assets/contributors.png">
    <span>Contributors</span>
  </div>
</template>
      
<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState, mapActions } = createNamespacedHelpers('inOrganization')
const { mapMutations } = createNamespacedHelpers('contributorInfo')

export default {
  name: 'ContributorButton', 
  data() {
    return{
        isClick: false,
    }
  },
  computed: {
    ...mapState({
      mode: state => state.mode
    })
  },
  methods: {
    ...mapMutations(['setContributors']),
    ...mapActions(['Contributors']),
    async inContributors(){
      const data = await this.Contributors()
      if(data){
        this.setContributors(data)
      }
      this.isClick = true
    }
  }
}
</script>

<style scoped>
.cont{
  cursor:pointer;
  padding-bottom: 0.7rem;
}
.click{
  background-color: #313131;
}
img{
  margin-top: 0.7rem;
  margin-left: 1rem;
  width: 1.5rem;
}
span{
  margin-left: 1rem;
  margin-bottom: 0.7rem;
  color: white;
}
</style>