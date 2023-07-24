<template>
  <div class="listframe">
    <div class="admin">
      <img src="@/assets/contriAdmin.png">
      <span>{{ admin_email }}</span>
      <button>Edit</button>
    </div>
    <div v-for="(item, index) in contributors" :key="index" class="normal">
      <span>{{ item.contributor_email }}</span>
      <button>Delete</button>
      <button>Edit</button>
    </div>
  </div>
</template>
    
<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers('contributorInfo')

export default {
  name: 'ContributorListItem', 
  computed:{
    ...mapState({
      admin_email: state => state.admin_email.contributor_email,
      contributors: state => state.contributors
    })
  }
}
</script>

<style scoped>
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
</style>