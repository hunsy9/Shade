<template>
  <div class="topcat" v-on:mouseover="toggleShiftIconWrapper(true)" v-on:mouseleave="toggleShiftIconWrapper(false)">
    <div class="category">
      <img class="categoryIcon" src="@/assets/file-branch-category.png" width="18"/>
      Category
    </div>
    <div class="shiftIconWrapper" v-if="shiftIconState == true" @click="toggleCategoryMenu">
      <img class="shiftIcon" v-if="topbtn == true" src="@/assets/chevron-right.png" @click="toggleCategoryMenu">
    </div>
  </div>
  <div class="emptyCategory" v-if="this.getCategory == null">Please Make Any Category</div>
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
      <div class="a" v-if="l1btn == true && l1index == l1">
        <div v-for="(l2, indexl2) in value" :key="indexl2">
          <div v-if="l2!=null" class="level2" @click="select_l2(l2)">
            <img class="dotSign" :src="theme==0 ? require('@/assets/dotsign-light.png') : require('@/assets/dotSign.png')">
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
  props:{
    theme: Number,
  },
  data() {
    return {
      shiftIconState: false,
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
    ...mapState('inOrganization', {
      projlist: state => state.projects,
      mode: state => state.mode,
      selected_categid : state => state.selected_categid,
      selected_proj_id: state => state.selected_proj_id,
      org_id: state=> state.organId,
      isAdmin : state => state.isAdmin
    }),
    ...mapGetters('inOrganization', ['getCategory']),
  },
  methods: {
    ...mapMutations('inOrganization', ['selectCatl1','selectCatl2']),
    toggleShiftIconWrapper(state){
      this.shiftIconState = state
    },
    toggleCategoryMenu(){
      this.$emit("toggleCategoryMenu")
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
  /*cursor:pointer;*/
  display: flex;
  animation: fade-in 1s linear;
}
.emptyCategory{
  color: #8f8f8f;
  font-size: 10px;
  font-weight: bold;
  text-align: center;
  padding-top: 80px;
}
.category{
  display: flex;
  align-items: center;
  padding-left: 7%;
  height: 4.5rem;
  line-height: 400%;
  color: var(--ModeTextColor);
  animation: fade-in 0.3s linear;
}
categoryIcon{
  margin-right: 2rem;
}
.shiftIconWrapper{
  right: 2rem;
  padding-top: 1.4rem;
  position: fixed;
  width: 1rem;
  height: 1rem;
  cursor: pointer;
  text-align: center;
  border-radius: 0.2rem;
}

.shiftIcon{
  width: 0.5rem;
  height: 0.5rem;
  cursor: pointer;
}
.shiftIcon:hover{
  background-color: #8E8E8E;
}
.level1{
  cursor:pointer;
  color: var(--ModeTextColor);
  background-color: var(--ModeLevel1Color);
  border-bottom: 0.5px solid rgba(26, 26, 26, 0.11);
  animation: fade-in 1s linear;
  font-size: 0.9rem;
}
.a{
  animation: fade-in 0.3s linear;
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
  color: var(--ModeTextColor);
  background-color: var(--ModeLevel2Color);
  border-bottom: 0.5px solid rgba(26, 26, 26, 0.2);
  font-size: 0.9rem;
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
  background-color: var(--ModeLevel2Color);
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
  cursor: pointer;
}
.edit{
  width: 12px;
  height: 12px;
  position: absolute;
  left: 170px;
  padding-top: 13px;
  cursor: pointer;
}
.del{
  width: 12px;
  height: 12px;
  position: absolute;
  right: 10px;
  padding-top:13px;
  cursor: pointer;
}
@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>