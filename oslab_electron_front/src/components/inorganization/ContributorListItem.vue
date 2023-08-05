<template>
  <div class="listframe">
    <div class="admin">
      <img src="@/assets/contriAdmin.png">
      <span>{{ admin_email }}</span>
      <button v-if="isAdmin">Mandate</button>
    </div>
    <div v-for="(item, index) in contributors" :key="index" class="normal">
      <span>{{ item.contributor_email }}</span>
      <button v-if="isAdmin" @click="deleteContributor(item.user_id, item.contributor_email)">Delete</button>
      <button v-if="isAdmin" @click="$emit('openPrivilegeModal',item.user_id, item.contributor_email)">Privileges</button>
    </div>
    <template v-if="isAdmin">
      <div v-for="(it,index) in pending_contributors" :key="index" class="waitingContributor">
        <span>{{ it.contributor_email }}</span>
        <img class="x" src="@/assets/testFail.png" @click="deleteContributor(it.user_id, it.contributor_email)"/>
        <img class="envelop" src="@/assets/envelop-unscreen.gif"/>
      </div>
    </template>
  </div>
</template>
    
<script>
import { mapState } from "vuex";
import {DeleteState} from "@/store/inOrganization";

export default {
  name: 'ContributorListItem',
  data(){
    return{
      DeleteState: DeleteState.DELETECONTRIBUTOR,
    }
  },
  computed:{
    ...mapState('inOrganization',{
      org_id:state=>state.organId,

    }),
    ...mapState('inOrganization', {
      isAdmin: state => state.isAdmin
    }),
    ...mapState('contributorInfo', {
      admin_email: state => state.admin_contributor.contributor_email,
      contributors: state => state.contributors,
      pending_contributors: state => state.pending_contributors
    }),
  },
  methods:{
    deleteContributor(user_id, contributor_email){
      const dto = {
        org_id: this.org_id,
        user_id: user_id,
        contributor_email: contributor_email
      }
      this.$emit('openDeleteModal', this.DeleteState, dto)
    }
  }
}
</script>

<style scoped>
.waitingContributor{
  color: white;
  margin-left: 5%;
  margin-right: auto;
  background: linear-gradient(-45deg,#91BCE4, #23a6d5, #3ab69a);
  background-size: 400% 400%;
  width: 90%;
  border-radius: 0.4rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  margin-top: 1rem;
  animation: gradient 4s ease infinite;
  animation: fadeInUp 1s ease backwards;
}
@keyframes gradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}
img{
  position: absolute;
  z-index: 1;
  width: 2.4rem;
}
.listframe{
  display: block;
}
span{
  line-height: 300%;
  padding-left: 5rem;
}
.admin{
  color: white;
  margin-left: 5%;
  margin-right: auto;
  background-color: #473d5bbe;
  width: 90%;
  border-radius: 0.4rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  animation: fadeInUp 1s ease backwards;
}
.normal{
  color: white;
  margin-top: 1rem;
  margin-left: 5%;
  margin-right: auto;
  background-color: #4B4B4B95;
  width: 90%;
  border-radius: 0.4rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  animation: fadeInUp 1s ease backwards;
}
button{  
  float: right;
  background-color: #989898c2;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
  color: white;
  padding: 0.1rem 1.5rem;
  margin-top: 1rem;
  margin-right: 1rem;
}
.envelop{
  position: absolute;
  margin-top: 5px;
  right: 4rem;
}
.x{
  width: 10px;
  height: 10px;
  margin-top: 5px;
  right: 3.5rem;
}
@keyframes fadeInUp{
  0%{transform:translate(0px, 7px); opacity: 0;}
  100%{transform:translate(0px, 0); opacity: 1;}
}
</style>