<template>
  <div class="organizationText">
    My Organization
  </div>
  <div class="listframe">
    <div class="itembox" v-for="(item, index) in limitedList" :key="index" @click="$emit('openInOganization', item)">
      <div class="icon_wrapper">
        <img class="org_icon" src="@/assets/shadeIcon.png"/>
      </div>
      <div class="oname_wrapper">
        <span class="oname"> {{ item.org_name }} </span>
      </div>
    </div>
    <div class="itemBox_default" @click="$emit('openAddOrganization')">
      <img src="@/assets/plus.png" width="30" style="margin-top: 5rem"/>
    </div>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers('organizationInfo')

export default {
  name: 'OrganizationListItem',
  data() {
    return {
      maxItems: 7,
      clickState: false
    }
  },
  computed: {
    ...mapState({
      list: state => state.organizationInfos
    }),
    limitedList() {
      // 최대 개수로 제한된 아이템 리스트 반환
      return this.list.slice(0, this.maxItems);
    },
    customStyle(index){
      return{
        '--test-duration': index
      }
    }
  }
}
</script>

<style scoped>
.organizationText{
  margin-top: 40px;
  margin-left: 3rem;
}
.listframe{
  display: flex;
  flex-wrap: nowrap;
  overflow-x: scroll;
  margin-top: 2rem;
  margin-left: 2rem;
  margin-right: 3rem;
}
.itembox{
  flex: 0 0 auto;
  overflow-x: scroll;
  white-space:nowrap;
  text-align: center;
  margin-left: 1rem;
  margin-bottom: 0.7rem;
  width: 200px;
  height: 200px;
  background-color: #323232;
  border-radius: 0.4rem;
  animation: fadeInUp 1s ease backwards;
  cursor: pointer;
}
.itembox:hover{
  background-color: #222222;
}
.itemBox_default{
  text-align: center;
  flex: 0 0 auto;
  margin-left: 1rem;
  margin-bottom: 0.7rem;
  width: 200px;
  height: 200px;
  background-color: #222222;
  border-radius: 0.4rem;
  animation: fadeInUp 1s ease backwards;
  cursor: pointer;
}
.itemBox_default:hover{
  background-color: #1e1e1e;
}
.org_icon{
  width: 80px;
}
.icon_wrapper{
  margin-top: 3.3rem;
}
.oname{
  width: 20rem;
}
.oname_wrapper{
  margin-top: 1rem;
}
button{  
  float: right;
  background-color: #989898c2;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
  color: white;
  padding: 0.1rem 1.8rem;
  margin-right: 1rem;
}
@keyframes fadeInUp{
  0%{transform:translate(0px, 7px); opacity: 0;}
  100%{transform:translate(0px, 0); opacity: 1;}
}
</style>