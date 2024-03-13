<template>
  <div class="zidx" @click="this.$emit('closeModalLogOut')">
    <main>
      <div class="modalframe">
        <div class="profile">
          <div style="display: flex; justify-content: space-between">
            <div class="profile-image">
              <img class="shadeIcon" src="@/assets/remove-bg-shadeIcon.png" width="30" height="30"/>
            </div>
            <div style="height: 60px">{{ userName }}</div>
          </div>
        </div>
        <div class="content" @click="toMain">
          To main
        </div>
        <div class="content" @click="closeModalLogOut">
          Logout
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import {mapMutations, mapState} from "vuex";
import router from "@/router";
import {ipcRenderer} from 'electron';

export default {
  name: 'ModalLogOut',
  props: {
    openModalLogOut: Boolean
  },
  computed: {
    ...mapState('login', {
      userName: state => state.userName,
      userEmail: state => state.userEmail
    }),
  },
  methods: {
    toMain() {
      ipcRenderer.send('reset-window')
      router.push('/')
      this.$emit('closeModalLogOut')
    },
    closeModalLogOut() {
      this.$emit('closeModalLogOut')
      this.setLogin(false)
      localStorage.removeItem('vuex')
      ipcRenderer.send('reset-window')
      router.push('/')
    },
    ...mapMutations('login', ['setLogin'])
  },
}
</script>

<style scoped>
.zidx {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: 10;
}

.zidx > main {
  z-index: 100;
  display: block;
  margin-top: 2rem;
  margin-left: auto;
  margin-right: 3.4rem;
  width: 220px;
  background-color: #f6f6f6;
  border-radius: 0.5rem;
  border: none;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  animation: fadeInUp 0.5s ease backwards;
}

.modalframe {
  position: relative;
  text-align: center;
}

.profile-image {
  color: #2C2C2C;
  /*justify-content: center;*/
  margin-top: 30px;
  /*margin-left: 80px;*/
  margin-bottom: 10px;
  width: 60px;
  height: 60px;
  border-radius: 3rem;
  background-color: #FFFFFF;
  border: 2px solid #858585;
}

.profile {
  text-align: center;
  display: flex;
  height: 100px;
}

.content {
  line-height: 200%;
  text-align: center;
  height: 2rem;
  cursor: pointer;
}

.content:hover {
  background-color: #efefef;
}

.shadeIcon {
  margin-top: 22%;
}

@keyframes fadeInUp {
  0% {
    transform: translate(0px, 7px);
    opacity: 0;
  }
  100% {
    transform: translate(0px, 0);
    opacity: 1;
  }
}
</style>