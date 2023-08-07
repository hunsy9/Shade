<template>
  <div class="zidx">
    <main>
      <div class="modalframe">
        <div class="header">
          Add New Project
        </div>
        <div class="float">
          <span>
            New Project Name
          </span>
          <input type="text" v-model="ProjectName">
        </div>
      </div>
      <div class="btnWrapper">
        <button @click="$emit('closeNewProjectModal')" class="cancel">Cancel</button>
        <button @click="addProject(ProjectName)" class="saveBtn">Save</button>
      </div>
    </main>
  </div>
</template>

<script>
import {createNamespacedHelpers} from 'vuex'
const { mapState, mapActions } = createNamespacedHelpers('inOrganization')

export default {
  name: 'ModalAddProject',
  data() {
    return {
      ProjectName: '',
    }
  },
  computed: {
    ...mapState({
      organid: state => state.organId
    })
  },
  methods: {
    ...mapActions(["addNewProject"]),
    async addProject(pName){
      const data = {
        org_id: parseInt(this.organid),
        project_name: pName
      }
      console.log("modal" + data)
      const res = await this.addNewProject(data)
      console.log("modal res: " + res)
      this.$emit('closeNewProjectModal')
    }
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
  width: 500px;
  height: 160px;
  background-color: #242424;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  animation: fade-in-down 0.5s;
}
.modalframe{
  margin-top: 15rem;
  width: 100%;
  height: 100%;
}
.header{
  padding-top: 1.5rem;
  text-align: center;
  color: white;
}
.float{
  margin-top: 1.7rem;
  float: right;
}
span{
  font-size: 0.8rem;
  color: white;
  padding-right: 1rem;
}
input{
  margin-right: 4rem;
  font-size: 0.9rem;
  color: white;
  padding: 0.2rem 0;
  text-align: center;
  background-color: #2f2f2f;
  width: 16rem;
  border: none;
  outline: none;
}
input::-webkit-inner-spin-button {
  appearance: none;
  -moz-appearance: none;
  -webkit-appearance: none;
}
.btnWrapper {
  top: -30px;
  width: 100%;
  display: flex;
  text-align: center;
  position: relative;
}
button {
  background-color: #5e5e5e;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor: pointer;
  display: inline-block;
  font-size: 0.8rem;
  color: white;
}
.saveBtn {
  margin-left: 340px;
  margin-right: 30px;
}
.cancel {
  margin-left: 30px;
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