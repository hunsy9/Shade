<template>
  <div class="container" v-if="full == false">

    <div class="leftsideframe">
      <AppInfo/>
      <hr/>
      <ContributorButton/>
      <LeftExpandingMenu/>
      <NewProjectButton v-if="isAdmin == true" @openNewProjectModal="openNewProjectModal = true"/>
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
          <NewServerButton v-if="isAdmin == true" @openModalAddServer="test1"/>
          <ServerListItem :v-if="catl2" @openModalServerInfo="doOpenModalServerInfo" @openDeleteModal="doOpenDeleteModal" @openModalAddServer="test1"/>
        </div>
        <div class="rightframe">
          <RightExpandingMenu @openAddCategoryModal="doOpenAddCategoryModal" @openDeleteModal="doOpenDeleteModal"/>
          <NewCategoryButton v-if="isAdmin == true" @openAddCategoryModal="doOpenAddCategoryModal"/>
        </div>
      </div>

      <div v-else-if="mode == 2">
        <span class="contribut">Contributors</span>
        <NewContributorButton v-if="isAdmin == true" @openInviteContributorModal="openInviteContributorModal = true"/>
        <ContributorListItem @openPrivilegeModal="doOpenPrivilegeModal" @openDeleteModal="doOpenDeleteModal"/>
      </div>

      <div v-else-if="mode == 3 && full == false">
        <TerminalWindow/>
      </div>
    </div>
  </div>
  <div v-else-if="mode == 3 && full == true">
    <TerminalWindow/>
  </div>

  <ModalAddServer :title="title" v-if="openModalAddServer" @closeModalAddServer="openModalAddServer = false"/>
  <ModalServerInfo :sName="sName" :sDesc="sDesc" v-if="openModalServerInfo" @closeModalAddServer="openModalServerInfo = false"/>
  <ModalAddCategory :ActionCategoryState="AddCategoryState" :ActionCategoryDto="AddCategoryDto" v-if="openAddCategoryModal" @closeAddCategoryModal="openAddCategoryModal = false"/>
  <DeleteModal :DeleteState="DeleteState" :DeleteDto="DeleteDto"  v-if="openDeleteModal" @closeDeleteModal="openDeleteModal = false"/>
  <ModalAddProject v-if="openNewProjectModal" @closeNewProjectModal="openNewProjectModal = false"/>

  <ModalAddContributor v-if="openInviteContributorModal" @closeInviteContributorModal="openInviteContributorModal = false"/>
  <ModalPrivileges :contributor="contributor" v-if="openPrivilegeModal" @closePrivilegeModal="openPrivilegeModal = false"></ModalPrivileges>

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
import ModalServerInfo from '@/components/inorganization/inmodal/ModalServerInfo.vue'
import ModalAddCategory from '@/components/inorganization/inmodal/ModalAddCategory.vue'
import DeleteModal from '@/components/common/DeleteModal.vue'
import ModalAddProject from '@/components/inorganization/inmodal/ModalAddProject.vue'

import { ipcRenderer } from 'electron';
import router from '@/router/index.js';

import { mapState, mapMutations } from "vuex";
import ModalAddContributor from "@/components/inorganization/inmodal/ModalAddContributor";
import ModalPrivileges from "@/components/inorganization/inmodal/contributor/ModalPrivileges";

export default {
  name: 'InOrganizationView',
  data(){
    return {
      onTerminal: false,
      onContributors: false,
      openModalAddServer: false,
      openModalServerInfo: false,
      openAddCategoryModal: false,
      openInviteContributorModal:false,
      openPrivilegeModal: false,
      openDeleteModal: false,
      openNewProjectModal: false,
      sName: "",
      sDesc: "",
      DeleteState: "",
      DeleteDto: "",
      AddCategoryState:"",
      AddCategoryDto:"",
      contributor:{
        id:"",
        email:""
      },
      title: ""
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
    doOpenModalServerInfo(name, desc){
      this.sName = name
      this.sDesc = desc
      this.openModalServerInfo = true
    },
    doOpenDeleteModal(DeleteState, dto){
      this.DeleteDto = dto
      this.openDeleteModal = true
      this.DeleteState = DeleteState
    },
    doOpenPrivilegeModal(contributor_id, contributor_email){
      this.contributor.id = contributor_id
      this.contributor.email = contributor_email
      this.openPrivilegeModal = true
    },
    test1(asdasd){
      this.openModalAddServer = true
      console.log(asdasd)
      this.title = asdasd
    },
    doOpenAddCategoryModal(addCategoryState, addCategoryDto){
      this.AddCategoryState = addCategoryState
      this.AddCategoryDto = addCategoryDto
      this.openAddCategoryModal = true
    }
  },
  components: {
    ModalPrivileges,
    ModalAddContributor,
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
    ModalServerInfo,
    ModalAddCategory,
    DeleteModal,
    ModalAddProject
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