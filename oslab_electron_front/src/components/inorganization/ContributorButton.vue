<template>
  <div class="cont" :class="{click:isClick}" @click="inContributors">
    <img src="@/assets/contributors.png">
    <span>Contributors</span>
  </div>
</template>
      
<script>
export default {
  name: 'ContributorButton', 
  data() {
    return{
        isClick: false,
    }
  },
  methods: {
    inContributors(){
      this.isClick = !this.isClick;
    }
  }
}
</script>

<style scoped>
.cont{
  cursor:pointer;
  padding-bottom: 0.7rem;

}
.click{
  background-color: #313131;
}
img{
  margin-top: 0.7rem;
  margin-left: 1rem;
  width: 1.5rem;
}
span{
  margin-left: 1rem;
  margin-bottom: 0.7rem;
  color: white;
}
</style>