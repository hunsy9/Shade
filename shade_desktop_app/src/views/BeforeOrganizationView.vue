<template>
  <div class="container">

    <div class="leftsideframe">
      <AppInfo/>
<!--      <NewOrganizationButton v-if="isLogin ==true" @openModalAddOrganization="openModalAddOrganization = true"/>-->
    </div>

    <div class="topbarframe">
      <img class="user" v-if="isLogin==true" src="@/assets/topBarFrame/settings.png" width="20" @click="openModalSettings = true"/>
      <img class="user" src="@/assets/topBarFrame/user-profile.png" width="20" @click="clickUserProfile(isLogin)"/>
    </div>

    <div class="contentframe">

      <template v-if="isLogin ==true">
        <RefetchBtn :RefetchState="RefetchState"/>
      </template>
      <template v-if="isLogin ==true && hasOrganization == true">
        <QuickAccess/>
        <OrganizationListItem @openInOganization="openInOganization" @openAddOrganization="openAddOrganization"/>
      </template>

      <div class="nocontent" v-else>
        Please Sign Up and Create New Organization !
      </div>
    </div>
    <ModalLogin v-if="openModalLogin" @closeAppLoginModal="openModalLogin = false" @openAppSignUpModal="openAppModalSignUp = true"/>
    <ModalSignUp v-if="openAppModalSignUp" @closeAppModalSignUp="openAppModalSignUp = false" @openModalLogin="openModalLogin = true"/>
    <ModalAddOrganization v-if="openModalAddOrganization" @closeModalAddOrganization="openModalAddOrganization = false"/>
    <ModalLogOut :openModalLogOut="openModalLogOut" v-if="openModalLogOut" @closeModalLogOut="openModalLogOut = false"/>
    <ModalSettings v-if="openModalSettings" @closeSettingsModal="openModalSettings = false"/>
  </div>
</template>

<script>
import AppInfo from '@/components/common/leftBar/AppInfo.vue'
import OrganizationListItem from '@/components/beforeOrganization/OrganizationListItem.vue'
import RefetchBtn from '@/components/common/RefetchBtn.vue'
import QuickAccess from "@/components/beforeOrganization/quickAccessComponent/QuickAccess";

import ModalLogin from '@/components/beforeOrganization/beforemodal/ModalLogin.vue'
import ModalSignUp from '@/components/beforeOrganization/beforemodal/login/ModalSignUp.vue'
import ModalAddOrganization from '@/components/beforeOrganization/beforemodal/ModalAddOrganization.vue'
import ModalLogOut from '@/components/beforeOrganization/beforemodal/ModalLogOut.vue'
import ModalSettings from "@/components/common/topBar/settings/ModalSettings";

import {createNamespacedHelpers, mapGetters} from 'vuex'
import {RefetchState} from "@/store/inOrganization";
const { mapMutations, mapActions } = createNamespacedHelpers('inOrganization')
const { mapState } = createNamespacedHelpers('login')

export default {
  name: 'BeforeLoginView',
  data() {
    return {
      hasOrganization: true,
      organizationNames: [],
      openModalLogin: false,
      openAppModalSignUp: false,
      openModalAddOrganization: false,
      RefetchState: RefetchState.BEFOREORG,
      openModalSettings: false,
      openModalLogOut: false,
    }
  },
  computed: {
    ...mapGetters('inOrganization',['getTheme']),
    ...mapState({
      isLogin: state => state.isLogin,
    }),
  },
  methods: {
    clickUserProfile(isLogin){
      if(isLogin){
        this.openModalLogOut = true
        return
      }
      this.openModalLogin = true
    },
    openInOganization(org) {
      this.getProjects(org)
      this.setOrg(org)
    },
    openAddOrganization(){
      this.openModalAddOrganization = true
    },
    ...mapMutations(['setOrg']),
    ...mapActions(['getProjects'])
  },
  components: {
    QuickAccess,
    AppInfo,
    // NewOrganizationButton,
    OrganizationListItem,
    ModalLogin,
    ModalSignUp,
    ModalAddOrganization,
    RefetchBtn,
    ModalLogOut,
    ModalSettings
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
  width: 12.5rem;
  height: 100%;
  background-color: #1b1b1b;
}

.topbarframe {
  position: absolute;
  top: 0;
  left: 12.5rem;
  width: calc(100% - 12.5rem);
  height: 3rem;
  background-color: #1b1b1b;
  border-left: solid 0.001rem #000000;
}

.contentframe {
  position: absolute;
  top: 3rem;
  left: 12.5rem;
  width: calc(100% - 12.5rem);
  height: calc(100% - 3rem);
  background-color: #1b1b1b;
  color: white;
  border-left: solid 0.001rem #000000;
}
.nocontent{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.user{
  color: white;
  float: right;
  position: relative;
  top: calc(50% - 0.6rem);
  margin-right: 1rem;
  width: auto;
  height: 1em;
  border: none;
  cursor: pointer;
}
</style>