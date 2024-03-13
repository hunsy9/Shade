<template>
  <div class="container" v-if="full == false">

    <div class="leftsideframe" :class="{'leftsideframe-term': mode == 3, 'leftsideframe-shifted': shiftState == true, 'leftsideframe-light': getTheme == 0}"
         v-on:mouseover="openHidingBtn" v-on:mouseleave="closeHidingBtn">
      <div class="hidingBtnWrapper" @click="shiftLeft" v-if="hidingBtnOpenedState">
        <img src="@/assets/chevron.png" width="12" height="12" @click="shiftLeft"/>
      </div>
      <LeftSideOrgBanner v-if="shiftState == false && mode != 3"/>
      <hr v-if="shiftState == false && mode != 3"/>
      <LocalTerminalButton v-if="shiftState == false && mode != 3" :theme="getTheme"/>
      <ProjectWhiteBoardButton v-if="shiftState == false && mode != 3" :theme="getTheme"/>
      <ContributorButton v-if="shiftState == false && mode != 3" :theme="getTheme"/>
      <RemoteDirectoryLeftExpandingMenu v-if="shiftState == false && mode==3"/>
      <ExitTerminalButton v-if="shiftState == false && mode==3"/>
<!--      <LeftExpandingMenu v-if="shiftState == false" :theme="getTheme"/>-->
<!--      <NewProjectButton  v-if="isAdmin == true && shiftState == false && mode != 3" @openNewProjectModal="openNewProjectModal = true"/>-->
    </div>

    <div class="topbarframe" :class="{'topbarframe-term':mode ==3, 'topbarframe-shifted':shiftState == true, 'topbarframe-light':getTheme == 0}">
      <div class="hidingBtnWrapper-top" @click="shiftRight" v-if="shiftState == true">
        <img :src="getTheme == 0 ? require('@/assets/menuIcon-light.png') : require('@/assets/menuIcon.png')" width="12" height="12" @click="shiftRight"/>
      </div>
      <ProjectSelection v-if="mode != 3" :shiftState="shiftState" @openProjectSelectionModal="openProjectSelectionModal = true" />
      <div class="userBtnWrapper user-profile" @click="openModalLogOut = true">
        <img :src="getTheme == 0 ? require('@/assets/topBarFrame/user-profile-light.png') : require('@/assets/topBarFrame/user-profile.png')" width="18" @click="openModalLogOut = true"/>
      </div>
      <div class="userBtnWrapper setting" @click="openModalSettings = true">
        <img :src="getTheme == 0 ? require('@/assets/topBarFrame/settings-light.png') : require('@/assets/topBarFrame/settings.png')" width="18" @click="openModalSettings = true" />
      </div>
      <TerminalTabBar v-if="mode==3"/>
    </div>


    <div class="contentframe" :class="{'contentframe-shifted' : shiftState == true, 'contentframe-light': getTheme == 0}">
      <div class="initialProjectWindow" v-if="mode == 0">Please select any menu</div>
      <div v-else-if="mode == 1">
        <div class="content">
          <PathBar :theme="getTheme"/>
          <ServerListItem :theme="getTheme" :v-if="catl2" @openModalServerInfo="doOpenModalServerInfo"
                          @openDeleteModal="doOpenDeleteModal" @openModalAddServer="test1" :isAdmin="isAdmin"/>
        </div>
        <div class="categoryFloating"  :class="{'categoryFloating-light': getTheme==0, 'categoryFloating-hided':categoryHidingState == true}">
          <RightExpandingMenu v-if="categoryHidingState == false" @toggleCategoryMenu="toggleCategoryMenu(true)" @openAddCategoryModal="doOpenAddCategoryModal" @openDeleteModal="doOpenDeleteModal" :theme="getTheme"/>
          <NewCategoryButton v-if="isAdmin == true && categoryHidingState == false" @openAddCategoryModal="doOpenAddCategoryModal"/>
        </div>
        <div class="categoryBanner" v-if="categoryHidingState == true" @click="toggleCategoryMenu(false)" v-on:mouseover="hidingBannerName = 'Category'" v-on:mouseleave="hidingBannerName = ''">
          <img src="@/assets/remove-bg-shadeIcon.png" width="20" height="20" style="margin-right: 5px"/>
          {{hidingBannerName}}
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
  <ModalServerInfo :sName="sName" :sDesc="sDesc" v-if="openModalServerInfo"
                   @closeModalAddServer="openModalServerInfo = false"/>
  <ModalAddCategory :ActionCategoryState="AddCategoryState" :ActionCategoryDto="AddCategoryDto"
                    v-if="openAddCategoryModal" @closeAddCategoryModal="openAddCategoryModal = false"/>
  <DeleteModal :DeleteState="DeleteState" :DeleteDto="DeleteDto" v-if="openDeleteModal"
               @closeDeleteModal="openDeleteModal = false"/>
  <ModalAddProject v-if="openNewProjectModal" @closeNewProjectModal="openNewProjectModal = false"/>

  <ModalAddContributor v-if="openInviteContributorModal"
                       @closeInviteContributorModal="openInviteContributorModal = false"/>
  <ModalPrivileges :contributor="contributor" v-if="openPrivilegeModal"
                   @closePrivilegeModal="openPrivilegeModal = false"/>
  <ModalProjectSelect v-if="openProjectSelectionModal" :shiftState="shiftState" :openProjectSelection="openProjectSelectionModal = true" @closeProjectSelectionModal="openProjectSelectionModal = false" :theme="getTheme" @openNewProjectModal="openNewProjectModal = true"/>
  <ModalLogOut :openModalLogOut="openModalLogOut" v-if="openModalLogOut" @closeModalLogOut="openModalLogOut = false"/>
  <ModalSettings v-if="openModalSettings" @closeSettingsModal="openModalSettings = false"/>
</template>
<script>
import NewContributorButton from '@/components/inOrganization/btnForAdmin/NewContributorButton.vue'
import NewCategoryButton from '@/components/inOrganization/btnForAdmin/NewCategoryButton.vue'

import PathBar from '@/components/inOrganization/projectContentWindow/PathBar.vue'

import ServerListItem from '@/components/inOrganization/projectContentWindow/ServerListItem.vue'
import RightExpandingMenu from '@/components/inOrganization/categoryFloatComponent/RightExpandingMenu.vue'

import ContributorListItem from '@/components/inOrganization/leftExpanding/contributorContentWindow/ContributorListItem.vue'
import ContributorButton from '@/components/inOrganization/leftExpanding/contributorContentWindow/ContributorButton.vue'

import TerminalWindow from '@/components/inOrganization/terminalWindow/TerminalWindow.vue'

import ModalAddServer from '@/components/inOrganization/modalComponent/ModalAddServer.vue'
import ModalServerInfo from '@/components/inOrganization/modalComponent/ModalServerInfo.vue'
import ModalAddCategory from '@/components/inOrganization/modalComponent/ModalAddCategory.vue'
import DeleteModal from '@/components/common/DeleteModal.vue'
import ModalAddProject from '@/components/inOrganization/modalComponent/ModalAddProject.vue'
import ModalLogOut from '@/components/beforeOrganization/beforemodal/ModalLogOut.vue'
import ModalAddContributor from "@/components/inOrganization/modalComponent/ModalAddContributor";
import ModalPrivileges from "@/components/inOrganization/modalComponent/contributor/ModalPrivileges";
import ModalSettings from "@/components/common/topBar/settings/ModalSettings";

import {mapState, mapMutations, mapGetters} from "vuex";
import RemoteDirectoryLeftExpandingMenu from "@/components/inOrganization/terminalWindow/remoteDirectory/RemoteDirectoryLeftExpandingMenu";
import LeftSideOrgBanner from "@/components/inOrganization/leftExpanding/LeftSideOrgBanner";
import ProjectSelection from "@/components/inOrganization/topBar/ProjectSelection.vue";
import ModalProjectSelect from "@/components/inOrganization/modalComponent/ModalProjectSelect.vue";
import ProjectWhiteBoardButton from "@/components/inOrganization/leftExpanding/projectWhiteBoardWindow/projectWhiteBoardButton.vue";
import ExitTerminalButton from "@/components/inOrganization/terminalWindow/ExitTerminalButton.vue";
import LocalTerminalButton from "@/components/inOrganization/terminalWindow/localTerminal/LocalTerminalButton.vue";
import TerminalTabBar from "@/components/inOrganization/terminalWindow/terminalTabBarList/TerminalTabBar.vue";

export default {
  name: 'InOrganizationView',
  data() {
    return {
      hidingBannerName: "",
      shiftState: false,
      categoryHidingState: false,
      hidingBtnOpenedState: false,
      onTerminal: false,
      onContributors: false,
      sName: "",
      sDesc: "",
      DeleteState: "",
      DeleteDto: "",
      AddCategoryState: "",
      AddCategoryDto: "",
      contributor: {
        id: "",
        email: ""
      },
      title: "",

      openModalAddServer: false,
      openModalServerInfo: false,
      openAddCategoryModal: false,
      openInviteContributorModal: false,
      openPrivilegeModal: false,
      openDeleteModal: false,
      openNewProjectModal: false,
      openProjectSelectionModal: false,
      openModalLogOut: false,
      openModalSettings: false,
    }
  },
  computed: {
    ...mapGetters('inOrganization',['getTheme']),
    ...mapState('login', {
      isLogin: state => state.isLogin,
    }),
    ...mapState('inOrganization', {
      mode: state => state.mode,
      full: state => state.full,
      catl2: state => state.selected_categ_l2,
      isAdmin: state => state.isAdmin
    }),
    ...mapState('inOrganization',{
      themeState: state => state.themeState
    })
  },
  methods: {
    ...mapMutations('inOrganization', ['resetSelect','selectInitialProjectWindow']),
    toggleCategoryMenu(state){
      this.categoryHidingState = state
      this.hidingBannerName = ''
    },
    openHidingBtn() {
      this.hidingBtnOpenedState = true
    },
    closeHidingBtn() {
      this.hidingBtnOpenedState = false
    },
    shiftLeft() {
      this.shiftState = true
    },
    shiftRight() {
      this.shiftState = false
    },
    doOpenModalServerInfo(name, desc) {
      this.sName = name
      this.sDesc = desc
      this.openModalServerInfo = true
    },
    doOpenDeleteModal(DeleteState, dto) {
      this.DeleteDto = dto
      this.openDeleteModal = true
      this.DeleteState = DeleteState
    },
    doOpenPrivilegeModal(contributor_id, contributor_email) {
      this.contributor.id = contributor_id
      this.contributor.email = contributor_email
      this.openPrivilegeModal = true
    },
    test1(asdasd) {
      this.openModalAddServer = true
      console.log(asdasd)
      this.title = asdasd
    },
    doOpenAddCategoryModal(addCategoryState, addCategoryDto) {
      this.AddCategoryState = addCategoryState
      this.AddCategoryDto = addCategoryDto
      this.openAddCategoryModal = true
    }
  },
  components: {
    TerminalTabBar,
    LocalTerminalButton,
    ExitTerminalButton,
    ProjectWhiteBoardButton,
    ModalProjectSelect,
    ProjectSelection,
    LeftSideOrgBanner,
    RemoteDirectoryLeftExpandingMenu,
    ModalSettings,
    TerminalWindow,
    ModalPrivileges,
    ModalAddContributor,
    ContributorButton,
    // LeftExpandingMenu,
    // NewProjectButton,
    // OrganizationName,
    PathBar,
    // NewServerButton,
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
:root{
  --ModeTextColor: #FFFFFF;
  --ModeModalBackgroundColor: #242424;
  --ModeInputColor: #2f2f2f;
  --ModeInputInnerTextColor: #FFFFFF;
  --ModeHidingBannerBackgroundColor: #282828;
  --ModeLeftExpandingHover: #313131;
  --ModeBtnColor: #5e5e5e;
  --ModeServerListItemBtnColor: #EFF0F4;
  --ModeTopBarBtnHover: #545454;
  --ModeLevel1Color: #383838;
  --ModeLevel2Color: #2C2C2C;
}
.userBtnWrapper {
  position: fixed;
  display: flex;
  align-items: center;
  width: 23px;
  height: 22px;
  text-align: center;
  border-radius: 0.2rem;
  cursor: pointer;
}

.userBtnWrapper:hover {
  background-color: var(--ModeTopBarBtnHover);
}

.user-profile{
  right: 3rem;
}

.setting{
  right: 1rem;
}

.container {
  overflow: hidden;
  position: relative;
  height: 100%;
}

.leftsideframe {
  position: absolute;
  top: 0;
  width: 13rem;
  height: 100%;
  background-color: #1e1e1e;
  transition: width 0.3s;
}
.leftsideframe-light {
  position: absolute;
  top: 0;
  left: 0;
  width: 13rem;
  height: 100%;
  background-color: #FFFFFF;
  transition: width 0.3s;
}

.leftsideframe-term {
  position: absolute;
  top: 0;
  left: 0;
  width: 13rem;
  height: 100%;
  background-color: #252525;
  animation: gradient 0.7s ease forwards;
}

.leftsideframe-shifted {
  position: absolute;
  top: 0;
  width: 0rem;
  border-right: 1px solid rgba(26, 26, 26, 0.11);
  transition: width 0.3s;
}

.hidingBtnWrapper {
  width: 18px;
  height: 18px;
  margin-top: 0.7rem;
  margin-left: 11.7rem;
  text-align: center;
  border-radius: 0.2rem;
  position: fixed;
  cursor: pointer;
}

.hidingBtnWrapper-top {
  width: 18px;
  height: 18px;
  margin-left: 4.5rem;
  text-align: center;
  border-radius: 0.2rem;
  position: fixed;
  cursor: pointer;
}

.hidingBtnWrapper-top:hover {
  background-color: var(--ModeTopBarBtnHover);
}

.hidingBtnWrapper:hover {
  background-color: var(--ModeTopBarBtnHover);
}

hr {
  margin-top: 2.5rem;
  margin-bottom: 0;
  border: solid 0.001rem #c7c7c728;
  border-bottom: 0;
}

.topbarframe {
  position: absolute;
  display: flex;
  align-items: center;
  left: 13rem;
  width: calc(100% - 13rem);
  height: 2.5rem;
  background-color: #1e1e1e;
  border-left: solid 0.001rem rgba(0, 0, 0, 0.2);
  transition-property: left, width;
  transition-duration: 0.3s, 0.3s;
}

.topbarframe-light{
  position: absolute;
  left: 13rem;
  width: calc(100% - 13rem);
  height: 2.5rem;
  background-color: #FFFFFF;
  border-left: solid 0.001rem #c7c7c728;
  transition-property: left, width;
  transition-duration: 0.3s, 0.3s;
}

.topbarframe-term {
  position: absolute;
  left: 13rem;
  width: calc(100% - 13rem);
  height: 2.5rem;
  border-left: solid 0.001rem #000000;
  animation: gradient 0.7s ease forwards;
}

.topbarframe-shifted {
  position: absolute;
  top: 0;
  left: 0rem;
  width: calc(100% - 0rem);
  height: 2.5rem;
  border-left: solid 0.001rem #c7c7c728;
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

.initialProjectWindow{
  color: var(--ModeTextColor);
  text-align: center;
  margin-top: 20rem;
}

.contentframe {
  position: absolute;
  top: 2.5rem;
  left: 13rem;
  width: calc(100% - 13rem);
  height: calc(100% - 2.5rem);
  background-color: #1e1e1e;
  border-left: solid 0.001rem rgba(0, 0, 0, 0.2);
  border-top: solid 1px rgba(67, 67, 67, 0.12);
  transition-property: left, width;
  transition-duration: 0.3s, 0.3s;
}

.contentframe-light{
  position: absolute;
  top: 2.5rem;
  left: 13rem;
  width: calc(100% - 13rem);
  height: calc(100% - 2.5rem);
  background-color: #f9f9f9;
  border-top: solid 0.001rem #c7c7c728;
  border-left: solid 0.001rem #c7c7c728;
}

.contentframe-shifted {
  position: absolute;
  top: 2.5rem;
  left: 0rem;
  width: 100%;
  height: calc(100% - 2.5rem);
}

.content {
  position: absolute;
  left: 0;
  width: calc(100% - 13rem);
  padding-top: 4rem;
}

.categoryFloating {
  position: fixed;
  right: 0;
  width: 13rem;
  height: 20rem;
  margin-top: 3rem;
  margin-right: 1rem;
  background-color: #282828;
  border-radius: 1rem;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  transition-property: width, height, margin-top;
  transition-duration: 0.4s, 0.4s, 0.3s;
}
.categoryFloating-hided{
  width: 0rem;
  height: 0rem;
  margin-top: 4rem;
}
.categoryFloating-light{
  background-color: #FFFFFF;
}

.contribut {
  padding-left: 5%;
  line-height: 620%;
  color: white;
}

.hiddenCategory{
  background-color: #FFFFFF;
  position: fixed;
  bottom: 3rem;
  right: 0;
  width: 40px;
  height: 40px;
  text-align: center;
  border-top-left-radius: 0.3rem;
  border-bottom-left-radius: 0.3rem;
  padding-right: 5px;
}

.categoryBanner{
  cursor: pointer;
  display: flex;
  position: absolute;
  text-align: center;
  width: 30px;
  height: 30px;
  padding-left: 10px;
  padding-top: 10px;
  margin-top: 4rem;
  right: 0;
  color: var(--ModeTextColor);
  background-color: var(--ModeHidingBannerBackgroundColor);
  border-top-left-radius: 0.4rem;
  border-bottom-left-radius: 0.4rem;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  transition: width 0.3s;
}
.categoryBanner:hover{
  width: 100px;
}
</style>