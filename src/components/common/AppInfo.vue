<template>
  <div class="shadeIcon">
    <img src="@/assets/shadeIcon.png" width="60px" height="60px">
  </div>
  <div class="text1">Shade</div>
  <div class="text2">Beta 0.2</div>
  <div class="line">
    <img class="size" src="@/assets/github.png">
  </div>
</template>

<script>
  export default {
  name: 'AppInfo',
  }
</script>

<style scoped>
.shadeIcon{
  text-align: center;
  width: 4rem;
  height: 4rem;
  /*background-color: #D9D9D9;*/
  border-radius: 0.5rem;
  display: block;
  margin-top: 8rem;
  margin-left: auto;
  margin-right: auto;
}
.text1{
  text-align: center;
  color: var(--ModeTextColor);
  font-weight: 500;
  display: block;
  margin-top: 1rem;
  margin-left: auto;
  margin-right: auto;
}
.text2{
  text-align: center;
  color: #AAAAAA;
  font-weight: 500;
  display: block;
  margin-top: 1rem;
  margin-left: auto;
  margin-right: auto;
}
.line{
  display: block;
  margin-top: 1rem;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  justify-content: center;
}
.size{
  margin-left: 0.2rem;
  margin-right: 0.2rem;
  width: 1rem;
  cursor:pointer;
}
</style>