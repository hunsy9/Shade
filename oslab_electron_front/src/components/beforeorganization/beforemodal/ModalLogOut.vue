<template>
  <div class="zidx">
    <main>
      <div class="modalframe">
        <button class="button" @click="closeModalLogOut">
          Log Out
        </button>
      </div>
    </main>
  </div>
</template>
    
<script>
import { mapMutations } from "vuex";

export default {
  name: 'ModalLogOut',
  methods: {
    closeModalLogOut() {
      this.$emit('closeModalLogOut')
    },
    ...mapMutations('login', ['setLogin'])
  },
  beforeUnmount() {
    this.setLogin(false)
    localStorage.removeItem('vuex')
  },
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
  margin: 0 auto;
  width: 200px;
  height: 200px;
  background-color: white;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
}
.modalframe {
  margin-top: 2rem;
  padding-top: 1.7rem;
  position: relative;
}
.button{
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