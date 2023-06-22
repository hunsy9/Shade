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
  position: absolute;
  bottom: 8%;
  left: 50%;
  transform: translateX(-50%);
  color: white;
  display: block;
  margin: 0 auto;
  background-color: #989898;
  padding-left: 1.8rem;
  padding-right: 1.8rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
}
</style>