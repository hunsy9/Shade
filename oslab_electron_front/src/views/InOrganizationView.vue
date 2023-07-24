<template>
  <div class="container" v-if="full == false">

    <div class="leftsideframe">
      <AppInfo/>
      <hr/>
      <ContributorButton/>
      <LeftExpandingMenu/>
      <NewProjectButton v-if="isAdmin == true"/>
    </div>

    <div class="topbarframe">
      <OrganizationName/>
      <TopBarButton/>
    </div>

    <div class="contentframe">
      <div v-if="mode == 0" style="color: white; text-align: center; margin-top: 16rem;">Please select any menu</div>
      <div v-else-if="mode == 1">
        <div class="content">
          <PathBar/>
          <NewServerButton v-if="isAdmin == true" @openModalAddServer="openModalAddServer = true"/>
          <ServerListItem v-if="catl2"/>
        </div>
        <div class="rightframe">
          <RightExpandingMenu/>
          <NewCategoryButton v-if="isAdmin == true"/>
        </div>
      </div>

      <div v-else-if="mode == 2">
        <span class="contribut">Contributors</span>
        <NewContributorButton v-if="isAdmin == true"/>
        <ContributorListItem/>
      </div>

      <div v-else-if="mode == 3 && full == false">
        <TerminalWindow/>
      </div>
    </div>
  </div>
  <div v-else-if="mode == 3 && full == true">
    <TerminalWindow/>
  </div>

  <ModalAddServer v-if="openModalAddServer" @closeModalAddServer="openModalAddServer = false"/>

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

import ModalAddServer from '@/components/inorganization/inmodal/ModalAddServer.vue'

import { ipcRenderer } from 'electron';
import router from '@/router/index.js';

import { mapState, mapMutations } from "vuex";

export default {
  name: 'InOrganizationView',
  data(){
    return {
      onTerminal: false,
      onContributors: false,
      openModalAddServer: false,
    }
  },
  computed: {
    ...mapState('login', {
      isLogin: state => state.isLogin,
      isAdmin: state => state.isAdmin
    }),
    ...mapState('inOrganization', {
      mode: state => state.mode,
      full: state => state.full,
      catl2: state => state.selected_categ_l2,
    }),
  },
  methods: {
    ...mapMutations('inOrganization',['resetSelect']),
    testIn() {
      this.resetSelect()
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
    ModalAddServer,
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

hr{
  margin-top: 2.5rem;
  margin-bottom: 0;
  border: solid 0.001rem #c7c7c728;
  border-bottom: 0;
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

.contribut{
  padding-left: 5%;
  line-height: 620%;
  color: white;
}
</style>