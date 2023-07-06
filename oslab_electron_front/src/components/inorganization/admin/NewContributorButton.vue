<template>
  <button class="button" @click="$emit('openNewOrganModal')">
    Add New Contributor
  </button>
</template>
      
<script>
export default {
  name: 'NewContributorButton', 
}
</script>

<style scoped>
.button{
  margin-left: auto;
  margin-right: 3rem;
  margin-top: 2rem;
  color: white;
  display: block;
  background-color: #989898;
  padding-left: 1.8rem;
  padding-right: 1.8rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
}
</style>