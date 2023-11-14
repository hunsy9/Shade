<template>
  <div class="zidx">
    <main>
      <img src="@/assets/xbutton.png" @click="$emit('closeModalAddServer')">
      <div>
        <div class="top">{{ sName }}</div>
        <hr>
      </div>
      <div class="desc">
        {{ sDesc }}
      </div>
    </main>
  </div>
</template>
  
<script>
export default {
  name: 'ModalServerInfo',
  props: {
    sName: String,
    sDesc: String,
  }

}
</script>

<style scoped>
.zidx{
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
}
.zidx > main {
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-top: 19rem;
  width: 500px;
  height: 220px;
  background-color: var(--ModeModalBackgroundColor);
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
}
img{
  position: relative;
  top: 0.5rem;
  left: 0.5rem;
  width: 0.7rem;
  height: 0.7rem;
}
.top {
  color: white;
  text-align: center;
  padding-top: 0.8rem;
}
hr{
  width: 14rem;
  border: solid white;
  border-width: 0.1px 0px 0px 0px;
  height: 1px;
}
.desc{
  font-size: 0.7rem;
  margin-top: 3rem;
  color: white;
  text-align: center;
}
</style>