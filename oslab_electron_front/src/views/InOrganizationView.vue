<template>
  <div class="container">

  <div class="leftsideframe">
    <AppInfo/>
    <hr/>
    <ContributorButton/>
    <LeftExpandingMenu/>
    <NewProjectButton v-if="isAdmin == true"/>
  </div>

  <div class="topbarframe">
    <OrganizationName/>
    <TopBarButton :userName="userName" :existUser="isLogin == true"/>
  </div>

  <div class="contentframe">
    <div v-if="onTerminal == false && onContributors == false">
      <div class="content">
        <PathBar/>
        <NewServerButton v-if="isAdmin == true"/>
        <ServerListItem/>
      </div>
      <div class="rightframe">
        <RightExpandingMenu/>
        <NewCategoryButton v-if="isAdmin == true"/>
      </div>
    </div>

    <div v-else-if="onContributors == true && onTerminal == false">
      <NewContributorButton v-if="isAdmin == true"/>
      <ContributorListItem/>
    </div>

    <div v-else>
      <TerminalWindow/>
    </div>
  </div>

  </div>
  <button @click="testIn()">
  화면 전환 버튼(개발용 추후 삭제)
  </button>
</template>



<script>
import AppInfo from '@/components/common/AppInfo.vue'
import ContributorButton from '@/components/inorganization/ContributorButton.vue'
import LeftExpandingMenu from '@/components/inorganization/LeftExpandingMenu.vue'
import NewProjectButton from '@/components/inorganization/admin/NewProjectButton.vue'

import OrganizationName from '@/components/inorganization/OrganizationName.vue'
import TopBarButton from '@/components/common/TopBarButton.vue'


import PathBar from '@/components/inorganization/PathBar.vue'
import NewServerButton from '@/components/inorganization/admin/NewServerButton.vue'
import ServerListItem from '@/components/inorganization/ServerListItem.vue'
import RightExpandingMenu from '@/components/inorganization/RightExpandingMenu.vue'
import NewCategoryButton from '@/components/inorganization/admin/NewCategoryButton.vue'

import NewContributorButton from '@/components/inorganization/admin/NewContributorButton.vue'
import ContributorListItem from '@/components/inorganization/ContributorListItem.vue'

import TerminalWindow from '@/components/inorganization/TerminalWindow.vue'

import { ipcRenderer } from 'electron';
import router from '@/router/index.js';

import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers('login')

export default {
  name: 'InOrganizationView',
  data(){
    return {
      existUser: true,
      onTerminal: false,
      onContributors: false,
    }
  },
  computed: {
    ...mapState({
      userName: state => state.userName,
      isLogin: state => state.isLogin,
      isAdmin: state => state.isAdmin
    }),
  },
  methods: {
    testIn() {
      router.push('/')
      ipcRenderer.send('reset-window')
    },
  },
  components: {
    AppInfo,
    ContributorButton,
    LeftExpandingMenu,
    NewProjectButton,
    OrganizationName,
    TopBarButton,
    PathBar,
    NewServerButton,
    ServerListItem,
    RightExpandingMenu,
    NewCategoryButton,
    NewContributorButton,
    ContributorListItem,
    TerminalWindow,
  }
}
</script>

<style scoped>
.container {
  position: relative;
  height: 100vh;
}

.leftsideframe {
  position: absolute;
  top: 0;
  left: 0;
  width: 15rem;
  height: 100%;
  background-color: #444444;
}

.topbarframe {
  position: absolute;
  top: 0;
  left: 15rem;
  width: calc(100% - 15rem);
  height: 7rem;
  background-color: #373737;
}

.contentframe {
  position: absolute;
  top: 7rem;
  left: 15rem;
  width: calc(100% - 15rem);
  height: calc(100% - 7rem);
  background-color: #2B2B2B;
}
.content{
  position: absolute;
  top: 0;
  left: 0;
  width: calc(100% - 15rem);
  height: 100%;
}

.rightframe{
  position: absolute;
  right: 0;
  width: 15rem;
  height: 100%;
  background-color: #444444;
}
hr{
  margin-top: 2.5rem;
  margin-bottom: 0;
  border: solid 0.001rem #c7c7c728;
  border-bottom: 0;
}
</style>