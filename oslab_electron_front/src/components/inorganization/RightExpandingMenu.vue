<template>
  <div class="topcat" @click="switch_top"> 
    <div class="category">
      Category
    </div>
    <img class="signTop" v-if="topbtn == true" src="@/assets/downSign.png">
    <img class="signTop" v-else src="@/assets/rightSign.png">
  </div>

  <div v-if="topbtn == true">
    <div v-for="(value, l1) in this.getCategory" :key="l1">
      <div class="level1" @click="select_l1(l1)">
        <img class="signL1" v-if="l1btn == true && l1index == l1" src="@/assets/downSign.png">
        <img class="signL1" v-else src="@/assets/rightSign.png">
        <span>
          {{ l1 }}
        </span>
        <img class="edit" v-if="l1btn == true && l1index==l1 && this.EditState" src="@/assets/edit.png" @click="editCategoryL1(l1)"/>
        <img class="del" v-if="l1btn == true && l1index==l1 && this.EditState" src="@/assets/trash.png" @click="deleteCategoryL1(l1)"/>
      </div>
      <div v-if="l1btn == true && l1index == l1">
        <div v-for="(l2, indexl2) in value" :key="indexl2">
          <div v-if="l2!=null" class="level2" @click="select_l2(l2)">
            <img class="dotSign" src="@/assets/dotSign.png">
            <span>
              {{ l2 }}
            </span>
            <img class="edit" v-if="l1btn == true && l1index == l1 && this.EditState" src="@/assets/edit.png" @click="editCategoryL2(l1,l2)"/>
            <img class="del" v-if="l1btn == true && l1index == l1 && this.EditState" src="@/assets/trash.png" @click="deleteCategoryL2(l1,l2)"/>
          </div>
        </div>
        <div class="editBar" v-if="isAdmin">
          <button class="editCategory" @click="openAddL2Category(l1)">Add</button>
          <button class="editCategory" @click="controlEditStatus">Edit</button>
        </div>
      </div>
    </div>
  </div>

</template>
  
<script>
import {mapState, mapGetters, mapMutations} from "vuex";
import {ActionCategoryState, DeleteState} from "@/store/inOrganization";

export default {
  name: 'RightExpandingMenu',
  data() {
    return {
      topbtn: true,
      l1btn: false,
      l1index: "",
      DeleteState: DeleteState.DELETECATEGORYL1,
      DeleteDto:"",
      ActionCategoryState: ActionCategoryState.ADDL2,
      ActionCategoryDto:"",
      EditState: false
    }
  },
  computed: {
    ...mapState('login', {
      isAdmin : state => state.isAdmin
    }),
    ...mapState('inOrganization', {
      projlist: state => state.projects,
      mode: state => state.mode,
      selected_categid : state => state.selected_categid,
      selected_proj_id: state => state.selected_proj_id,
      org_id: state=> state.organId
    }),
    ...mapGetters('inOrganization', ['getCategory']),
  },
  methods: {
    ...mapMutations('inOrganization', ['selectCatl1','selectCatl2']),
    switch_top(){
      this.topbtn = !this.topbtn
    },
    select_l1(index){
      this.selectCatl1(index)
      if(this.l1index != index){
        this.l1btn = true
        this.EditState = false
      } else{
        this.l1btn = !this.l1btn
      }
      this.l1index = index
    },
    select_l2(server){
      this.selectCatl2(server)
    },
    openAddL2Category(l1_name){
      let a = this.projlist.find(p=>p.project_id === this.selected_proj_id).project_server
      let matchedKey = Object.keys(a).filter(psKey=>psKey.split(":")[1] === l1_name)
      const dto = {
        category_id: matchedKey[0].split(":")[0],
      }
      this.ActionCategoryState = ActionCategoryState.ADDL2
      this.$emit('openAddCategoryModal', this.ActionCategoryState, dto)
    },
    controlEditStatus(){
      this.EditState = !this.EditState
    },
    editCategoryL1(l1_name){
      let a = this.projlist.find(p=>p.project_id === this.selected_proj_id).project_server
      let categoryToServerKey = Object.keys(a).filter(k=>k.split(":")[1] === l1_name)
      const dto = {
        categoryKeys: categoryToServerKey,
        editValue: l1_name,
        type: "L1"
      }
      this.ActionCategoryState = ActionCategoryState.EDIT
      this.$emit('openAddCategoryModal', this.ActionCategoryState, dto)
    },
    editCategoryL2(l1,l2){
      let a = this.projlist.find(p=>p.project_id === this.selected_proj_id).project_server
      let categoryToServerKey = Object.keys(a).filter(k=>k.split(":")[1] === l1 && k.split(":")[2] === l2)
      const dto = {
        categoryKeys: categoryToServerKey,
        editValue: l2,
        type: "L2"
      }
      this.ActionCategoryState = ActionCategoryState.EDIT
      this.$emit('openAddCategoryModal', this.ActionCategoryState, dto)
    },
    deleteCategoryL1(l1_name){
      let a = this.projlist.find(p=>p.project_id === this.selected_proj_id).project_server
      let categoryToServerKey = Object.keys(a).filter(k=>k.split(":")[1] === l1_name)
      let categoryToServerKeyNotNull = Object.keys(a).filter(k=>k.split(":")[1] === l1_name && a[k]!=null)
      let targetServerList = []
      categoryToServerKeyNotNull.map(key=> a[key].map(server => targetServerList.push(server.server_id)))
      const dto = {
        org_id: this.org_id,
        project_id: this.selected_proj_id,
        categoryKeys: categoryToServerKey,
        targetServerList: targetServerList,
        type: "L1"
      }
      this.DeleteDto = dto
      this.DeleteState = DeleteState.DELETECATEGORYL1
      this.$emit('openDeleteModal', this.DeleteState, this.DeleteDto)
    },
    deleteCategoryL2(l1,l2){
      let a = this.projlist.find(p=>p.project_id === this.selected_proj_id).project_server
      let categoryToServerKey = Object.keys(a).filter(k=>k.split(":")[1] === l1 && k.split(":")[2] === l2)
      let categoryToServerKeyNotNull = Object.keys(a).filter(k=>k.split(":")[1] === l1 && k.split(":")[2] === l2 && a[k]!=null)
      let targetServerList = []
      categoryToServerKeyNotNull.map(key => a[key].map(server=> targetServerList.push(server.server_id)))
      const dto = {
        org_id: this.org_id,
        project_id: this.selected_proj_id,
        categoryKeys: categoryToServerKey,
        targetServerList: targetServerList,
        type: "L2"
      }
      this.DeleteDto = dto
      this.DeleteState = DeleteState.DELETECATEGORYL2
      this.$emit('openDeleteModal', this.DeleteState, this.DeleteDto)
    }
  },
}
</script>

<style scoped>
.topcat{
  cursor:pointer;
}
.category{
  display: inline;
  padding-left: 5%;
  height: 4.5rem;
  width: 70%;
  line-height: 400%;
  color: white;
}
.signTop{
  margin-left: 8.6rem;
  width: 0.5rem;
  height: 0.5rem;
}
.level1{
  cursor:pointer;
  color: white;
  background-color: #383838;
  border-bottom: 1px solid rgba(26, 26, 26, 0.11);
}


.signL1{
  margin-left: 1rem;
  width: 0.5rem;
  height: 0.5rem;
}
.level1 span{
  padding-left: 5%;
  line-height: 250%;
}
.level2{
  cursor:pointer;
  color: white;
  background-color: #2C2C2C;
  border-bottom: 1px solid rgba(26, 26, 26, 0.2);
  animation: dropdown_menu-6;
}
.dotSign{
  margin-left: 2rem;
  margin-bottom: 0.2rem;
  width: 0.2rem;
  height: 0.2rem;
}
.level2 span{
  padding-left: 5%;
  line-height: 250%;
}
.editBar{
  display: flex;
  justify-content: space-evenly;
  height: 40px;
  background-color: #2C2C2C;
}
.editCategory{
  height: 17px;
  width: 60px;
  margin-top: 10px;
  background-color: #6F6A6A;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  color: white;
}
.edit{
  width: 12px;
  height: 12px;
  position: absolute;
  left: 200px;
  padding-top:13px
}
.del{
  width: 12px;
  height: 12px;
  position: absolute;
  right: 10px;
  padding-top:13px;
}

</style>