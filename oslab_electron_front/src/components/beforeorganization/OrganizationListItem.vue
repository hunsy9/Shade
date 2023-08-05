<template>
  <div class="listframe">
    <div class="itembox" v-for="(item, index) in limitedList" :key="index" :style="{ backgroundColor: colors[index % colors.length] }">
      <span class="oname"> {{ item.org_name }} </span>
      <!-- <button>
        Delete
      </button> -->
      <button @click="$emit('openInOganization', item)">
        Connect
      </button>

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
      colors: ["#AFA4C6a2", "#A4C6B5a2", "#A4BEC6a2", "#C6A4A4a2"],
      maxItems: 7,
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
  }
}
</script>

<style scoped>
.listframe{
  display: block;
  margin-top: 2rem;
  margin-left: 3rem;
  margin-right: 3rem;
}
.itembox{
  margin-bottom: 0.7rem;
  background-color: #a4bec6a2;
  border-radius: 0.4rem;
  padding: 1rem 0;
  animation: fadeInUp 1s ease backwards;
}
.oname{
  padding-left: 2rem;
  width: 20rem;
  margin: 0;
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