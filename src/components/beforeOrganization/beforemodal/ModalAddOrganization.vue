<template>
  <div class="zidx">
    <main>
      <div class="modalframe">
        <div class="header">Create Organization </div>
        <div class="namebar">
          <span class="text1">Organization Name</span>
          <input class="inpname" type="text" v-model="org_name">
          <span class="dummyspan" v-if="is_valid==true">dummy</span>
          <span class="orgspan" @click="create" v-if="is_valid==false"> Please Enter the Organization Name !</span>
        </div>
        <div class="btn">
          <button class="cancelbtn" @click="closeNewOrganModal"> Cancel </button>
          <button class="createbtn" @click="create"> Create </button>
        </div>
      </div>
    </main>
  </div>
</template>

<script>

import { mapActions } from "vuex";
import { mapGetters } from "vuex";
import { mapMutations } from "vuex";

export default {
  name: 'ModalAddOrganization',
  data() {
    return {
      org_name: "",
      is_valid: true,
    }
  },
  methods: {
    async create(){
      this.org_name = this.org_name.trim()
      if (this.org_name.length == 0) {
        this.is_valid = false
        return
      }
      this.is_valid = true
      //api 추가 후 종료
      
      const user_id = this.getUserID()
      const param1 = {
        org_name: this.org_name,
        org_admin_id: user_id
      }
      const org_id = await this.tryAddMaster(param1)

      const param2 = {
        user_id: user_id,
        org_id: org_id,
      }
      const adduser_data = await this.tryAddOrgUser(param2)
      if (!adduser_data) {
        console.log("add Org fail")
        return
      }

      const param3 = {
        org_id: org_id,
        contributor_id: user_id,
        contributor_email: this.getUserEmail()
      }
      const addcont_data = await this.tryAddOrgCont(param3)
      if (!addcont_data) {
        console.log("add OrgCont fail")
        return
      }

      const org_data = await this.tryGetOrg(user_id)
      if (!org_data) {
        console.log("org를 가져오지 못했습니다.")
        return 
      }

      this.setOrgInfo(org_data)

      this.org_name = ""
      this.$emit("closeModalAddOrganization")
    },
    closeNewOrganModal() {
      this.is_auth = false
      this.$emit("closeModalAddOrganization")
    },
    ...mapActions('addorganization', ['tryAddMaster', 'tryAddOrgUser', 'tryAddOrgCont']),
    ...mapActions('login', ['tryGetOrg']),
    ...mapGetters('login' , ['getUserID', 'getUserEmail']),
    ...mapMutations('organizationInfo', ['setOrgInfo'])
  }
}
</script>

<style scoped>

.zidx{
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
}
.zidx > main {
  display: block;
  margin: 0 auto;
  margin-top: 7rem;
  width: 625px;
  height: 375px;
  background-color: #242424;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  animation: fade-in-down 0.5s;
}
.modalframe{
  width: 100%;
  height: 100%;
  animation: fade-in-down 0.5s;
}
.header{
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
  padding-top: 2rem;
  color: white;
  text-align: center;
}
.namebar{
  margin-top: 7.5rem;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
}
.text1{
  padding-left: 5rem;
  /* margin-left: calc(10rem); */
  color: white;
}
.inpname{
  color: white;
  position: relative;
  left: 0.5rem;
  text-align: center;
  background-color: #565656;
  width: 45%;
  height: 1.4rem;
  border: 1px;
}
.dummyspan {
  position: relative;
  left: 22.5rem;
  display: block;
  color: #242424;
  font-size: 9px;
}
.orgspan {
  position: relative;
  left: 22.5rem;
  display: block;
  color: tomato;
  font-size: 9px;
}
.cancelbtn {
  position: relative;
  left: 1rem;
  top: 8.7rem;
  color: white;
  background-color: #989898cf;
  width: 6rem;
  height: 1.4rem;
  border-radius: 0.4rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.createbtn{
  position: relative;
  left: 25.3rem;
  top: 8.7rem;
  color: white;
  padding: 0px;
  background-color: #989898cf;
  width: 7rem;
  height: 1.4rem;
  border-radius: 0.4rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.fade-in-down {
  animation: fade-in-down 2s ease;
  animation-fill-mode: forwards;
}
@keyframes fade-in-down {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>