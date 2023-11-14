<template>
  <button class="button" @click="$emit('openNewProjectModal')">
    New Project
  </button>
</template>
    
<script>
export default {
  name: 'NewProjectButton', 
}
</script>

<style scoped>
.button{
  position: absolute;
  bottom: 5%;
  left: 50%;
  transform: translateX(-50%);
  color: var(--ModeTextColor);
  display: block;
  margin: 0 auto;
  background-color: var(--ModeBtnColor);
  width: 65%;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
}
</style>