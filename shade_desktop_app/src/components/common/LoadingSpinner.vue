<template>
  <div class="spinner-container">
    <div class="spinner" :style="{width: width, height: height}"/>
  </div>
</template>

<script>
export default {
  props: {
    width : String,
    height : String,
  }
};
</script>

<style scoped>
.spinner-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
.spinner {
  border-radius: 50%;
  border: 3px solid #e0e0e0;
  border-bottom: 3px solid #fe9616;
  animation: spin 1s linear infinite;
  position: relative;
}
@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>