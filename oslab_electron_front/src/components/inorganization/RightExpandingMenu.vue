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
      </div>

      <div v-if="l1btn == true && l1index == l1">
        <div v-for="(l2, indexl2) in value" :key="indexl2">
          <div v-if="l2!=null" class="level2" @click="select_l2(l2)">
            <img src="@/assets/dotSign.png">
            <span>
              {{ l2 }}
            </span>
          </div>
        </div>
        <div class="editBar" v-if="isAdmin">
          <button class="editCategory" @click="$emit('openAddCategoryModal')">Add</button>
          <button class="editCategory" @click="$emit('openDeleteModal', DeleteState)">Delete</button>
        </div>
      </div>
    </div>
  </div>

</template>
  
<script>
import { mapState, mapGetters, mapMutations } from "vuex";
import {DeleteState} from "@/store/inOrganization";

export default {
  name: 'RightExpandingMenu',
  data() {
    return {
      topbtn: true,
      l1btn: false,
      l1index: "",
      DeleteState: DeleteState.DELETECATEGORYL1,
    }
  },
  computed: {
    ...mapState('login', {
      isAdmin : state => state.isAdmin
    }),
    ...mapState('inOrganization', {
      projlist: state => state.projects,
      mode: state => state.mode,
      selected_categid : state => state.selected_categid
    }),
    ...mapGetters('inOrganization', ['getCategory'])
  },
  methods: {
    ...mapMutations('inOrganization', ['selectCatl1','selectCatl2']),
    addL1(){

    },
    addL2(){

    },
    switch_top(){
      this.topbtn = !this.topbtn
    },
    select_l1(index){
      this.selectCatl1(index)
      if(this.l1index != index){
        this.l1btn = true
      } else{
        this.l1btn = !this.l1btn
      }
      this.l1index = index
    },
    select_l2(server){
      this.selectCatl2(server)
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
}
.level2 img{
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
}
</style>