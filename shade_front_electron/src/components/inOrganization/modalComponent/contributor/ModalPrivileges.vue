<template>
  <div class="modal">
    <div class="overlay">
      <div class="modal-card">
        <div class="categoryNameWrapper">Privileges</div>
        <div class="emailInfo">
          <label class="emailInput"> Contributor Email </label>
          {{ contributor.email }}
          <input type="text">
        </div>
        <div class="projectSelector">
          <label class="emailInput"> Select Project </label>
          <div class="aselect" :data-value="value"
               :data-list="list">
            <div class="selector" @click="toggle()">
              <div class="label">
                <span>{{ value }}</span>
              </div>
              <div class="arrow" :class="{ expanded : visible }"></div>
              <div :class="{ hidden : !visible, visible }">
                <ul>
                  <li :class="{ current : item === value }" v-for="item in projectMap" @click="select(item)" v-bind:key="item">{{ item[1] }}</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="listWrapper">
          <div class="list">
            <div v-for="item in selectedServerNameList" :key="item" style="margin-top: 10px">
              {{item[1]}}
            </div>
          </div>
        </div>
        <div class="btnWrapper">
          <button @click="$emit('closePrivilegeModal')" class="cancel">Cancel</button>
          <button>Save</button>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
import { mapState} from "vuex";

export default {
  name: 'ModalPrivileges',
  props: {
    contributor: Object
  },
  computed:{
    ...mapState('inOrganization',{
      projectMap: state => state.projects.map(p=>[p.project_id, p.project_name]),
    }),
    ...mapState('inOrganization', {
      project: state => state.projects
    })
  },
  data() {
    return {
      selectedProjectId: 0,
      value: 'Project',
      list: this.projectMap,
      visible: false,
      selectedServerMap: [],
      selectedServerNameList:[]
    }
  },
  methods: {
    toggle() {
      this.visible = !this.visible;
    },
    select(option) {
      this.value = option[1];
      this.selectedProjectId = option[0]
      this.selectedServerMap = this.project.find((project)=> project.project_id === this.selectedProjectId).project_server
      if(this.selectedServerMap == null){
        this.selectedServerNameList = []
        return
      }
      this.selectedServerNameList = Object.values(this.selectedServerMap).filter(s=>s!=null).flat().map(k=>[k.server_id,k.server_name])
    }
  }
}
</script>

<style scoped>
.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.modal-card {
  position: relative;
  max-width: 475px;
  min-height: 260px;
  margin: auto;
  margin-top: 150px;
  padding: 20px;
  z-index: 10;
  background-color: #242424;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  animation: fade-in-down 0.5s;
}

button {
  background-color: #989898;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor: pointer;
  display: inline-block;
  font-size: 0.8rem;
  color: white;
}

.categoryNameWrapper {
  text-align: center;
  justify-content: center;
  display: flex;
  color: white;
  margin-bottom: 30px;
  margin-top: 15px;
}

input {
  font-size: 0.9rem;
  color: white;
  padding: 0.2rem 0;
  text-align: center;
  background-color: #2f2f2f;
  width: 17rem;
  border: none;
  outline: none;
}
.emailInfo {
  text-align: center;
  margin-top: 3px;
  margin-bottom: 3px;
}

.emailInput{
  font-size: 0.8rem;
  padding-right: 2rem;
  color: white;
}

.btnWrapper {
  top: 30px;
  width: 100%;
  display: flex;
  text-align: center;
  justify-content: space-between;
  position: relative;
}

.projectSelector{
  display: flex;
  text-align: center;
  justify-content: center;
  margin-top: 10px;
  margin-right: 35px;
  margin-bottom: 20px;
}
.aselect {
  width: 17rem;
}
.selector {
  background: #2f2f2f;
  position: relative;
  z-index: 1;
  border-bottom: 0.1rem solid #C3C3C3;
}
.arrow {
  position: absolute;
  right: 10px;
  top: 35%;
  width: 0;
  height: 0;
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-top: 6px solid #888;
  transform: rotateZ(0deg) translateY(0px);
  transition-duration: 0.3s;
  transition-timing-function: cubic-bezier(.59,1.39,.37,1.01);
}
.expanded {
  transform: rotateZ(180deg) translateY(2px);
}
.label {
  display: block;
  padding: 4px;
  font-size: 12px;
  color: white;
}
ul {
  width: 100%;
  list-style-type: none;
  padding: 0;
  margin: 0;
  font-size: 10px;
  position: absolute;
  z-index: 1;
  background: #fff;
}
li {
  padding: 4px;
  color: #666;
}
li:hover{
  color: white;
  background: #888;
}
.current {
  background: #eaeaea;
}
.hidden {
  visibility: hidden;
}
.visible {
  visibility: visible;
}

.listWrapper{
  text-align: center;
  justify-content: center;
}
.list{
  background-color: #2f2f2f;
  width: 400px;
  height: 200px;
  margin: auto;
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
