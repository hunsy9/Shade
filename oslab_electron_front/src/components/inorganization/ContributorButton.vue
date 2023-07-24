<template>
  <div class="cont" :class="{click:isClick && mode == 2}" @click="inContributors">
    <img src="@/assets/contributors.png">
    <span>Contributors</span>
  </div>
</template>
      
<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { terminalState } from '../../store/inOrganization';


export default {
  name: 'ContributorButton', 
  data() {
    return{
        isClick: false,
    }
  },
  computed: {
    ...mapState('inOrganization',{
      mode: state => state.mode,
      exitStatus : state => state.exitShellState
    })
  },
  methods: {
    ...mapMutations('contributorInfo',['setContributors']),
    ...mapMutations('inOrganization',['selectContributors']),
    ...mapActions('inOrganization',['Contributors']),
    async inContributors(){
      if(this.exitStatus == terminalState.OPENED){
        alert("The terminal did not shut down\n\n" +
            "Shut down the terminal first!")
        return
      }
      const data = await this.Contributors()
      if(data.admin_email.user_id){
        this.setContributors(data)
        this.selectContributors()
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