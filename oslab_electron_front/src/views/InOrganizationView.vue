<template>
  <div class="container" v-if="full == false">

    <div class="leftsideframe" :class="{'leftsideframe-term':mode == 3}">
      <AppInfo/>
      <hr/>
      <ContributorButton/>
      <LeftExpandingMenu/>
      <NewProjectButton v-if="isAdmin == true" @openNewProjectModal="openNewProjectModal = true"/>
    </div>

    <div class="topbarframe" :class="{'topbarframe-term':mode ==3}">
      <OrganizationName/>
      <TopBarButton :isIn="true" @openModalLogOut="openModalLogOut = true"/>
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
  <ModalPrivileges :contributor="contributor" v-if="openPrivilegeModal" @closePrivilegeModal="openPrivilegeModal = false"/>
  <ModalLogOut :openModalLogOut="openModalLogOut" v-if="openModalLogOut" @closeModalLogOut="openModalLogOut = false"/>
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
import ModalLogOut from '@/components/beforeorganization/beforemodal/ModalLogOut.vue'

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
      openModalLogOut: false,
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
      title: "",
    }
  },
  computed: {
    ...mapState('login', {
      isLogin: state => state.isLogin,
    }),
    ...mapState('inOrganization', {
      mode: state => state.mode,
      full: state => state.full,
      catl2: state => state.selected_categ_l2,
      isAdmin: state => state.isAdmin
    }),
  },
  methods: {
    ...mapMutations('inOrganization',['resetSelect']),
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
    TerminalWindow,
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
    ModalAddServer,
    ModalServerInfo,
    ModalAddCategory,
    DeleteModal,
    ModalAddProject,
    ModalLogOut,
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

.leftsideframe-term{
  position: absolute;
  top: 0;
  left: 0;
  width: 15rem;
  height: 100%;
  background-color: #161616;
  animation: gradient 0.7s ease forwards;
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
.topbarframe-term{
  position: absolute;
  top: 0;
  left: 15rem;
  width: calc(100% - 15rem);
  height: 7rem;
  animation: gradient 0.7s ease forwards;
}

@keyframes gradient {
  0% {
    background-position: 0% 50%;
    background-color: #373737;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
    background-color: #121212;
  }
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